package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.ContactDto;
import com.yil.contact.dto.CreateContactDto;
import com.yil.contact.model.Contact;
import com.yil.contact.model.ContactType;
import com.yil.contact.service.ContactService;
import com.yil.contact.service.ContactTypeService;
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
@RequestMapping(value = "v1/contacts")
public class ContactController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final ContactService contactService;
    private final ContactTypeService contactTypeService;

    @Autowired
    public ContactController(ContactService contactService, ContactTypeService contactTypeService) {
        this.contactService = contactService;
        this.contactTypeService = contactTypeService;
    }

    @GetMapping
    public ResponseEntity<PageDto<ContactDto>> findAll(
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<Contact> contactPage = contactService.findAllByDeletedTimeIsNull(pageable);
            PageDto<ContactDto> pageDto = PageDto.toDto(contactPage, ContactService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactDto> findById(@PathVariable Long id) {
        try {
            Contact contact;
            try {
                contact = contactService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            ContactDto dto = ContactService.toDto(contact);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                 @Valid @RequestBody CreateContactDto dto) {
        try {
            Contact contact = new Contact();
            contact.setContactTypeId(dto.getContactTypeId());
            contact.setCreatedUserId(authenticatedContactId);
            contact.setCreatedTime(new Date());
            contact = contactService.save(contact);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactDto dto) {
        try {
            ContactType contactType;
            try {
                contactType = contactTypeService.findById(dto.getContactTypeId());
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            Contact contact = null;
            try {
                contact = contactService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            contact.setContactTypeId(contactType.getId());
            contact = contactService.save(contact);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                         @PathVariable Long id) {
        try {
            Contact contact;
            try {
                contact = contactService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            contact.setDeletedUserId(authenticatedContactId);
            contact.setDeletedTime(new Date());
            contactService.save(contact);
            return ResponseEntity.ok("Contact deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
