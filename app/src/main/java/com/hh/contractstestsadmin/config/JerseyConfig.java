package com.hh.contractstestsadmin.config;

import com.hh.contractstestsadmin.resource.FileResource;
import com.hh.contractstestsadmin.resource.HealthResource;
import com.hh.contractstestsadmin.resource.StatusResource;
import com.hh.contractstestsadmin.resource.ValidationResource;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
  public JerseyConfig() {
    register(HealthResource.class);
    register(StatusResource.class);
    register(FileResource.class);
    register(ValidationResource.class);
    register(ApiListingResource.class);
    register(SwaggerSerializers.class);
  }

}
