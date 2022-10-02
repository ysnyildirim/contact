package com.yil.contact.repository;

import com.yil.contact.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactTypeDao extends JpaRepository<ContactType, Long> {
}
