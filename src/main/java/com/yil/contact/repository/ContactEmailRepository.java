package com.yil.contact.repository;

import com.yil.contact.model.ContactEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactEmailRepository extends JpaRepository<ContactEmail, Long> {
    Page<ContactEmail> findAllByContactIdAndDeletedTimeIsNull(Pageable pageable, Long contactId);
}
