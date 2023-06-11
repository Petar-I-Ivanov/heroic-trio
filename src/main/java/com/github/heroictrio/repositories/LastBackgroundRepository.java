package com.github.heroictrio.repositories;

import com.github.heroictrio.models.gameboard.terrain.Background;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class LastBackgroundRepository {

  private EntityManager entityManager;

  public LastBackgroundRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Background getHighestBackground(Long gameId) {

    String queryString = "SELECT b FROM Background b WHERE b.game.id = :gameId ORDER BY b.id DESC";
    TypedQuery<Background> query = entityManager.createQuery(queryString, Background.class);
    query.setParameter("gameId", gameId);
    query.setMaxResults(1);

    return query.getSingleResult();
  }
}
