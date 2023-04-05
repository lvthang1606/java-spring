package com.thangle.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@ActiveProfiles("TEST")
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    protected ResultActions get(final String path) throws Exception {
        return perform(MockMvcRequestBuilders.get(path));
    }

    protected ResultActions post(final String path, final Object body) throws Exception {
        final String requestBody = mapper.writeValueAsString(body);
        return perform(MockMvcRequestBuilders.post(path).with(csrf()).content(requestBody));
    }

    protected ResultActions put(final String path, final Object body) throws Exception {
        final String requestBody = mapper.writeValueAsString(body);
        return perform(MockMvcRequestBuilders.put(path).with(csrf()).content(requestBody));
    }

    protected ResultActions delete(final String path) throws Exception {
        return perform(MockMvcRequestBuilders.delete(path).with(csrf()));
    }

    private ResultActions perform(final MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON));
    }
}
