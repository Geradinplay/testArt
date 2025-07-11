package com.sap.capire.vectortest.embedding;

import org.springframework.stereotype.Component;

@Component
public class EmbeddingModel {

    public EmbeddingResult embed(String content) {
        if (content == null) content = "";
        float[] vector = new float[Math.min(content.length(), 32)];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (float) content.charAt(i);
        }
        return new EmbeddingResult(vector);
    }

    public static class EmbeddingResult {
        private final float[] vector;
        public EmbeddingResult(float[] vector) {
            this.vector = vector;
        }
        public float[] vector() {
            return vector;
        }
        public EmbeddingResult content() {
            return this;
        }
    }

}
