package com.yude.brainstormerspring.controller;

import com.yude.brainstormerspring.model.Brain;
import com.yude.brainstormerspring.model.Idea;
import com.yude.brainstormerspring.model.form.IdeaAuthForm;
import com.yude.brainstormerspring.repository.BrainUserRepository;
import com.yude.brainstormerspring.repository.IdeaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class BrainIdeaController {

    @Autowired
    private BrainUserRepository brainUserRepository;
    @Autowired
    private IdeaRepository ideaRepository;

    public BrainIdeaController(BrainUserRepository brainUserRepository, IdeaRepository ideaRepository) {
        this.brainUserRepository = brainUserRepository;
        this.ideaRepository = ideaRepository;
    }

    @RequestMapping(value = "/brainpost", 
                    method = RequestMethod.POST, 
                    produces = MediaType.APPLICATION_JSON_VALUE, 
                    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addIdeaToBrain(@RequestBody IdeaAuthForm ideaAuthForm){
        Brain user = brainUserRepository.findBrainByUsername(ideaAuthForm.getUsername()).orElse(new Brain());
        Idea idea = ideaRepository.findIdeaById(ideaAuthForm.getId()).orElse(new Idea());

        if (user == null) return;
        if (idea == null) return;

        user.getIdeas().add(idea);
        brainUserRepository.save(user);
    }
    
}