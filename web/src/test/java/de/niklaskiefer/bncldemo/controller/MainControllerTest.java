package de.niklaskiefer.bnclDemo.controller;

import de.niklaskiefer.bnclDemo.MainApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MainApplication.class)
public class MainControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testOpenThymeleaf() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testConvertBncl() throws Exception {
        String bncl = de.niklaskiefer.bnclCore.MainApplication.testBncl;
        this.mockMvc.perform(post("/convertBncl?bncl=" + bncl)).
                andExpect(status().isOk());
    }
}
