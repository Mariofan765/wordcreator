package com.mariofan.wordcreator.views;

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

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Route("")
public class HomeView extends VerticalLayout {

    private final WordCreateService wordCreateService;

    public HomeView(WordCreateService wordCreateService) {
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

    private Component setForm() {
        Div form = new Div();
        Input input = setInput();
        Button button = setButton();

        button.addClickListener(e -> {
            try {
                byte[] data = wordCreateService.createWord(input.getValue());

                StreamResource resource = new StreamResource(
                        "word.docx",
                        () -> new ByteArrayInputStream(data)
                );

                // Создаем новую кнопку для скачивания
                Button downloadButton = new Button("Скачать Word");
                Anchor downloadLink = new Anchor(resource, "");
                downloadLink.getElement().setAttribute("download", true);
                downloadLink.add(downloadButton);

                // Очищаем форму и показываем результат
                form.removeAll();
                form.add(input, downloadLink);

                // Можно добавить кнопку для возврата к форме
                Button backButton = new Button("Создать новый документ",
                        event -> {
                            form.removeAll();
                            form.add(input, button);
                        });
                form.add(backButton);

            } catch (Exception ex) {
                ex.printStackTrace();
                // Добавьте обработку ошибки для пользователя
                Notification.show("Ошибка при создании документа");
            }
        });

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
        return button;
    }

}
