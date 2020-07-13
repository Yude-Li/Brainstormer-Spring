package com.yude.brainstormerspring.controller;

import com.yude.brainstormerspring.model.Brain;
import com.yude.brainstormerspring.model.form.FollowForm;
import com.yude.brainstormerspring.repository.BrainUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class FollowController {

    @Autowired
    private BrainUserRepository brainUserRepository;
    
    public FollowController(BrainUserRepository brainUserRepository) {
        this.brainUserRepository = brainUserRepository;
    }

    @RequestMapping(value = "/follow", 
                    method = RequestMethod.POST, 
                    produces = MediaType.APPLICATION_JSON_VALUE, 
                    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Brain addFollow(@RequestBody FollowForm followForm){
        Brain user = brainUserRepository.findBrainByUsername(followForm.getUsername()).orElse(new Brain());
        Brain follow = brainUserRepository.findBrainByUsername(followForm.getFollowUsername()).orElse(new Brain());

        if (user == null) return null;
        if (follow == null) return null;

        user.getFollows().add(follow);
        

        return brainUserRepository.save(user);
    }

    @RequestMapping(value = "/unfollow", 
    method = RequestMethod.POST, 
    produces = MediaType.APPLICATION_JSON_VALUE, 
    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Brain removeFollow(@RequestBody FollowForm followForm){
        Brain user = brainUserRepository.findBrainByUsername(followForm.getUsername()).orElse(new Brain());
        Brain follow = brainUserRepository.findBrainByUsername(followForm.getFollowUsername()).orElse(new Brain());

        if (user == null) return null;
        if (follow == null) return null;

        user.getFollows().remove(follow);
        brainUserRepository.save(user);

        return brainUserRepository.save(user);
    }
    
}