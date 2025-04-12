package com.fetch.receiptproc.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class Item {


    private String id;

    @NotBlank(message = "ShortDescription cannot be blank")
    @Pattern(regexp = "^[\\w\\s\\-]+$")
    private String shortDescription;

    @NotBlank(message = "Price cannot be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String price;
}
