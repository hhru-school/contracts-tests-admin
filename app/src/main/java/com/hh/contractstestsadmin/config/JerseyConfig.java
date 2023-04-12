package com.hh.contractstestsadmin.config;

import com.hh.contractstestsadmin.resource.HealthResource;
import com.hh.contractstestsadmin.resource.StatusResource;
import com.hh.contractstestsadmin.resource.ValidationResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
  public JerseyConfig() {
    register(HealthResource.class);
    register(StatusResource.class);
    register(ValidationResource.class);
  }
}
