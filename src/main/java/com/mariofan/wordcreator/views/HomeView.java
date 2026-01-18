package com.mariofan.wordcreator.views;

import com.mariofan.wordcreator.services.ElementDto;
import com.mariofan.wordcreator.services.WordCreateService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.io.ByteArrayInputStream;
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
        Input input = setInput();
        Button button = setButton();
        byte[] data = wordCreateService.createWord(List.of(new ElementDto()));
        button.addClickListener(e -> wordCreateService.addDownload(form, input, button, null, data));
        form.add(input, button);
        return form;
    }

    private Input setInput() {
        Input input = new Input();
        input.setType("text");
        input.setPlaceholder("Enter your text here...");
        return input;
    }

    private Button setButton() {
        Button button = new Button("Start");
        button.getElement().setAttribute("width", "100%");
        return button;
    }

}
