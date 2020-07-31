package com.yude.brainstormerspring.repository;

import java.util.List;
import java.util.Optional;

import com.yude.brainstormerspring.model.Todo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>{

    public List<Todo> findAllByUsername(String username);
    public Optional<Todo> findTodoByIdeaId(long id);
    
}