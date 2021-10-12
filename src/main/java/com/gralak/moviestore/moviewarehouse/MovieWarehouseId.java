package com.gralak.moviestore.moviewarehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieWarehouseId implements Serializable
{
    @Column(name = "movie_id")
    private Long movieId;
    @Column(name = "warehouse_id")
    private Long warehouseId;
}
