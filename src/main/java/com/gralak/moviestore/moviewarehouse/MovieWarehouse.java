package com.gralak.moviestore.moviewarehouse;

import com.gralak.moviestore.warehouse.Warehouse;
import com.gralak.moviestore.movie.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movie_warehouse")
@Getter
@Setter
@NoArgsConstructor
public class MovieWarehouse
{
    @EmbeddedId
    private MovieWarehouseId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("warehouseId")
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(nullable = false)
    private int quantity;

    public MovieWarehouse(Movie movie, Warehouse warehouse, int quantity)
    {
        this.id = new MovieWarehouseId(movie.getId(), warehouse.getId());
        this.movie = movie;
        this.warehouse = warehouse;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieWarehouse that = (MovieWarehouse) o;
        return quantity == that.quantity && id.equals(that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, quantity);
    }
}
