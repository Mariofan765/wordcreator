package com.mariofan.wordcreator.services;

import com.nimbusds.jose.util.Pair;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.stereotype.Service;

@Service
public class RadioButtonGroupCustomFunc<T>  {


    public RadioButtonGroup<T> setRadioButtonGroup(String label, T ...items) {
        RadioButtonGroup<T> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioButtonGroup.setLabel(label);
        radioButtonGroup.setItems(items);
        return radioButtonGroup;
    }


}


/*
 * "Уровень доступа (public, private, protected)_Тип возврат.Объекта (void, необходимый тип)_Название функции_Аргументы функции (Тип аргумента_Название аргумента,...)_{Тело функции}"
 * */
