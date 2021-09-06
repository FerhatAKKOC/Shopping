package com.example.demo.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @Column(unique = true)
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles", cascade = CascadeType.ALL)
    private Collection<User> users;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
