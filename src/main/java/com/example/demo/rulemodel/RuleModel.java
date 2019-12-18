package com.example.demo.rulemodel;

import com.example.demo.domainmodel.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * class for populating the domain model instance
 */
public class RuleModel {

    private static Logger logger = LoggerFactory.getLogger(RuleModel.class);

    public static void populate(Patient fact, String question, String answer){

        if(question.equals("Which age group does the patient belong to?")) {
            fact.init();
            fact.setAge(answer);
        }

        else if(question.equals("Does the patient have a sore throat?")) {
            //fact.init();
            if(answer.equals("yes")) {
                fact.addSymptom("sore throat");
            }
        }

        else {
            logger.info("Question did not match any=" +  question);
        }
    }

    public static void populate(Patient fact, String question, List<String> answers){


        if(question.equals("Does the patient suffer from any of the following conditions?")) {
            for(int i=0; i<answers.size();i++){
                fact.addSymptom(answers.get(i));
            }
        }

        else {
            logger.info("Question did not match any=" +  question);
        }
    }

}
