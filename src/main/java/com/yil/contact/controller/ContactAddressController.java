package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.ContactAddressDto;
import com.yil.contact.dto.CreateContactAddressDto;
import com.yil.contact.model.AddressType;
import com.yil.contact.model.ContactAddress;
import com.yil.contact.service.AddressTypeService;
import com.yil.contact.service.ContactAddressService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "v1/contacts/{contactId}/address")
public class ContactAddressController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final ContactAddressService contactAddressService;
    private final AddressTypeService addressTypeService;

    @Autowired
    public ContactAddressController(ContactAddressService contactAddressService, AddressTypeService addressTypeService) {
        this.contactAddressService = contactAddressService;
        this.addressTypeService = addressTypeService;
    }

    @GetMapping
    public ResponseEntity<PageDto<ContactAddressDto>> findAll(
            @PathVariable Long contactId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<ContactAddress> contactPage = contactAddressService.findAllByAndContactIdAndDeletedTimeIsNull(pageable, contactId);
            PageDto<ContactAddressDto> pageDto = PageDto.toDto(contactPage, ContactAddressService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactAddressDto> findById(
            @PathVariable Long contactId,
            @PathVariable Long id) {
        try {
            ContactAddress entity;
            try {
                entity = contactAddressService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getContactId().equals(contactId))
                return ResponseEntity.notFound().build();
            ContactAddressDto dto = ContactAddressService.toDto(entity);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactAddressId,
                                 @PathVariable Long contactId,
                                 @Valid @RequestBody CreateContactAddressDto dto) {
        try {
            AddressType addressType = null;
            try {
                addressType = addressTypeService.findById(dto.getAddressTypeId());
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            ContactAddress entity = new ContactAddress();
            entity.setContactId(contactId);
            entity.setAddressTypeId(addressType.getId());
            entity.setCountryId(dto.getCountryId());
            entity.setCityId(dto.getCityId());
            entity.setDistrictId(dto.getDistrictId());
            entity.setStreetId(dto.getStreetId());
            entity.setExteriorDoorId(dto.getExteriorDoorId());
            entity.setInteriorDoorId(dto.getInteriorDoorId());
            entity.setCreatedUserId(authenticatedContactAddressId);
            entity.setCreatedTime(new Date());
            entity = contactAddressService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactAddressId,
                                  @PathVariable Long contactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactAddressDto dto) {
        try {
            AddressType addressType = null;
            try {
                addressType = addressTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            ContactAddress entity = null;
            try {
                entity = contactAddressService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getContactId().equals(contactId))
                return ResponseEntity.notFound().build();
            entity.setAddressTypeId(addressType.getId());
            entity.setCountryId(dto.getCountryId());
            entity.setCityId(dto.getCityId());
            entity.setDistrictId(dto.getDistrictId());
            entity.setStreetId(dto.getStreetId());
            entity.setExteriorDoorId(dto.getExteriorDoorId());
            entity.setInteriorDoorId(dto.getInteriorDoorId());
            entity = contactAddressService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactAddressId,
                                         @PathVariable Long contactId,
                                         @PathVariable Long id) {
        try {
            ContactAddress entity;
            try {
                entity = contactAddressService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getContactId().equals(contactId))
                return ResponseEntity.notFound().build();
            entity.setDeletedUserId(authenticatedContactAddressId);
            entity.setDeletedTime(new Date());
            entity = contactAddressService.save(entity);
            return ResponseEntity.ok("Contact address deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
