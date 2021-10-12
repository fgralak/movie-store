package com.gralak.moviestore.controller;

import com.gralak.moviestore.warehouse.Warehouse;
import com.gralak.moviestore.warehouse.WarehouseService;
import com.gralak.moviestore.warehouse.WarehouseController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WarehouseControllerTest
{
    @Mock
    private WarehouseService warehouseService;

    private WarehouseController testedController;

    @BeforeEach
    void setUp()
    {
        testedController = new WarehouseController(warehouseService);
    }

    @Test
    void shouldGetAllWarehouses()
    {
        //given
        //when
        testedController.getAllWarehouses();

        //then
        verify(warehouseService).getWarehouses();
    }

    @Test
    void shouldGetWarehouseById()
    {
        //given
        Long warehouseId = 1L;

        //when
        testedController.getWarehouseById(warehouseId);

        //then
        verify(warehouseService).getWarehouseById(warehouseId);
    }

    @Test
    void shouldAddWarehouse()
    {
        //given
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");

        //when
        testedController.addWarehouse(warehouse);

        //then
        ArgumentCaptor<Warehouse> captor = ArgumentCaptor.forClass(Warehouse.class);

        verify(warehouseService).addWarehouse(captor.capture());

        assertThat(captor.getValue()).isEqualTo(warehouse);
    }

    @Test
    void shouldUpdateWarehouse()
    {
        //given
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");

        //when
        testedController.updateWarehouse(warehouse);

        //then
        ArgumentCaptor<Warehouse> captor = ArgumentCaptor.forClass(Warehouse.class);

        verify(warehouseService).updateWarehouse(captor.capture());

        assertThat(captor.getValue()).isEqualTo(warehouse);
    }

    @Test
    void shouldDeleteWarehouseById()
    {
        //given
        Long warehouseId = 1L;

        //when
        testedController.deleteWarehouseById(warehouseId);

        //then
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        verify(warehouseService).deleteWarehouseById(captor.capture());

        assertThat(captor.getValue()).isEqualTo(warehouseId);
    }
}