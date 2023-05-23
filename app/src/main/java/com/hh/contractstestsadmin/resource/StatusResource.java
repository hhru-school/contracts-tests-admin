package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.api.ErrorMessageDto;
import com.hh.contractstestsadmin.dto.api.StandInfoDto;
import com.hh.contractstestsadmin.dto.api.StandStatusDto;
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
      response = StandInfoDto.class,
      responseContainer = "List")
  @Path("stands")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStands(@QueryParam("search") String search) {
    try {
      return Response.ok(statusService.getStands(search)).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

  @ApiOperation(
      value = "Get stand status information",
      response = StandStatusDto.class)
  @Path("/stands/{standName}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStatus(@PathParam("standName") String standName) {
    try {
      return Response.ok(statusService.getStatus(standName)).build();
    } catch (StandNotFoundException exception) {
      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessageDto(exception.getMessage())).build();
    } catch (Exception exception) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessageDto(exception.getMessage())).build();
    }
  }

}
