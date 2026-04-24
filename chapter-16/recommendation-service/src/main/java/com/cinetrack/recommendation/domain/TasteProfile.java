package com.cinetrack.recommendation.domain;

import java.util.HashMap;
import java.util.Map;

public class TasteProfile {

    private final String userId;
    private final Map<String, Double> genreScores;
    private final Map<String, Double> actorScores;

    public TasteProfile(String userId) {
        this.userId = userId;
        this.genreScores = new HashMap<>();
        this.actorScores = new HashMap<>();
    }

    public String getUserId() { return userId; }
    public Map<String, Double> getGenreScores() { return genreScores; }
    public Map<String, Double> getActorScores() { return actorScores; }

    public void addGenreScore(String genre, double delta) {
        genreScores.merge(genre, delta, Double::sum);
    }

    public void addActorScore(String actor, double delta) {
        actorScores.merge(actor, delta, Double::sum);
    }
}
