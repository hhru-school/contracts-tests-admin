package com.hh.contractstestsadmin.dao.minio.mapper;

public class Util {

  /**
   * The artefact path is like 'expectation/jlogic/01.01.json' or 'schema/jlogic/01.01.json'
   * The pattern match this path and divides it into groups:
   * 1 - expectation
   * 2 - /
   * 3 - jlogic
   * 4 - /
   * 5 - 01.01
   * 6 - .json
   */
  public static String ARTEFACT_PATH_PATTERN = "(^[^\\/]*)(\\/)([^\\/]*)(\\/)(.*)(\\.json|\\.yaml)";

  /**
   * Extracts the service name from the specified artefact path
   *
   * @param artefactPath <artefact name>/<service name>/<artefact file>, e.g expectation/jlogic/00.01.01.json
   * @return the service name, e.g jlogic
   */
  public static String extractServiceName(String artefactPath) {
    return artefactPath.replaceFirst(ARTEFACT_PATH_PATTERN, "$3");
  }

  /**
   * Extracts <artefact name>/<service name> string (the artefact key) from the specified artefactPath
   *
   * @param artefactPath <artefact name>/<service name>/<artefact file>, e.g expectation/jlogic/00.01.01.json
   * @return e.g expectation/jlogic
   */
  public static String extractArtefactKey(String artefactPath) {
    return artefactPath.replaceFirst(ARTEFACT_PATH_PATTERN, "$1$2$3");
  }


  /**
   * Extracts the artefact version from the specified artefactPath
   *
   * @param artefactPath <artefact name>/<service name>/<artefact file>, e.g expectation/jlogic/00.01.01.json
   * @return e.g 00.01.01
   */
  public static String extractArtefactVersion(String artefactPath) {
    return artefactPath.replaceFirst(ARTEFACT_PATH_PATTERN, "$5");
  }

}
