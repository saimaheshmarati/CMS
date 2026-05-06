package com.cms.contact.controller;
package com.cms.controller;

import com.cms.entity.Contact;
import com.cms.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:3000")   // React dev server
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    // POST /api/contacts
    @PostMapping
    public ResponseEntity<Contact> create(@RequestBody Contact contact) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactService.createContact(contact));
    }

    // GET /api/contacts
    @GetMapping
    public ResponseEntity<List<Contact>> getAll() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    // GET /api/contacts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    // PUT /api/contacts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id,
                                          @RequestBody Contact contact) {
        return ResponseEntity.ok(contactService.updateContact(id, contact));
    }

    // DELETE /api/contacts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}