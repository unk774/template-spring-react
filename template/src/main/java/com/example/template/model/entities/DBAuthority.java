package com.example.template.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "db_authority")
public class DBAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "db_user_x_db_authority",
            joinColumns = @JoinColumn(name = "db_authority_id"),
            inverseJoinColumns = @JoinColumn(name = "db_user_id"))
    private Set<DBUser> DBUsers;

    public DBAuthority(String name) {
        this.name = name;
    }

    public DBAuthority() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DBUser> getDBUsers() {
        return DBUsers;
    }

    public void setDBUsers(Set<DBUser> DBUsers) {
        this.DBUsers = DBUsers;
    }
}
