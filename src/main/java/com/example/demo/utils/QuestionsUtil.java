package com.example.demo.utils;

import com.example.demo.ControllerKB;
import com.example.demo.domainmodel.OptionTextValue;
import com.example.demo.domainmodel.Patient;
import com.example.demo.domainmodel.Question;
import com.example.demo.domainmodel.Survey;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public final class QuestionsUtil {
    private static Logger logger = LoggerFactory.getLogger(ControllerKB.class);

    public static List<Question> initializeQuestions() {
        List<Question> questions = new ArrayList<>();
        Resource resource = new ClassPathResource("survey");
        List<String> list = null;
        try {
            list = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert list != null;
        for (int i = 0; i < list.size(); i += 4) {
            String question = list.get(i);
            List<String> tags = Arrays.asList(list.get(i + 1).split(","));
            String questionType = list.get(i + 2);
            String[] answers = list.get(i + 3).split(";");
            HashMap<String, List<String>> answersAndTags = new HashMap<>();
            for (String s : answers) {
                List<String> answersTagsTemp = Arrays.asList(s.split(","));
                String answer = answersTagsTemp.get(0);
                List<String> answersAndTagsList = new ArrayList<>(answersTagsTemp.subList(1, answersTagsTemp.size()));
                answersAndTags.put(answer, answersAndTagsList);
            }
            questions.add(new Question(question, tags, answersAndTags, questionType));
        }
        return questions;
    }

    private static Question getNextQuestion(Question question, Patient patient) {
        List<Question> questions = initializeQuestions();
        Question next = null;
        for (Question q : questions) {
            if (q.getText().equals(question.getText())) {
                next = q;
                break;
            }
        }
        if (patient.getRecommendations() != null) {
            for (String tag : patient.getRecommendations().keySet()) {
                if (next != null && next.getTags().contains(tag)) {
                    next = questions.get(questions.indexOf(next));
                }
            }
        }
        return next;
    }


    public static Survey getSurveyInstance(Question question, Patient patient) throws Exception {
        Survey survey = new Survey();
        Question next = getNextQuestion(question, patient);
        List<String> answers = new ArrayList<>(next.getAnswers().keySet());
        survey.setQuestion(next);
        survey.setOptions(String.join(",", answers));
        survey.setQuestionText(next.getText());
        survey.setDisplayType(next.getQuestionType());
        String[] options = survey.getOptions().split(",");
        OptionTextValue[] optionTextValues = new OptionTextValue[options.length];
        for (int i = 0; i < options.length; i++){
            optionTextValues[i] = new OptionTextValue(next.getText(), options[i]);
        }
        survey.setOptionTextValue(optionTextValues);
        return survey;
    }
}
