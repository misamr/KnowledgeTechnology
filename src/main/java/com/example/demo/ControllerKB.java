package com.example.demo;

import com.example.demo.domainmodel.Patient;
import com.example.demo.domainmodel.Question;
import com.example.demo.domainmodel.Survey;
import com.example.demo.rulemodel.Inference;
import com.example.demo.rulemodel.RuleModel;
import com.example.demo.utils.KnowledgeBase;
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
import java.util.Map;

/**
 * This is the controller class
 */
@Controller
public class ControllerKB {

    private Logger logger = LoggerFactory.getLogger(ControllerKB.class);
    private List<Question> questions;
    private Map<String, String[]> specialistsMap = null;

    @Resource
    private Patient patient; //this is a session scoped variable

    private Survey rootSurvey;

    /**
     * load the questions and the knowledge base on startup
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        questions = QuestionsUtil.initializeQuestions();
        specialistsMap = KnowledgeBase.getDomainKnowledgeMap();
    }

    /**
     * this will load the initial form
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/kb")
    public String kbForm(Model model) throws Exception {
        rootSurvey = QuestionsUtil.getSurveyInstance(questions.get(0).getText(), patient);
        specialistsMap = KnowledgeBase.getDomainKnowledgeMap();
        patient = new Patient();
        logger.info("Page initiated");
        model.addAttribute("survey", rootSurvey);
        model.addAttribute("questionText", rootSurvey.getQuestionText());
        model.addAttribute("singleSelectAllText", rootSurvey.getOptionTextValue());
        logger.info("Option text vals: " + Arrays.toString(rootSurvey.getOptionTextValue()));
        model.addAttribute("singleSelectAllValues", rootSurvey.getOptions());
        logger.info("options " + rootSurvey.getOptions());
        model.addAttribute("displayType", rootSurvey.getDisplayType());
        logger.info("display type " + rootSurvey.getDisplayType());
        return "home";
    }

    /**
     * it will load all the subsequent questions and the final recommendations
     *
     * @param survey
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/kb")
    public String kbSubmit(@ModelAttribute Survey survey, Model model) throws Exception {
        /* Returning the appropriate pages after the questionnaire has been completed */
        logger.info("Selected Values Radio " + survey.getRadioButtonSelectedValue());
        logger.info("Selected Values CB " + Arrays.toString(survey.getCheckBoxSelectedValues()));
        List<String> values = Arrays.asList(survey.getCheckBoxSelectedValues() == null ?
                getValues(new String[]{survey.getRadioButtonSelectedValue()}) : getValues(survey.getCheckBoxSelectedValues()));

        String questionText = survey.getQuestionText();
        if (values.size() == 1) {
            RuleModel.populate(patient, questionText, values.get(0));
        } else {
            RuleModel.populate(patient, questionText, values);
        }
        Question question = QuestionsUtil.getNextQuestion(questionText, patient);
        Survey surveyNew = QuestionsUtil.getSurveyInstance(question.getText(), patient);
        if (surveyNew.getQuestionText().equals("exit")) {
            //if user has reached the last questionText then fire inference and get the recommendation
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

    /**
     * function to get the value selected by the user
     * for radio buttons : single value
     * for checkboxes : multiple values returned
     *
     * @param selectedValue
     * @return retVal
     */
    private String[] getValues(String[] selectedValue) {
        logger.info("the selected values are: " + Arrays.toString(selectedValue));

        //        retVal[0] = temp[0];
//        retVal[1] = ""; //answer field
//        retVal[2] = temp[2];
//        retVal[3] = temp[3];
//        retVal[4] = temp[4];
//        logger.info("Refactoring: retVal: " + Arrays.toString(retVal));
//        for (String s : selectedValue) {
//            retVal[1] = retVal[1] + s.split("%")[1] + ",";
//        }
//        retVal[1] = retVal[1].substring(0, retVal[1].lastIndexOf(","));
//        logger.info("Refactoring: retVal after for loop: " + Arrays.toString(retVal));
        return selectedValue;

    }
}
