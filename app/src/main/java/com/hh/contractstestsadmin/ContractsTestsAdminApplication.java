package com.hh.contractstestsadmin;


import com.hh.contractstestsadmin.config.JerseyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ JerseyConfig.class })
public class ContractsTestsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractsTestsAdminApplication.class, args);
    }

}
