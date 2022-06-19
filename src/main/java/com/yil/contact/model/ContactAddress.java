package com.yil.contact.model;

import com.yil.contact.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CONTACT_ADDRESS")
public class ContactAddress extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "CONTACT_ADDRESS_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_CONTACT_ADDRESS_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "CONTACT_ADDRESS_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTACT_ID")
    private Long contactId;

    @Column(name = "ADDRESS_TYPE_ID")
    private Long addressTypeId;

    @Column(name = "COUNTRY_ID")
    private Long countryId;

    @Column(name = "CITY_ID")
    private Long cityId;

    @Column(name = "DISTRICT_ID")
    private Long districtId;

    @Column(name = "STREET_ID")
    private Long streetId;

    @Column(name = "EXTERIOR_DOOR_ID")
    private Long exteriorDoorId;

    @Column(name = "INTERIOR_DOOR_ID")
    private Long interiorDoorId;
}
