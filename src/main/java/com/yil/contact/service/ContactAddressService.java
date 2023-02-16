package com.yil.contact.service;

import com.yil.contact.base.Mapper;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.ContactAddressDto;
import com.yil.contact.dto.ContactAdressRequest;
import com.yil.contact.exception.AddressTypeNotFoundException;
import com.yil.contact.exception.ContactAddressNotFoundException;
import com.yil.contact.model.ContactAddress;
import com.yil.contact.repository.ContactAddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContactAddressService {
    private final ContactAddressDao contactAddressDao;
    private final AddressTypeService addressTypeService;
    private final Mapper<ContactAddress, ContactAddressDto> mapper = new Mapper<>(ContactAddressService::toDto);

    @Autowired
    public ContactAddressService(ContactAddressDao contactAddressDao, AddressTypeService addressTypeService) {
        this.contactAddressDao = contactAddressDao;
        this.addressTypeService = addressTypeService;
    }

    public static ContactAddressDto toDto(ContactAddress entity) {
        if (entity == null)
            throw new NullPointerException("Contact address is null");
        ContactAddressDto dto = new ContactAddressDto();
        dto.setId(entity.getId());
        dto.setContactId(entity.getContactId());
        dto.setAddressTypeId(entity.getAddressTypeId());
        dto.setRegionId(entity.getRegionId());
        return dto;
    }

    public ContactAddressDto findById(Long id) throws ContactAddressNotFoundException {
        return mapper.map(contactAddressDao.findById(id).orElseThrow(ContactAddressNotFoundException::new));
    }

    public PageDto<ContactAddressDto> findAllByContactId(Pageable pageable, Long contactId) {
        return mapper.map(contactAddressDao.findAllByContactId(pageable, contactId));
    }

    public ContactAddressDto findByIdAndContactId(Long id, long contactId) throws ContactAddressNotFoundException {
        return mapper.map(contactAddressDao.findByIdAndContactId(id, contactId).orElseThrow(ContactAddressNotFoundException::new));
    }

    public void deleteById(long id) {
        contactAddressDao.deleteById(id);
    }

    public ContactAddressDto save(ContactAdressRequest request, Long contactId, Long authenticatedUserId) throws AddressTypeNotFoundException {
        if (!addressTypeService.existsById(request.getAddressTypeId()))
            throw new AddressTypeNotFoundException();
        ContactAddress entity = new ContactAddress();
        entity.setContactId(contactId);
        entity.setAddressTypeId(request.getAddressTypeId());
        entity.setRegionId(request.getRegionId());
        entity.setCreatedUserId(authenticatedUserId);
        entity.setCreatedTime(new Date());
        entity = contactAddressDao.save(entity);
        return mapper.map(entity);
    }

    public ContactAddressDto replace(Long id, ContactAdressRequest request, Long authenticatedUserId) throws AddressTypeNotFoundException, ContactAddressNotFoundException {
        if (!addressTypeService.existsById(request.getAddressTypeId()))
            throw new AddressTypeNotFoundException();
        ContactAddress entity = contactAddressDao.findById(id).orElseThrow(ContactAddressNotFoundException::new);
        entity.setAddressTypeId(request.getAddressTypeId());
        entity.setRegionId(request.getRegionId());
        entity.setLastModifyUserId(authenticatedUserId);
        entity.setLastModifyDate(new Date());
        entity = contactAddressDao.save(entity);
        return mapper.map(entity);
    }


}
