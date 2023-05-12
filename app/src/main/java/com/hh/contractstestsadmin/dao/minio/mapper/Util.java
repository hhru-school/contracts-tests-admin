package com.hh.contractstestsadmin.dao.minio.mapper;

public class Util {

  /**
   * Removes e.g 'expectation/' prefix from 'expectation/jlogic' String, just to live the service name only
   */
  public static String removeArtefactTypePrefix(String serviceNameWithArtefactPrefix, String artefact, String separator) {
    return serviceNameWithArtefactPrefix.replaceFirst(artefact + separator, "");
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
