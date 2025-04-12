package com.fetch.receiptproc.model;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Receipt {


    private String id;

    @NotBlank(message = "Retailer cannot be blank")
    @Pattern(regexp = "^[\\w\\s\\-&]+$")
    private String retailer;

    @NotBlank(message = "Purchase Date cannot be blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String purchaseDate;

    @NotBlank(message = "Purchase time cannot be blank")
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$")
    private String purchaseTime;

    @NotBlank(message = "Total cannot be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String total;

    @NotEmpty(message = "List cannot be blank")
    @Size(min = 1)
    private List<Item> items;
}
