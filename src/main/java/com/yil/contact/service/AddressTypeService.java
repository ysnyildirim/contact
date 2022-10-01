package com.yil.contact.service;

import com.yil.contact.dto.AddressTypeDto;
import com.yil.contact.exception.AddressTypeNotFoundException;
import com.yil.contact.model.AddressType;
import com.yil.contact.repository.AddressTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressTypeService {

    private final AddressTypeDao addressTypeDao;

    @Autowired
    public AddressTypeService(AddressTypeDao addressTypeDao) {
        this.addressTypeDao = addressTypeDao;
    }

    public static AddressTypeDto toDto(AddressType f) {
        if (f == null)
            throw new NullPointerException("Address type is null");
        AddressTypeDto dto = new AddressTypeDto();
        dto.setId(f.getId());
        dto.setName(f.getName());
        return dto;
    }

    public AddressType save(AddressType addressType) {
        return addressTypeDao.save(addressType);
    }

    public void deleteById(long id) {
        addressTypeDao.deleteById(id);
    }

    public AddressType findById(Long id) throws AddressTypeNotFoundException {
        return addressTypeDao.findById(id).orElseThrow(AddressTypeNotFoundException::new);
    }

    public boolean existsById(long id) {
        return addressTypeDao.existsById(id);
    }

    public List<AddressType> findAll() {
        return addressTypeDao.findAll();
    }

}
