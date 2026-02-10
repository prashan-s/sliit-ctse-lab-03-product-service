package com.sliit.product_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliit.product_service.dto.ProductRequest;
import com.sliit.product_service.dto.ProductResponse;
import com.sliit.product_service.service.ProductService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void createProduct_ReturnsCreated() throws Exception {
        ProductRequest request = new ProductRequest("Test", "Desc", BigDecimal.valueOf(9.99));
        ProductResponse response = new ProductResponse(1L, "Test", "Desc", BigDecimal.valueOf(9.99));
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/products/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void getAllProducts_ReturnsList() throws Exception {
        List<ProductResponse> responses = List.of(
                new ProductResponse(1L, "One", "D1", BigDecimal.ONE),
                new ProductResponse(2L, "Two", "D2", BigDecimal.TEN)
        );
        Mockito.when(productService.getAllProducts()).thenReturn(responses);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("One"));
    }

    @Test
    void getProductById_NotFound_Returns404() throws Exception {
        Mockito.when(productService.getProductById(99L)).thenThrow(new java.util.NoSuchElementException("not found"));

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_Returns204() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
