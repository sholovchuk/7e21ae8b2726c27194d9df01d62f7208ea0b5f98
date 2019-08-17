package com.nosph.testtasks.xml.analyzer;

import java.util.List;
import java.util.Optional;

import com.nosph.testtasks.xml.model.Element;

public interface Analyzer
{
    Optional<Element> findMostSimilarElement(Element targetElement, List<Element> elements);
}
