package com.hh.contractstestsadmin.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Mapper {

  protected static OffsetDateTime convertDateTime(LocalDateTime localDateTime){
    return localDateTime.atOffset(ZoneOffset.UTC);
  }
}
