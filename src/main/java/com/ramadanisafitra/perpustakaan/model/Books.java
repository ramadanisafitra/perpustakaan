package com.ramadanisafitra.perpustakaan.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="tbl_book")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    @NotBlank(message = "Title is required")
    private String title;

    @Column(name = "isbn_number")
    @NotBlank(message = "ISBN number is required")
    private String isbnNumber;

    @Column(name = "book_stock")
    @NotNull(message = "Book stock is required")
    private Long bookStock;

}
