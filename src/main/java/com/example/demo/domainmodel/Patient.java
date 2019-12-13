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
    private List<String> recommendations;

    public void init() {
        this.age = null;
        this.recommendations = null;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

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
