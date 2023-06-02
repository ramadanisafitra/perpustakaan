package com.ramadanisafitra.perpustakaan.controller;

import com.ramadanisafitra.perpustakaan.model.Books;
import com.ramadanisafitra.perpustakaan.model.Customer;
import com.ramadanisafitra.perpustakaan.service.PerpustakaanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/")
public class PerpustakaanController {

    @Autowired
    private PerpustakaanService perpustakaanService;

    @PostMapping("/books")
    public Books enterBookData( @RequestBody Books books) {
        return perpustakaanService.inputBookData(books);
    }

    @PostMapping("/customers")
    public Customer enterBookData(@RequestBody Customer customer) {
        return perpustakaanService.inputBorrowerData(customer);
    }
}
