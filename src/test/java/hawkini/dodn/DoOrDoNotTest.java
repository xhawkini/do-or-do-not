package hawkini.dodn;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoOrDoNotTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    private DoOrDoNot.Handler handler;

    @BeforeEach
    void setUp() {
        handler = new DoOrDoNot.Handler();
    }

    @Test
    void pingTest() throws IOException {
        Map<String, String> response = handler.ping();
        assertEquals("{message=pong}", response.toString());
    }

    @Test
    void startTest() throws IOException {
        JsonNode startRequest = OBJECT_MAPPER.readTree("{}");
        Map<String, String> response = handler.start(startRequest);
        assertEquals("#888888", response.get("color"));
        assertEquals("regular", response.get("headType"));
        assertEquals("regular", response.get("tailType"));
    }

    @Test
    void moveTest() throws IOException {
        JsonNode moveRequest = OBJECT_MAPPER.readTree("{}");
        Map<String, String> response = handler.move(moveRequest);

        List<String> options = new ArrayList<String>();
        options.add("up");
        options.add("down");
        options.add("left");
        options.add("right");

        assertTrue(options.contains(response.get("move")));
    }

    @Test
    void endTest() throws IOException {
        JsonNode endRequest = OBJECT_MAPPER.readTree("{}");
        Map<String, String> response = handler.end(endRequest);
        assertEquals(0, response.size());
    }
}
