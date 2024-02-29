package com.example.demo.repo;



import com.example.demo.models.Publishers;
import com.example.demo.models.Stock;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface PublishersRepo extends JpaRepository<Publishers, Long> {
    Optional<Publishers> findById(long id);

    List<Publishers> findByDeletedFalse();
    @Modifying
    @Transactional
    @Query("UPDATE Publishers p SET p.deleted = true WHERE p.idPublishers = :publisherId " +
            "AND NOT EXISTS (SELECT s FROM Stock s WHERE s.idPublisher = p AND s.deleted = false)")
    void softDeletePublisherIfNoStocks(@Param("publisherId") long publisherId);


    @Query("SELECT s FROM Stock s WHERE s.idPublisher.idPublishers = :publisherId AND s.deleted = false")
    List<Stock> findStocksByPublisherIdAndNotDeleted(@Param("publisherId") long publisherId);
}
