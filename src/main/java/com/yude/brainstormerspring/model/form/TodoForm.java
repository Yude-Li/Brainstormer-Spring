package com.yude.brainstormerspring.model.form;

import lombok.Data;

@Data
public class TodoForm {
    private long id;
    private long ideaId;
    private String ideaTitle;
    private String citeUsername;
    private boolean markAsDone;
    private boolean isCiting;    
}