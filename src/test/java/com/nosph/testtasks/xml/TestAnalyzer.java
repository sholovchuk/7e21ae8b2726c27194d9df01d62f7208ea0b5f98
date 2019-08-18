package com.nosph.testtasks.xml;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.nosph.testtasks.xml.analyzer.Analyzer;
import com.nosph.testtasks.xml.analyzer.ElementAttributesSimilarityAnalyzer;
import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.model.impl.ElementImpl;

public class TestAnalyzer extends TestSupport
{
    private Analyzer analyzer = new ElementAttributesSimilarityAnalyzer();

    private Element targetElement;

    @Before
    public void prepare()
    {
        targetElement = ElementImpl.create("", "", map("id", "make-everything-ok-button",
                                                       "class", "btn btn-success",
                                                       "href", "#ok",
                                                       "title", "Make-Button",
                                                       "rel", "next",
                                                       "onclick", "javascript:window.okDone(); return false;"));
    }

    @Test
    public void testFirstExample()
    {
        List<Element> elements = Arrays.asList(ElementImpl.create("", "", map("class", "btn btn-danger",
                                                                              "href", "#ok",
                                                                              "title", "Make-Button",
                                                                              "onclick", "javascript:window.close(); return false;")),
                                               ElementImpl.create("", "", map("class", "btn btn-success",
                                                                              "href", "#check-and-ok",
                                                                              "title", "Make-Button",
                                                                              "rel", "done",
                                                                              "onclick", "javascript:window.okDone(); return false;")));

        Optional<Element> mostSimilarElementOpt = analyzer.findMostSimilarElement(targetElement, elements);

        assertTrue(mostSimilarElementOpt.isPresent());

        Element mostSimilarElement = mostSimilarElementOpt.get();

        assertThat(mostSimilarElement, is(elements.get(1)));
    }

    @Test
    public void testSecondExample()
    {
        List<Element> elements = Arrays.asList(ElementImpl.create("", "", map("class", "btn test-link-ok",
                                                                              "href", "#ok",
                                                                              "title", "Make-Button",
                                                                              "rel", "next",
                                                                              "onclick", "javascript:window.okComplete(); return false;")),
                                               ElementImpl.create("", "", map("class", "btn btn-info",
                                                                              "href", "#incorrect-link",
                                                                              "title", "Make-Button",
                                                                              "onclick", "javascript:window.close(); return false;")));

        Optional<Element> mostSimilarElementOpt = analyzer.findMostSimilarElement(targetElement, elements);

        assertTrue(mostSimilarElementOpt.isPresent());

        Element mostSimilarElement = mostSimilarElementOpt.get();

        assertThat(mostSimilarElement, is(elements.get(0)));
    }

    @Test
    public void testThirdExample()
    {
        List<Element> elements = Arrays.asList(ElementImpl.create("", "", map("class", "btn btn-warning",
                                                                              "href", "#ok",
                                                                              "rel", "next",
                                                                              "onclick", "javascript:window.close(); return false;")),
                                               ElementImpl.create("", "", map("class", "btn btn-success",
                                                                              "href", "#ok",
                                                                              "title", "Do-Link",
                                                                              "rel", "next",
                                                                              "onclick", "javascript:window.okDone(); return false;")));

        Optional<Element> mostSimilarElementOpt = analyzer.findMostSimilarElement(targetElement, elements);

        assertTrue(mostSimilarElementOpt.isPresent());

        Element mostSimilarElement = mostSimilarElementOpt.get();

        assertThat(mostSimilarElement, is(elements.get(1)));
    }

    @Test
    public void testFourthExample()
    {
        List<Element> elements = Arrays.asList(ElementImpl.create("", "", map("class", "btn btn-success",
                                                                              "href", "#ok",
                                                                              "title", "Make-Button",
                                                                              "rel", "next",
                                                                              "onclick", "javascript:window.okFinalize(); return false;")),
                                               ElementImpl.create("", "", map("class", "btn btn-default btn-block")));

        Optional<Element> mostSimilarElementOpt = analyzer.findMostSimilarElement(targetElement, elements);

        assertTrue(mostSimilarElementOpt.isPresent());

        Element mostSimilarElement = mostSimilarElementOpt.get();

        assertThat(mostSimilarElement, is(elements.get(0)));
    }
}
