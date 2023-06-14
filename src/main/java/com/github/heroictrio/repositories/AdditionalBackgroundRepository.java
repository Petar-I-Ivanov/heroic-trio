package com.github.heroictrio.repositories;

import com.github.heroictrio.models.gameboard.terrain.Background;
import com.github.heroictrio.utilities.Position;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class AdditionalBackgroundRepository {

  private EntityManager entityManager;

  public AdditionalBackgroundRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public int getHighestValueForGameId(Long gameId) {

    String queryString =
        "SELECT MAX(CAST(b.sign AS INTEGER)) FROM Background b WHERE b.game.id = :gameId";
    Query query = entityManager.createQuery(queryString);
    query.setParameter("gameId", gameId);

    return (Integer) query.getSingleResult();
  }

  public List<Background> getBackgroundsBetween(Long gameId, Position from, Position to) {

    String queryString =
        "SELECT b FROM Background b WHERE b.game.id = ?1 AND b.rowLocation BETWEEN ?2 "
            + "AND ?3 AND b.colLocation BETWEEN ?4 AND ?5";

    TypedQuery<Background> query = entityManager.createQuery(queryString, Background.class);

    query.setParameter(1, gameId);

    query.setParameter(2, from.getRow());
    query.setParameter(3, to.getRow());

    query.setParameter(4, from.getCol());
    query.setParameter(5, to.getCol());

    return query.getResultList();
  }
}
