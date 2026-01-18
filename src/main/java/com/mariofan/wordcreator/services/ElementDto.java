package com.mariofan.wordcreator.services;

import com.mariofan.wordcreator.services.enums.Alignment;
import com.mariofan.wordcreator.services.enums.FontStyle;
import com.mariofan.wordcreator.services.enums.Type;
import lombok.Getter;
import lombok.Setter;

import com.mariofan.wordcreator.services.enums.Type.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
public class ElementDto {

    private Integer position;
    private Alignment alignment;
    private Integer fontSize;
    private String fontFamily;
    private FontStyle fontStyle;
    private String value;
    private Type type;
    private TypePosition typePosition;


}


