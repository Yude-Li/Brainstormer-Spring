package com.yude.brainstormerspring.controller;

import java.util.List;

import com.yude.brainstormerspring.model.Brain;
import com.yude.brainstormerspring.model.form.BrainForm;
import com.yude.brainstormerspring.repository.BrainUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class BrainUserController {

    @Autowired
    private BrainUserRepository brainUserRepository;

    public BrainUserController(BrainUserRepository brainUserRepository) {
        this.brainUserRepository = brainUserRepository;
    }

    // @GetMapping("brains")
    @RequestMapping(value = "brains", method = RequestMethod.GET)
    public List<Brain> findAll() {
        return brainUserRepository.findAll();
    }

    // @PostMapping("/brain/create")
    @RequestMapping(value = "/brain/create", 
                    method = RequestMethod.POST, 
                    produces = MediaType.APPLICATION_JSON_VALUE, 
                    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Brain createBrain(@RequestBody BrainForm brainForm) {
        Brain brain = new Brain();
        brain.setEmail(brainForm.getEmail());
        brain.setUsername(brainForm.getUsername());
        brain.setPassword(brainForm.getPassword());
        brain.setFirstName(brainForm.getFirstName());
        brain.setLastName(brainForm.getLastName());
        return brainUserRepository.save(brain);
    }

    @PostMapping("/brain/{username}")
    public Brain findBrainByUsername(@PathVariable("username") String username) {
        return brainUserRepository.findBrainByUsername(username).orElse(new Brain());
    }

    @RequestMapping(value = "/brain/login", 
    method = RequestMethod.POST, 
    produces = MediaType.APPLICATION_JSON_VALUE, 
    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Brain brainLogin(@RequestBody BrainForm brainForm) {
        return brainUserRepository.findBrainByEmailAndPassword(brainForm.getEmail(), brainForm.getPassword()).orElse(new Brain());
    }

    @RequestMapping(value = "/brain/update", 
                    method = RequestMethod.POST, 
                    produces = MediaType.APPLICATION_JSON_VALUE, 
                    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Brain updateBrain(@RequestBody BrainForm brainForm) {

        Brain brain = brainUserRepository.findBrainByUsername(brainForm.getUsername()).orElse(new Brain());
        brain.setEmail(brainForm.getEmail());
        brain.setUsername(brainForm.getUsername());
        brain.setPassword(brainForm.getPassword());
        brain.setFirstName(brainForm.getFirstName());
        brain.setLastName(brainForm.getLastName());
        return brainUserRepository.save(brain);
    }
}