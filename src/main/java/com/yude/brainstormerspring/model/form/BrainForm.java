package com.yude.brainstormerspring.model.form;

import lombok.Data;

@Data
public class BrainForm {
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}