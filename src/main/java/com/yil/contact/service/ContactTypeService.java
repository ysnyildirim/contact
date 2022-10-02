package com.yil.contact.service;

import com.yil.contact.dto.ContactTypeDto;
import com.yil.contact.exception.ContactTypeNotFoundException;
import com.yil.contact.model.ContactType;
import com.yil.contact.repository.ContactTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactTypeService {
    private final ContactTypeDao contactTypeDao;

    @Autowired
    public ContactTypeService(ContactTypeDao contactTypeDao) {
        this.contactTypeDao = contactTypeDao;
    }

    public static ContactTypeDto toDto(ContactType f) {
        if (f == null)
            throw new NullPointerException("Contact type is null");
        ContactTypeDto dto = new ContactTypeDto();
        dto.setName(f.getName());
        dto.setId(f.getId());
        return dto;
    }

    public ContactType save(ContactType contactType) {
        return contactTypeDao.save(contactType);
    }

    public void deleteById(Long id) {
        contactTypeDao.deleteById(id);
    }

    public ContactType findById(Long id) throws ContactTypeNotFoundException {
        return contactTypeDao.findById(id).orElseThrow(ContactTypeNotFoundException::new);
    }

    public List<ContactType> findAll() {
        return contactTypeDao.findAll();
    }

    public boolean existsById(Long id) {
        return contactTypeDao.existsById(id);
    }
}
