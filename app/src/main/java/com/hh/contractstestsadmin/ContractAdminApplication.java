package com.hh.contractstestsadmin;


import com.hh.contractstestsadmin.config.JerseyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ JerseyConfig.class })
public class ContractAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractAdminApplication.class, args);
    }

}
