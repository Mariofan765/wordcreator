package com.mariofan.wordcreator.services;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamResource;
import org.apache.poi.ss.usermodel.FontFamily;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;


@Service
public class WordCreateService {

    public byte[] createWord() throws IOException {
        try (XWPFDocument doc = new XWPFDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            doc.write(baos);
            return baos.toByteArray();
        }
    }

    public byte[] createWord(List<ElementDto> values) throws IOException {
        try (XWPFDocument doc = new XWPFDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            for (ElementDto element : values) {
                switch (element.getType()) {
                    case HEADER -> generateHeader(element, doc);
                    case BODY -> generateBody(element, doc);
                    case FOOTER -> generateFooter(element, doc);
                    default -> throw new IllegalArgumentException("Invalid element");
                }
            }

            doc.write(baos);
            return baos.toByteArray();
        }
    }

    private XWPFHeader generateHeader(ElementDto element, XWPFDocument doc) {
        XWPFHeader header = setHeaderType(element, doc);

        XWPFParagraph paragraph = setHeaderAlignment(element, header);

        setHeaderValue(element, paragraph);

        return header;
    }

    private XWPFHeader setHeaderType(ElementDto element, XWPFDocument doc) {
        HeaderFooterType headerType = switch (element.getTypePosition()) {
            case FIRST -> HeaderFooterType.FIRST;
            case EVEN -> HeaderFooterType.EVEN;
            case DEFAULT -> HeaderFooterType.DEFAULT;
        };
        if (headerType != null) {
            return doc.createHeader(headerType);
        }
        throw new IllegalArgumentException("Unknown element type position: " + element.getTypePosition());
    }

    private XWPFParagraph setHeaderAlignment(ElementDto element, XWPFHeader header) {
        XWPFParagraph paragraph = header.createParagraph();
        switch (element.getAlignmentElement()) {
            case LEFT -> paragraph.setAlignment(ParagraphAlignment.LEFT);
            case CENTER ->paragraph.setAlignment(ParagraphAlignment.CENTER);
            case RIGHT -> paragraph.setAlignment(ParagraphAlignment.RIGHT);
            default -> throw new IllegalArgumentException("Invalid alignment position: " + element.getAlignmentElement());
        }
        return paragraph;
    }

    private void setHeaderValue(ElementDto element, XWPFParagraph paragraph) {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(
                element.getFontSize() == null ? 16 : element.getFontSize()
        );
        run.setFontFamily(
                element.getFontFamily() == null ? "Times New Roman" : element.getFontFamily()
        );
        switch (element.getFontStyle()) {
            case BOLD -> run.setBold(true);
            case ITALIC -> run.setItalic(true);
            case BOLD_ITALIC -> {
                run.setItalic(true);
                run.setBold(true);
            }
            case NORMAL -> {}
            default -> throw new IllegalArgumentException("Invalid font style: " + element.getFontStyle());
        }
        run.setText(element.getValue());
    }

    private void generateBody(ElementDto element, XWPFDocument doc) {
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFParagraph paragraph1 = doc.createParagraph();

        paragraph.createRun().setText("0");
        paragraph1.createRun().setText("1");


        doc.setParagraph(paragraph, 1);
        doc.setParagraph(paragraph1, 0);

    }

    private void generateFooter(ElementDto element, XWPFDocument doc) {
    }

    public void addDownload(Div form, Input value, Button button, String fileName, byte[] file) {
        try {
            byte[] data = file;
            StreamResource resource = new StreamResource(
                    fileName == null ? "word_" + Instant.now() + ".docx" : fileName,
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

