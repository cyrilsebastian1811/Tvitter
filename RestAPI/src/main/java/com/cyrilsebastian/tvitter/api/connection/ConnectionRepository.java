package com.cyrilsebastian.tvitter.api.connection;

import com.cyrilsebastian.tvitter.api.profile.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
    List<Connection> findAllByAccepter_IdAndEstablished(UUID accepter_Id, boolean established);
    List<Connection> findAllByRequester_IdAndEstablished(UUID requester_Id, boolean established);
//    Optional<Connection> findAllByRequester_IdAndAccepter_Id(UUID requester_Id, UUID accepter_Id);
    Optional<Connection> findAllByRequester_IdAndAccepter_IdAndEstablished(UUID requester_Id, UUID accepter_Id, boolean established);
}
