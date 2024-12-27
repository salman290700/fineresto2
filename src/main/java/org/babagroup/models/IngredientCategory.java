package org.babagroup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class IngredientCategory {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @Column(nullable = false)
    private String updatedBy;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @JsonIgnore
    @Column(nullable = false)
    private Date updatedAt;

    private String name;
}
