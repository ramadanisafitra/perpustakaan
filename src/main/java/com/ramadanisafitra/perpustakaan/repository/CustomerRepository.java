package com.ramadanisafitra.perpustakaan.repository;

import com.ramadanisafitra.perpustakaan.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer ,Long> {

    boolean existsByIdCardNo(Long idCardNo);

    Optional<Customer> findByIdCardNo(Long idCardNo);
}
