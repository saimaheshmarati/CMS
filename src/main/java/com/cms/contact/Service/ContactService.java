package com.cms.contact.Service;

import com.cms.contact.entity.Contact;
import com.cms.contact.exception.ContactNotFoundException;
import com.cms.contact.exception.DuplicateEmailException;
import com.cms.contact.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    
    public Contact createContact(Contact contact) {
        if (contactRepository.existsByEmail(contact.getEmail())) {
            throw new DuplicateEmailException(contact.getEmail());
        }
        return contactRepository.save(contact);
    }

    
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    
    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
    }

    
    public Contact updateContact(Long id, Contact updatedContact) {
        Contact existing = getContactById(id);

        
        if (!existing.getEmail().equals(updatedContact.getEmail())
                && contactRepository.existsByEmail(updatedContact.getEmail())) {
            throw new DuplicateEmailException(updatedContact.getEmail());
        }

        existing.setFirstName(updatedContact.getFirstName());
        existing.setLastName(updatedContact.getLastName());
        existing.setEmail(updatedContact.getEmail());
        existing.setPhone(updatedContact.getPhone());
        existing.setCompany(updatedContact.getCompany());
        existing.setAddress(updatedContact.getAddress());

        return contactRepository.save(existing);
    }

    
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ContactNotFoundException(id);
        }
        contactRepository.deleteById(id);
    }
}