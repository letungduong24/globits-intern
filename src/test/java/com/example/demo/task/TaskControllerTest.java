package com.example.demo.task;

import com.example.demo.rest.RestTaskController;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestTaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void testExportTasksToExcel() throws Exception {
        // Mock the service to return a resource
        Resource mockResource = new ByteArrayResource("test excel content".getBytes());
        when(taskService.exportTasksToExcel()).thenReturn(mockResource);

        // Test the endpoint
        mockMvc.perform(get("/api/tasks/export/excel"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=tasks_export.xlsx"))
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
    }
}
