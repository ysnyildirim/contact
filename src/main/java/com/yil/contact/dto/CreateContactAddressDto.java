package com.yil.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactAddressDto {
    @NotNull
    private Long addressTypeId;
    private Long countryId;
    private Long cityId;
    private Long districtId;
    private Long streetId;
    private Long exteriorDoorId;
    private Long interiorDoorId;
}
