package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.ErrorMessageDto;
import com.hh.contractstestsadmin.exception.StandNotFoundException;
import com.hh.contractstestsadmin.service.StatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.ws.rs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api
@Path("api")
public class FileResource {

  private final StatusService statusService;
  public static final Logger LOG = LoggerFactory.getLogger(FileResource.class);

  @Inject
  public FileResource(StatusService statusService) {
    this.statusService = statusService;
  }

  @ApiOperation(
      value = "Check health")
  @GET
  @Path("/stands/{standName}/file/")
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response getSharedFileLink(@PathParam("standName") String standName, @QueryParam("fileLink") String encodeFilePath) {
    try {
      return Response.ok(statusService.getSharedFileLink(standName, encodeFilePath)).build();
    } catch (StandNotFoundException exception) {
      LOG.warn("not found entity by standName {} and encodeFilePath {}", standName, encodeFilePath, exception);
      return Response.status(Response.Status.NOT_FOUND)
          .entity(new ErrorMessageDto(exception.getMessage()))
          .build();
    } catch (Exception exception) {
      LOG.error("internal error by standName {} and  encodeFilePath {}", standName, encodeFilePath, exception);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorMessageDto(exception.getMessage()))
          .build();
    }
  }
}
