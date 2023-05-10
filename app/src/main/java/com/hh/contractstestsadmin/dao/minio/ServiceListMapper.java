package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.Service;
import io.minio.Result;
import io.minio.messages.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

public class ServiceListMapper {

  @NotNull
  public static List<Service> map(Iterable<Result<Item>> bucketItems) throws StandsDaoException {
    Map<String, Item> lastModifiedArtifacts = getLastModifiedArtifacts(bucketItems);
     return lastModifiedArtifacts.entrySet()
         .stream()
         .collect(Collectors.toMap(
             entry -> ((String)entry.getKey()).replace("expectation/", "").replace("schema/", ""),
             entry -> {
               try {
                 return ServiceMapper.map((Map.Entry<String, Item>) entry);
               } catch (StandsDaoException e) {
                 throw new RuntimeException(e);
               }
             },
             (prevService,currentService) -> {
               if (prevService.isConsumer()) {
                 try {
                   prevService.setProducerData(currentService.getProducerData().get());
                 } catch (StandsDaoException e) {
                   throw new RuntimeException(e);
                 }
               } else {
                 try {
                   prevService.setConsumerData(currentService.getConsumerData().get());
                 } catch (StandsDaoException e) {
                   throw new RuntimeException(e);
                 }
               }
               return prevService;
             }
         ))
         .values()
         .stream()
         .toList();
  }

  // remove json or yaml file from the path
  // expectation/subscription/01.02.json -> expectation/subscription
  private static String removeArtifactInfo(String itemPath) {
    return itemPath.replaceFirst("(^[^\\/]*\\/[^\\/]*)(.*)", "$1");
  }

  @NotNull
  private static Map<String, Item> getLastModifiedArtifacts(Iterable<Result<Item>> bucketItems) throws StandsDaoException {
    Map<String, Item> itemMap = new HashMap<>();
    try {
      for (Result<Item> itemResult : bucketItems) {
        Item currItem = itemResult.get();
        String serviceTypeNameKey = removeArtifactInfo(currItem.objectName());
        Optional<Item> prevItemOptional = Optional.ofNullable(itemMap.putIfAbsent(serviceTypeNameKey, currItem));
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
