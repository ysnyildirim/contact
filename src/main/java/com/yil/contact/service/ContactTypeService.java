package com.yil.contact.service;

import com.yil.contact.dto.ContactTypeDto;
import com.yil.contact.model.ContactType;
import com.yil.contact.model.EmailType;
import com.yil.contact.repository.ContactTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;

    @Autowired
    public ContactTypeService(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
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
        return contactTypeRepository.save(contactType);
    }

    public ContactType findById(Long id) throws EntityNotFoundException {
        return contactTypeRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });
    }

    public List<ContactType> findAllByDeletedTimeIsNull() {
        return contactTypeRepository.findAllByDeletedTimeIsNull();
    }

}
