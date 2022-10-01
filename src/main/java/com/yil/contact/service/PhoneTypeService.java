package com.yil.contact.service;

import com.yil.contact.dto.PhoneTypeDto;
import com.yil.contact.exception.PhoneTypeNotFoundException;
import com.yil.contact.model.PhoneType;
import com.yil.contact.repository.PhoneTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneTypeService {

    private final PhoneTypeDao phoneTypeDao;

    @Autowired
    public PhoneTypeService(PhoneTypeDao phoneTypeDao) {
        this.phoneTypeDao = phoneTypeDao;
    }

    public static PhoneTypeDto toDto(PhoneType f) {
        if (f == null)
            throw new NullPointerException("Phone type is null");
        PhoneTypeDto dto = new PhoneTypeDto();
        dto.setName(f.getName());
        dto.setId(f.getId());
        return dto;
    }

    public PhoneType save(PhoneType phoneType) {
        return phoneTypeDao.save(phoneType);
    }

    public void deleteById(long id) {
        phoneTypeDao.deleteById(id);
    }

    public PhoneType findById(Long id) throws PhoneTypeNotFoundException {
        return phoneTypeDao.findById(id).orElseThrow(PhoneTypeNotFoundException::new);
    }

    public List<PhoneType> findAll() {
        return phoneTypeDao.findAll();
    }
}
