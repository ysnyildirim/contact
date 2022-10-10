package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.Mapper;
import com.yil.contact.dto.ContactTypeDto;
import com.yil.contact.dto.CreateContactTypeDto;
import com.yil.contact.exception.ContactTypeNotFoundException;
import com.yil.contact.model.ContactType;
import com.yil.contact.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cnt/v1/contact-types")
public class ContactTypeController {
    private final ContactTypeService contactTypeService;
    private final Mapper<ContactType, ContactTypeDto> mapper = new Mapper<>(ContactTypeService::toDto);

    @Cacheable("contact-type")
    @GetMapping
    public ResponseEntity<List<ContactTypeDto>> findAll() {
        return ResponseEntity.ok(mapper.map(contactTypeService.findAll()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactTypeDto> findById(@PathVariable Long id) throws ContactTypeNotFoundException {
        return ResponseEntity.ok(mapper.map(contactTypeService.findById(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                 @Valid @RequestBody CreateContactTypeDto dto) {
        ContactType contactType = new ContactType();
        contactType.setName(dto.getName());
        contactType = contactTypeService.save(contactType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactTypeDto dto) throws ContactTypeNotFoundException {
        ContactType contactType = contactTypeService.findById(id);
        contactType.setName(dto.getName());
        contactType = contactTypeService.save(contactType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactId,
                                         @PathVariable Long id) {
        contactTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
