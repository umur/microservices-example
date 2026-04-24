package com.cinetrack.review.infrastructure;

/**
 * Schema registry integration point.
 * In production, configure spring.kafka.producer.value-serializer to
 * io.confluent.kafka.serializers.KafkaAvroSerializer and use generated
 * Avro classes from review-posted.avsc.
 */
public final class AvroSchemaDoc {
    private AvroSchemaDoc() {}
}
