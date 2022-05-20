package com.yil.contact.service;

import com.yil.contact.dto.ContactDto;
import com.yil.contact.model.Contact;
import com.yil.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact findById(Long id) throws EntityNotFoundException {
        return contactRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public Page<Contact> findAllByDeletedTimeIsNull(Pageable pageable) {
        return contactRepository.findAllByDeletedTimeIsNull(pageable);
    }

    public static ContactDto toDto(Contact contact) throws NullPointerException {
        if (contact == null)
            throw new NullPointerException("Contact is null");
        ContactDto dto = new ContactDto();
        dto.setId(contact.getId());
        dto.setContactTypeId(contact.getContactTypeId());
        return dto;
    }
}
