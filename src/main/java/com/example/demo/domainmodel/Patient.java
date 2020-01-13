package com.example.demo.domainmodel;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This the domain model and it is session scoped
 * for each session a new instance is created
 * it is managed by Spring
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Patient implements Serializable {
    private HashMap<String, Integer> recommendations;
    private List<String> specialists;

    public void init() {
        this.recommendations = new HashMap<>();
        this.specialists = new ArrayList<>();
    }

    public HashMap<String, Integer> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(HashMap<String, Integer> recommendations) {
        this.recommendations = recommendations;
    }

    public List<String> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(List<String> specialists) {
        this.specialists = specialists;
    }

    @Override
    public String toString() {
        return "Patient{" +
                ", recommendations=" + recommendations +
                '}';
    }
}
