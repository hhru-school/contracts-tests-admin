package com.hh.contractstestsadmin.dao.minio.mapper;

import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractArtefactVersion;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractServiceName;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.Service;
import io.minio.messages.Item;
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
   * @param entry with Key like 'expectation/jlogic' where 'expectation' is 'consumer.artefact.name' from
   *              minio.properties and jlogic is a service name
   *              and Value is minio Item represents an object in the storage
   * @return the mapped Service
   * @throws StandsDaoException
   */
  public Service map(Map.Entry<String, Item> entry) throws StandsDaoException {
    String expectation = minioProperties.getProperty("minio.consumer.artefact.name");
    String schema = minioProperties.getProperty("minio.producer.artefact.name");

    String entryKey = entry.getKey();
    Item entryValue = entry.getValue();
    if (entryKey.contains(expectation)) {
      String serviceName = extractServiceName(entryValue.objectName());
      return new Service(serviceName, extractArtefactVersion(entryValue.objectName()), consumerDataMapper.map(entryValue));

    } else if (entryKey.contains(schema)) {
      String serviceName = extractServiceName(entryValue.objectName());
      return new Service(serviceName, extractArtefactVersion(entryValue.objectName()), producerDataMapper.map(entryValue));

    } else {
      throw new StandsDaoException("Bucket structure is different from the expected one");
    }
  }

}
