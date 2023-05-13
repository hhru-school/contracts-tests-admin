package com.hh.contractstestsadmin.dao.minio.mapper;

import static com.hh.contractstestsadmin.dao.minio.mapper.Util.removeArtefactFilePostfix;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.removeArtefactNamePrefix;
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
  public List<Service> map(Iterable<Result<Item>> standItems) throws StandsDaoException {
    String expectation = minioProperties.getProperty("minio.consumer.artefact.name");
    String schema = minioProperties.getProperty("minio.producer.artefact.name");
    String separator = minioProperties.getProperty("minio.object.name.separator");

    Map<String, Item> lastModifiedArtefacts = getLastModifiedArtefacts(standItems);
    return lastModifiedArtefacts.entrySet()
        .stream()
        .collect(Collectors.toMap(
            entry -> removeArtefactNamePrefix(
                removeArtefactNamePrefix((String) entry.getKey(), expectation, separator), schema, separator),
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
                throw new RuntimeException("Impossible to merge consumer and producer data correctly for '" + prevService.getName() + "' service");
              }
              return prevService;
            }
        ))
        .values()
        .stream()
        .toList();
  }

  /**
   * Returns a map with e.g 'expectation/jlogic'(<consumer_artefact_name>/<service_name>) key and the artefact item info of the particular service
   * that is presented in the collection as a consumer or e.g 'schema/subscriptions'(<producer_artefact_name>/<service_name>) key and the artefact
   * item info of the particular service that is presented in the collection as a producer.
   *
   * @param standItems all items that represents different versions of artefacts. It means to one service as a consumer the collection can contain
   *                   several artefacts of different versions.
   * @return a map that will contain only one last modified artefact for a particular service as a consumer/producer. In case the service is
   * presented as a consumer and as a producer, it will be placed in the map twice with 2 different keys:
   * 'exectation/<servicename>' and 'schema/<servicename>', and with 2 different values: consumer artefact item info and producer artefact
   * item info
   * @throws StandsDaoException
   */
  @NotNull
  private static Map<String, Item> getLastModifiedArtefacts(Iterable<Result<Item>> standItems) throws StandsDaoException {
    Map<String, Item> itemMap = new HashMap<>();
    try {

      for (Result<Item> itemResult : standItems) {
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
