package com.yil.contact.service;

import com.yil.contact.dto.ContactEmailDto;
import com.yil.contact.exception.ContactEmailNotFoundException;
import com.yil.contact.model.ContactEmail;
import com.yil.contact.repository.ContactEmailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ContactEmailService {
    private final ContactEmailDao contactEmailDao;

    @Autowired
    public ContactEmailService(ContactEmailDao contactEmailDao) {
        this.contactEmailDao = contactEmailDao;
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

    public ContactEmail findById(Long id) throws EntityNotFoundException, ContactEmailNotFoundException {
        return contactEmailDao.findById(id).orElseThrow(ContactEmailNotFoundException::new);
    }

    public ContactEmail findByIdAndContactId(Long id, long contactId) throws ContactEmailNotFoundException {
        return contactEmailDao.findByIdAndContactId(id, contactId).orElseThrow(ContactEmailNotFoundException::new);
    }

    public void deleteByIdAndContactId(long id, long contactId) {
        contactEmailDao.deleteByIdAndContactId(id, contactId);
    }

    public ContactEmail save(ContactEmail contact) {
        return contactEmailDao.save(contact);
    }

    public Page<ContactEmail> findAllByContactId(Pageable pageable, Long contactId) {
        return contactEmailDao.findAllByContactId(pageable, contactId);
    }
}
