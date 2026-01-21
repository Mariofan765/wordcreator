package com.mariofan.wordcreator.services;

import com.mariofan.wordcreator.services.enums.AlignmentElement;
import com.mariofan.wordcreator.services.enums.FontStyle;
import com.mariofan.wordcreator.services.enums.Type;
import com.mariofan.wordcreator.services.enums.TypePosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElementDto {

    private Integer position;
    private AlignmentElement alignmentElement;
    private Integer fontSize;
    private String fontFamily;
    private FontStyle fontStyle;
    private String value;
    private Type type;
    private TypePosition typePosition;



    @Override
    public String toString() {
        return "ElementDto{" +
                "position=" + position +
                ", alignment=" + alignmentElement +
                ", fontSize=" + fontSize +
                ", fontFamily='" + fontFamily + '\'' +
                ", fontStyle=" + fontStyle +
                ", value='" + value + '\'' +
                ", type=" + type +
                ", typePosition=" + typePosition +
                '}';
    }
}


