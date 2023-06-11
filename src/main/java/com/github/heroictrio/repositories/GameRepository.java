package com.github.heroictrio.repositories;

import com.github.heroictrio.models.Game;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class GameRepository {

  private EntityManager entityManager;

  public GameRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Game findById(Long gameId) {
    return entityManager.find(Game.class, gameId);
  }

  public void save(Game game) {

    if (game.getId() == null) {
      entityManager.persist(game);
      return;
    }

    entityManager.merge(game);
  }
}
