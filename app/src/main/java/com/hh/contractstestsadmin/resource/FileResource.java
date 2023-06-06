package com.hh.contractstestsadmin.resource;

import com.hh.contractstestsadmin.dto.api.ErrorMessageDto;
import com.hh.contractstestsadmin.dto.api.FileLinkDto;
import com.hh.contractstestsadmin.exception.IllegalFilePathException;
import com.hh.contractstestsadmin.exception.MinioClientException;
import com.hh.contractstestsadmin.service.StatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
      value = "get file link for download artefact",
      response = FileLinkDto.class
  )
  @GET
  @Path("/download-links")
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response getSharedFileLink(@NotNull @QueryParam("filePath") String encodeFilePath) {
    try {
      return Response.ok(statusService.getSharedFileLink(encodeFilePath)).build();
    } catch (MinioClientException exception) {
      LOG.warn("minio client responded with unsuccessful code {}", exception.getStatusCode(), exception);
      return Response.status(Response.Status.fromStatusCode(exception.getStatusCode()))
          .entity(new ErrorMessageDto(exception.getMessage()))
          .build();
    } catch (IllegalFilePathException e) {
      LOG.error("illegal argument encodeFilePath {}", encodeFilePath, e);
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessageDto(e.getMessage())).build();
    }
    catch (Exception exception) {
      LOG.error("internal error by encodeFilePath {}", encodeFilePath, exception);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorMessageDto(exception.getMessage()))
          .build();
    }
  }
}
