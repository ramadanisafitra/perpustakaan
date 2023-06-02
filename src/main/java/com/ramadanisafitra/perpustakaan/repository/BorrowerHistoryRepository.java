package com.ramadanisafitra.perpustakaan.repository;

import com.ramadanisafitra.perpustakaan.model.Books;
import com.ramadanisafitra.perpustakaan.model.BorrowerHistory;
import com.ramadanisafitra.perpustakaan.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowerHistoryRepository extends JpaRepository<BorrowerHistory, Long> {
    List<BorrowerHistory> findByCustomer(Customer customer);
    List<BorrowerHistory> findByCustomerAndIsDeleted(Customer customer, boolean isDeleted);
    List<BorrowerHistory> findByIsDeletedFalse();
}