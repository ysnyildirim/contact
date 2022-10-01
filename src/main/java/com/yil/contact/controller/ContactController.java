package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.Mapper;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.ContactDto;
import com.yil.contact.dto.CreateContactDto;
import com.yil.contact.exception.ContactNotFoundException;
import com.yil.contact.exception.ContactTypeNotFoundException;
import com.yil.contact.model.Contact;
import com.yil.contact.service.ContactService;
import com.yil.contact.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cnt/v1/contacts")
public class ContactController {

    private final ContactService contactService;
    private final ContactTypeService contactTypeService;
    private final Mapper<Contact, ContactDto> mapper = new Mapper<>(ContactService::toDto);


    @GetMapping
    public ResponseEntity<PageDto<ContactDto>> findAll(
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 1000)
            size = 1000;
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(mapper.map(contactService.findAll(pageable)));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactDto> findById(@PathVariable Long id) throws ContactNotFoundException {
        return ResponseEntity.ok(mapper.map(contactService.findById(id)));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                 @Valid @RequestBody CreateContactDto dto) {
        Contact contact = new Contact();
        contact.setContactTypeId(dto.getContactTypeId());
        contact.setCreatedUserId(authenticatedContactId);
        contact.setCreatedTime(new Date());
        contact = contactService.save(contact);
        return ResponseEntity.created(null).build();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactDto dto) throws ContactTypeNotFoundException, ContactNotFoundException {
        if (!contactTypeService.existsById(dto.getContactTypeId()))
            throw new ContactTypeNotFoundException();
        Contact contact = contactService.findById(id);
        contact.setContactTypeId(dto.getContactTypeId());
        contact = contactService.save(contact);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                         @PathVariable Long id) {
        contactService.deleteById(id);
        return ResponseEntity.ok("Contact deleted.");
    }


}
