package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.Mapper;
import com.yil.contact.base.PageDto;
import com.yil.contact.dto.ContactAddressDto;
import com.yil.contact.dto.CreateContactAddressDto;
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
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cnt/v1/contacts/{contactId}/address")
public class ContactAddressController {
    private final ContactAddressService contactAddressService;
    private final AddressTypeService addressTypeService;
    private final Mapper<ContactAddress, ContactAddressDto> mapper = new Mapper<>(ContactAddressService::toDto);

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
        return ResponseEntity.ok(mapper.map(contactAddressService.findAllByAndContactId(pageable, contactId)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactAddressDto> findById(
            @PathVariable Long contactId,
            @PathVariable Long id) throws ContactAddressNotFoundException {
        return ResponseEntity.ok(mapper.map(contactAddressService.findByIdAndContactId(id, contactId)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactAddressId,
                                 @PathVariable Long contactId,
                                 @Valid @RequestBody CreateContactAddressDto dto) throws AddressTypeNotFoundException {
        if (!addressTypeService.existsById(dto.getAddressTypeId()))
            throw new AddressTypeNotFoundException();
        ContactAddress entity = new ContactAddress();
        entity.setContactId(contactId);
        entity.setAddressTypeId(dto.getAddressTypeId());
        entity.setCountryId(dto.getCountryId());
        entity.setCityId(dto.getCityId());
        entity.setDistrictId(dto.getDistrictId());
        entity.setStreetId(dto.getStreetId());
        entity.setExteriorDoorId(dto.getExteriorDoorId());
        entity.setInteriorDoorId(dto.getInteriorDoorId());
        entity.setCreatedUserId(authenticatedContactAddressId);
        entity.setCreatedTime(new Date());
        entity = contactAddressService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactAddressId,
                                  @PathVariable Long contactId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateContactAddressDto dto) throws AddressTypeNotFoundException, ContactAddressNotFoundException {
        if (!addressTypeService.existsById(dto.getAddressTypeId()))
            throw new AddressTypeNotFoundException();
        ContactAddress entity = contactAddressService.findByIdAndContactId(id, contactId);
        entity.setAddressTypeId(dto.getAddressTypeId());
        entity.setCountryId(dto.getCountryId());
        entity.setCityId(dto.getCityId());
        entity.setDistrictId(dto.getDistrictId());
        entity.setStreetId(dto.getStreetId());
        entity.setExteriorDoorId(dto.getExteriorDoorId());
        entity.setInteriorDoorId(dto.getInteriorDoorId());
        entity = contactAddressService.save(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedContactAddressId,
                                         @PathVariable Long contactId,
                                         @PathVariable Long id) {
        contactAddressService.deleteByIdAndContactId(id, contactId);
        return ResponseEntity.ok("Contact address deleted.");
    }
}
