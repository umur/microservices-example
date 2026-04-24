CREATE TABLE watchlist_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    movie_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL,
    added_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_user_movie UNIQUE (user_id, movie_id)
);

CREATE INDEX idx_watchlist_user ON watchlist_items (user_id);
