package com.gralak.moviestore.warehouse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warehouse")
@Api(tags = {"Warehouse Controller"})
public class WarehouseController
{
    private final WarehouseService warehouseService;

    @ApiOperation(value = "Get all available warehouses", notes = "Retrieve a list of all warehouses")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "List of warehouses retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get all warehouses"),
                    @ApiResponse(code = 404, message = "Movies couldn't be found")}
    )
    @GetMapping("/all")
    public ResponseEntity<List<Warehouse>> getAllWarehouses()
    {
        List<Warehouse> warehouses = warehouseService.getWarehouses();
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @ApiOperation(value = "Get warehouse with a given id", notes = "Retrieve a warehouse with a given id", response = Warehouse.class)
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Warehouse with given id retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get a warehouse"),
                    @ApiResponse(code = 404, message = "Warehouse with a given id couldn't be found")}
    )
    @GetMapping
    public ResponseEntity<Warehouse> getWarehouseById(@RequestParam Long id)
    {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        return new ResponseEntity<>(warehouse, HttpStatus.OK);
    }

    @ApiOperation(value = "Add a new warehouse", notes = "Add a new warehouse information into the system", response = Warehouse.class)
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "Warehouse created successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to create a warehouse"),
                    @ApiResponse(code = 404, message = "Warehouse couldn't be created")}
    )
    @PostMapping
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouseToAdd)
    {
        Warehouse warehouse = warehouseService.addWarehouse(warehouseToAdd);
        return new ResponseEntity<>(warehouse, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update existing warehouse", notes = "Update existing warehouse information", response = Warehouse.class)
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Warehouse information updated successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to update a warehouse"),
                    @ApiResponse(code = 404, message = "Warehouse couldn't be updated")}
    )
    @PutMapping
    public ResponseEntity<Warehouse> updateWarehouse(@RequestBody Warehouse warehouseForUpdate)
    {
        Warehouse warehouse = warehouseService.updateWarehouse(warehouseForUpdate);
        return new ResponseEntity<>(warehouse, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete warehouse with given id", notes = "Delete existing warehouse with given id")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Warehouse information deleted successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to delete a warehouse"),
                    @ApiResponse(code = 404, message = "Warehouse couldn't be deleted")}
    )
    @DeleteMapping
    public ResponseEntity<?> deleteWarehouseById(@RequestParam Long id)
    {
        warehouseService.deleteWarehouseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
