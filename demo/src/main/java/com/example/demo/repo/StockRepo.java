package com.example.demo.repo;


import com.example.demo.models.Borrow;
import com.example.demo.models.Category;
import com.example.demo.models.Stock;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface StockRepo extends JpaRepository<Stock,Long> {
     Optional<Stock> findById(long idBook);
     List<Stock> findByCategory(Category category);
     List<Stock> findByCategoryAndDeletedIsFalse(Category category);

     List<Stock> findByDeletedFalse();
     default void softDeleteStockIfNoOpenBorrows(Stock stock, List<Borrow> openBorrows) {
          for (Borrow borrow : openBorrows) {
               if (borrow.getIdBook().equals(stock)) {
                    return; // If there is an open borrow for this book, do not delete
               }
          }
          stock.setDeleted(true); // Soft delete the stock
          save(stock);
     }

     @Transactional
     default void updatePublicationDate(Long id, Date newPublicationDate) {
          findById(id).ifPresent(stock -> {
               stock.setPublicationDate(newPublicationDate);
               save(stock);
          });
     }

     @Modifying
     @Query("UPDATE Stock s SET s.Amount = s.Amount + :amountToAdd, s.AmountInLibrary = s.AmountInLibrary + :amountToAdd WHERE s.idBook = :id")
     void incrementAmountById(@Param("id") long id, @Param("amountToAdd") long amountToAdd);

}

//public interface ProductRepo extends JpaRepository<Product,Long> {
//    Optional<Product> findById(long id);