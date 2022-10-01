package com.yil.contact.repository;

import com.yil.contact.model.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypeDao extends JpaRepository<AddressType, Long> {

}
