package com.hh.contractstestsadmin.dao.minio.mapper;

import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractArtefactVersion;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractServiceName;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.Service;
import io.minio.messages.Item;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

public class ServiceMapper {

  private Properties minioProperties;
  private ConsumerDataMapper consumerDataMapper;
  private ProducerDataMapper producerDataMapper;

  public ServiceMapper(Properties minioProperties, ConsumerDataMapper consumerDataMapper, ProducerDataMapper producerDataMapper) {
    this.minioProperties = minioProperties;
    this.consumerDataMapper = consumerDataMapper;
    this.producerDataMapper = producerDataMapper;
  }

  /**
   * Maps Entry to Service
   *
   * @param artefactEntry with Key like 'expectation/jlogic' where 'expectation' is 'consumer.artefact.type'/'producer.artefact.type' from
   *                      minio.properties and jlogic is a service name
   *                      and Value is minio Item represents an object in the storage
   * @param artefactUrl
   * @return the mapped Service
   * @throws StandsDaoException
   */
  public Service map(Map.Entry<String, Item> artefactEntry, String artefactUrl) throws StandsDaoException {
    String consumerArtefactType = minioProperties.getProperty("minio.consumer.artefact.type");
    String producerArtefactType = minioProperties.getProperty("minio.producer.artefact.type");

    String artefactKey = artefactEntry.getKey();
    Item artefactItem = artefactEntry.getValue();
    String artefactPath = artefactItem.objectName();

    if (artefactKey.contains(consumerArtefactType)) {
      String serviceName = extractServiceName(artefactPath);
      return new Service(serviceName, extractArtefactVersion(artefactPath), consumerDataMapper.map(artefactItem, artefactUrl));

    } else if (artefactKey.contains(producerArtefactType)) {
      String serviceName = extractServiceName(artefactPath);
      return new Service(serviceName, extractArtefactVersion(artefactPath), producerDataMapper.map(artefactItem, artefactUrl));

    } else {
      throw new StandsDaoException("Bucket structure is different from the expected one for " + artefactPath + " service");
    }
  }

}
