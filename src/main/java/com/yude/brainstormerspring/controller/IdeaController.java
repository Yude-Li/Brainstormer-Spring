package com.yude.brainstormerspring.controller;

import java.util.List;

import com.yude.brainstormerspring.model.Brain;
import com.yude.brainstormerspring.model.Idea;
import com.yude.brainstormerspring.model.form.IdeaForm;
import com.yude.brainstormerspring.repository.BrainUserRepository;
import com.yude.brainstormerspring.repository.IdeaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class IdeaController {
    
    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private BrainUserRepository brainUserRepository;

    private BrainIdeaController brainIdeaController;

    public IdeaController(IdeaRepository ideaRepository, BrainUserRepository brainUserRepository, BrainIdeaController brainIdeaController) {
        this.ideaRepository = ideaRepository;
        this.brainUserRepository = brainUserRepository;
        this.brainIdeaController = brainIdeaController;
    }

    @GetMapping("ideas")
    public List<Idea> findAll() {
        return ideaRepository.findAll();
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
        return ideaRepository.save(idea);
    }

    
}