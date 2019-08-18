package com.nosph.testtasks.xml.analyzer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.text.JaroWinkler;
import com.nosph.testtasks.xml.text.StringLengthSimilarityCalculator;
import com.nosph.testtasks.xml.text.TextSimilarityCalculator;

public class ElementAttributesSimilarityAnalyzer implements Analyzer
{
    private static final Predicate<Map.Entry<String, String>> idAttribute = e -> !e.getKey().equals("id");

    private TextSimilarityCalculator similarityCalculator = new StringLengthSimilarityCalculator(new JaroWinkler());

    @Override
    public Optional<Element> findMostSimilarElement(Element targetElement, List<Element> elements)
    {
        Map<String, Double> attributesWeights = calculateAttributesWeightsBasedOnTotalAttributesLength(targetElement);

        NavigableMap<Double, Element> elementsSortedBySimilarity = new TreeMap<Double, Element>(Collections.reverseOrder());
        for(Element elementToCompare : elements)
        {
            double similarity = calculateSimilarity(targetElement, elementToCompare, attributesWeights);
            elementsSortedBySimilarity.put(similarity, elementToCompare);
        }

        return elementsSortedBySimilarity.isEmpty()? Optional.empty() : Optional.ofNullable(elementsSortedBySimilarity.firstEntry().getValue());
    }

    private Map<String, Double> calculateAttributesWeightsBasedOnTotalAttributesLength(Element targetElement)
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

    private double calculateSimilarity(Element targetElement, Element elementToCompare, Map<String, Double> attributesWeights)
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
