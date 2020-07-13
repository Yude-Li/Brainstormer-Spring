package com.yude.brainstormerspring.repository;

import java.util.Optional;

import com.yude.brainstormerspring.model.Brain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BrainUserRepository extends JpaRepository<Brain, Long>{

    public Optional<Brain> findBrainByUsername(String username);
    public Optional<Brain> findBrainByEmail(String email);

    public Optional<Brain> findBrainByEmailAndPassword(String email, String pwd);
}