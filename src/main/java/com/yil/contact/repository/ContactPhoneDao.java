package com.yil.contact.repository;

import com.yil.contact.model.ContactPhone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ContactPhoneDao extends JpaRepository<ContactPhone, Long> {
    Page<ContactPhone> findAllByAndContactId(Pageable pageable, Long contactId);

    Optional<ContactPhone> findByIdAndContactId(long id, long contactId);

    @Modifying
    @Transactional
    void deleteByIdAndContactId(long id, long contactId);
}
