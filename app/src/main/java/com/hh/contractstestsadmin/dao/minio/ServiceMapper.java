package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.Service;
import io.minio.messages.Item;
import java.util.Map;

public class ServiceMapper {

  public static Service map(Map.Entry<String, Item> entry) throws StandsDaoException {
    if (entry.getKey().contains("expectation")) {
      return new Service(entry.getKey(), entry.getKey(), ConsumerDataMapper.map(entry.getValue()));
    } else if (entry.getKey().contains("schema")) {
      return new Service(entry.getKey(), entry.getKey(), ProducerDataMapper.map(entry.getValue()));
    } else {
      throw new StandsDaoException("Bucket structure is different from the expected one");
    }
  }

}
