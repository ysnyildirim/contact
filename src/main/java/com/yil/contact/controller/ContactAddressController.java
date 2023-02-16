package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.Mapper;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.ContactAddressDto;
import com.yil.contact.dto.ContactAdressRequest;
import com.yil.contact.dto.ContactAdressResponse;
import com.yil.contact.exception.AddressTypeNotFoundException;
import com.yil.contact.exception.ContactAddressNotFoundException;
import com.yil.contact.model.ContactAddress;
import com.yil.contact.service.AddressTypeService;
import com.yil.contact.service.ContactAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cnt/v1/contacts/{contactId}/address")
public class ContactAddressController {
    private final ContactAddressService contactAddressService;

    @GetMapping
    public ResponseEntity<PageDto<ContactAddressDto>> findAll(
            @PathVariable Long contactId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 1000)
            size = 1000;
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(contactAddressService.findAllByContactId(pageable, contactId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactAddressDto> findById(
            @PathVariable Long contactId,
            @PathVariable Long id) throws ContactAddressNotFoundException {
        return ResponseEntity.ok(contactAddressService.findByIdAndContactId(id, contactId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContactAdressResponse> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                                        @PathVariable Long contactId,
                                                        @Valid @RequestBody ContactAdressRequest dto) throws AddressTypeNotFoundException {
        ContactAddressDto addressDto = contactAddressService.save(dto, contactId, authenticatedUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ContactAdressResponse.builder().id(addressDto.getId()).build());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContactAdressResponse> replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                                         @PathVariable Long id,
                                                         @Valid @RequestBody ContactAdressRequest request) throws AddressTypeNotFoundException, ContactAddressNotFoundException {
        ContactAddressDto addressDto = contactAddressService.replace(id, request, authenticatedUserId);
        return ResponseEntity.status(HttpStatus.OK).body(ContactAdressResponse.builder().id(addressDto.getId()).build());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @PathVariable Long id) {
        contactAddressService.deleteById(id);
        return ResponseEntity.ok("Contact address deleted.");
    }
}
