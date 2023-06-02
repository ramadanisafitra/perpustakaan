package com.ramadanisafitra.perpustakaan.repository;

import com.ramadanisafitra.perpustakaan.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustumeRepository extends JpaRepository<Customer ,Long> {

    boolean existsByIdCardNo(Long idCardNo);

}
