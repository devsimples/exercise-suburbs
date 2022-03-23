package com.city.management.exercise.api;

import static com.city.management.exercise.TestUtil.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.city.management.exercise.model.Suburb;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SuburbApiIT {

    @Autowired
    private MockMvc mvc;

    private static final String API = "/api/v1/suburb";

    @Test
    public void getSuburb_should_return_3_items() throws Exception {
        mvc.perform(get(API))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value("3"))
                .andExpect(jsonPath("$[0].name").value("Albert Park"))
                .andExpect(jsonPath("$[0].postCode").value(1000))
                .andExpect(jsonPath("$[1].name").value("Balwyn"))
                .andExpect(jsonPath("$[1].postCode").value(2000))
                .andExpect(jsonPath("$[2].name").value("Caroline Springs"))
                .andExpect(jsonPath("$[2].postCode").value(3000));
    }

    @Test
    public void persistMultiple_should_persist_all_items() throws Exception {
        var suburbs = List.of(
                new Suburb(null, "Docklands", 4000L),
                new Suburb(null, "East Melbourne", 5000L)
        );

        mvc.perform(post(API)
                        .content(asJsonString(suburbs))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value("2"))
                .andExpect(jsonPath("$[0].name").value("Docklands"))
                .andExpect(jsonPath("$[0].postCode").value(4000))
                .andExpect(jsonPath("$[1].name").value("East Melbourne"))
                .andExpect(jsonPath("$[1].postCode").value(5000));
    }

    @Test
    public void findByFilter_should_filter_by_post_code_1000_1000() throws Exception {
        mvc.perform(get(API + "/filter").param("postCodeStart", "1000").param("postCodeEnd", "1000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value("1"))
                .andExpect(jsonPath("$[0].name").value("Albert Park"));
    }

    @Test
    public void findByFilter_should_filter_by_post_code_1000_2000() throws Exception {
        mvc.perform(get(API + "/filter").param("postCodeStart", "1000").param("postCodeEnd", "2000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value("2"))
                .andExpect(jsonPath("$[0].name").value("Albert Park"))
                .andExpect(jsonPath("$[1].name").value("Balwyn"));
    }

    @Test
    public void findByFilter_should_filter_by_post_code_2000_3000() throws Exception {
        mvc.perform(get(API + "/filter").param("postCodeStart", "2000").param("postCodeEnd", "3000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value("2"))
                .andExpect(jsonPath("$[0].name").value("Balwyn"))
                .andExpect(jsonPath("$[1].name").value("Caroline Springs"));
    }

    @Test
    public void findByFilter_should_return_empty_when_not_found() throws Exception {
        mvc.perform(get(API + "/filter").param("postCodeStart", "8000").param("postCodeEnd", "9000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value("0"));

    }
}