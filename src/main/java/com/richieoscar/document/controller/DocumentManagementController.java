package com.richieoscar.document.controller;

import com.richieoscar.document.model.Document;
import com.richieoscar.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/api/v1")
public class DocumentManagementController {

    private DocumentRepository repository;

    @Autowired
    public DocumentManagementController(DocumentRepository repository) {
        this.repository = repository;
    }

    @DeleteMapping(path = {"/deleteDocument/{id}"})
    @PreAuthorize("hasAuthority('document:write')")
    public void deleteDocument(@PathVariable("id") Integer id) {
        Document doc = repository.findById(id).get();
        repository.delete(doc);
    }

    @PutMapping(path = {"/updateDocument/{id}"})
    @PreAuthorize("hasAuthority('document:write')")
    public Document updateDocument(@PathVariable("id") Integer id, @RequestBody Document document) {
        Document doc = repository.findById(id).get();
        doc.setDescription(document.getDescription());
        doc.setName(document.getName());
        repository.save(doc);
        return doc;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_ADMIN_TRAINEE')")
    public List<Document> getDocuments() {
        return repository.findAll();
    }
}
