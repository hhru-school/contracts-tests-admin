package com.hh.contractstestsadmin.dao.minio.mapper;

public class Util {

  /**
   * Removes the consumer/producer artefact name prefix, e.g 'expectation/' prefix from 'expectation/jlogic'('<consumer_aftefact_name>/<service_name>)
   * String, just the service name only is left
   */
  public static String removeArtefactNamePrefix(String serviceNameWithPrefix, String artefactName, String separator) {
    return serviceNameWithPrefix.replaceFirst(artefactName + separator, "");
  }

  /**
   * Remove json or yaml file from the path
   *
   * @param itemPath e.g expectation/subscription/01.02.json
   * @return e.g expectation/subscription
   */
  public static String removeArtefactFilePostfix(String itemPath) {
    return itemPath.replaceFirst("(^[^\\/]*\\/[^\\/]*)(.*)", "$1");
  }
}
