package com.ramadanisafitra.perpustakaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerHistoryResponse {
    private String customerName;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}