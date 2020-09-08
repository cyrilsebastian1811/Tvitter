package com.cyrilsebastian.tvitter.api.post;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends CrudRepository<Post, UUID> {
    List<Post> findAllByOwner_IdOrderByCreatedAt(UUID owner_Id);
    int countAllByOwner_Id(UUID owner_Id);
}
