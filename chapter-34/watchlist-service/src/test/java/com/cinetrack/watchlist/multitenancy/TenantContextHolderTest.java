package com.cinetrack.watchlist.multitenancy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TenantContextHolderTest {

    @AfterEach
    void cleanup() {
        TenantContextHolder.clear();
    }

    @Test
    void setAndGetTenant_works() {
        TenantContextHolder.setTenant("tenant-1");
        assertThat(TenantContextHolder.getTenant()).isEqualTo("tenant-1");
    }

    @Test
    void clear_removesTenant() {
        TenantContextHolder.setTenant("tenant-1");
        TenantContextHolder.clear();
        assertThat(TenantContextHolder.getTenant()).isNull();
    }

    @Test
    void getTenant_whenNotSet_returnsNull() {
        assertThat(TenantContextHolder.getTenant()).isNull();
    }
}
