package com.hh.contractstestsadmin.dao.minio;

import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.ConsumerData;
import io.minio.messages.Item;
import java.net.MalformedURLException;
import java.net.URL;

public class ConsumerDataMapper {

  public static ConsumerData map(Item item) throws StandsDaoException {
    return new ConsumerData(getURL(item), item.lastModified().toLocalDateTime());
  }

  private static URL getURL(Item item) throws StandsDaoException {
    try {
      return new URL("http://example.com");
    } catch (MalformedURLException e) {
      throw new StandsDaoException(e);
    }
  }
}
