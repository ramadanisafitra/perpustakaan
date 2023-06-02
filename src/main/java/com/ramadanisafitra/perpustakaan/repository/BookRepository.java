package com.ramadanisafitra.perpustakaan.repository;

import com.ramadanisafitra.perpustakaan.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Books,Long> {

}
