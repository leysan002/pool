package ru.glt.pool.search;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class SearchService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<SearchResult> search(SearchConditions conditions) {
        return entityManager.createQuery("select new ru.glt.pool.search.SearchResult(t.orderId, t.clientId, c.name, t.datetime) " +
                "from TimeTable t  left join Client c on t.clientId = c.id " +
                "where (:clientName is null or c.name like :clientName) and " +
                "(:from is null or t.datetime >= :from) and " +
                "(:to is null or t.datetime <= :to)", SearchResult.class)
                .setParameter("clientName", conditions.getClientName() != null ? "%" + conditions.getClientName() + "%" : null)
                .setParameter("from", conditions.getFrom()).setParameter("to", conditions.getTo())
                .getResultList();
    }
}

