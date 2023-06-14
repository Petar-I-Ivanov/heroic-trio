package com.github.heroictrio.api;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import com.github.heroictrio.dto.MappingService;
import com.github.heroictrio.validators.Input;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/game")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class GameApi {

  private static final int UNPROCESSABLE_ENTITY_STATUS_CODE = 422;

  private MappingService mappingService;

  public GameApi(MappingService mappingService) {
    this.mappingService = mappingService;
  }

  @GET
  @Path("/{gameId}")
  public Response getGame(@PathParam("gameId") String gameId) {

    try {
      return Response.status(Status.OK).entity(mappingService.getGameById(gameId)).build();
    }

    catch (IllegalArgumentException e) {
      return Response.status(Status.NOT_FOUND).entity("There's no game with this ID!").build();
    }

    catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured!").build();
    }
  }

  @POST
  public Response startNewGame() {

    try {
      return Response.status(Status.CREATED).entity(mappingService.startNewGame()).build();
    }

    catch (IllegalArgumentException e) {
      return Response.status(UNPROCESSABLE_ENTITY_STATUS_CODE).entity(e.getMessage()).build();
    }

    catch (Exception e) {
      return Response.status(Status.CONFLICT).entity("An error occured!").build();
    }
  }

  @PUT
  @Path("/{gameId}")
  public Response makeAction(@PathParam("gameId") String gameId, @Valid Input input) {

    try {
      return Response.status(Status.OK).entity(mappingService.makeAction(gameId, input)).build();
    }

    catch (IllegalArgumentException e) {
      return Response.status(UNPROCESSABLE_ENTITY_STATUS_CODE).entity(e.getMessage()).build();
    }

    catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured").build();
    }
  }
}
