package com.mariofan.wordcreator.services;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("test")
@AllArgsConstructor
public class TestController {

    private final WordCreateService wordCreateService;


    @PostMapping
    public ResponseEntity<Resource> createWord(@RequestBody(required = false) List<ElementDto> elements) throws IOException {
        List<ElementDto> data = elements != null ? elements : List.of(new ElementDto());
        byte[] wordFile = wordCreateService.createWord(data);

        ByteArrayResource resource = new ByteArrayResource(wordFile);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated-document.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(wordFile.length)
                .body(resource);
    }
}
