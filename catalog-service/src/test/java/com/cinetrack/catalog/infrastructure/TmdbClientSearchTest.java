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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TmdbClientSearchTest {

    @Mock
    RestClient.Builder restClientBuilder;

    @Mock
    RestClient restClient;

    @Mock
    @SuppressWarnings("rawtypes")
    RestClient.RequestHeadersUriSpec uriSpec;

    @Mock
    @SuppressWarnings("rawtypes")
    RestClient.RequestHeadersSpec headersSpec;

    @Mock
    RestClient.ResponseSpec responseSpec;

    TmdbClient tmdbClient;

    @BeforeEach
    void setUp() {
        when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);
        tmdbClient = new TmdbClient(restClientBuilder, "test-api-key");
    }

    @Test
    @SuppressWarnings("unchecked")
    void searchMovies_whenRestClientReturns_returnsList() {
        doReturn(uriSpec).when(restClient).get();
        doReturn(headersSpec).when(uriSpec).uri(anyString(), any(), any());
        doReturn(responseSpec).when(headersSpec).retrieve();
        List<Map<String, Object>> expected = List.of(Map.of("title", "Inception"));
        doReturn(expected).when(responseSpec).body(List.class);

        var result = tmdbClient.searchMovies("inception");

        assertThat(result).hasSize(1);
    }

    @Test
    void searchFallback_withRuntimeException_returnsEmpty() {
        var result = tmdbClient.searchFallback("inception", new RuntimeException("timeout"));

        assertThat(result).isEmpty();
    }

    @Test
    void searchFallback_withDifferentExceptions_alwaysReturnsEmpty() {
        assertThat(tmdbClient.searchFallback("x", new RuntimeException("timeout"))).isEmpty();
        assertThat(tmdbClient.searchFallback("x", new IllegalStateException("circuit open"))).isEmpty();
    }
}
