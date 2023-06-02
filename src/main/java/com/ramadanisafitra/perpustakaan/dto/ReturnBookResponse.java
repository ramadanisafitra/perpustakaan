package com.ramadanisafitra.perpustakaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBookResponse {

    private String status;
    private String message;
}
