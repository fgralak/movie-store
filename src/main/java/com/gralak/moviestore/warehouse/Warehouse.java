package com.gralak.moviestore.warehouse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gralak.moviestore.moviewarehouse.MovieWarehouse;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "warehouse")
@NoArgsConstructor
public class Warehouse
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String streetAndNumber;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String postcode;

    @OneToMany(mappedBy = "warehouse", orphanRemoval = true)
    @JsonIgnore
    private Set<MovieWarehouse> movies = new HashSet<>();

    public Warehouse(String streetAndNumber, String city, String postcode)
    {
        this.streetAndNumber = streetAndNumber;
        this.city = city;
        this.postcode = postcode;
    }
}
