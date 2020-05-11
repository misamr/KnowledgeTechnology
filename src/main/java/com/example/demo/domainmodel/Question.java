package com.example.demo.domainmodel;


import java.util.HashMap;
import java.util.List;

/**
 * Class representing question data
 */
public class Question {
    private List<Problem> problems;
    private String text;
    private List<String> answers;
    private String questionType;
    private List<Question> nextQuestions;
    public Question(String text, List<String> answers, String questionType,
                    List<Problem> problems) {
        this.text = text;
        this.answers = answers;
        this.questionType = questionType;
        this.problems = problems;
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


    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }


    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }


    public List<Question> getNextQuestions() {
        return nextQuestions;
    }

    public void setNextQuestions(List<Question> nextQuestions) {
        this.nextQuestions = nextQuestions;
    }


}
