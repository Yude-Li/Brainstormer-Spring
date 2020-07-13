package com.yude.brainstormerspring.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = {"ideas", "follows"})
// @EqualsAndHashCode(exclude = "followers")
public class Brain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = false, nullable = false)
    private String password;

    @Column(unique = false, nullable = false)
    private String firstName;

    @Column(unique = false, nullable = false)
    private String lastName;

    @OneToMany
    @JsonIgnore
    private Set<Idea> ideas;

    @ManyToMany
    // @JsonIgnore
    private Set<Brain> follows;

    
}