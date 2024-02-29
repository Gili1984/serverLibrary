package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="publishers")
public class Publishers {
    @Id
    private  long idPublishers;
    private String publishersName;
    private String location;
    private boolean deleted = false;
    public Publishers() {
    }

    public Publishers(long idPublishers) {
        this.idPublishers = idPublishers;

    }
}
