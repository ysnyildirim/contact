package com.yil.contact.service;

import com.yil.contact.dto.ContactAddressDto;
import com.yil.contact.exception.ContactAddressNotFoundException;
import com.yil.contact.model.ContactAddress;
import com.yil.contact.repository.ContactAddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactAddressService {
    private final ContactAddressDao contactAddressDao;

    @Autowired
    public ContactAddressService(ContactAddressDao contactAddressDao) {
        this.contactAddressDao = contactAddressDao;
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

    public ContactAddress findById(Long id) throws ContactAddressNotFoundException {
        return contactAddressDao.findById(id).orElseThrow(ContactAddressNotFoundException::new);
    }

    public ContactAddress save(ContactAddress contact) {
        return contactAddressDao.save(contact);
    }

    public Page<ContactAddress> findAllByAndContactId(Pageable pageable, Long contactId) {
        return contactAddressDao.findAllByAndContactId(pageable, contactId);
    }

    public ContactAddress findByIdAndContactId(Long id, long contactId) throws ContactAddressNotFoundException {
        return contactAddressDao.findByIdAndContactId(id, contactId).orElseThrow(ContactAddressNotFoundException::new);
    }

    public void deleteByIdAndContactId(long id, long contactId) {
        contactAddressDao.deleteByIdAndContactId(id, contactId);
    }
}
