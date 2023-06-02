package com.ramadanisafitra.perpustakaan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookRequest {

    private Long bookId;
    private Long customerId;
    private String borrowDate;

}
