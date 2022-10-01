package com.yil.contact.service;

import com.yil.contact.dto.ContactPhoneDto;
import com.yil.contact.exception.ContactPhoneNotFoundException;
import com.yil.contact.model.ContactPhone;
import com.yil.contact.repository.ContactPhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ContactPhoneService {
    private final ContactPhoneDao contactPhoneDao;

    @Autowired
    public ContactPhoneService(ContactPhoneDao contactPhoneDao) {
        this.contactPhoneDao = contactPhoneDao;
    }

    public static ContactPhoneDto toDto(ContactPhone contactPhone) {
        if (contactPhone == null)
            throw new NullPointerException("Contact phone is null");
        ContactPhoneDto dto = new ContactPhoneDto();
        dto.setId(contactPhone.getId());
        dto.setContactId(contactPhone.getContactId());
        dto.setPhoneTypeId(contactPhone.getPhoneTypeId());
        dto.setNumber(contactPhone.getValue());
        return dto;
    }

    public ContactPhone findById(Long id) throws EntityNotFoundException {
        return contactPhoneDao.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public ContactPhone save(ContactPhone contact) {
        return contactPhoneDao.save(contact);
    }

    public Page<ContactPhone> findAllByAndContactId(Pageable pageable, Long contactId) {
        return contactPhoneDao.findAllByAndContactId(pageable, contactId);
    }

    public ContactPhone findByIdAndContactId(long id, long contactId) throws ContactPhoneNotFoundException {
        return contactPhoneDao.findByIdAndContactId(id, contactId).orElseThrow(ContactPhoneNotFoundException::new);
    }

    public void deleteByIdAndContactId(long id, long contactId) {
        contactPhoneDao.deleteByIdAndContactId(id, contactId);
    }
}
