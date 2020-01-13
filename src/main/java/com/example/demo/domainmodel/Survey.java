package com.example.demo.domainmodel;

import java.util.Arrays;

public class Survey {
    private Question question;
    private String questionText;
    private String[] options;
    private String[] checkBoxSelectedValues;
    private String radioButtonSelectedValue;
    private String checkBoxSelectedValue;
    private String displayType;
    private OptionTextValue[] optionTextValue;
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    public String getCheckBoxSelectedValue() {
        return checkBoxSelectedValue;
    }

    public void setCheckBoxSelectedValue(String checkBoxSelectedValue) {
        this.checkBoxSelectedValue = checkBoxSelectedValue;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String question) {
        this.questionText = question;
    }


    public String getRadioButtonSelectedValue() {
        return radioButtonSelectedValue;
    }

    public void setRadioButtonSelectedValue(String radioButtonSelectedValue) {
        this.radioButtonSelectedValue = radioButtonSelectedValue;
    }

    public String[] getOptions() {
        return options;
    }

    public String[] getCheckBoxSelectedValues() {
        return checkBoxSelectedValues;
    }

    public void setCheckBoxSelectedValues(String[] checkBoxSelectedValues) {
        this.checkBoxSelectedValues = checkBoxSelectedValues;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public OptionTextValue[] getOptionTextValue() {
        return optionTextValue;
    }

    public void setOptionTextValue(OptionTextValue[] optionTextValue) {
        this.optionTextValue = optionTextValue;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "question='" + questionText + '\'' +
                ", options=" + options +
                ", radioButtonSelectedValue='" + radioButtonSelectedValue + '\'' +
                ", checkBoxSelectedValues='" + Arrays.toString(checkBoxSelectedValues) + '\'' +
                ", displayType='" + displayType + '\'' +
                '}';
    }
}
