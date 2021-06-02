package at.technikumwien.personwebapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Integration test

@SpringBootTest
@ActiveProfiles("test")
@Tag("extended")
@AutoConfigureMockMvc // mocks the web layer -> faster
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // starts web layer (tomcat) for tests -> slower (would also test tomcat)
public class PersonResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;

    @Test
    public void testRetrieve() throws Exception { // could generate exception
        var requestBuilder = MockMvcRequestBuilders
                .get("/resources/persons/1")
                .accept(MediaType.APPLICATION_JSON);

        // option 1: use expect methods instead of assert
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testRetrieveAll() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/resources/persons")
                .accept(MediaType.APPLICATION_JSON);

        var response = mockMvc
                .perform(requestBuilder)
                .andReturn().getResponse();

        var jsonString = response.getContentAsString();
        var persons = om.readValue(
                jsonString,
                new TypeReference<List<Person>>() { // needed to get a list of persons
        });

        // option 2: use assert methods
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertThat(response.getContentType()).containsIgnoringCase(MediaType.APPLICATION_JSON_VALUE);
        assertEquals(1, persons.size());
    }
}
