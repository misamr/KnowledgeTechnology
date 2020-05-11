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
    private boolean fever;
    private int age = Integer.MIN_VALUE;
    private int systolic = Integer.MIN_VALUE;
    private int diastolic = Integer.MIN_VALUE;
    private int weight = Integer.MIN_VALUE;
    private int height = Integer.MIN_VALUE;
    private int temperature = Integer.MIN_VALUE;

    public void init() {
        this.recommendations = new HashMap<>();
        this.specialists = new ArrayList<>();
        this.problems = new ArrayList<>();
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
                "recommendations=" + recommendations +
                ", specialists=" + specialists +
                ", problems=" + problems +
                ", fever=" + fever +
                ", age=" + age +
                ", systolic=" + systolic +
                ", diastolic=" + diastolic +
                ", weight=" + weight +
                ", height=" + height +
                ", temperature=" + temperature +
                '}';
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public boolean isFever() {
        return fever;
    }

    public void setFever(boolean fever) {
        this.fever = fever;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
