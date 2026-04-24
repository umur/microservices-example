package com.cinetrack.watchlist.multitenancy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TenantInterceptorTest {

    TenantInterceptor interceptor = new TenantInterceptor();

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;

    @AfterEach
    void cleanup() { TenantContextHolder.clear(); }

    @Test
    void preHandle_withTenantHeader_setsTenant() throws Exception {
        when(request.getHeader("X-Tenant-Id")).thenReturn("tenant-acme");
        interceptor.preHandle(request, response, new Object());
        assertThat(TenantContextHolder.getTenant()).isEqualTo("tenant-acme");
    }

    @Test
    void preHandle_withoutTenantHeader_setsDefault() throws Exception {
        when(request.getHeader("X-Tenant-Id")).thenReturn(null);
        interceptor.preHandle(request, response, new Object());
        assertThat(TenantContextHolder.getTenant()).isEqualTo("default");
    }

    @Test
    void preHandle_withBlankTenantHeader_setsDefault() throws Exception {
        when(request.getHeader("X-Tenant-Id")).thenReturn("  ");
        interceptor.preHandle(request, response, new Object());
        assertThat(TenantContextHolder.getTenant()).isEqualTo("default");
    }

    @Test
    void afterCompletion_clearsTenant() throws Exception {
        TenantContextHolder.setTenant("tenant-acme");
        interceptor.afterCompletion(request, response, new Object(), null);
        assertThat(TenantContextHolder.getTenant()).isNull();
    }
}
