package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name="requests")
public class Borrow{
    @Id
    private  long idBorrowing;

    @ManyToOne
    @JoinColumn(name = "reader", referencedColumnName = "idReader")
    private Readers idReader;

    @ManyToOne
    @JoinColumn(name = "idBook", referencedColumnName = "idBook")
    private Stock idBook;

    private Date dateBorrowing;

    //private Date dateReturn;


    private LocalDate dateReturn;

}
