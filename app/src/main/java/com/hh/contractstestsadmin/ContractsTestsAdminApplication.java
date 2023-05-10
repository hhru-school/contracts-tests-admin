package com.hh.contractstestsadmin;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContractsTestsAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContractsTestsAdminApplication.class, args);
  }

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

}
