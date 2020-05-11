package com.example.demo.rulemodel;

import com.example.demo.domainmodel.Patient;
import com.example.demo.domainmodel.Problem;
import com.example.demo.domainmodel.Question;
import com.example.demo.utils.QuestionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * class for populating the domain model instance
 */
public class RuleModel {

    private static Logger logger = LoggerFactory.getLogger(RuleModel.class);
    private static Queue<Question> questions = QuestionsUtil.initializeQuestions();

    public static void populate(Patient patient, Question question, Queue<Question> questions, List<String> answer) {
        logger.info("checkbox: " + question.getText());
        if (patient.getProblems().size() > 0 && question.getProblems().size() > 0) {
            Problem patientProblem = patient.getProblems().get(0);
            String major = patientProblem.getComplaint();
            switch (major) {
                case "Headache": {
                    Problem patientSecondaryProblem = patient.getProblems().get(1);
                    switch (patientSecondaryProblem.getComplaint()) {
                        case "Muscle / joint pain":
                        case "Sore throat / cough / sinusitis":
                        case "None of the above":
                        case "Fever":
                            patientProblem.setSymptoms(answer);
                            break;
                    }
                    break;
                }
                case "Muscle / joint pain": {
                    Problem patientSecondaryProblem = patient.getProblems().get(1);
                    switch (patientSecondaryProblem.getComplaint()) {
                        case "Headache":
                        case "!Difficulty breathing":
                            patientProblem.setSymptoms(answer);
                            break;
                    }
                    break;
                }
                case "Chest pain":
                case "Sore throat / cough / sinusitis":
                case "Abdominal problems":
                case "Difficulty breathing":
                    patientProblem.setSymptoms(answer);
                    break;
            }

        }
        removeIrrelevantQuestions(questions, patient.getProblems());
    }

    public static void populate(Patient patient, Question question, Queue<Question> questions, String answer) {
        logger.info("radio: " + question.getText());

        if (!question.getProblems().get(0).getComplaint().equals("")) {
            Problem patientProblem = patient.getProblems().get(0);
            String major = patientProblem.getComplaint();
            Problem patientSecondaryProblem = patient.getProblems().size() > 1 ?
                    patient.getProblems().get(1) : null;
            assert questions.peek() != null;
            switch (major) {
                case "Headache":
                    if (patientProblem.getSeverity() == null) {
                        patientProblem.setSeverity(answer);
                    } else if (patientProblem.getFrequency() == null) {
                        patientProblem.setFrequency(answer);
                    } else if (patient.getProblems().size() == 1) {
                        patient.getProblems().add(new Problem(answer));
                    } else {
                        switch (Objects.requireNonNull(patientSecondaryProblem).getComplaint()) {
                            case "Muscle / joint pain":
                                if (patientProblem.getFrequency().equals("Recurring (chronic)")) {
                                    Problem secondary = patient.getProblems().get(1);
                                    if (secondary.getSeverity() == null) {
                                        secondary.setSeverity(answer);
                                    } else if (patientProblem.getRiskFactors() == null) {
                                        patientProblem.setRiskFactors(Collections.singletonList(answer));
                                    }
                                }
                                break;
                            case "Sore throat / cough / sinusitis":
                                if (question.getText().equals("Is the patient a smoker?")) {
                                    if (answer.equals("Yes")) {
                                        patientSecondaryProblem.getRiskFactors().add("Smoker");
                                    }
                                }
                                break;

                        }
                    }
                    break;
                case "Muscle / joint pain":
                    switch (question.getText()) {
                        case "Does the patient have a headache?":
                            if (answer.equals("Yes")) {
                                patient.getProblems().add(new Problem("Headache"));
                            } else {
                                patient.getProblems().add(new Problem("!Headache"));
                            }
                            break;
                        case "Does the patient have difficulty breathing?":
                            if (answer.equals("Yes")) {
                                patient.getProblems().add(new Problem("Difficulty breathing"));
                            } else {
                                patient.getProblems().add(new Problem("!Difficulty breathing"));
                            }
                            break;
                        case "Does the patient have chest pain?":
                            if (answer.equals("Yes")) {
                                patient.getProblems().add(new Problem("Chest pain"));
                            } else {
                                patient.getProblems().add(new Problem("!Chest pain"));
                            }
                            break;
                    }
                    break;
                case "Chest pain":
                    if (question.getText().equals("Does the patient have difficulty breathing?")) {
                        if (answer.equals("Yes")) {
                            patient.getProblems().add(new Problem("Difficulty breathing"));
                        } else {
                            patient.getProblems().add(new Problem("!Difficulty breathing"));

                        }
                    }
                    break;
                case "Sore throat / cough / sinusitis":
                    if (question.getText().equals("Does the patient have dermatitis (rashes / swollen skin)?")) {
                        if (answer.equals("Yes")) {
                            patient.getProblems().add(new Problem("Dermatitis"));
                        } else {
                            patient.getProblems().add(new Problem("!Dermatitis"));

                        }
                    }
                    break;
                case "Difficulty breathing":
                    if (question.getText().equals("Does the patient have muscle join / pain?")) {
                        if (answer.equals("Yes")) {
                            patient.getProblems().add(new Problem("Muscle / joint pain"));
                        }
                    } else if (question.getText().equals("Does the patient have chest pain /discomfort?")) {
                        if (answer.equals("Yes")) {
                            patient.getProblems().add(new Problem("Chest pain"));
                        } else {
                            patient.getProblems().add(new Problem("!Chest pain"));
                        }
                    }
                    break;
            }

        } else {
            patient.setProblems(new ArrayList<>());
            patient.getProblems().add(new Problem(answer));
        }
        removeIrrelevantQuestions(questions, patient.getProblems());

    }

    private static void removeIrrelevantQuestions(Queue<Question> questions, List<Problem> patientProblems) {
        if (questions.peek() != null) {
            logger.info("Null :" + patientProblems.toString() + ", " + questions.peek().getProblems());
            while (questions.peek() != null && !findMatch(patientProblems, questions.peek().getProblems())) {
                questions.remove();
            }
        }


    }

    private static boolean findMatch(List<Problem> patientProblems, List<Problem> qProblems) {
        boolean match = false;
        int cnt = 0;
        for (Problem q : qProblems) {
            for (Problem p : patientProblems) {
                logger.info("Null checks:" + p.toString() + ", " + q.toString());
                if (q.getComplaint().equals(p.getComplaint()) &&
                        compareStrings(q.getFrequency(), p.getFrequency()) &&
                        compareStrings(q.getSeverity(), p.getSeverity())) {
                    logger.info("Match: " + q.getComplaint() + "," + p.getComplaint());
                    match = true;
                    cnt++;
                    break;
                }
            }
        }
        logger.info("finished null check");
        if (cnt != patientProblems.size()) match = false;
        return match;
    }


    public static boolean compareStrings(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return true;
        } else if (s1.charAt(0) == '!') {
            if (s2.charAt(0) == '!') {
                return s1.substring(1).equals(s2.substring(1));
            } else {
                return !s1.substring(1).equals(s2);
            }
        } else {
            if (s2.charAt(0) == '!') {
                return !s1.equals(s2.substring(1));
            } else {
                return s1.equals(s2);
            }
        }
    }

    public static Question getQuestionKB(String question) {
        Question questionKB = null;
        for (Question q : questions) {
            if (q.getText().equals(question)) {
                questionKB = q;
                break;
            }
        }
        if (questionKB == null) logger.info("Question did not match any=" + question);
        return questionKB;
    }
}
