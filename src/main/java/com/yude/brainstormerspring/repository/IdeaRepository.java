package com.yude.brainstormerspring.repository;

import java.util.List;
import java.util.Optional;

import com.yude.brainstormerspring.model.Idea;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IdeaRepository extends JpaRepository<Idea, Long>{
    public Optional<Idea> findIdeaById(Long id);
    public List<Idea> findAllByOrderByIdAsc();
    public List<Idea> findAllByOrderByIdDesc();
    public List<Idea> findAllByAuthorUsername(String username);
    public List<Idea> findAllByOriginalIdeaId(long id);
}