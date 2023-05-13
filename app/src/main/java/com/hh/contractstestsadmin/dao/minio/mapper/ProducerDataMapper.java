package com.hh.contractstestsadmin.dao.minio.mapper;

import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.ProducerData;
import io.minio.messages.Item;
import java.net.MalformedURLException;
import java.net.URL;

public class ProducerDataMapper {

  public ProducerData map(Item item) throws StandsDaoException {
    return new ProducerData(getURL(item), item.lastModified().toLocalDateTime());
  }

  private URL getURL(Item item) throws StandsDaoException {
    try {
      return new URL("http://example.com");
    } catch (MalformedURLException e) {
      throw new StandsDaoException(e);
    }
  }
}
