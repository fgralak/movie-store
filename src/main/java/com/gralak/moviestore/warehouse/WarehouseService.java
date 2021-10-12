package com.gralak.moviestore.warehouse;

import com.gralak.moviestore.exception.MissingWarehouseDataException;
import com.gralak.moviestore.exception.WarehouseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseService
{
    private final WarehouseRepo warehouseRepo;

    public List<Warehouse> getWarehouses()
    {
        return warehouseRepo.findAll();
    }

    public Warehouse getWarehouseById(Long id)
    {
        return warehouseRepo.findById(id).orElseThrow(() -> new WarehouseNotFoundException(id));
    }

    public Warehouse addWarehouse(Warehouse warehouseToAdd)
    {
        if (warehouseToAdd.getStreetAndNumber() == null || warehouseToAdd.getStreetAndNumber().length() == 0 ||
                warehouseToAdd.getCity() == null || warehouseToAdd.getCity().length() == 0 ||
                warehouseToAdd.getPostcode() == null || warehouseToAdd.getPostcode().length() == 0)
        {
            throw new MissingWarehouseDataException();
        }
        return warehouseRepo.save(warehouseToAdd);
    }

    public Warehouse updateWarehouse(Warehouse warehouseForUpdate)
    {
        boolean exists = warehouseRepo.existsById(warehouseForUpdate.getId());
        if (!exists)
        {
            throw new WarehouseNotFoundException(warehouseForUpdate.getId());
        }
        if (warehouseForUpdate.getStreetAndNumber() == null || warehouseForUpdate.getStreetAndNumber().length() == 0 ||
                warehouseForUpdate.getCity() == null || warehouseForUpdate.getCity().length() == 0 ||
                warehouseForUpdate.getPostcode() == null || warehouseForUpdate.getPostcode().length() == 0)
        {
            throw new MissingWarehouseDataException();
        }
        return warehouseRepo.save(warehouseForUpdate);
    }

    public void deleteWarehouseById(Long id)
    {
        boolean exists = warehouseRepo.existsById(id);
        if (!exists)
        {
            throw new WarehouseNotFoundException(id);
        }
        warehouseRepo.deleteById(id);
    }
}
