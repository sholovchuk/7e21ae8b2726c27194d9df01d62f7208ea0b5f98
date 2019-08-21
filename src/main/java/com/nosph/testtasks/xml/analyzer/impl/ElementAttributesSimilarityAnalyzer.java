package com.nosph.testtasks.xml.analyzer.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.nosph.testtasks.xml.analyzer.Analyzer;
import com.nosph.testtasks.xml.model.XmlElement;
import com.nosph.testtasks.xml.text.TextSimilarityCalculator;
import com.nosph.testtasks.xml.text.impl.JaroWinkler;
import com.nosph.testtasks.xml.text.impl.StringLengthSimilarityCalculator;

public class ElementAttributesSimilarityAnalyzer implements Analyzer
{
    private static final Predicate<Map.Entry<String, String>> idAttribute = e -> !e.getKey().equals("id");

    private TextSimilarityCalculator similarityCalculator = new StringLengthSimilarityCalculator(new JaroWinkler());

    @Override
    public Optional<XmlElement> findMostSimilarElement(XmlElement targetElement, List<XmlElement> elements)
    {
        if(elements.isEmpty() || targetElement.getAttributes().isEmpty())
        {
            return Optional.empty();
        }

        Map<String, Double> attributesWeights = calculateAttributesWeightsBasedOnTotalAttributesLength(targetElement);

        NavigableMap<Double, XmlElement> elementsSortedBySimilarity = new TreeMap<Double, XmlElement>(Collections.reverseOrder());
        for(XmlElement elementToCompare : elements)
        {
            double similarity = calculateSimilarity(targetElement, elementToCompare, attributesWeights);
            elementsSortedBySimilarity.put(similarity, elementToCompare);
        }

        if(elementsSortedBySimilarity.isEmpty())
        {
            return Optional.empty();
        }

        double similarity = elementsSortedBySimilarity.firstEntry().getKey();

        return Double.compare(similarity, 0.01) < 0? Optional.empty() : Optional.ofNullable(elementsSortedBySimilarity.firstEntry().getValue());
    }

    private Map<String, Double> calculateAttributesWeightsBasedOnTotalAttributesLength(XmlElement targetElement)
    {
        Set<Map.Entry<String, String>> attributes = targetElement.getAttributes().entrySet();

        int allAttributesLength = attributes.stream()
                                            .filter(idAttribute)
                                            .map(e -> e.getValue().length())
                                            .reduce(Integer::sum)
                                            .orElseThrow(RuntimeException::new);

        Map<String, Double> attributesWeights = attributes.stream()
                                                          .filter(idAttribute)
                                                          .collect(Collectors.toMap(
                                                              e -> e.getKey(),
                                                              e -> 1.0 * e.getValue().length() / allAttributesLength
                                                          ));

        return attributesWeights;
    }

    private double calculateSimilarity(XmlElement targetElement, XmlElement elementToCompare, Map<String, Double> attributesWeights)
    {
        double similarity = 0.0;
        Map<String, String> targetElemetAttributes = targetElement.getAttributes();
        Map<String, String> elementToCompareAttributes = elementToCompare.getAttributes();
        for(Map.Entry<String, Double> attributeWithWeight : attributesWeights.entrySet())
        {
            String key = attributeWithWeight.getKey();
            if(elementToCompareAttributes.containsKey(key))
            {
                double weight = attributeWithWeight.getValue();
                String targetElementAttribute = targetElemetAttributes.get(key);
                String elementToCompareAttribute = elementToCompareAttributes.get(key);

                similarity += weight * similarityCalculator.compare(targetElementAttribute, elementToCompareAttribute);
            }
        }
        return similarity;
    }
}
