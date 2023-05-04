package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.model.Service;
import io.minio.Result;
import io.minio.messages.Item;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;

public class ServiceListMapper {

  @NotNull
  public static List<Service> map(Iterable<Result<Item>> bucketItems) {
    return Collections.emptyList();
  }
}
