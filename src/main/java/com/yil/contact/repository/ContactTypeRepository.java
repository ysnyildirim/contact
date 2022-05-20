package com.yil.contact.repository;

import com.yil.contact.model.ContactType;
import com.yil.contact.model.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {
    List<ContactType> findAllByDeletedTimeIsNull();

}
