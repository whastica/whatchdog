package com.devInnovators.Whatchdog.Sync.controllerSync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devInnovators.Whatchdog.Sync.serviceSync.SyncService;

@RestController
public class SyncController {
/* 
    @Autowired
    private SyncService syncService;

    // Endpoint para sincronizar un solo reporte por su ID
    @PostMapping("/sync/report/{id}")
    public ResponseEntity<String> syncReportById(@PathVariable String id) {
        syncService.syncReportById(id);
        return ResponseEntity.ok("Report " + id + " synchronized successfully");
    }

    // Endpoint para sincronizar todos los reportes
    @PostMapping("/sync/reports")
    public ResponseEntity<String> syncAllReports() {
        syncService.syncAllReports();
        return ResponseEntity.ok("All reports synchronized successfully");
    } */
}