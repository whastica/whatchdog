package com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Sync.controllerSync;

import com.devInnovators.whatchdog_revision_priorizacion.revision_priorizacion.Sync.serviceSync.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    @Autowired
    private SyncService syncService;

    // Endpoint para sincronizar un Issue
    @PostMapping("/issue/{issueId}")
    public ResponseEntity<String> syncIssue(@PathVariable String issueId) {
        syncService.syncIssue(issueId);
        return new ResponseEntity<>("Issue sincronizado con éxito", HttpStatus.OK);
    }

    // Endpoint para sincronizar un Report
    @PostMapping("/report/{reportId}")
    public ResponseEntity<String> syncReport(@PathVariable String reportId) {
        syncService.syncReport(reportId);
        return new ResponseEntity<>("Report sincronizado con éxito", HttpStatus.OK);
    }
}
