package com.cinetrack.catalog.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TmdbClientTest {

    @Mock
    private RestClient.Builder restClientBuilder;

    @Mock
    private RestClient restClient;

    @BeforeEach
    void setUp() {
        when(restClientBuilder.baseUrl(any(String.class))).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);
    }

    @Test
    void searchFallback_whenExceptionThrown_returnsEmptyList() {
        TmdbClient client = new TmdbClient(restClientBuilder, "test-key");
        List<Map<String, Object>> result = client.searchFallback("Inception", new RuntimeException("timeout"));
        assertThat(result).isEmpty();
    }
}
