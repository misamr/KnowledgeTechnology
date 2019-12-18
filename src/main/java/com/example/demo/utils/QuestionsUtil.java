package com.example.demo.utils;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.*;
import org.jgrapht.io.*;
import com.example.demo.domainmodel.OptionTextValue;
import com.example.demo.domainmodel.Survey;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.*;

/**
 * this class loads the questionnaire from questionsGraphUP.gml
 */
public final class QuestionsUtil {

    private static String getQuestionsGraph() throws Exception {
        Resource resource = new ClassPathResource("questionsGraphUP.gml");
        List<String> list = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
        return String.join("", list);

    }

    /**
     * load the graph
     *
     * @return
     * @throws Exception
     */
    public static Graph<CustomVertex, RelationshipEdge> getGraph() throws Exception {

        Graph<CustomVertex, RelationshipEdge> g  = new DirectedPseudograph<>(RelationshipEdge.class);

        VertexProvider<CustomVertex> vp = (id, attributes) -> {
            return new CustomVertex(id,attributes.get("label").toString(), attributes.get("display").toString() );
        };


        EdgeProvider<CustomVertex, RelationshipEdge> ep = (from, to, label, attributes) -> {

            return new RelationshipEdge(attributes.get("label").toString());
        };

        GmlImporter<CustomVertex, RelationshipEdge> importer = new GmlImporter<>(vp, ep);

        importer.importGraph(g, new StringReader(getQuestionsGraph()));

        return g;
    }

    /**
     * get a specific node for the specified custom vertex from the graph
     *
     * @param g
     * @param cv
     * @return
     * @throws Exception
     */
    public static Survey getSurveyInstance(Graph<CustomVertex, RelationshipEdge> g, CustomVertex cv) throws Exception{
        Survey survey = new Survey();

        Set<RelationshipEdge> edges = g.outgoingEdgesOf(cv);
        String[] options = new String[edges.size()];
        String[] optionsValues = new String[edges.size()];
        OptionTextValue[]  optV = new OptionTextValue[edges.size()];
        final AtomicInteger indexHolder = new AtomicInteger();
        edges.forEach(element -> {
            int i = indexHolder.getAndIncrement();
            options[i] = element.getLabel();
            optionsValues[i] = element.getLabel() + "x";
            String value  = cv.getLabel() + "%" + options[i] + "%" + g.getEdgeTarget(element).getId() + "%" + g.getEdgeTarget(element).getLabel()
                    + "%"  + g.getEdgeTarget(element).getDisplay(); //when you click on 'no' what should be the next value (concatenate)
            optV[i] = new OptionTextValue(element.getLabel(),value );
        });
        survey.setOptions(options);
        survey.setOptionsValues(optionsValues);
        survey.setOptionTextValues(optV);
        survey.setQuestion(cv.getLabel());
        survey.setDisplayType(cv.getDisplay());
        return survey;
    }
}
