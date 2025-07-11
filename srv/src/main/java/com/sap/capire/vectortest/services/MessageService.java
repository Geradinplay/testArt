package com.sap.capire.vectortest.services;

import com.sap.cds.CdsData;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;

import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.sap.capire.vectortest.embedding.EmbeddingModel;


@Service
@ServiceName("MessageService")
public class MessageService implements EventHandler {
    // CAP service for database (CDS persistence) operations
    @Autowired
    private PersistenceService db;
    // ML/AI model for generating embeddings from message text
    @Autowired
    private EmbeddingModel embeddingModel;

    
   
    @After(event = "READ", entity = "MessageService.Messages")
    public void afterReadMessage(List<CdsData> messages) {
        System.out.println("[DEBUG] afterReadMessage called, messages.size=" + messages.size());
        for (CdsData message : messages) {
            String content = (String) message.get("content");
            Object id = message.get("ID");
            // Try to load embedding from MessageEmbedding table
            List<CdsData> embeddingRows = db.run(
                Select.from("vector.test.MessageEmbedding").where(m -> m.get("message_ID").eq(id))
            ).listOf(CdsData.class);
            List<Float> embedding = new ArrayList<>();
            for (CdsData row : embeddingRows) {
                Object valueObj = row.get("value");
                if (valueObj instanceof Number) {
                    embedding.add(((Number) valueObj).floatValue());
                }
            }
            if (!embedding.isEmpty()) {
                message.put("embedding", embedding);
                System.out.println("[DEBUG] Message ID=" + id + ", embedding loaded from DB, size=" + embedding.size());
            } else if (content != null) {
                // If embedding is not in DB, generate, save and return it
                float[] newEmbedding = embeddingModel.embed(content).vector();
                for (int i = 0; i < newEmbedding.length; i++) {
                    CdsData embeddingRow = CdsData.create();
                    embeddingRow.put("ID", UUID.randomUUID().toString());
                    embeddingRow.put("message_ID", id);
                    embeddingRow.put("idx", i);
                    embeddingRow.put("value", newEmbedding[i]);
                    db.run(Insert.into("vector.test.MessageEmbedding").entry(embeddingRow));
                    embedding.add(newEmbedding[i]);
                }
                message.put("embedding", embedding);
                System.out.println("[DEBUG] Message ID=" + id + ", embedding generated and saved, size=" + embedding.size());
            }
        }
    }

    @After(event = "CREATE", entity = "vector.test.Message")
    public void afterCreateMessage(List<CdsData> messages) {
        for (CdsData message : messages) {
            String content = (String) message.get("content");
            Object id = message.get("ID");
            if (content != null && id != null) {
                float[] embedding = embeddingModel.embed(content).vector();
                for (int i = 0; i < embedding.length; i++) {
                    CdsData embeddingRow = CdsData.create();
                    embeddingRow.put("ID", UUID.randomUUID().toString());
                    embeddingRow.put("message_ID", id);
                    embeddingRow.put("idx", i);
                    embeddingRow.put("value", embedding[i]);
                    db.run(Insert.into("vector.test.MessageEmbedding").entry(embeddingRow));
                }
            }
        }
    }

    @After(event = "UPDATE", entity = "vector.test.Message")
    public void afterUpdateMessage(List<CdsData> messages) {
        for (CdsData message : messages) {
            Object id = message.get("ID");
            String content = (String) message.get("content");
            if (id != null && content != null) {
                // Remove old embeddings
                db.run(com.sap.cds.ql.Delete.from("vector.test.MessageEmbedding").where(m -> m.get("message_ID").eq(id)));
                // Add new embeddings
                float[] embedding = embeddingModel.embed(content).vector();
                for (int i = 0; i < embedding.length; i++) {
                    CdsData embeddingRow = CdsData.create();
                    embeddingRow.put("ID", UUID.randomUUID().toString());
                    embeddingRow.put("message_ID", id);
                    embeddingRow.put("idx", i);
                    embeddingRow.put("value", embedding[i]);
                    db.run(com.sap.cds.ql.Insert.into("vector.test.MessageEmbedding").entry(embeddingRow));
                }
            }
        }
    }

   public List<CdsData> getAllMessages() {
        return db.run(Select.from("vector.test.Message")).listOf(CdsData.class);
    }
    
    public List<CdsData> getAllMessagesWithEmbedding() {
        List<CdsData> messages = getAllMessages();
        for (CdsData message : messages) {
            Object id = message.get("ID");
            List<CdsData> embeddingRows = db.run(
                Select.from("vector.test.MessageEmbedding").where(m -> m.get("message_ID").eq(id))
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
        return messages;
    }
}
