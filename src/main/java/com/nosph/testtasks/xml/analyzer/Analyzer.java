package com.nosph.testtasks.xml.analyzer;

import java.util.List;
import java.util.Optional;

import com.nosph.testtasks.xml.model.XmlElement;

public interface Analyzer
{
    Optional<XmlElement> findMostSimilarElement(XmlElement targetElement, List<XmlElement> elements);
}
