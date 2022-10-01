package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.Mapper;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.ContactPhoneDto;
import com.yil.contact.dto.CreateContactPhoneDto;
import com.yil.contact.exception.ContactPhoneNotFoundException;
import com.yil.contact.model.ContactPhone;
import com.yil.contact.service.ContactPhoneService;
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
@RequestMapping(value = "/api/cnt/v1/contacts/{contactId}/phones")
public class ContactPhoneController {

    private final ContactPhoneService contactPhoneService;
    private final Mapper<ContactPhone, ContactPhoneDto> mapper = new Mapper<>(ContactPhoneService::toDto);

    @GetMapping
    public ResponseEntity<PageDto<ContactPhoneDto>> findAll(
            @PathVariable Long contactId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 1000)
            size = 1000;
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(mapper.map(contactPhoneService.findAllByAndContactId(pageable, contactId)));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactPhoneDto> findById(
            @PathVariable Long contactId,
            @PathVariable Long id) throws ContactPhoneNotFoundException {
        return ResponseEntity.ok(mapper.map(contactPhoneService.findByIdAndContactId(id, contactId)));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactPhoneId,
                                 @PathVariable Long contactId,
                                 @Valid @RequestBody CreateContactPhoneDto dto) {
        ContactPhone entity = new ContactPhone();
        entity.setContactId(contactId);
        entity.setValue(dto.getValue());
        entity.setPhoneTypeId(dto.getPhoneTypeId());
        entity.setCreatedUserId(authenticatedContactPhoneId);
        entity.setCreatedTime(new Date());
        entity = contactPhoneService.save(entity);
        return ResponseEntity.created(null).build();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactPhoneId,
                                  @PathVariable Long contactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactPhoneDto dto) throws ContactPhoneNotFoundException {
        ContactPhone entity = contactPhoneService.findByIdAndContactId(id, contactId);
        entity.setPhoneTypeId(dto.getPhoneTypeId());
        entity.setValue(dto.getValue());
        entity = contactPhoneService.save(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@PathVariable Long contactId,
                                         @PathVariable Long id) {
        contactPhoneService.deleteByIdAndContactId(id, contactId);
        return ResponseEntity.ok("Contact phone deleted.");
    }


}
