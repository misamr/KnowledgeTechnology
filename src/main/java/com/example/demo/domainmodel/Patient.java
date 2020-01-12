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
    private String age;
    private List<String> symptoms;
    private List<String> medications;
    private HashMap<String, Integer> recommendations;
    private List<String> specialists;

    public void init() {
        this.age = null;
        this.symptoms = new ArrayList<>();
        this.medications = new ArrayList<>();
        this.recommendations = new HashMap<>();
        this.specialists = new ArrayList<>();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public void addSymptom(String symptom) {
        this.symptoms.add(symptom);
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public void addMedication(String medication) {
        this.medications.add(medication);
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
                "age='" + age + '\'' +
                ", recommendations=" + recommendations +
                '}';
    }
}
