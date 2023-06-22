package com.hh.contractstestsadmin.dao.minio.mapper;

import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractArtefactKey;
import static com.hh.contractstestsadmin.dao.minio.mapper.Util.extractServiceName;
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
import org.apache.maven.artifact.versioning.ComparableVersion;

public class ServiceListMapper {

  private Properties minioProperties;

  private ServiceMapper serviceMapper;

  public ServiceListMapper(Properties minioProperties, ServiceMapper serviceMapper) {
    this.minioProperties = minioProperties;
    this.serviceMapper = serviceMapper;
  }

  @NotNull
  public List<Service> map(Collection<Item> standArtefacts, String standName) throws StandsDaoException {
    Map<String, Item> artefactsMap = getArtefactsMap(standArtefacts);

    return artefactsMap.entrySet()
        .stream()
        .collect(Collectors.toMap(
            artefact -> extractServiceName(getArtefactPath(artefact)),
            artefact -> {
              try {
                String artefactPath = standName + "/" + getArtefactPath(artefact);
                return serviceMapper.map(artefact, artefactPath);
              } catch (StandsDaoException e) {
                throw new RuntimeException(e);
              }
            },
            (prevService, currentService) -> {
              try {
                return merge(prevService, currentService);
              } catch (StandsDaoException e) {
                throw new RuntimeException(e);
              }
            }
        ))
        .values()
        .stream()
        .toList();
  }

  /**
   * The method merge 2 services into one in case the versions of the services are the same. Otherwise, the method returns the service has a newer
   * version.
   *
   * @param initService       Initial service for merging.
   * @param serviceToBeMerged The service to be merged.
   * @return The merged service or the initial service or the service to be merged
   * @throws StandsDaoException
   */
  private Service merge(Service initService, Service serviceToBeMerged) throws StandsDaoException {
    boolean initServiceIsConsumer = initService.getConsumerData().isPresent();
    boolean initServiceIsProducer = initService.getProducerData().isPresent();


    boolean initServiceHasNewerVersion = initServiceHasNewerVersion(initService, serviceToBeMerged);
    boolean initServiceHasTheSameVersion = initServiceHasTheSameVersion(initService, serviceToBeMerged);

    if (initServiceHasNewerVersion) {

      return initService;
    } else if (initServiceHasTheSameVersion) {

      if (initServiceIsConsumer) {

        initService.setProducerData(serviceToBeMerged.getProducerData().get());
        return initService;
      } else if (initServiceIsProducer) {

        initService.setConsumerData(serviceToBeMerged.getConsumerData().get());
        return initService;
      } else {

        throw new StandsDaoException("Impossible to merge consumer and producer data correctly for '" + initService.getName() + "' service");
      }

    } else {

      return serviceToBeMerged;
    }

  }

  /**
   * Returns a map with an artefact key, e.g 'expectation/jlogic'(<consumer_artefact_name>/<service_name>), and the artefact item info of the
   * particular service that is presented in the map as a consumer, or e.g 'schema/subscriptions'(<producer_artefact_name>/<service_name>) key and
   * the artefact item info of the particular service that is presented in the map as a producer.
   *
   * @param artefacts all items that represents service artefact's info
   * @return Map<String, Item> In case the service is presented as a consumer and as a producer, it will be placed in the map twice with 2
   * different keys:
   * 'exectation/<servicename>' and 'schema/<servicename>', and with 2 different values: consumer artefact item info and producer artefact
   * item info
   * @throws StandsDaoException
   */
  private Map<String, Item> getArtefactsMap(Collection<Item> artefacts) {
    return artefacts
        .stream()
        .collect(Collectors.toMap(item -> extractArtefactKey(item.objectName()), identity()));
  }

  private String getArtefactPath(Map.Entry<String, Item> artefactEntry) {
    return artefactEntry.getValue().objectName();
  }

  private boolean initServiceHasNewerVersion(Service initService, Service serviceToBeMerged) {
    ComparableVersion initServiceVersion = new ComparableVersion(initService.getVersion());
    ComparableVersion serviceToBeMergedVersion = new ComparableVersion(serviceToBeMerged.getVersion());
    int compareResult = initServiceVersion.compareTo(serviceToBeMergedVersion);
    return compareResult > 0;
  }

  private boolean initServiceHasTheSameVersion(Service initService, Service serviceToBeMerged) {
    ComparableVersion initServiceVersion = new ComparableVersion(initService.getVersion());
    ComparableVersion serviceToBeMergedVersion = new ComparableVersion(serviceToBeMerged.getVersion());
    int compareResult = initServiceVersion.compareTo(serviceToBeMergedVersion);
    return compareResult == 0;
  }
}
