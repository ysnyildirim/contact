package com.yil.contact.model;

import com.yil.contact.base.AbstractEntity;
import com.yil.contact.base.IEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "CONTACT_PHONE")
public class ContactPhone implements IEntity {
    @Id
    @SequenceGenerator(name = "CONTACT_PHONE_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_CONTACT_PHONE_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "CONTACT_PHONE_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "CONTACT_ID", nullable = false)
    private Long contactId;
    @Column(name = "NUMBER", nullable = false)
    private Long number;
    @Column(name = "PHONE_TYPE_ID", nullable = false)
    private Long phoneTypeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
}
