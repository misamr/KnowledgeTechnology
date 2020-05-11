package com.example.demo.utils;

import com.example.demo.ControllerKB;
import com.example.demo.domainmodel.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


public final class QuestionsUtil {
    private static Logger logger = LoggerFactory.getLogger(ControllerKB.class);

    /**
     * Initialzie the list of questions based on survey.txt
     *
     * @return final list of questions with tags of problems defined
     */
    public static Queue<Question> initializeQuestions() {
        Queue<Question> questions = new ArrayDeque<>();
        Resource resource = new ClassPathResource("survey");
        List<String> list = null;
        try {
            list = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert list != null;
        for (int i = 0; i < list.size(); i += 5) {
            String question = list.get(i);
            List<String> preconditions = Arrays.asList(list.get(i + 1).split(";"));
            List<Problem> problems = parsePreconditions(preconditions);
            String questionType = list.get(i + 2);
            List<String> answers = null;
            if (!questionType.equals("text")) {
                answers = Arrays.asList(list.get(i + 3).split(","));
            }
            questions.add(new Question(question, answers, questionType, problems));
        }
        return questions;
    }

    /**
     * Used to correctly parse the preconditions of the questions
     *
     * @param preconditions tags for problems
     * @return list of problems objects
     */
    private static List<Problem> parsePreconditions(List<String> preconditions) {
        if (preconditions.size() == 0) return new ArrayList<>();
        List<Problem> problems = new ArrayList<>();
        for (int i = 0; i < preconditions.size(); i++) {
            List<String> complaint = Arrays.asList(preconditions.get(i).split(","));
            Problem problem = new Problem(complaint.get(0));
            if (i == 0) {
                problem.setMajor(true);
            }
            for (String s : complaint) {
                List<String> supportive = Arrays.asList(s.split(":"));
                if (supportive.get(0).equals("frequency")) {
                    problem.setFrequency(supportive.get(1));
                } else if (supportive.get(0).equals("severity")) {
                    problem.setSeverity(supportive.get(1));
                }
            }
            problems.add(problem);
        }
        return problems;
    }


    /**
     * Get the question info for processing in controller
     *
     * @param question question to be asked
     * @return exit or populate the model
     */
    public static Survey getSurveyInstance(Question question) {
        Survey survey = new Survey();
        try {
            if (!question.getQuestionType().equals("text")) {
                String[] answersString = getSelectedAnswers(Objects.requireNonNull(question));
                survey.setOptions(answersString);
                String[] options = survey.getOptions();
                OptionTextValue[] optionTextValues = new OptionTextValue[options.length];
                for (int i = 0; i < options.length; i++) {
                    optionTextValues[i] = new OptionTextValue(question.getText(), options[i]);
                }
                survey.setOptionTextValue(optionTextValues);
            }
            survey.setQuestion(question);
            survey.setQuestionText(question.getText());
            survey.setDisplayType(question.getQuestionType());
        } catch (NullPointerException e) {
            survey.setQuestionText("exit");
        }
        return survey;
    }

    /**
     * Get the initial question
     *
     * @param questions list of questions
     * @return exit or populate the model
     */
    public static Survey getInitialSurveyInstance(Queue<Question> questions) {
        Survey survey = new Survey();
        Question question = questions.peek();
        survey.setQuestion(question);
        assert question != null;
        if (!question.getQuestionType().equals("text")) {
            String[] answersString = getSelectedAnswers(Objects.requireNonNull(question));
            survey.setOptions(answersString);
            String[] options = survey.getOptions();
            OptionTextValue[] optionTextValues = new OptionTextValue[options.length];
            for (int i = 0; i < options.length; i++) {
                optionTextValues[i] = new OptionTextValue(question.getText(), options[i]);
            }
            survey.setOptionTextValue(optionTextValues);
        }
        survey.setQuestionText(question.getText());
        survey.setDisplayType(question.getQuestionType());

        return survey;
    }

    /**
     * Get the selected answers
     *
     * @param question list of questions
     * @return selected answers depending on question type
     */
    private static String[] getSelectedAnswers(Question question) {
        List<String> answers = new ArrayList<>(question.getAnswers());
        String[] answersString = new String[answers.size()];
        for (int i = 0; i < answers.size(); i++) {
            answersString[i] = answers.get(i);
        }
        return answersString;
    }
}
