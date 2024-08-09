package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.exceptions.WarehouseNotFoundException;
import com.coursework.eshopkursinisbackend.model.Warehouse;
import com.coursework.eshopkursinisbackend.repos.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseRest {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping(value = "/warehouses")
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseRepository.findAll());
    }

    @GetMapping(value = "/warehouse/{id}")
    public EntityModel<Warehouse> getWarehouseById(@PathVariable(name = "id") int id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException(id));
        return EntityModel.of(warehouse,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WarehouseRest.class).getWarehouseById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WarehouseRest.class).getAllWarehouses()).withRel("Warehouses"));
    }

    @PostMapping(value = "addWarehouse")
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse savedWarehouse = warehouseRepository.saveAndFlush(warehouse);
        return ResponseEntity.ok(savedWarehouse);
    }

    @PutMapping(value = "updateWarehouse/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable(name = "id") int id, @RequestBody Warehouse warehouseInfo) {

        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException(id));
        warehouse.setTitle(warehouseInfo.getTitle());
        warehouse.setAddress(warehouseInfo.getAddress());
        warehouseRepository.save(warehouse);
        return new ResponseEntity<>(warehouse, HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteWarehouse/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable(name = "id") int id) {
        warehouseRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException(id));
        warehouseRepository.deleteById(id);
        return new ResponseEntity<>("Warehouse with id = " + id + " was successfully deleted", HttpStatus.OK);
    }
}
