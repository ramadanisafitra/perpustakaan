package com.ramadanisafitra.perpustakaan.service;


import com.ramadanisafitra.perpustakaan.exception.NotFoundException;
import com.ramadanisafitra.perpustakaan.model.Books;
import com.ramadanisafitra.perpustakaan.model.Customer;
import com.ramadanisafitra.perpustakaan.repository.BookRepository;
import com.ramadanisafitra.perpustakaan.repository.CustumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerpustakaanService {


    @Autowired
    BookRepository bookRepository;
    @Autowired
    CustumeRepository custumeRepository;


    public Books inputBookData(Books books) {
        if (books.getTitle() == null || books.getIsbnNumber() == null || books.getBookStock() == null) {
            throw new IllegalArgumentException("All fields must be provided.");
        }
        return bookRepository.save(books);
    }

    public Books getBookDataById(Long id) {
        Optional<Books> books = bookRepository.findById(id);
        if (books.isPresent()) {
            return books.get();
        } else {
            throw new NotFoundException("Book not found");
        }
    }

    public List<Books> getAllBookData() {
        return bookRepository.findAll();
    }

    public Customer inputBorrowerData(Customer customer) {
        if (customer.getCustname() == null || customer.getIdCardNo() == null) {
            throw new IllegalArgumentException("All fields must be provided.");
        }

        // Check if IdCardNo already exists in the database
        if (custumeRepository.existsByIdCardNo(customer.getIdCardNo())) {
            throw new IllegalArgumentException("IdCardNo already exists.");
        }

        return custumeRepository.save(customer);
    }


}
