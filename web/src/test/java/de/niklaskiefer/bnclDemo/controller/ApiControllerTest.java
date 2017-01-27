package de.niklaskiefer.bnclDemo.controller;

import de.niklaskiefer.bnclDemo.ApiController;
import de.niklaskiefer.bnclDemo.MainApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MainApplication.class)
public class ApiControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testConvert() throws Exception {
        String bncl = de.niklaskiefer.bnclCore.MainApplication.testBncl;

        mockMvc.perform(post("/api/convert")
                .content(bncl)
                .contentType(MediaType.TEXT_PLAIN)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testConvertBadRequest() throws Exception {
        mockMvc.perform(post("/api/convert"))
                .andExpect(status().isBadRequest());
    }
}