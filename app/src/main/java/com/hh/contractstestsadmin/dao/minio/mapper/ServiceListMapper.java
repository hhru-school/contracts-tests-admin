package com.hh.contractstestsadmin.dao.minio.mapper;

import static com.hh.contractstestsadmin.dao.minio.mapper.Util.removeArtefactFilePostfix;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.removeArtefactTypePrefix;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.Service;
import io.minio.Result;
import io.minio.messages.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.Optional.ofNullable;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

public class ServiceListMapper {

  private Properties minioProperties;

  private ServiceMapper serviceMapper;

  public ServiceListMapper(Properties minioProperties, ServiceMapper serviceMapper) {
    this.minioProperties = minioProperties;
    this.serviceMapper = serviceMapper;
  }

  @NotNull
  public List<Service> map(Iterable<Result<Item>> bucketItems) throws StandsDaoException {
    String expectation = minioProperties.getProperty("minio.consumer.artefact.name");
    String schema = minioProperties.getProperty("minio.producer.artefact.name");
    String separator = minioProperties.getProperty("minio.object.name.separator");

    Map<String, Item> lastModifiedArtifacts = getLastModifiedArtefactVersions(bucketItems);
    return lastModifiedArtifacts.entrySet()
        .stream()
        .collect(Collectors.toMap(
            entry -> removeArtefactTypePrefix(
                removeArtefactTypePrefix((String) entry.getKey(), expectation, separator), schema, separator),
            entry -> {
              try {
                return serviceMapper.map((Map.Entry<String, Item>) entry);
              } catch (StandsDaoException e) {
                throw new RuntimeException(e);
              }
            },
            (prevService, currentService) -> {
              if (prevService.getConsumerData().isPresent()) {
                try {
                  prevService.setProducerData(currentService.getProducerData().get());
                } catch (StandsDaoException e) {
                  throw new RuntimeException(e);
                }
              } else if (prevService.getProducerData().isPresent()) {
                try {
                  prevService.setConsumerData(currentService.getConsumerData().get());
                } catch (StandsDaoException e) {
                  throw new RuntimeException(e);
                }
              } else {
                throw new RuntimeException("Impossible to merge consumer and producer data correctly for service " + prevService.getName());
              }
              return prevService;
            }
        ))
        .values()
        .stream()
        .toList();
  }

  @NotNull
  private static Map<String, Item> getLastModifiedArtefactVersions(Iterable<Result<Item>> bucketItems) throws StandsDaoException {
    Map<String, Item> itemMap = new HashMap<>();
    try {

      for (Result<Item> itemResult : bucketItems) {
        Item currItem = itemResult.get();
        String serviceTypeNameKey = removeArtefactFilePostfix(currItem.objectName());
        Optional<Item> prevItemOptional = ofNullable(itemMap.putIfAbsent(serviceTypeNameKey, currItem));

        if (prevItemOptional.isPresent()) {
          Item prevItem = prevItemOptional.get();

          if (currItem.lastModified().isAfter(prevItem.lastModified())) {
            itemMap.put(serviceTypeNameKey, currItem);
          }
        }
      }

    } catch (Exception e) {
      throw new StandsDaoException(e);
    }

    return itemMap;
  }
}
