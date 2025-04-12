package com.branches.controller;

import com.branches.exception.NotFoundException;
import com.branches.model.Employee;
import com.branches.request.EmployeePostRequest;
import com.branches.response.EmployeeGetResponse;
import com.branches.service.EmployeeService;
import com.branches.utils.EmployeeUtils;
import com.branches.utils.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = EmployeeController.class)
@Import(FileUtils.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private EmployeeService service;
    @Autowired
    private FileUtils fileUtils;
    private final String URL = "/v1/employees";
    private List<EmployeeGetResponse> employeeGetResponseList;

    @BeforeEach
    void init() {
        employeeGetResponseList = EmployeeUtils.newEmployeeGetResponseList();
    }

    @Test
    @DisplayName("GET /v1/employees returns all employees when the given argument is null")
    @Order(1)
    void findAll_ReturnsAllEmployees_WhenGivenArgumentIsNull() throws Exception {
        BDDMockito.when(service.findAll(null)).thenReturn(employeeGetResponseList);
        String expectedResponse = fileUtils.readResourceFile("employee/get-employees-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/employees returns the found employees when the argument is given")
    @Order(2)
    void findAll_ReturnsFoundEmployees_WhenArgumentIsGiven() throws Exception {
        String nameToSearch = "Marcus";
        BDDMockito.when(service.findAll(nameToSearch)).thenReturn(List.of(employeeGetResponseList.getFirst()));
        String expectedResponse = fileUtils.readResourceFile("employee/get-employees-marcus-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", nameToSearch))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/employees returns an empty list when the given argument is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenGivenArgumentIsNotFound() throws Exception {
        String randomName = "nameNotRegistered";
        BDDMockito.when(service.findAll(randomName)).thenReturn(Collections.emptyList());
        String expectedResponse = fileUtils.readResourceFile("employee/get-employees-nameNotRegistered-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", randomName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/employees/1 returns found employee when successful")
    @Order(4)
    void findById_ReturnsFoundEmployee_WhenSuccessful() throws Exception {
        EmployeeGetResponse expectedEmployee = employeeGetResponseList.getFirst();
        long idToSearch = 1L;

        BDDMockito.when(service.findById(idToSearch)).thenReturn(expectedEmployee);
        String expectedResponse = fileUtils.readResourceFile("employee/get-employee-by-id-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", idToSearch))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/employees/ throws NotFoundException when id is not found")
    @Order(5)
    void findById_ThrowsNotFoundException_WhenIdIsNotFound() throws Exception {
        long randomId = 131222L;

        BDDMockito.when(service.findById(randomId)).thenThrow(new NotFoundException("Employee not Found"));
        String expectedResponse = fileUtils.readResourceFile("employee/get-employee-by-id-404.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", randomId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("POST /v1/employees returns saved employee when successful")
    @Order(4)
    void save_ReturnsSavedEmployee_WhenGivenSuccessful() throws Exception {
        BDDMockito.when(service.save(ArgumentMatchers.any(EmployeePostRequest.class))).thenReturn(EmployeeUtils.newEmployeePostResponse());

        String request = fileUtils.readResourceFile("employee/post-request-employee-valid-category-200.json");
        String expectedResponse = fileUtils.readResourceFile("employee/post-response-employee-201.json");

        mockMvc.perform(
                    MockMvcRequestBuilders.post(URL)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("POST /v1/employees throws not found exception when given category does not exists")
    @Order(5)
    void save_ThrowsNotFoundException_WhenGivenCategoryNotExists() throws Exception {

        BDDMockito.when(service.save(ArgumentMatchers.any(EmployeePostRequest.class))).thenThrow(new NotFoundException("Category not Found"));

        String request = fileUtils.readResourceFile("employee/post-request-employee-invalid-category-200.json");
        String expectedResponse = fileUtils.readResourceFile("employee/post-response-employee-404.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.post(URL)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @ParameterizedTest
    @MethodSource("postEmployeeBadRequestSource")
    @DisplayName("POST /v1/employees return BadRequest when fields are invalid")
    @Order(7)
    void save_ReturnsBadRequest_WhenFieldAreInvalid(String fileName, List<String> expectedErrors) throws Exception {
        String request = fileUtils.readResourceFile("employee/%s".formatted(fileName));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(URL)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Exception exception = mvcResult.getResolvedException();

        System.out.println(exception.getMessage());

        Assertions.assertThat(exception.getMessage())
                .isNotNull()
                .contains(expectedErrors);
    }

    private static Stream<Arguments> postEmployeeBadRequestSource() {
        String nameRequiredError = "The field name is required";
        String lastNameRequiredError = "The field lastName is required";
        String categoryId = "The field categoryId is required";

        List<String> expectedErrors = List.of(nameRequiredError, lastNameRequiredError, categoryId);
        return Stream.of(
                Arguments.of("post-request-employee-empty-fields-400.json", expectedErrors),
                Arguments.of("post-request-employee-blank-fields-400.json", expectedErrors)
        );
    }

    @Test
    @DisplayName("DELETE /v1/employees/1 removes employee when successful")
    @Order(8)
    void deleteById_RemovesEmployee_WhenSuccessful() throws Exception {
        Employee employeeToDelete = EmployeeUtils.newEmployeeList().getFirst();
        Long idToDelete = employeeToDelete.getId();

        BDDMockito.doNothing().when(service).deleteById(idToDelete);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", idToDelete))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /v1/employees/25256595 throws NotFoundException when given id is not found")
    @Order(9)
    void deleteById_ThrowsNotFoundException_WhenGivenIdIsNotFound() throws Exception {
        Long randomId = 25256595L;

        BDDMockito.doThrow(new NotFoundException("Employee not Found")).when(service).deleteById(randomId);

            String expectedResponse = fileUtils.readResourceFile("employee/delete-employee-by-id-404.json");

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", randomId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }
}