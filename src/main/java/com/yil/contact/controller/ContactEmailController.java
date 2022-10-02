package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.Mapper;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.ContactEmailDto;
import com.yil.contact.dto.CreateContactEmailDto;
import com.yil.contact.exception.ContactEmailNotFoundException;
import com.yil.contact.model.ContactEmail;
import com.yil.contact.service.ContactEmailService;
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
@RequestMapping(value = "/api/cnt/v1/contacts/{contactId}/emails")
public class ContactEmailController {
    private final ContactEmailService contactEmailService;
    private final Mapper<ContactEmail, ContactEmailDto> mapper = new Mapper<>(ContactEmailService::toDto);

    @GetMapping
    public ResponseEntity<PageDto<ContactEmailDto>> findAll(
            @PathVariable Long contactId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 1000)
            size = 1000;
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(mapper.map(contactEmailService.findAllByContactId(pageable, contactId)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactEmailDto> findById(
            @PathVariable Long contactId,
            @PathVariable Long id) throws ContactEmailNotFoundException {
        return ResponseEntity.ok(mapper.map(contactEmailService.findByIdAndContactId(id, contactId)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactEmailId,
                                 @PathVariable Long contactId,
                                 @Valid @RequestBody CreateContactEmailDto dto) {
        ContactEmail entity = new ContactEmail();
        entity.setContactId(contactId);
        entity.setEmail(dto.getEmail());
        entity.setCreatedUserId(authenticatedContactEmailId);
        entity.setCreatedTime(new Date());
        entity = contactEmailService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactEmailId,
                                  @PathVariable Long contactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactEmailDto dto) throws ContactEmailNotFoundException {
        ContactEmail entity = contactEmailService.findByIdAndContactId(id, contactId);
        entity.setEmail(dto.getEmail());
        entity = contactEmailService.save(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactEmailId,
                                         @PathVariable Long contactId,
                                         @PathVariable Long id) {
        contactEmailService.deleteByIdAndContactId(id, contactId);
        return ResponseEntity.ok("Contact email deleted.");
    }
}
