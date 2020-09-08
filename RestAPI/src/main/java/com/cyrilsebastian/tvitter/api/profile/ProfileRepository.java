package com.cyrilsebastian.tvitter.api.profile;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, UUID> {
    @Query(
            value = "SELECT *, (6371 * acos( cos(?1) * cos(latitude) * cos(longitude - ?2) + sin(?1) * sin(latitude) )) as dist FROM profiles HAVING dist < ?3 AND id!=?4 LIMIT 10 OFFSET ?5",
            nativeQuery = true)
    List<Profile> findAllByDistance(double latitude, double longitude, int radius, UUID id, int page);
}
