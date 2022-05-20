package com.yil.contact.service;

import com.yil.contact.dto.ContactAddressDto;
import com.yil.contact.model.ContactAddress;
import com.yil.contact.repository.ContactAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ContactAddressService {
    private final ContactAddressRepository contactAddressRepository;

    @Autowired
    public ContactAddressService(ContactAddressRepository contactAddressRepository) {
        this.contactAddressRepository = contactAddressRepository;
    }

    public ContactAddress findById(Long id) throws EntityNotFoundException {
        return contactAddressRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public ContactAddress save(ContactAddress contact) {
        return contactAddressRepository.save(contact);
    }

    public Page<ContactAddress> findAllByAndContactIdAndDeletedTimeIsNull(Pageable pageable,Long contactId) {
        return contactAddressRepository.findAllByAndContactIdAndDeletedTimeIsNull(pageable,contactId);
    }

    public static ContactAddressDto toDto(ContactAddress entity) {
        if (entity == null)
            throw new NullPointerException("Contact address is null");
        ContactAddressDto dto = new ContactAddressDto();
        dto.setId(entity.getId());
        dto.setContactId(entity.getContactId());
        dto.setAddressTypeId(entity.getAddressTypeId());
        dto.setCountryId(entity.getCountryId());
        dto.setCityId(entity.getCityId());
        dto.setDistrictId(entity.getDistrictId());
        dto.setStreetId(entity.getStreetId());
        dto.setExteriorDoorId(entity.getExteriorDoorId());
        dto.setInteriorDoorId(entity.getInteriorDoorId());
        return dto;
    }
}
