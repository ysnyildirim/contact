package com.yil.contact.repository;

import com.yil.contact.model.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {
    List<AddressType> findAllByDeletedTimeIsNull();

}
