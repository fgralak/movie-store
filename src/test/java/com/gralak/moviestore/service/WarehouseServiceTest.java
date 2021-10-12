package com.gralak.moviestore.service;

import com.gralak.moviestore.warehouse.Warehouse;
import com.gralak.moviestore.exception.MissingWarehouseDataException;
import com.gralak.moviestore.exception.WarehouseNotFoundException;
import com.gralak.moviestore.warehouse.WarehouseRepo;
import com.gralak.moviestore.warehouse.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest
{
    @Mock
    private WarehouseRepo warehouseRepo;

    private WarehouseService testedService;

    @BeforeEach
    void setUp()
    {
        testedService = new WarehouseService(warehouseRepo);
    }

    @Test
    void shouldGetAllWarehouses()
    {
        //when
        testedService.getWarehouses();

        //then
        verify(warehouseRepo).findAll();
    }

    @Test
    void shouldGetWarehouseById()
    {
        //given
        Long id = 1L;
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");

        given(warehouseRepo.findById(id)).willReturn(java.util.Optional.of(warehouse));

        //when
        testedService.getWarehouseById(id);

        //then
        verify(warehouseRepo).findById(id);
    }

    @Test
    void shouldThrowWarehouseNotFoundException()
    {
        //given
        Long id = 1L;
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        warehouse.setId(id);

        //when
        //then
        assertThatThrownBy(() -> testedService.getWarehouseById(id))
                .isInstanceOf(WarehouseNotFoundException.class)
                .hasMessage("Could not find warehouse with given id: " + id);

        assertThatThrownBy(() -> testedService.updateWarehouse(warehouse))
                .isInstanceOf(WarehouseNotFoundException.class)
                .hasMessage("Could not find warehouse with given id: " + id);

        assertThatThrownBy(() -> testedService.deleteWarehouseById(id))
                .isInstanceOf(WarehouseNotFoundException.class)
                .hasMessage("Could not find warehouse with given id: " + id);
    }

    @Test
    void shouldAddWarehouse()
    {
        //given
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");

        //when
        testedService.addWarehouse(warehouse);

        //then
        ArgumentCaptor<Warehouse> captor = ArgumentCaptor.forClass(Warehouse.class);

        verify(warehouseRepo).save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(warehouse);
    }

    @Test
    void shouldUpdateWarehouse()
    {
        //given
        Long id = 1L;
        Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
        warehouse.setId(id);

        given(warehouseRepo.existsById(id)).willReturn(true);

        //when
        testedService.updateWarehouse(warehouse);

        //then
        ArgumentCaptor<Warehouse> captor = ArgumentCaptor.forClass(Warehouse.class);

        verify(warehouseRepo).save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(warehouse);
    }

    @Test
    void shouldThrowMissingWarehouseDataException()
    {
        //given
        Long id = 1L;
        Warehouse warehouse = new Warehouse("", "Warszawa", "00-897");
        warehouse.setId(id);

        given(warehouseRepo.existsById(id)).willReturn(true);

        //when
        //then
        assertThatThrownBy(() -> testedService.addWarehouse(warehouse))
                .isInstanceOf(MissingWarehouseDataException.class)
                .hasMessage("Empty value in column: streetAndNumber, city or/and postcode");

        assertThatThrownBy(() -> testedService.updateWarehouse(warehouse))
                .isInstanceOf(MissingWarehouseDataException.class)
                .hasMessage("Empty value in column: streetAndNumber, city or/and postcode");
    }

    @Test
    void shouldDeleteWarehouseById()
    {
        //given
        Long id = 1L;

        given(warehouseRepo.existsById(id)).willReturn(true);

        //when
        testedService.deleteWarehouseById(id);

        //then
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        verify(warehouseRepo).deleteById(captor.capture());

        assertThat(captor.getValue()).isEqualTo(id);
    }
}