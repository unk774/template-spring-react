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
@Table(name = "db_user")
public class DBUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    @ManyToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "db_user_x_db_authority",
            joinColumns = @JoinColumn(name = "db_user_id"),
            inverseJoinColumns = @JoinColumn(name = "db_authority_id"))
    private Set<DBAuthority> dbAuthorities;

    public DBUser(String username, String password, Set<DBAuthority> appAuthorities) {
        this.username = username;
        this.password = password;
        this.dbAuthorities = appAuthorities;
    }

    public DBUser() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<DBAuthority> getDbAuthorities() {
        return dbAuthorities;
    }

    public void setDbAuthorities(Set<DBAuthority> dbAuthorities) {
        this.dbAuthorities = dbAuthorities;
    }
}
