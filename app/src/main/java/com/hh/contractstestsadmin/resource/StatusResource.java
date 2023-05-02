package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.ServicesContainerDto;
import com.hh.contractstestsadmin.dto.StandsContainerDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.service.StatusService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api
@Path("api")
public class StatusResource {

  private final StatusService statusService;

  @Inject
  public StatusResource(StatusService statusService) {
    this.statusService = statusService;
  }

  @ApiOperation(
      value = "Get list with stands",
      response = StandsContainerDto.class)
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

  @ApiOperation(
      value = "Get services related to the stand",
      response = ServicesContainerDto.class)
  @Path("/stands/{standName}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStatus(
      @PathParam("standName") String standName,
      @QueryParam("historySizeLimit") Long historySizeLimit) {
    try {
      return Response.ok(statusService.getStatus(standName, historySizeLimit)).build();
    } catch (StandNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }
  }

}
