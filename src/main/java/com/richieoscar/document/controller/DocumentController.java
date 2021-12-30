package com.richieoscar.document.controller;

import com.richieoscar.document.model.Document;
import com.richieoscar.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class DocumentController {
    @Autowired
    private DocumentRepository repository;

    @PostMapping("/upload")
    public String upload(@RequestParam("document") MultipartFile file, @RequestParam("description") String description, Model model) {
        Document document = new Document();
        document.setName(file.getOriginalFilename());
        document.setDescription(description);
        try {
            document.setData(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository.save(document);
        saveAndGet(model, "documents");
        return "showUpload";
    }

    private void saveAndGet(Model model, String attributeName) {
        List<Document> documents = repository.findAll();
        model.addAttribute(attributeName, documents);
    }

    @RequestMapping("/showUpload")
    public String showUpload(Model model) {
        saveAndGet(model, "documents");
        return "showUpload";
    }

    @GetMapping("/download")
    public StreamingResponseBody download(@RequestParam("id") Integer id, HttpServletResponse response) {
        Optional<Document> optionalDocument = repository.findById(id);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            byte[] data = document.getData();
            response.setHeader("Content-Disposition", "attachment;filename-downloaded.jpeg");
            return outputStream -> {
                outputStream.write(data);
            };
        } else throw new IllegalStateException("Document not found" + id);
    }

}
