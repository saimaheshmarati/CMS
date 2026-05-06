package com.cms.contact.controller;


import com.cms.contact.entity.Contact;
import com.cms.contact.Service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:3000")   // React dev server
//@RequiredArgsConstructor
public class ContactController {

    private ContactService contactService;

    
    @PostMapping
    public ResponseEntity<Contact> create(@RequestBody Contact contact) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactService.createContact(contact));
    }

    
    @GetMapping
    public ResponseEntity<List<Contact>> getAll() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id,
                                          @RequestBody Contact contact) {
        return ResponseEntity.ok(contactService.updateContact(id, contact));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}