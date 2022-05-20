package com.yil.contact.repository;

import com.yil.contact.model.ContactPhone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPhoneRepository extends JpaRepository<ContactPhone, Long> {
    Page<ContactPhone> findAllByAndContactIdAndDeletedTimeIsNull(Pageable pageable,Long contactId);
}
