package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.CreateContactEmailDto;
import com.yil.contact.dto.ContactEmailDto;
import com.yil.contact.model.ContactEmail;
import com.yil.contact.service.ContactEmailService;
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
@RequestMapping(value = "/api/contact/v1/contacts/{contactId}/emails")
public class ContactEmailController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final ContactEmailService contactEmailService;

    @Autowired
    public ContactEmailController(ContactEmailService contactEmailService) {
        this.contactEmailService = contactEmailService;
    }

    @GetMapping
    public ResponseEntity<PageDto<ContactEmailDto>> findAll(
            @PathVariable Long contactId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<ContactEmail> contactPage = contactEmailService.findAllByContactIdAndDeletedTimeIsNull(pageable,contactId);
            PageDto<ContactEmailDto> pageDto = PageDto.toDto(contactPage, ContactEmailService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactEmailDto> findById(
            @PathVariable Long contactId,
            @PathVariable Long id) {
        try {
            ContactEmail contact;
            try {
                contact = contactEmailService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!contact.getContactId().equals(contactId))
                return ResponseEntity.notFound().build();
            ContactEmailDto dto = ContactEmailService.toDto(contact);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactEmailId,
                                 @PathVariable Long contactId,
                                 @Valid @RequestBody CreateContactEmailDto dto) {
        try {
            ContactEmail entity = new ContactEmail();
            entity.setContactId(contactId);
            entity.setEmail(dto.getEmail());
            entity.setCreatedUserId(authenticatedContactEmailId);
            entity.setCreatedTime(new Date());
            entity = contactEmailService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactEmailId,
                                  @PathVariable Long contactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactEmailDto dto) {
        try {
            ContactEmail entity = null;
            try {
                entity = contactEmailService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getContactId().equals(contactId))
                return ResponseEntity.notFound().build();
            entity.setEmail(dto.getEmail());
            entity = contactEmailService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactEmailId,
                                         @PathVariable Long contactId,
                                         @PathVariable Long id) {
        try {
            ContactEmail entity;
            try {
                entity = contactEmailService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getContactId().equals(contactId))
                return ResponseEntity.notFound().build();
            entity.setDeletedUserId(authenticatedContactEmailId);
            entity.setDeletedTime(new Date());
            entity = contactEmailService.save(entity);
            return ResponseEntity.ok("Contact email deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
