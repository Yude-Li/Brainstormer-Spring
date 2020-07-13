package com.yude.brainstormerspring.repository;

import java.util.Optional;

import com.yude.brainstormerspring.model.Idea;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaRepository extends JpaRepository<Idea, Long>{
    public Optional<Idea> findIdeaById(Long id);
}