package com.mariofan.wordcreator.services;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamResource;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class WordCreateService {

    public byte[] createWord(String text) throws IOException {
        try (XWPFDocument doc = new XWPFDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            XWPFParagraph paragraph = doc.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(text);

            doc.write(baos);
            return baos.toByteArray();
        }
    }

    public void addDownload(Div form, Input value, Button button) {
        try {
            byte[] data = createWord(value.getValue());
            StreamResource resource = new StreamResource(
                    "word.docx",
                    () -> new ByteArrayInputStream(data)
            );

            Button downloadButton = new Button("Скачать Word");
            Anchor downloadLink = new Anchor(resource, "");
            downloadLink.getElement().setAttribute("download", true);
            downloadLink.add(downloadButton);

            // Очищаем форму и показываем результат
            form.removeAll();
            form.add(value, downloadLink);

            // Можно добавить кнопку для возврата к форме
            Button backButton = new Button("Создать новый документ",
                    event -> {
                        form.removeAll();
                        form.add(value, button);
                    });
            form.add(backButton);

        } catch (Exception ex) {
            ex.printStackTrace();
            Notification.show("Ошибка при создании документа");
        }
    }
}

