package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.Mapper;
import com.yil.contact.dto.AddressTypeDto;
import com.yil.contact.dto.CreateAddressTypeDto;
import com.yil.contact.exception.AddressTypeNotFoundException;
import com.yil.contact.model.AddressType;
import com.yil.contact.service.AddressTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cnt/v1/address-types")
public class AddressTypeController {

    private final AddressTypeService addressTypeService;
    private final Mapper<AddressType, AddressTypeDto> mapper = new Mapper<>(AddressTypeService::toDto);

    @GetMapping
    public ResponseEntity<List<AddressTypeDto>> findAll() {
        return ResponseEntity.ok(mapper.map(addressTypeService.findAll()));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressTypeDto> findById(@PathVariable Long id) throws AddressTypeNotFoundException {
        return ResponseEntity.ok(mapper.map(addressTypeService.findById(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedAddressId,
                                 @Valid @RequestBody CreateAddressTypeDto dto) {
        AddressType addressType = new AddressType();
        addressType.setName(dto.getName());
        addressType = addressTypeService.save(addressType);
        return ResponseEntity.created(null).build();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedAddressId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateAddressTypeDto dto) throws AddressTypeNotFoundException {
        AddressType addressType = addressTypeService.findById(id);
        addressType.setName(dto.getName());
        addressType = addressTypeService.save(addressType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedAddressId,
                                         @PathVariable Long id) {
        addressTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
