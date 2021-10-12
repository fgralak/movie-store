package com.gralak.moviestore.moviewarehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MovieWarehouseRepo extends JpaRepository<MovieWarehouse, MovieWarehouseId>
{
    @Query("SELECT m FROM MovieWarehouse m WHERE m.id.warehouseId = ?1")
    Set<MovieWarehouse> findAllMoviesFromWarehouseId(Long id);

    @Query("SELECT m FROM MovieWarehouse m WHERE m.id.movieId = ?1")
    Set<MovieWarehouse> findAllWarehousesFromMovieId(Long id);
}
