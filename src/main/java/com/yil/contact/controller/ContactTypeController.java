package com.yil.contact.controller;

import com.yil.contact.base.ApiHeaders;
import com.yil.contact.dto.CreateContactTypeDto;
import com.yil.contact.dto.ContactTypeDto;
import com.yil.contact.model.ContactType;
import com.yil.contact.service.ContactTypeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "v1/contact-types")
public class ContactTypeController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final ContactTypeService contactTypeService;

    @Autowired
    public ContactTypeController(ContactTypeService contactTypeService) {
        this.contactTypeService = contactTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ContactTypeDto>> findAll() {
        try {
            List<ContactType> data = contactTypeService.findAllByDeletedTimeIsNull();
            List<ContactTypeDto> dtoData = new ArrayList<>();
            data.forEach(f -> {
                dtoData.add(ContactTypeService.toDto(f));
            });
            return ResponseEntity.ok(dtoData);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactTypeDto> findById(@PathVariable Long id) {
        try {
            ContactType contactType = contactTypeService.findById(id);
            ContactTypeDto dto = ContactTypeService.toDto(contactType);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {

            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                 @Valid @RequestBody CreateContactTypeDto dto) {
        try {
            ContactType contactType = new ContactType();
            contactType.setName(dto.getName()); 
            contactType.setCreatedUserId(authenticatedContactId);
            contactType.setCreatedTime(new Date());
            contactType = contactTypeService.save(contactType);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactTypeDto dto) {
        try {
            ContactType contactType;
            try {
                contactType = contactTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            } 
            contactType.setName(dto.getName());
            contactType = contactTypeService.save(contactType);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                         @PathVariable Long id) {
        try {
            ContactType contactType;
            try {
                contactType = contactTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            contactType.setDeletedUserId(authenticatedContactId);
            contactType.setDeletedTime(new Date());
            contactTypeService.save(contactType);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

}
