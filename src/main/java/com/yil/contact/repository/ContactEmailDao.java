package com.yil.contact.repository;

import com.yil.contact.model.ContactEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ContactEmailDao extends JpaRepository<ContactEmail, Long> {
    Page<ContactEmail> findAllByContactId(Pageable pageable, Long contactId);

    Optional<ContactEmail> findByIdAndContactId(Long id, long contactId);

    @Modifying
    @Transactional
    void deleteByIdAndContactId(long id, long contactId);
}
