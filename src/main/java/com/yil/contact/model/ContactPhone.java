package com.yil.contact.model;

import com.yil.contact.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ContactPhone")
public class ContactPhone extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "ContactPhone_Sequence_Generator",
            sequenceName = "Seq_ContactPhone",
            allocationSize = 1)
    @GeneratedValue(generator = "ContactPhone_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "ContactId", nullable = false)
    private Long contactId;
    @Column(name = "PhoneId", nullable = false)
    private Long number;
    @Column(name = "PhoneTypeId", nullable = false)
    private Long phoneTypeId;

}
