package com.example.demo.domainmodel;

import java.util.Arrays;

public class Survey {

    private String question;
    private String[]  options;
    private String[] optionsValues;
    private OptionTextValue[] optionTextValues;
    private String radioButtonSelectedValue;
    private String displayType;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String[] getOptionsValues() {
        return optionsValues;
    }

    public void setOptionsValues(String[] optionsValues) {
        this.optionsValues = optionsValues;
    }

    public OptionTextValue[] getOptionTextValues() {
        return optionTextValues;
    }

    public void setOptionTextValues(OptionTextValue[] optionTextValues) {
        this.optionTextValues = optionTextValues;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "question='" + question + '\'' +
                ", options=" + Arrays.toString(options) +
                ", optionsValues=" + Arrays.toString(optionsValues) +
                ", optionTextValues=" + Arrays.toString(optionTextValues) +
                ", radioButtonSelectedValue='" + radioButtonSelectedValue + '\'' +
                ", displayType='" + displayType + '\'' +
                '}';
    }
}
