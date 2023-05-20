package com.hh.contractstestsadmin.dao.minio.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class UtilTest {

  @Test
  void extractServiceName() {
    assertEquals("jlogic", Util.extractServiceName("expectation/jlogic/00.01.01.json"));
    assertEquals("jlogic", Util.extractServiceName("schema/jlogic/00.01.01.yaml"));
    assertEquals("jlogic", Util.extractServiceName("expectation/jlogic/00.01.01-SNAPSHOT.json"));
    assertEquals("jlogic", Util.extractServiceName("schema/jlogic/00.01.01-SNAPSHOT.yaml"));
  }

  @Test
  void extractArtefactKey() {
    assertEquals("expectation/jlogic", Util.extractArtefactKey("expectation/jlogic/00.01.01.json"));
    assertEquals("schema/jlogic", Util.extractArtefactKey("schema/jlogic/00.01.01.yaml"));
    assertEquals("expectation/jlogic", Util.extractArtefactKey("expectation/jlogic/00.01.01-SNAPSHOT.json"));
    assertEquals("schema/jlogic", Util.extractArtefactKey("schema/jlogic/00.01.01-SNAPSHOT.yaml"));
  }

  @Test
  void extractArtefactVersion() {
    assertEquals("00.01.01", Util.extractArtefactVersion("expectation/jlogic/00.01.01.json"));
    assertEquals("00.01.01", Util.extractArtefactVersion("schema/jlogic/00.01.01.yaml"));
    assertEquals("00.01.01-SNAPSHOT", Util.extractArtefactVersion("expectation/jlogic/00.01.01-SNAPSHOT.json"));
    assertEquals("00.01.01-SNAPSHOT", Util.extractArtefactVersion("schema/jlogic/00.01.01-SNAPSHOT.yaml"));
  }

}
