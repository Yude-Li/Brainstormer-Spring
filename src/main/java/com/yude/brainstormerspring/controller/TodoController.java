package com.yude.brainstormerspring.controller;

import java.util.ArrayList;
import java.util.List;

import com.yude.brainstormerspring.model.Brain;
import com.yude.brainstormerspring.model.Idea;
import com.yude.brainstormerspring.model.Todo;
import com.yude.brainstormerspring.model.form.CiteForm;
import com.yude.brainstormerspring.repository.BrainUserRepository;
import com.yude.brainstormerspring.repository.IdeaRepository;
import com.yude.brainstormerspring.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private BrainUserRepository brainUserRepository;
    @Autowired
    private IdeaRepository ideaRepository;
    
    public TodoController(TodoRepository todoRepository, BrainUserRepository brainUserRepository, IdeaRepository ideaRepository) {
        this.todoRepository = todoRepository;
        this.ideaRepository = ideaRepository;
        this.brainUserRepository = brainUserRepository;
    }

    @RequestMapping(value = "/todo/all", 
    method = RequestMethod.GET)
    public List<Idea> allTodoForBrain(@RequestParam String username) {
        Brain brain = brainUserRepository.findBrainByUsername(username).orElse(new Brain());        
        if (brain == null) return null;

        List<Todo> todoList = todoRepository.findAllByUsername(username);

        List<Idea> ideaList = new ArrayList<>();

        for (Todo todo : todoList) {
            ideaList.add(todo.getIdea());
        }

        return ideaList;
    }

    @RequestMapping(value = "/cite", 
    method = RequestMethod.POST, 
    produces = MediaType.APPLICATION_JSON_VALUE, 
    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Idea newTodo(@RequestBody CiteForm citeForm) {
        if (!citeForm.isCiting()) { // not citing, add
            Brain brain = brainUserRepository.findBrainByUsername(citeForm.getCiteUsername()).orElse(new Brain());
            Idea idea = ideaRepository.findIdeaById(citeForm.getCiteIdeaId()).orElse(new Idea());
            
            if (brain == null) return null;
            if (idea == null) return null;
            Idea newIdea = new Idea();
            newIdea.setOriginalIdea(idea);
            newIdea.setAuthor(brain);
            newIdea.setTitle(citeForm.getCiteTitle());
            newIdea.setContext(citeForm.getCiteContext());
            newIdea.setContent(citeForm.getCiteContent());
            Idea savedNewIdea = ideaRepository.save(newIdea);

            Todo todo = new Todo();
            todo.setIdea(savedNewIdea);
            todo.setUsername(brain.getUsername());
            todo.setMarkAsDone(false);
            todoRepository.save(todo);
            return savedNewIdea;
        }
        else { // already citing, remove
            todoRepository.deleteById(citeForm.getCiteIdeaId());
            return null;
        }
    }
    
    // @RequestMapping(value = "/todo/delete", 
    // method = RequestMethod.POST, 
    // produces = MediaType.APPLICATION_JSON_VALUE, 
    // consumes = {MediaType.APPLICATION_JSON_VALUE})
    // public boolean deleteIdea(@RequestBody Todo idea) {
    //     ideaRepository.deleteById(idea.getId());
    //     return true;
    // }
}