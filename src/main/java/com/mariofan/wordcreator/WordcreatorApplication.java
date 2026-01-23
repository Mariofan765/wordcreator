package com.mariofan.wordcreator;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(name = "Word Creator", shortName = "WordCreator")
public class WordcreatorApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(WordcreatorApplication.class, args);
    }
}

