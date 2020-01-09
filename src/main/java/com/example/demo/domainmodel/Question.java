package com.example.demo.domainmodel;

import java.util.HashMap;
import java.util.List;

public class Question {
    private String text;
    private List<String> tags;
    private HashMap<String, List<String>> answers;
    private String questionType;

    public Question(String text, List<String> tags, HashMap<String, List<String>> answers, String questionType) {
        this.text = text;
        this.tags = tags;
        this.answers = answers;
        this.questionType = questionType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public HashMap<String, List<String>> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, List<String>> answers) {
        this.answers = answers;
    }


}
