package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.Service;
import io.minio.Result;
import io.minio.messages.Item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

public class ServiceListMapper {

  @NotNull
  public static List<Service> map(Iterable<Result<Item>> bucketItems) throws StandsDaoException {
    Map<String, Service> mappedServices = new HashMap<>();
    try {
      for (Result<Item> itemResult : bucketItems) {
        Item item = itemResult.get();
        String currentService = ServiceMapper.map(item);
      }
    } catch (Exception e) {
      throw new StandsDaoException(e);
    }
    return Collections.emptyList();
  }
}
