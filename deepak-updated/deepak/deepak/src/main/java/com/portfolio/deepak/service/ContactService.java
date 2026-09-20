package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Contact;
import com.portfolio.deepak.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Save contact message
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    // Get all messages
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
    public List<Contact> getRecentContacts() {

        return contactRepository.findTop5ByOrderByCreatedAtDesc();

    }

    // Get message by ID
    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Message not found"));
    }

    // Delete message
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

}