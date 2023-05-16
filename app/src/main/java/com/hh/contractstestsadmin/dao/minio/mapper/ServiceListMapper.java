package com.hh.contractstestsadmin.dao.minio.mapper;

import static com.hh.contractstestsadmin.dao.minio.mapper.Util.removeArtefactNamePrefix;
import com.hh.contractstestsadmin.exception.StandsDaoException;
import com.hh.contractstestsadmin.model.artefacts.Service;
import io.minio.messages.Item;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static java.util.function.UnaryOperator.identity;
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
  public List<Service> map(Collection<Item> standItems) throws StandsDaoException {
    Map<String, Item> artefactsMap = getArtefactsMap(standItems);

    return artefactsMap.entrySet()
        .stream()
        .collect(Collectors.toMap(
            entry -> extractServiceNameFromArtefactKey(entry.getKey()),
            entry -> {
              try {
                return serviceMapper.map(entry);
              } catch (StandsDaoException e) {
                throw new RuntimeException(e);
              }
            },
            (prevService, currentService) -> merge(prevService, currentService)
        ))
        .values()
        .stream()
        .toList();
  }

  private Service merge(Service initService, Service serviceToBeMerged) {
    try {
      boolean initServiceIsConsumer = initService.getConsumerData().isPresent();
      boolean initServiceIsProducer = initService.getProducerData().isPresent();

      if (initServiceIsConsumer) {
        initService.setProducerData(serviceToBeMerged.getProducerData().get());
      } else if (initServiceIsProducer) {
        initService.setConsumerData(serviceToBeMerged.getConsumerData().get());
      } else {
        throw new RuntimeException("Impossible to merge consumer and producer data correctly for '" + initService.getName() + "' service");
      }
    } catch (StandsDaoException e) {
      throw new RuntimeException(e);
    }

    return initService;
  }

  /**
   * Returns a map with an artefact key, e.g 'expectation/jlogic'(<consumer_artefact_name>/<service_name>), and the artefact item info of the
   * particular service that is presented in the map as a consumer, or e.g 'schema/subscriptions'(<producer_artefact_name>/<service_name>) key and
   * the artefact item info of the particular service that is presented in the map as a producer.
   *
   * @param standItems all items that represents service artefact's info
   * @return Map<String, Item> In case the service is presented as a consumer and as a producer, it will be placed in the map twice with 2
   * different keys:
   * 'exectation/<servicename>' and 'schema/<servicename>', and with 2 different values: consumer artefact item info and producer artefact
   * item info
   * @throws StandsDaoException
   */
  private Map<String, Item> getArtefactsMap(Collection<Item> standItems) {
    return standItems
        .stream()
        .collect(Collectors.toMap(item -> Util.removeArtefactFilePostfix(((Item) item).objectName()), identity()));
  }

  /**
   * Converts the artefact map key that is presented as <consumer_artefact_name>/<service_name>(e.g 'expectation/jlogic') or
   * as <producer_artefact_name>/<service_name>(e.g schema/subscriptions) to the service name.
   *
   * @param artefactKey 'expectation/jlogic' or 'schema/subcsriptions'
   * @return the service name, e.g 'jlogic'
   */
  private String extractServiceNameFromArtefactKey(String artefactKey) {
    String expectation = minioProperties.getProperty("minio.consumer.artefact.name");
    String schema = minioProperties.getProperty("minio.producer.artefact.name");
    String separator = minioProperties.getProperty("minio.object.name.separator");

    return removeArtefactNamePrefix(
        removeArtefactNamePrefix(artefactKey, expectation, separator), schema, separator);
  }
}
