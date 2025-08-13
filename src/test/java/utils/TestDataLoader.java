package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDataLoader {

    public static Object[][] loadRoomTypesFromJson(String filePath) {
        List<Object[]> data = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(new File(filePath));
            for (JsonNode node : root) {
                data.add(new Object[]{ node.get("roomType").asText() });
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load room types from JSON", e);
        }

        return data.toArray(new Object[0][0]);
    }
}
