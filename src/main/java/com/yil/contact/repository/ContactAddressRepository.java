package com.yil.contact.repository;

import com.yil.contact.model.ContactAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactAddressRepository extends JpaRepository<ContactAddress, Long> {
    Page<ContactAddress> findAllByAndContactIdAndDeletedTimeIsNull(Pageable pageable, Long contactId);
}
