package com.yil.contact.repository;

import com.yil.contact.model.PhoneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneTypeRepository extends JpaRepository<PhoneType, Long> {
    List<PhoneType> findAllByDeletedTimeIsNull();

}
