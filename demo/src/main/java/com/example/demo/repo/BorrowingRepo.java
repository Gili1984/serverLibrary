package com.example.demo.repo;



import com.example.demo.models.Borrow;
import com.example.demo.models.Stock;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.awt.print.Pageable;
import java.security.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BorrowingRepo extends JpaRepository<Borrow, Long> {
    Optional<Borrow> findById(long id);
  //  Optional<Borrow> findByIdBook(Long idBook);
  List<Borrow> findByIdBook_IdBook(long idBook);
    List<Borrow> findByIdReader_IdReader(long readerId);
    List<Borrow> findByDateReturnIsNull();
    // Method to find borrowings with null return date based on idBorrowing
// Method to find borrowings with null return date based on idReader
    List<Borrow> findByIdReader_IdReaderAndDateReturnIsNull(long readerId);
  List<Borrow> findByDateReturnIsNullAndIdBook_IdBook(long idBook);
//  @Query("SELECT s " +
//          "FROM Stock s " +
//          "JOIN Borrow b ON s.idBook = CAST(b.idBook AS Long) " +
//          "GROUP BY s " +
//          "ORDER BY COUNT(b.idBook) DESC " +
//          "LIMIT 3")
//  List<Stock> findTop5BorrowedBooks();
@Query("SELECT s " +
        "FROM Stock s " +
        "JOIN Borrow b ON s.idBook = CAST(b.idBook AS Long) " +
        "WHERE s.deleted = false " +
        "GROUP BY s " +
        "ORDER BY COUNT(b.idBook) DESC " +
        "LIMIT 3")
List<Stock> findTop5BorrowedBooks();

  @Modifying
  @Transactional
  @Query("UPDATE Borrow b SET b.dateReturn = :dateReturn WHERE b.idBorrowing = :id AND b.dateReturn IS NULL")
  void updateDateReturn(@Param("id") long id, @Param("dateReturn") LocalDate dateReturn);

}
