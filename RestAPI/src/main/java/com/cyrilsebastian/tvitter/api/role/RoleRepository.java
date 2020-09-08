package com.cyrilsebastian.tvitter.api.role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
    public Optional<Role> findByName(String name);
    public boolean existsByName(String name);
}
