package com.sap.capire.vectortest.controllers;

import com.sap.cds.services.persistence.PersistenceService;
import com.sap.capire.vectortest.util.JsonCdsDataConverter;
import com.sap.cds.CdsData;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Insert;
import com.sap.capire.vectortest.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private PersistenceService db;

    @Autowired
    private MessageService messageService;

    @GetMapping("/all")
    public ResponseEntity<String> getAllMessages() {
        List<CdsData> messages = messageService.getAllMessages();
        try {
            String json = JsonCdsDataConverter.toJson(messages);
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("JSON serialization error: " + e.getMessage());
        }
    }

    @GetMapping("/em")
    public ResponseEntity<String> getAllMessagesEmbedding() {
        List<CdsData> messages = messageService.getAllMessages();

        for (CdsData message : messages) {
            Object id = message.get("ID");
            List<CdsData> embeddingRows = db.run(
                Select.from("vector.test.MessageEmbedding").where(m -> m.get("message_ID_ID").eq(id))
            ).listOf(CdsData.class);

            List<Float> embedding = new ArrayList<>();
            for (CdsData row : embeddingRows) {
                Object valueObj = row.get("value");
                if (valueObj instanceof Number) {
                    embedding.add(((Number) valueObj).floatValue());
                }
            }
            message.put("embedding", embedding);
        }
        try {
            String json = JsonCdsDataConverter.toJson(messages);
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("JSON serialization error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<CdsData> createMessage(@RequestBody CdsData message) {
        db.run(Insert.into("vector.test.Message").entry(message));
        return ResponseEntity.ok(message);
    }
}
