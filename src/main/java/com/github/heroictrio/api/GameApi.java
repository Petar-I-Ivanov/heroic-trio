package com.github.heroictrio.api;

import com.github.heroictrio.dto.GameDTO;
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
import jakarta.ws.rs.core.MediaType;

@Path("/game")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GameApi {

  private MappingService mappingService;

  public GameApi(MappingService mappingService) {
    this.mappingService = mappingService;
  }

  @GET
  @Path("/{gameId}")
  public GameDTO getGame(@PathParam("gameId") Long gameId) {
    return mappingService.getGameById(gameId);
  }

  @POST
  public GameDTO startNewGame() {
    return mappingService.startNewGame();
  }

  @PUT
  @Path("/{gameId}")
  public GameDTO makeAction(@PathParam("gameId") Long gameId, @Valid Input input) {
    return mappingService.makeAction(gameId, input);
  }
}
