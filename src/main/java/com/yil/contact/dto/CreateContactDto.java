package com.yil.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactDto {
    @NotNull
    private Long contactTypeId;
}
