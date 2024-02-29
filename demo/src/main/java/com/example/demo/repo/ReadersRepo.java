package com.example.demo.repo;



import com.example.demo.models.Borrow;
import com.example.demo.models.Readers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.io.Reader;
import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface ReadersRepo extends JpaRepository<Readers,Long> {
    Optional<Readers> findById(long id);
    List<Readers> findByDeletedFalse();
    default void softDeleteReaderIfNoOpenBorrows(Readers reader, List<Borrow> openBorrows) {
        for (Borrow borrow : openBorrows) {
            if (borrow.getIdReader().equals(reader)) {
                return; // If there is an open borrow for this book, do not delete
            }
        }
        reader.setDeleted(true); // Soft delete the stock
        save(reader);
    }
}
