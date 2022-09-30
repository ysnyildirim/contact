package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.CreateContactPhoneDto;
import com.yil.contact.dto.ContactPhoneDto;
import com.yil.contact.model.ContactPhone;
import com.yil.contact.service.ContactPhoneService;
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
@RequestMapping(value = "/api/contact/v1/contacts/{contactId}/phones")
public class ContactPhoneController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final ContactPhoneService contactPhoneService;

    @Autowired
    public ContactPhoneController(ContactPhoneService contactPhoneService) {
        this.contactPhoneService = contactPhoneService;
    }

    @GetMapping
    public ResponseEntity<PageDto<ContactPhoneDto>> findAll(
            @PathVariable Long contactId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<ContactPhone> contactPage = contactPhoneService.findAllByAndContactIdAndDeletedTimeIsNull(pageable, contactId);
            PageDto<ContactPhoneDto> pageDto = PageDto.toDto(contactPage, ContactPhoneService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactPhoneDto> findById(
            @PathVariable Long contactId,
            @PathVariable Long id) {
        try {
            ContactPhone contact;
            try {
                contact = contactPhoneService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!contact.getContactId().equals(contactId))
                return ResponseEntity.notFound().build();
            ContactPhoneDto dto = ContactPhoneService.toDto(contact);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactPhoneId,
                                 @PathVariable Long contactId,
                                 @Valid @RequestBody CreateContactPhoneDto dto) {
        try {
            ContactPhone entity = new ContactPhone();
            entity.setContactId(contactId);
            entity.setNumber(dto.getNumber());
            entity.setPhoneTypeId(dto.getPhoneTypeId());
            entity.setCreatedUserId(authenticatedContactPhoneId);
            entity.setCreatedTime(new Date());
            entity = contactPhoneService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactPhoneId,
                                  @PathVariable Long contactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactPhoneDto dto) {
        try {
            ContactPhone entity = null;
            try {
                entity = contactPhoneService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getContactId().equals(contactId))
                return ResponseEntity.notFound().build();
            entity.setPhoneTypeId(dto.getPhoneTypeId());
            entity.setNumber(dto.getNumber());
            entity = contactPhoneService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactPhoneId,
                                         @PathVariable Long contactId,
                                         @PathVariable Long id) {
        try {
            ContactPhone entity;
            try {
                entity = contactPhoneService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getContactId().equals(contactId))
                return ResponseEntity.notFound().build();
            entity = contactPhoneService.save(entity);
            return ResponseEntity.ok("Contact phone deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
