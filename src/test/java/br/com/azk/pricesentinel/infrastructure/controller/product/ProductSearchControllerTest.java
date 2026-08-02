package br.com.azk.pricesentinel.infrastructure.controller.product;

import br.com.azk.pricesentinel.application.dto.response.ProductSearchResponse;
import br.com.azk.pricesentinel.application.mapper.ProductSearchResponseMapper;
import br.com.azk.pricesentinel.domain.enums.Store;
import br.com.azk.pricesentinel.domain.model.ProductSearchResult;
import br.com.azk.pricesentinel.domain.port.in.ProductSearchUseCase;
import br.com.azk.pricesentinel.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductSearchController.class)
class ProductSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductSearchUseCase productSearchUseCase;

    @MockitoBean
    private ProductSearchResponseMapper mapper;

    @Test
    void shouldReturnProducts() throws Exception {

        // Given
        ProductSearchResult result =
                ProductSearchResult.builder()
                        .name("Ryzen 7 5700X")
                        .price(Money.of("999.90"))
                        .store(Store.AMAZON)
                        .url("https://amazon.com.br/teste")
                        .build();

        ProductSearchResponse response =
                ProductSearchResponse.builder()
                        .name("Ryzen 7 5700X")
                        .price("R$ 999,90")
                        .store("AMAZON")
                        .url("https://amazon.com.br/teste")
                        .build();

        when(productSearchUseCase.search("Ryzen"))
                .thenReturn(List.of(result));

        when(mapper.toResponse(result))
                .thenReturn(response);

        // When / Then
        mockMvc.perform(
                        get("/api/products/search")
                                .param("query", "Ryzen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Ryzen 7 5700X"))
                .andExpect(jsonPath("$[0].price")
                        .value("R$ 999,90"))
                .andExpect(jsonPath("$[0].store")
                        .value("AMAZON"))
                .andExpect(jsonPath("$[0].url")
                        .value("https://amazon.com.br/teste"));

        verify(productSearchUseCase).search("Ryzen");
        verify(mapper).toResponse(result);
    }

    @Test
    void shouldReturnEmptyList() throws Exception {

        when(productSearchUseCase.search("Ryzen"))
                .thenReturn(List.of());

        mockMvc.perform(
                        get("/api/products/search")
                                .param("query", "Ryzen"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(productSearchUseCase).search("Ryzen");
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldReturnBadRequestWhenQueryIsMissing() throws Exception {

        mockMvc.perform(
                        get("/api/products/search"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(productSearchUseCase);
        verifyNoInteractions(mapper);
    }

}