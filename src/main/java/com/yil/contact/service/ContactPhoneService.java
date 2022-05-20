package com.yil.contact.service;

import com.yil.contact.dto.ContactPhoneDto;
import com.yil.contact.model.ContactPhone;
import com.yil.contact.repository.ContactPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ContactPhoneService {
    private final ContactPhoneRepository contactPhoneRepository;

    @Autowired
    public ContactPhoneService(ContactPhoneRepository contactPhoneRepository) {
        this.contactPhoneRepository = contactPhoneRepository;
    }

    public ContactPhone findById(Long id) throws EntityNotFoundException {
        return contactPhoneRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public ContactPhone save(ContactPhone contact) {
        return contactPhoneRepository.save(contact);
    }

    public Page<ContactPhone> findAllByAndContactIdAndDeletedTimeIsNull(Pageable pageable, Long contactId) {
        return contactPhoneRepository.findAllByAndContactIdAndDeletedTimeIsNull(pageable,contactId);
    }

    public static ContactPhoneDto toDto(ContactPhone contactPhone) {
        if (contactPhone == null)
            throw new NullPointerException("Contact phone is null");
        ContactPhoneDto dto = new ContactPhoneDto();
        dto.setId(contactPhone.getId());
        dto.setContactId(contactPhone.getContactId());
        dto.setPhoneTypeId(contactPhone.getPhoneTypeId());
        dto.setNumber(contactPhone.getNumber());
        return dto;
    }
}
