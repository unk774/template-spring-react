package com.example.template.repositories;

import com.example.template.model.entities.DBUser;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootTest
@TestPropertySource(properties = {"spring.jpa.properties.hibernate.javax.cache.uri = classpath://hibernateCache.xml"})
public class RoleModelTests {

    @Autowired
    DBUserRepository DBUserRepository;

    @Autowired
    DBAuthorityRepository DBAuthorityRepository;

    @Test
    @Transactional
    public void testCreated() {
        List<DBUser> DBUsers = StreamSupport.stream(DBUserRepository.findAll().spliterator(), false).collect(Collectors.toList());
        Assertions.assertEquals(2, DBUsers.size());
        Assertions.assertTrue(DBUsers.stream().anyMatch(DBUser -> DBUser.getUsername().equals("admin") && !DBUser.getDbAuthorities().isEmpty()));
        Assertions.assertTrue(DBUsers.stream().anyMatch(DBUser -> DBUser.getUsername().equals("user") && !DBUser.getDbAuthorities().isEmpty()));
    }

    @Test
    public void testFindByUsername() {
        Optional<DBUser> appUser = DBUserRepository.findByUsername("admin");
        Assertions.assertTrue(appUser.isPresent());
        Assertions.assertEquals("admin", appUser.get().getUsername());
    }
}
