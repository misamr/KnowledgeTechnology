package java.com.example.demo;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.com.example.demo.utils.KnowledgeBase;
import org.slf4j.*;
import org.jgrapht.Graph;
import java.com.example.demo.domainmodel.Patient;
import java.com.example.demo.domainmodel.Survey;
import java.com.example.demo.rulemodel.RuleModel;
import java.com.example.demo.rulemodel.Inference;
import java.com.example.demo.utils.CustomVertex;
import java.com.example.demo.utils.QuestionsUtil;
import java.com.example.demo.utils.RelationshipEdge;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This is the controller class
 */
@Controller
public class ControllerKB {

    private Logger logger = LoggerFactory.getLogger(KBController.class);
    private Graph<CustomVertex, RelationshipEdge> graph;
    private Map<String, String[]> specialistsMap = null;

    @Resource
    private UserProfile fact; //this is a session scoped variable

    private Survey rootSurvey;

    /**
     * load the questions and the knowledge base on startup
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        graph = QuestionsUtil.getGraph();
        rootSurvey = QuestionsUtil.getSurveyInstance(graph, root);
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
        graph = QuestionsUtil.getGraph();
        rootSurvey = QuestionsUtil.getSurveyInstance(graph, root);
        Map = KnowledgeBase.getDomainKnowledgeMap();
        fact = new UserProfile();
        logger.info("Page initiated");
        model.addAttribute("survey", rootSurvey);

        model.addAttribute("question", rootSurvey.getQuestion());
        model.addAttribute("singleSelectAllText", rootSurvey.getOptionTextValues());
        model.addAttribute("singleSelectAllValues", rootSurvey.getOptionsValues());
        model.addAttribute("displayType", rootSurvey.getDisplayType());
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
    public String kbSubmit(@ModelAttribute Survey survey, Model model) throws Exception { /*Returning the appropriate pages after the questionnaire has been completed*/
        logger.info("Selected Values" +  survey.getRadioButtonSelectedValue());
        String[] values =  getValues(survey.getRadioButtonSelectedValue());
        CustomVertex cv = new CustomVertex(values[2], values[3], values[4]);
        String question = values[0];
        String selectedVal = values[1];
        RuleModel.populate(fact, question, selectedVal);

        Survey surveyNew = QuestionsUtil.getSurveyInstance(graph, cv);
        if (surveyNew.getQuestion().equals("exit")) { //if user has reached the last question then fire inference and get the recommendation
            Inference.inferRules(fact, specialistsMap);
            model.addAttribute("recommendations", fact.getRecommendations());
            return "recommendation";
        } else { //populate the model
            model.addAttribute("survey", surveyNew);
            model.addAttribute("question", surveyNew.getQuestion());
            model.addAttribute("singleSelectAllText", surveyNew.getOptionTextValues());
            model.addAttribute("singleSelectAllValues", surveyNew.getOptionsValues());
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
     * @return
     */
    private String[] getValues(String selectedValue){
        String[] retVal = new String[5];
        String[] multipleVal = selectedValue.split(",");
        String[] temp = multipleVal[0].split("%");
        retVal[0] = temp[0];
        retVal[1] = ""; //answer field
        retVal[2] = temp[2];
        retVal[3] = temp[3];
        retVal[4] = temp[4];
        for(int i = 0; i < multipleVal.length; i++){
            retVal[1] =  retVal[1] + multipleVal[i].split("%")[1] + ",";
        }
        retVal[1] = retVal[1].substring(0, retVal[1].lastIndexOf(","));

        return retVal;

    }
}
