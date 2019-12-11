package java.com.example.demo.rulemodel;

import java.com.example.demo.domainmodel.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * class for populating the domain model instance
 */
public class RuleModel {

    private static Logger logger = LoggerFactory.getLogger(RuleModel.class);

    public static void populate(UserProfile fact, String question, String answer){

        if(question.equals("Which age group do you belong to?")) {
            fact.init();
            fact.setAge(answer);
        } else {
            logger.info("Question did not match any=" +  question);
        }

    }

}
