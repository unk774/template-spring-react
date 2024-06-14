package com.example.template.repositories;

import com.example.template.model.entities.DBAuthority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBAuthorityRepository extends CrudRepository<DBAuthority, Long> {
}
