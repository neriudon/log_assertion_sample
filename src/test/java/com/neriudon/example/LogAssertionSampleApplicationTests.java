package com.neriudon.example;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class LogAssertionSampleApplicationTests {

  private static final String BODY = "test";

  private static final String PATH_WITH_INTERCEPTOR = "/sample";

  private static final String LOG_KIND_REQUEST_AND_RESPONSE = "TOTAL";

  private static final String LOG_KIND_METHOD = "METHOD";

  @Autowired
  WebApplicationContext webApplicationContext;

  @Autowired
  JdbcTemplate jdbcTemplate;

  MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(log())
        .addDispatcherServletCustomizer(customizer -> {
          customizer.setThrowExceptionIfNoHandlerFound(true);
        }).build();
  }

  @Test
  public void outputPerformanceLogTest() throws Exception {

    mockMvc.perform(post(PATH_WITH_INTERCEPTOR).content(BODY)).andExpect(status().isOk())
        .andExpect(content().string(BODY));

    // get log from database
    List<String> logginEvents = jdbcTemplate.queryForList(
        "SELECT formatted_message FROM logging_event ORDER BY timestmp", String.class);

    if (logginEvents.size() != 2) {
      fail();
    }
    // assert log
    assertThat(String.valueOf(logginEvents.get(0)), containsString(LOG_KIND_METHOD));
    assertThat(String.valueOf(logginEvents.get(1)), containsString(LOG_KIND_REQUEST_AND_RESPONSE));
  }
}
