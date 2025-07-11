package com.sap.capire.vectortest.util;

import com.sap.cds.CdsData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonCdsDataConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // CdsData -> Map<String, Object>
    private static Map<String, Object> toMap(CdsData cdsData) {
        Map<String, Object> map = new HashMap<>();
        for (String key : cdsData.keySet()) {
            map.put(key, cdsData.get(key));
        }
        return map;
    }

    // CdsData -> JSON String
    public static String toJson(CdsData cdsData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(toMap(cdsData));
    }

    // JSON String -> CdsData
    public static CdsData fromJson(String json) throws JsonProcessingException {
        Map<String, Object> map = objectMapper.readValue(json, Map.class);
        return CdsData.create(map);
    }

    // List<CdsData> -> JSON String
    public static String toJson(List<CdsData> cdsDataList) throws JsonProcessingException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (CdsData cdsData : cdsDataList) {
            mapList.add(toMap(cdsData));
        }
        return objectMapper.writeValueAsString(mapList);
    }

    // JSON String -> List<CdsData>
    public static List<CdsData> fromJsonArray(String json) throws JsonProcessingException {
        List<Map<String, Object>> mapList = objectMapper.readValue(json, List.class);
        List<CdsData> cdsDataList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            cdsDataList.add(CdsData.create(map));
        }
        return cdsDataList;
    }
}
