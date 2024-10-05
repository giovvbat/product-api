package com.giovanna.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.giovanna.demo.enums.UserAuthority;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_role")
public class RoleModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private UUID id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @ElementCollection(targetClass = UserAuthority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "role_authorities", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_authorities", nullable = false)
    private Set<UserAuthority> authorities;

    @JsonBackReference
    @OneToMany(mappedBy = "role", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserModel> users = new HashSet<>();

    public RoleModel() {}

    public RoleModel(UUID id, String roleName, Set<UserAuthority> authorities, Set<UserModel> users) {
        this.id = id;
        this.roleName = roleName;
        this.authorities = authorities;
        this.users = users;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    public Set<UserModel> getUsers() {
        return users;
    }

    public void setUsers(Set<UserModel> users) {
        this.users = users;
    }
}

