package com.yil.contact.model;

import com.yil.contact.base.IEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "CONTACT_PHONE",
        schema = "CNT")
public class ContactPhone implements IEntity {
    @Id
    @SequenceGenerator(name = "CONTACT_PHONE_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_CONTACT_PHONE_ID",
            schema = "CNT")
    @GeneratedValue(generator = "CONTACT_PHONE_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "CONTACT_ID", nullable = false)
    private Long contactId;
    @Column(name = "VALUE", nullable = false)
    private Long value;
    @Column(name = "PHONE_TYPE_ID", nullable = false)
    private Long phoneTypeId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
}
