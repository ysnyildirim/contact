package com.yil.contact.model;

import com.yil.contact.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ContactAddress")
public class ContactAddress extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "ContactAddress_Sequence_Generator",
            sequenceName = "Seq_ContactAddress",
            allocationSize = 1)
    @GeneratedValue(generator = "ContactAddress_Sequence_Generator")
    @Column(name = "Id")
    private Long id;

    @Column(name = "ContactId")
    private Long contactId;

    @Column(name = "AddressTypeId")
    private Long addressTypeId;

    @Column(name = "CountryId")
    private Long countryId;

    @Column(name = "CityId")
    private Long cityId;

    @Column(name = "DistrictId")
    private Long districtId;

    @Column(name = "StreetId")
    private Long streetId;

    @Column(name = "ExteriorDoorId")
    private Long exteriorDoorId;

    @Column(name = "InteriorDoorId")
    private Long interiorDoorId;
}
