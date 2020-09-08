package com.cyrilsebastian.tvitter.api.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConnectionService {
    @Autowired
    private ConnectionRepository repository;

    public void addConnection(Connection connection) {
        repository.save(connection);
    }

    public Connection getPendingConnection(UUID requesterId, UUID accepterId) {
        return repository.findAllByRequester_IdAndAccepter_IdAndEstablished(requesterId, accepterId, false).orElse(null);
    }

    public Connection getEstablishedConnection(UUID requesterId, UUID accepterId) {
        return repository.findAllByRequester_IdAndAccepter_IdAndEstablished(requesterId, accepterId, true).orElse(null);
    }

    public void updateConnection(Connection connection) {
        addConnection(connection);
    }

    public List<Connection> getPending(UUID accepterId) {
        return repository.findAllByAccepter_IdAndEstablished(accepterId, false);
    }

    public List<Connection> getFollowers(UUID accepterId) {
        return repository.findAllByAccepter_IdAndEstablished(accepterId, true);
    }

    public List<Connection> getFollowing(UUID requesterId) {
        return repository.findAllByRequester_IdAndEstablished(requesterId, true);
    }
}
