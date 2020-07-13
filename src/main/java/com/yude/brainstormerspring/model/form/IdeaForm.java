package com.yude.brainstormerspring.model.form;

import lombok.Data;

@Data
public class IdeaForm {
    private String username;
    private String title;
    private String context;
    private String content;    
}