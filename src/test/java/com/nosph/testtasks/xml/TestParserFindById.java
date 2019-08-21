package com.nosph.testtasks.xml;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.Test;

import com.nosph.testtasks.xml.model.XmlElement;
import com.nosph.testtasks.xml.model.XmlElements;
import com.nosph.testtasks.xml.parser.Parser;
import com.nosph.testtasks.xml.parser.impl.JsoupParser;

public class TestParserFindById extends TestSupport
{
    private Parser parser = new JsoupParser();

    @Test
    public void testOriginFile() throws IOException
    {
        XmlElements elements = parser.parse(getTestResource("examples/sample-0-origin.html"));

        Optional<XmlElement> targetElementOpt = elements.getElementById("make-everything-ok-button");

        assertTrue(targetElementOpt.isPresent());

        XmlElement targetElement = targetElementOpt.get();

        assertThat(targetElement.getXPath(), equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > a"));
        assertThat(targetElement.getAttributes(), equalTo(map("id", "make-everything-ok-button",
                                                                      "class", "btn btn-success",
                                                                      "href", "#ok",
                                                                      "title", "Make-Button",
                                                                      "rel", "next",
                                                                      "onclick", "javascript:window.okDone(); return false;")));
    }
}
