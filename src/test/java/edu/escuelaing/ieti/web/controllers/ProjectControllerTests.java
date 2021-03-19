package edu.escuelaing.ieti.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.escuelaing.ieti.business.model.Finance;
import edu.escuelaing.ieti.business.model.Project;
import edu.escuelaing.ieti.business.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void shouldCreateProject() throws Exception {
        Finance finance = new Finance(1, 1, 1, 1, new Date(), new Date());
        Project project = new Project("proyectox", "test", null, "video", "country", "f", finance);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(project))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
