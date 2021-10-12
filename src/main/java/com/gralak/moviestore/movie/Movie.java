package com.gralak.moviestore.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gralak.moviestore.moviewarehouse.MovieWarehouse;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "movie")
@NoArgsConstructor
public class Movie
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private int productionYear;
    private String direction;
    @ElementCollection
    private List<String> genre;
    @ElementCollection
    private List<String> production;
    private double price;

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    @JsonIgnore
    private Set<MovieWarehouse> warehouses = new HashSet<>();

    public Movie(String title, int productionYear, String direction, List<String> genre,
                 List<String> production, double price)
    {
        this.title = title;
        this.productionYear = productionYear;
        this.direction = direction;
        this.genre = genre;
        this.production = production;
        this.price = price;
    }
}
