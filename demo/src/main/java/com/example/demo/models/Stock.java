package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name="StockOfTheBooks")
public class Stock {

    @Id

    private  long idBook;

    private String bookName;

    @ManyToOne
    @JoinColumn(name = "idPublisher", referencedColumnName = "idPublishers")
    private Publishers idPublisher;

    @Enumerated(EnumType.STRING)
    private Category  category;

    private Date pulicationDate;
    private boolean isStar= false;;

    private long Amount;
    private long AmountInLibrary;

    private String img;
    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private Double price;
    private boolean deleted = false;



    public Stock(long idBook) {
        this.idBook = idBook;
    }

    public Stock() {
    }

    public Date getPublicationDate() {
        return pulicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.pulicationDate = publicationDate;
    }


    public void setIsStar(boolean b) {
        this.isStar=b;
    }
}
