package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.model.Warehouse;
import com.coursework.eshopkursinisbackend.repos.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WarehouseRest {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping(value = "/warehouses")
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseRepository.findAll());
    }

    @PostMapping(value = "addWarehouse")
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse savedWarehouse = warehouseRepository.saveAndFlush(warehouse);
        return ResponseEntity.ok(savedWarehouse);
    }
}
