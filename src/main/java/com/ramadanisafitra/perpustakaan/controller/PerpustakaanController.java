package com.ramadanisafitra.perpustakaan.controller;

import com.ramadanisafitra.perpustakaan.dto.BorrowBookRequest;
import com.ramadanisafitra.perpustakaan.dto.BorrowerHistoryResponse;
import com.ramadanisafitra.perpustakaan.dto.ReturnBookRequest;
import com.ramadanisafitra.perpustakaan.dto.ReturnBookResponse;
import com.ramadanisafitra.perpustakaan.model.Books;
import com.ramadanisafitra.perpustakaan.model.Customer;
import com.ramadanisafitra.perpustakaan.service.PerpustakaanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class PerpustakaanController {

    @Autowired
    private PerpustakaanService perpustakaanService;

    @PostMapping("/books")
    public Books enterBookData(@RequestBody Books books) {
        return perpustakaanService.inputBookData(books);
    }

    @GetMapping("/books")
    public List<Books> getAllBookData() {
        return perpustakaanService.getAllBookData();
    }
    @GetMapping("/books/{id}")
    public Books getBookDataById(@PathVariable Long id) {
        return perpustakaanService.getBookDataById(id);
    }

    @PutMapping("/books/{id}")
    public Books updateBookData(@PathVariable Long id, @RequestBody Books updatedBook) {
        return perpustakaanService.updateBookData(id, updatedBook);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        perpustakaanService.deleteBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Book has been successfully deleted.");
    }

    @PostMapping("/customers")
    public Customer enterBookData(@RequestBody Customer customer) {
        return perpustakaanService.inputBorrowerData(customer);
    }

    @GetMapping("/customers")
    public List<Customer> getAllBorrowerData() {

        return perpustakaanService.getAllBorrowerData();
    }
    @GetMapping("/customers/{id}")
    public Customer getBorrowerData(@PathVariable Long id) {

        return perpustakaanService.getBorrowerData(id);
    }

    @PutMapping("/customers/{id}")
    public Customer updateBorrowerData(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        return perpustakaanService.updateBorrowerData(id, updatedCustomer);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteBorrowerData(@PathVariable Long id) {
        perpustakaanService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Data has been successfully deleted.");
    }

    @PostMapping("/booklending")
    public ResponseEntity<String> bookLending(@RequestBody BorrowBookRequest request) {
        perpustakaanService.bookLending(request);
        return ResponseEntity.status(HttpStatus.OK).body("Book has been borrowed successfully.");
    }

    @PostMapping("/returnbook")
    public ReturnBookResponse returnBook (@RequestBody ReturnBookRequest request){
        return perpustakaanService.returnBook(request);

    }

    @GetMapping("/getborrowerhistorylist")
    public List<BorrowerHistoryResponse> getBorrowerHistoryList(){
        return perpustakaanService.getBorrowerHistoryList();
    }
}
