package com.cinetrack.review.infrastructure;

import org.junit.jupiter.api.Test;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

class AvroSchemaDocTest {

    @Test
    void avroSchemaFile_existsOnClasspath() {
        InputStream schema = getClass().getResourceAsStream("/avro/review-posted.avsc");
        assertThat(schema).isNotNull();
    }
}
