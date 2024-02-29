package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="copy")
public class Copy {
    @Id
    private  long idCopy;
    private  long idBook;
    private Boolean isBorrowed;
    private Date dateOfCome;
}
