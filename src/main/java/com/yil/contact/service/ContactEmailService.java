package com.yil.contact.service;

import com.yil.contact.dto.ContactEmailDto;
import com.yil.contact.model.ContactEmail;
import com.yil.contact.repository.ContactEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ContactEmailService {
    private final ContactEmailRepository contactEmailRepository;

    @Autowired
    public ContactEmailService(ContactEmailRepository contactEmailRepository) {
        this.contactEmailRepository = contactEmailRepository;
    }

    public ContactEmail findById(Long id) throws EntityNotFoundException {
        return contactEmailRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public ContactEmail save(ContactEmail contact) {
        return contactEmailRepository.save(contact);
    }


    public static ContactEmailDto toDto(ContactEmail contactEmail) {
        if (contactEmail == null)
            throw new NullPointerException("Contact email is null");
        ContactEmailDto dto = new ContactEmailDto();
        dto.setContactId(contactEmail.getContactId());
        dto.setId(contactEmail.getId());
        dto.setAddress(contactEmail.getEmail());
        return dto;
    }

    public Page<ContactEmail> findAllByContactIdAndDeletedTimeIsNull(Pageable pageable, Long contactId) {
        return contactEmailRepository.findAllByContactIdAndDeletedTimeIsNull(pageable,contactId);
    }
}
