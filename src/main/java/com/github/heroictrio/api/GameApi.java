package com.github.heroictrio.api;

import com.github.heroictrio.dto.GameDTO;
import com.github.heroictrio.dto.MappingService;
import com.github.heroictrio.validator.Input;
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

    // TODO: move/ability
    // TODO: heroPick
    // TODO: if move -> direction
    // TODO: if ability and wizard -> {row, col} x 2 + isAscending
    // TODO: if ability and gnome -> direction + numberOfRows (2 or 3)
    // TODO: if ability and dwarf -> {row, col} x 2

    char heroPick = input.getHeroPick().charAt(0);
    char direction = input.getDirection().charAt(0);

    return mappingService.makeAction(gameId, heroPick, direction);
  }
}
