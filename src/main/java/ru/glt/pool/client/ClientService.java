package ru.glt.pool.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;


    public List<ClientBase> getClients() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientBase> criteriaQuery = criteriaBuilder.createQuery(ClientBase.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        criteriaQuery.multiselect(root.get("id"), root.get("name"));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Optional<Client> getClient(Integer id) {
        return clientRepository.findById(id);
    }

    public void addClient(Client client) {
        client.setId(null);
        clientRepository.save(client);
    }

    public void updateClient(Client client) {
        if (clientRepository.existsById(client.getId())) {
            clientRepository.save(client);
        }
    }
}