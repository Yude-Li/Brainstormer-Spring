package com.yude.brainstormerspring.model.form;

import lombok.Data;

@Data
public class CiteForm {

    private long citeIdeaId;
    private String citeUsername;
    private String citeTitle;
    private String citeContext;
    private String citeContent;
    private boolean isCiting;
    
}