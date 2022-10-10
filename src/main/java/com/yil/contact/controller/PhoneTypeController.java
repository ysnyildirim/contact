package com.yil.contact.controller;

import com.yil.contact.base.ApiConstant;
import com.yil.contact.base.Mapper;
import com.yil.contact.dto.CreatePhoneTypeDto;
import com.yil.contact.dto.PhoneTypeDto;
import com.yil.contact.exception.PhoneTypeNotFoundException;
import com.yil.contact.model.PhoneType;
import com.yil.contact.service.PhoneTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cnt/v1/phone-types")
public class PhoneTypeController {
    private final Mapper<PhoneType, PhoneTypeDto> mapper = new Mapper<>(PhoneTypeService::toDto);
    private final PhoneTypeService phoneTypeService;

    @Cacheable("phone-type")
    @GetMapping
    public ResponseEntity<List<PhoneTypeDto>> findAll() {
        return ResponseEntity.ok(mapper.map(phoneTypeService.findAll()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PhoneTypeDto> findById(@PathVariable Long id) throws PhoneTypeNotFoundException {
        return ResponseEntity.ok(mapper.map(phoneTypeService.findById(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedPhoneId,
                                 @Valid @RequestBody CreatePhoneTypeDto dto) {
        PhoneType phoneType = new PhoneType();
        phoneType.setName(dto.getName());
        phoneType = phoneTypeService.save(phoneType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedPhoneId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreatePhoneTypeDto dto) throws PhoneTypeNotFoundException {
        PhoneType phoneType = phoneTypeService.findById(id);
        phoneType.setName(dto.getName());
        phoneType = phoneTypeService.save(phoneType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedPhoneId,
                                         @PathVariable Long id) {
        phoneTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
