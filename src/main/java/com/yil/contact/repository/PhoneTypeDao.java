package com.yil.contact.repository;

import com.yil.contact.model.PhoneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneTypeDao extends JpaRepository<PhoneType, Long> {


}
