package com.blps.airlineservice.repository;

import com.blps.airlineservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
}
