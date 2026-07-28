/**
 * Tests the InfoController class.
 * This test verifies that: The HTTP endpoint exists, it returns HTTP 200 and the JSON respsonse is correct.
 * MockMvc simulates HTTP requests without starting a web server.
 */

package de.kurs.taskapi.controller;

import de.kurs.taskapi.service.InfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InfoControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        //Create the service.
        InfoService service = new InfoService();
        //Inject the service into the controller.
        InfoController controller = new InfoController(service);

        //Build a standalone MockMvc instance for testing only this controller.
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void getInfoReturnsApplicationInformation() 
            throws Exception {
            
        //Simulate an HTTP GET request without opening a real network port.
        mockMvc.perform(get("/api/info"))
                //verify that HTTP status 200 is returned.
                .andExpect(status().isOk())
                //verify JSON field.
                .andExpect(jsonPath("$.application").value("DevOpsApi"))
                .andExpect(jsonPath("$.version").value("1.0"))
                .andExpect(jsonPath("$.status").value("Up and running!"));
    }
}