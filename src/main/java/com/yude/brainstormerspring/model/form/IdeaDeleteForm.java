package com.yude.brainstormerspring.model.form;

import lombok.Data;

@Data
public class IdeaDeleteForm {

    private long id;
    private String title;
    private String context;
    private String content;
    private String authorName;
    
}