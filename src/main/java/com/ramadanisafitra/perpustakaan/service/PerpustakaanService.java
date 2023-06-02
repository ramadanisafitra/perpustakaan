package com.ramadanisafitra.perpustakaan.service;


import com.ramadanisafitra.perpustakaan.dto.BorrowBookRequest;
import com.ramadanisafitra.perpustakaan.dto.BorrowerHistoryResponse;
import com.ramadanisafitra.perpustakaan.dto.ReturnBookRequest;
import com.ramadanisafitra.perpustakaan.dto.ReturnBookResponse;
import com.ramadanisafitra.perpustakaan.exception.NotFoundException;
import com.ramadanisafitra.perpustakaan.model.Books;
import com.ramadanisafitra.perpustakaan.model.BorrowerHistory;
import com.ramadanisafitra.perpustakaan.model.Customer;
import com.ramadanisafitra.perpustakaan.repository.BookRepository;
import com.ramadanisafitra.perpustakaan.repository.BorrowerHistoryRepository;
import com.ramadanisafitra.perpustakaan.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerpustakaanService {


    @Autowired
    BookRepository bookRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BorrowerHistoryRepository borrowerHistoryRepository;

    private static final String REQUIRED_FIELDS_MESSAGE = "All fields must be provided.";


    public Books inputBookData(Books books) {
        if (books.getTitle() == null
                || books.getIsbnNumber() == null
                || books.getBookStock() == null) {
            throw new IllegalArgumentException(REQUIRED_FIELDS_MESSAGE);
        }
        // Check if books already exists
        if (bookRepository.existsByTitle(books.getTitle())) {
            throw new IllegalArgumentException("Book already exists.");
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

    public Books updateBookData(Long id, Books updatedBook) {
        if (updatedBook.getTitle() == null
                || updatedBook.getIsbnNumber() == null
                || updatedBook.getBookStock() == null) {
            throw new IllegalArgumentException(REQUIRED_FIELDS_MESSAGE);
        }
        Books existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found."));
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setIsbnNumber(updatedBook.getIsbnNumber());
        existingBook.setBookStock(updatedBook.getBookStock());

        return bookRepository.save(existingBook);
    }

    public void deleteBookById(Long id) {
        Books existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found."));

        bookRepository.delete(existingBook);
    }


    public Customer inputBorrowerData(Customer customer) {
        if (customer.getCustname() == null
                || customer.getIdCardNo() == null
                || customer.getEmail() == null) {
            throw new IllegalArgumentException(REQUIRED_FIELDS_MESSAGE);
        }
        // Check if IdCardNo already exists
        if (customerRepository.existsByIdCardNo(customer.getIdCardNo())) {
            throw new IllegalArgumentException("IdCardNo already exists.");
        }
        return customerRepository.save(customer);
    }

    public List<Customer> getAllBorrowerData() {
        return customerRepository.findAll();
    }

    public Customer getBorrowerData(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new NotFoundException("Data not found");
        }
    }

    public Customer updateBorrowerData(Long id, Customer updateCustomer) {
        if (updateCustomer.getIdCardNo() == null
                || updateCustomer.getCustname() == null
                || updateCustomer.getEmail() == null) {
            throw new IllegalArgumentException(REQUIRED_FIELDS_MESSAGE);
        }
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Data not found."));
        existingCustomer.setIdCardNo(updateCustomer.getIdCardNo());
        existingCustomer.setCustname(updateCustomer.getCustname());
        existingCustomer.setEmail(updateCustomer.getEmail());
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomerById(Long id) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found."));

        customerRepository.delete(existingCustomer);
    }

    public void bookLending(BorrowBookRequest request) {
        Books book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found."));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found."));

        LocalDate borrowDate = LocalDate.parse(request.getBorrowDate());

        if (book.getBookStock() == 0) {
            throw new IllegalArgumentException("Book is not available for borrowing.");
        }

        if (!isCustomerEligibleToBorrow(customer)) {
            throw new IllegalArgumentException("Customer is already borrowing a book.");
        }


        BorrowerHistory borrowerHistory = new BorrowerHistory();
        borrowerHistory.setBook(book);
        borrowerHistory.setCustomer(customer);
        borrowerHistory.setIsDeleted(false);
        borrowerHistory.setBorrowDate(borrowDate);
        borrowerHistory.setReturnDate(borrowDate.plusDays(30));

        borrowerHistoryRepository.save(borrowerHistory);

        // reduce book stock
        book.setBookStock(book.getBookStock() - 1);
        bookRepository.save(book);
    }

    public boolean isCustomerEligibleToBorrow(Customer customer) {
        List<BorrowerHistory> borrowerHistories = borrowerHistoryRepository.findByCustomerAndIsDeleted(customer, false);
        for (BorrowerHistory borrowerHistory : borrowerHistories) {
            if (!borrowerHistory.getIsDeleted()) {
                return false;
            }
        }
        return true;
    }



    public ReturnBookResponse returnBook(ReturnBookRequest request) {
        Optional<Customer> customer = customerRepository.findByIdCardNo(request.getIdCardNo());
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer not found.");
        }

        // Get all borrow records of the customer
        List<BorrowerHistory> borrowerHistories = borrowerHistoryRepository.findByCustomer(customer.get());
        if (borrowerHistories.isEmpty()) {
            throw new NotFoundException("No borrow records found for the customer.");
        }

        LocalDate returnDate = LocalDate.parse(request.getReturnDate());

        ReturnBookResponse returnBookResponse = new ReturnBookResponse();

        for (BorrowerHistory borrowerHistory : borrowerHistories) {
            // Set the return date for each borrow record
            borrowerHistory.setReturnDate(returnDate);
            borrowerHistory.setIsDeleted(true);
            borrowerHistoryRepository.save(borrowerHistory);

            log.info("start :{}",borrowerHistory.getBorrowDate());
            log.info("end :{}",borrowerHistory.getReturnDate());

            // Calculate overdue days
            long overdueDays = Math.max(0, ChronoUnit.DAYS.between(borrowerHistory.getBorrowDate(), borrowerHistory.getReturnDate()))-30;
            log.info("isi overrrer:{}",overdueDays);

            // Check if the book is returned on time or overdue
            if (overdueDays > 0) {
                returnBookResponse.setStatus("Late");
                returnBookResponse.setMessage("Book is returned late by " + overdueDays + " days.");
            } else {
                returnBookResponse.setStatus("On time");
                returnBookResponse.setMessage("Book is returned on time.");
            }

            // add book stock
            Books book = borrowerHistory.getBook();
            book.setBookStock(book.getBookStock() + 1);
            bookRepository.save(book);
        }

        return returnBookResponse;
    }

    public List<BorrowerHistoryResponse> getBorrowerHistoryList() {
        List<BorrowerHistory> borrowerHistories = borrowerHistoryRepository.findByIsDeletedFalse();
        List<BorrowerHistoryResponse> responseList = new ArrayList<>();

        for (BorrowerHistory borrowerHistory : borrowerHistories) {
            BorrowerHistoryResponse response = convertToResponse(borrowerHistory);
            responseList.add(response);
        }

        return responseList;
    }

    public BorrowerHistoryResponse convertToResponse(BorrowerHistory borrowerHistory) {
        BorrowerHistoryResponse response = new BorrowerHistoryResponse();
        response.setCustomerName(borrowerHistory.getCustomer().getCustname());
        response.setBookTitle(borrowerHistory.getBook().getTitle());
        response.setBorrowDate(borrowerHistory.getBorrowDate());
        response.setReturnDate(borrowerHistory.getReturnDate());
        return response;
    }



}
