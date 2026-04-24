package com.cinetrack.recommendation.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TasteProfileTest {

    @Test
    void getUserId_returnsUserId() {
        var profile = new TasteProfile("user-1");
        assertThat(profile.getUserId()).isEqualTo("user-1");
    }

    @Test
    void addGenreScore_accumulatesScores() {
        var profile = new TasteProfile("user-1");
        profile.addGenreScore("action", 3.0);
        profile.addGenreScore("action", 2.0);
        assertThat(profile.getGenreScores().get("action")).isEqualTo(5.0);
    }

    @Test
    void addActorScore_accumulatesScores() {
        var profile = new TasteProfile("user-1");
        profile.addActorScore("Tom Hanks", 4.0);
        profile.addActorScore("Tom Hanks", 1.0);
        assertThat(profile.getActorScores().get("Tom Hanks")).isEqualTo(5.0);
    }

    @Test
    void addGenreScore_multipleDifferentGenres() {
        var profile = new TasteProfile("user-1");
        profile.addGenreScore("action", 2.0);
        profile.addGenreScore("drama", 3.0);
        assertThat(profile.getGenreScores()).hasSize(2);
    }

    @Test
    void initialProfile_hasEmptyScores() {
        var profile = new TasteProfile("user-2");
        assertThat(profile.getGenreScores()).isEmpty();
        assertThat(profile.getActorScores()).isEmpty();
    }
}
