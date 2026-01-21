package com.mariofan.wordcreator.views;

import com.mariofan.wordcreator.services.ElementDto;
import com.mariofan.wordcreator.services.WordCreateService;
import com.mariofan.wordcreator.services.enums.AlignmentElement;
import com.mariofan.wordcreator.services.enums.FontStyle;
import com.mariofan.wordcreator.services.enums.Type;
import com.mariofan.wordcreator.services.enums.TypePosition;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.util.List;

@Route("")
public class HomeView extends VerticalLayout {

    private final WordCreateService wordCreateService;

    public HomeView(WordCreateService wordCreateService) throws IOException {
        this.wordCreateService = wordCreateService;
        setPageStyle();
        add(setForm());


    }

    private void setPageStyle() {
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setWidth("100vw");
        this.setHeight("100vh");
    }

    private Component setForm() throws IOException {
        Div form = new Div();
        Input input = setInput(form);
        Button button = setButton(form);

        RadioButtonGroup<Type> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setLabel("ZOV");
        radioButtonGroup.setItems(Type.BODY, Type.HEADER, Type.FOOTER);
        RadioButtonGroup<TypePosition> radioButtonGroupT = new RadioButtonGroup<>();
        radioButtonGroupT.setLabel("GOYDA");
        radioButtonGroupT.setItems(TypePosition.DEFAULT, TypePosition.EVEN, TypePosition.FIRST);

        input.addFocusListener(e -> {
            form.add(radioButtonGroup, radioButtonGroupT);
        });
        button.addClickListener(e -> {
            ElementDto elementDto = new ElementDto();
            elementDto.setValue(input.getValue());
            elementDto.setType(radioButtonGroup.getValue());
            elementDto.setTypePosition(radioButtonGroupT.getValue());
            elementDto.setAlignmentElement(AlignmentElement.CENTER);
            elementDto.setPosition(0);
            elementDto.setFontStyle(FontStyle.BOLD);
            elementDto.setFontFamily("ROBOT");
            elementDto.setFontSize(12);
            try {
                byte[] data = wordCreateService.createWord(List.of( elementDto));
                wordCreateService.addDownload(form, input, button, null, data);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            form.add(new Paragraph(elementDto.toString()));
        });
        form.add(input, button);
        return form;
    }

    private Input setInput(Div form) {
        Input input = new Input();
        input.setType("text");
        input.setPlaceholder("Enter your text here...");

        return input;
    }

    private Button setButton(Div form) {
        Button button = new Button("Start");
        button.getElement().setAttribute("width", "100%");
        return button;
    }

}
