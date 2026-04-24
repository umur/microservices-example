CREATE TABLE watchlist_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    aggregate_id UUID NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    payload TEXT NOT NULL,
    occurred_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    sequence_number BIGSERIAL
);

CREATE INDEX idx_watchlist_events_aggregate ON watchlist_events (aggregate_id, sequence_number);
