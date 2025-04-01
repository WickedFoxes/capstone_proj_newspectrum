package com.capstone.newspectrum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Hiperlink {
    @Id
    @GeneratedValue
    private Long id;
}
