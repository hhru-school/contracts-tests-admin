package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.service.StatusService;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api")
public class StatusResource {

  private final StatusService statusService;

  @Inject
  public StatusResource(StatusService statusService) {
    this.statusService = statusService;
  }

  @Path("stands")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStands() {
    try {
      return Response.ok(statusService.getStands()).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }
  }

  @Path("services")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getServices(@NotNull @QueryParam("standName") String standName) {
    try {
      return Response.ok(statusService.getServices(standName)).build();
    } catch (StandNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }
  }

}
