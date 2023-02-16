package com.yil.contact.repository;

import com.yil.contact.model.ContactAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ContactAddressDao extends JpaRepository<ContactAddress, Long> {
    Page<ContactAddress> findAllByContactId(Pageable pageable, Long contactId);

    Optional<ContactAddress> findByIdAndContactId(Long id, long contactId);

}
