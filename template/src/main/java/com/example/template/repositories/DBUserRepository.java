package com.example.template.repositories;


import com.example.template.model.entities.DBUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DBUserRepository extends CrudRepository<DBUser, Long> {

    Optional<DBUser> findByUsername(String username);
}
