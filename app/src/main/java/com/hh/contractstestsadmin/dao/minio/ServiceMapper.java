package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.model.Service;
import io.minio.messages.Item;

public class ServiceMapper {

  public static String map(Item item) {
    return item.toString();
  }
}
