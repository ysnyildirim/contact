package com.yil.contact.service;

import com.yil.contact.dto.ContactDto;
import com.yil.contact.exception.ContactNotFoundException;
import com.yil.contact.model.Contact;
import com.yil.contact.repository.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ContactDao contactDao;

    @Autowired
    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public static ContactDto toDto(Contact contact) throws NullPointerException {
        if (contact == null)
            throw new NullPointerException("Contact is null");
        ContactDto dto = new ContactDto();
        dto.setId(contact.getId());
        dto.setContactTypeId(contact.getContactTypeId());
        return dto;
    }

    public Contact findById(Long id) throws ContactNotFoundException {
        return contactDao.findById(id).orElseThrow(ContactNotFoundException::new);
    }

    public Contact save(Contact contact) {
        return contactDao.save(contact);
    }

    public void deleteById(long id) {
        contactDao.deleteById(id);
    }

    public Page<Contact> findAll(Pageable pageable) {
        return contactDao.findAll(pageable);
    }
}
