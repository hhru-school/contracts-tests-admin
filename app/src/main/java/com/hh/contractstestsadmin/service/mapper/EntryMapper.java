package com.hh.contractstestsadmin.service.mapper;

import com.hh.contractstestsadmin.dto.api.EntryDto;
import com.hh.contractstestsadmin.model.Entry;
import java.util.List;

public class EntryMapper {

  public static EntryDto map(Entry entry) {
    return new EntryDto(entry.getKey(), entry.getValue());
  }

  public static List<EntryDto> mapList(List<Entry> entries) {
    return entries.stream()
        .map(EntryMapper::map)
        .toList();
  }
}
