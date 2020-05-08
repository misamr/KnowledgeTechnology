package com.example.demo;

import com.example.demo.domainmodel.Patient;
import com.example.demo.domainmodel.Question;
import com.example.demo.domainmodel.Survey;
import com.example.demo.rulemodel.Inference;
import com.example.demo.rulemodel.RuleModel;
import com.example.demo.utils.QuestionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * This is the controller class
 */
@Controller
public class ControllerKB {

    private Logger logger = LoggerFactory.getLogger(ControllerKB.class);
    private Queue<Question> questions;

    @Resource
    private Patient patient; //this is a session scoped variable
    private List<Question> coveredQuestions;
    private Survey rootSurvey;

    /**
     * load the questions and the knowledge base on startup
     *
     */
    @PostConstruct
    public void init() {
        questions = QuestionsUtil.initializeQuestions();
    }

    /**
     * Loads the initial form
     *
     * @param model model of the system
     * @return formed page of options
     */
    @GetMapping("/kb")
    public String kbForm(Model model) {
        rootSurvey = QuestionsUtil.getInitialSurveyInstance(questions, patient);
        patient.init();
        logger.info("Page initiated");
        model.addAttribute("survey", rootSurvey);
        model.addAttribute("questionText", rootSurvey.getQuestionText());
        model.addAttribute("singleSelectAllText", rootSurvey.getOptionTextValue());
        model.addAttribute("singleSelectAllValues", rootSurvey.getOptions());
        logger.info("options " + Arrays.toString(rootSurvey.getOptions()));
        model.addAttribute("displayType", rootSurvey.getDisplayType());
        return "home";
    }

    /**
     * Loads all the subsequent questions and the final recommendations
     *
     * @param survey set of questions to be asked
     * @param model model of the system
     * @return sends to the next page of survey or results page
     */
    @PostMapping("/kb")
    public String kbSubmit(@ModelAttribute Survey survey, Model model) {
        /* Returning the appropriate pages after the questionnaire has been completed */
        List<String> values = Arrays.asList(survey.getCheckBoxSelectedValues() == null ?
                new String[]{survey.getRadioButtonSelectedValue()} : survey.getCheckBoxSelectedValues());
        Question currentQuestion = questions.peek();
        logger.info(String.valueOf(currentQuestion.getProblems().get(0).getComplaint().equals("")));
        if (values.size() == 1) {
            RuleModel.populate(patient, currentQuestion, questions, values.get(0));
        } else {
            RuleModel.populate(patient, currentQuestion, questions, values);
        }
        logger.info("Patient data " + patient.getRecommendations().keySet());
        questions.remove();
        Question nextQuestion = questions.peek();
        assert nextQuestion != null;
        Survey surveyNew = QuestionsUtil.getSurveyInstance(nextQuestion, patient);
        if (surveyNew.getQuestionText().equals("exit")) {
            Inference.inferRules(patient);
            model.addAttribute("specialists", patient.getSpecialists());
            return "recommendation";
        } else { //populate the model
            model.addAttribute("survey", surveyNew);
            model.addAttribute("questionText", surveyNew.getQuestionText());
            model.addAttribute("singleSelectAllText", surveyNew.getOptions());
            model.addAttribute("singleSelectAllValues", surveyNew.getOptions());
            model.addAttribute("displayType", surveyNew.getDisplayType());
            return "home";
        }
    }
}
