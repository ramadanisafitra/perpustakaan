package com.ramadanisafitra.perpustakaan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="tbl_customer")
public class Customer {

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private Long id;

    @Column(name = "cust_name")
    private String custname;

    @Column(name = "id_card_no")
    private Long idCardNo;

    @Column(name = "email")
    private String email;
}
