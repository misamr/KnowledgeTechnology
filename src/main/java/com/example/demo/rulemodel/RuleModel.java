package com.example.demo.rulemodel;

import com.example.demo.domainmodel.Patient;
import com.example.demo.domainmodel.Problem;
import com.example.demo.domainmodel.Question;
import com.example.demo.utils.QuestionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * class for populating the domain model instance
 */
public class RuleModel {

    private static Logger logger = LoggerFactory.getLogger(RuleModel.class);
    private static Queue<Question> questions = QuestionsUtil.initializeQuestions();

    public static void populate(Patient patient, Question question, Queue<Question> questions, List<String> answer) {
        if (patient.getProblems().size() > 0 && question.getProblems().size() > 0) {
            Problem patientProblem = patient.getProblems().get(0);
            Problem questionProblem = question.getProblems().get(0);

            if (patientProblem.getComplaint().equals(questionProblem.getComplaint())) {
                String major = patientProblem.getComplaint();

                while (!Objects.requireNonNull(questions.peek()).getProblems().get(0).getComplaint().equals(major)) {
                    questions.remove();
                }
                if (major.equals("Headache")) {
                    Problem patientSecondaryProblem = patient.getProblems().get(1);

                    if (patientSecondaryProblem.getComplaint().equals("Muscle / joint pain")) {
                        if (patientSecondaryProblem.getSymptoms().isEmpty()) {
                            patientProblem.setSymptoms(answer);
                        } else {
                            patientProblem.setRiskFactors(answer);
                            String tag;
                            if (((patientProblem.getSeverity().equals("Moderate") ||
                                    patientProblem.getSeverity().equals("Severe")) ||
                                    (patientSecondaryProblem.getSeverity().equals("Moderate") ||
                                            patientSecondaryProblem.getSeverity().equals("Severe")))) {
                                if ((patientProblem.getRiskFactors().size() > 0 ||
                                        patientProblem.getSymptoms().size() > 0)) {
                                    tag = "Physiotherapist";
                                } else if (patientProblem.getSymptoms().contains("Fatigue")) {
                                    tag = "Neurologist";
                                } else {
                                    tag = "No specialist";
                                }
                                patient.getRecommendations().put(tag,
                                        patient.getRecommendations().getOrDefault(tag, 0) + 1);
                            }
                        }
                    } else if (patientSecondaryProblem.getComplaint().equals("Sore throat / cough / sinusitis")) {
                        patientProblem.setSymptoms(answer);
                    } else if (patientSecondaryProblem.getComplaint().equals("Fever")) {
                        patientProblem.setSymptoms(answer);
                        if (patientProblem.getSymptoms().size() >= 2) {
                            String tag = "Endocrynologist";
                            patient.getRecommendations().put(tag,
                                    patient.getRecommendations().getOrDefault(tag, 0) + 1);
                        }
                    } else if (patientSecondaryProblem.getComplaint().equals("None of the above")) {
                        patientProblem.setSymptoms(answer);
                        if ((patientProblem.getSeverity().equals("Low") || patientProblem.getSeverity().equals("Moderate"))
                                && patientProblem.getSymptoms().size() > 0 && !patientProblem.getSymptoms().contains("None of the above")) {
                            String tag = "Endocrynologist";
                            patient.getRecommendations().put(tag,
                                    patient.getRecommendations().getOrDefault(tag, 0) + 1);
                            questions.remove();
                        }
                    }

                }

            }
        }

    }

    public static void populate(Patient patient, Question question, Queue<Question> questions, String answer) {
        if (!question.getProblems().get(0).getComplaint().equals("")) {
            Problem patientProblem = patient.getProblems().get(0);
            Problem questionProblem = question.getProblems().get(0);


            if (patientProblem.getComplaint().equals(questionProblem.getComplaint())) {
                String major = patientProblem.getComplaint();

                while (!Objects.requireNonNull(questions.peek()).getProblems().get(0).getComplaint().equals(major)) {
                    questions.remove();
                }
                if (major.equals("Headache")) {
                    if (patientProblem.getSeverity() == null) {
                        patientProblem.setSeverity(answer);
                    } else if (patientProblem.getFrequency() == null) {
                        patientProblem.setFrequency(answer);
                    } else if (patient.getProblems().size() == 1) {
                        patient.getProblems().add(new Problem(answer));
                    } else if (patientProblem.getFrequency().equals("Recurring (chronic)") &&
                            patient.getProblems().get(1).getComplaint().equals("Muscle / joint pain")) {
                        Problem secondary = patient.getProblems().get(1);
                        if (secondary.getSeverity() == null) {
                            secondary.setSeverity(answer);
                        } else if (secondary.getSeverity().equals("Severe")
                                && patientProblem.getSeverity().equals("Severe")) {
                            String tag1 = "Physiotherapist";
                            String tag2 = "Neurologist";
                            patient.getRecommendations().put(tag1,
                                    patient.getRecommendations().getOrDefault(tag1, 0) + 1);
                            patient.getRecommendations().put(tag2,
                                    patient.getRecommendations().getOrDefault(tag2, 0) + 1);


                        } else if ((secondary.getSeverity().equals("Severe") ||
                                secondary.getSeverity().equals("Moderate"))
                                && (patientProblem.getSeverity().equals("Severe") ||
                                patientProblem.getSeverity().equals("Moderate"))) {
                        }
                    }
                }

            }
        } else {
            patient.setProblems(new ArrayList<>());
            patient.getProblems().add(new Problem(answer));
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
