package com.sap.capire.vectortest.controllers;

import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cds.CdsData;
import com.sap.cds.ql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private PersistenceService db;

    @GetMapping
    public ResponseEntity<List<CdsData>> getAllMessages() {
        List<CdsData> messages = db.run(Select.from("vector.test.Message")).listOf(CdsData.class);
        return ResponseEntity.ok(messages);
    }
}
