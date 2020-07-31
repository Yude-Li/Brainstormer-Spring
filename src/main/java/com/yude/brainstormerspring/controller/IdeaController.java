package com.yude.brainstormerspring.controller;

import java.util.ArrayList;
import java.util.List;

import com.yude.brainstormerspring.model.Brain;
import com.yude.brainstormerspring.model.Idea;
import com.yude.brainstormerspring.model.Todo;
import com.yude.brainstormerspring.model.form.IdeaDeleteForm;
import com.yude.brainstormerspring.model.form.IdeaForm;
import com.yude.brainstormerspring.repository.BrainUserRepository;
import com.yude.brainstormerspring.repository.IdeaRepository;
import com.yude.brainstormerspring.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class IdeaController {
    
    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private BrainUserRepository brainUserRepository;
    @Autowired
    private TodoRepository todoRepository;

    public IdeaController(IdeaRepository ideaRepository, BrainUserRepository brainUserRepository, TodoRepository todoRepository) {
        this.ideaRepository = ideaRepository;
        this.brainUserRepository = brainUserRepository;
        this.todoRepository = todoRepository;
    }

    @GetMapping("ideas")
    public List<Idea> findAll() {
        return ideaRepository.findAllByOrderByIdDesc();
    }

    @RequestMapping(value = "/idea/new", 
    method = RequestMethod.POST, 
    produces = MediaType.APPLICATION_JSON_VALUE, 
    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Idea newIdea(@RequestBody IdeaForm ideaForm) {

        Brain brain = brainUserRepository.findBrainByUsername(ideaForm.getUsername()).orElse(new Brain());

        if (brain == null) return null;

        Idea idea = new Idea();
        idea.setTitle(ideaForm.getTitle());
        idea.setContext(ideaForm.getContext());
        idea.setContent(ideaForm.getContent());
        idea.setAuthor(brain);

        Idea newIdea = ideaRepository.save(idea);
        brain.getIdeas().add(newIdea);

        return newIdea;
    }

    @RequestMapping(value = "/idea/brainideas", 
    method = RequestMethod.GET)
    public List<Idea> ideasFromBrain(@RequestParam String username) {
        Brain brain = brainUserRepository.findBrainByUsername(username).orElse(new Brain());        
        if (brain == null) return null;

        return ideaRepository.findAllByAuthorUsername(username);
    }

    @RequestMapping(value = "/idea/followbrainsideas", 
    method = RequestMethod.POST, 
    produces = MediaType.APPLICATION_JSON_VALUE, 
    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<Idea> ideasFromFollowedBrains(@RequestBody IdeaForm ideaForm) {

        List<Idea> ideas = new ArrayList<>();

        Brain brain = brainUserRepository.findBrainByUsername(ideaForm.getUsername()).orElse(new Brain());
        if (brain == null) return null;

        ideas.addAll(brain.getIdeas());

        if(brain.getFollows().size() > 0) {
            for (Brain follow : brain.getFollows()) {
                if(follow.getIdeas().size() > 0) {
                    ideas.addAll(follow.getIdeas());
                }
            }
        }

        return ideas;
    }

    @RequestMapping(value = "/idea/delete", 
    method = RequestMethod.POST, 
    produces = MediaType.APPLICATION_JSON_VALUE, 
    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<Idea> deleteIdea(@RequestBody IdeaDeleteForm idea) {

        List<Idea> ideaList = ideaRepository.findAllByOriginalIdeaId(idea.getId());

        for(Idea i : ideaList){
            deleteAnIdea(i);
        }

        Todo todo = todoRepository.findTodoByIdeaId(idea.getId()).orElse(null);

        if(todo != null) {
            todoRepository.deleteById(todo.getId());
        }

        ideaRepository.deleteById(idea.getId());

        Brain brain = brainUserRepository.findBrainByUsername(idea.getAuthorName()).orElse(new Brain());        
        if (brain == null) return null;

        return ideaRepository.findAllByAuthorUsername(brain.getUsername());
    }

    private void deleteAnIdea(Idea idea) {
        List<Idea> ideaList = ideaRepository.findAllByOriginalIdeaId(idea.getId());

        Todo todo = todoRepository.findTodoByIdeaId(idea.getId()).orElse(null);

        if(todo != null) {
            todoRepository.deleteById(todo.getId());
        }

        for(Idea i : ideaList){
            deleteAnIdea(i);
        }

        ideaRepository.deleteById(idea.getId());
    }
    
}