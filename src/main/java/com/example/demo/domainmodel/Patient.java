package com.example.demo.domainmodel;

import java.io.Serializable;
import java.util.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * This the domain model and it is session scoped
 * for each session a new instance is created
 * it is managed by Spring
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Patient  implements Serializable {
    private String age;
    private String gender;
    private List<String> symptoms ;
    private List<String> medications;
    private List<String> recommendations;

    public void init() {
        this.age = null;
        this.gender = null;
        //this.symptoms = new ArrayList<String>();
        this.symptoms = new ArrayList<String>();;
        this.medications = new ArrayList<String>();
        this.recommendations = null;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getGender(){return gender;}
    public void setGender(String gender) {this.gender = gender;}

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
    public void addMedication(String medication) {this.medications.add(medication);}

    
    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "age='" + age + '\'' +
                ", recommendations=" + recommendations +
                '}';
    }
}
