package com.example.demo.domainmodel;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Domain model of the system
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Patient implements Serializable {
    private HashMap<String, Integer> recommendations;
    private List<String> specialists;
    private List<Problem> problems;

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

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }
}
