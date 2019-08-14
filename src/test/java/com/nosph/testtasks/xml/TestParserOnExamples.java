package com.nosph.testtasks.xml;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.model.Elements;
import com.nosph.testtasks.xml.parser.SearchingParser;
import com.nosph.testtasks.xml.parser.impl.ParserImpl;

public class TestParserOnExamples extends ParserTestSupport
{
    private SearchingParser parser = new ParserImpl();

    @Test
    public void testOriginFile() throws IOException
    {
        Elements elements = parser.parse("a", getTestResource("examples/sample-0-origin.html"));
        List<Element> foundElements = elements.getElementsByTag("a");

        assertThat(foundElements, hasSize(43));

        assertThat(foundElements.get(32).getName(), equalTo("a"));
        assertThat(foundElements.get(32).getXPath(), equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > a"));
        assertThat(foundElements.get(32).getAttributes(), equalTo(map("id", "make-everything-ok-button",
                                                                      "class", "btn btn-success",
                                                                      "href", "#ok",
                                                                      "title", "Make-Button",
                                                                      "rel", "next",
                                                                      "onclick", "javascript:window.okDone(); return false;")));
    }

    @Test
    public void testFirstFile() throws IOException
    {
        Elements elements = parser.parse("a", getTestResource("examples/sample-1-evil-gemini.html"));
        List<Element> foundElements = elements.getElementsByTag("a");

        assertThat(foundElements, hasSize(44));

        assertThat(foundElements.get(32).getName(), equalTo("a"));
        assertThat(foundElements.get(32).getXPath(), equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > a[1]"));
        assertThat(foundElements.get(32).getAttributes(), equalTo(map("class", "btn btn-danger",
                                                                      "href", "#ok",
                                                                      "title", "Make-Button",
                                                                      "onclick", "javascript:window.close(); return false;")));

        assertThat(foundElements.get(33).getName(), equalTo("a"));
        assertThat(foundElements.get(33).getXPath(), equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > a[2]"));
        assertThat(foundElements.get(33).getAttributes(), equalTo(map("class", "btn btn-success",
                                                                      "href", "#check-and-ok",
                                                                      "title", "Make-Button",
                                                                      "rel", "done",
                                                                      "onclick", "javascript:window.okDone(); return false;")));
    }

    @Test
    public void testSecondFile() throws IOException
    {
        Elements elements = parser.parse("a", getTestResource("examples/sample-2-container-and-clone.html"));
        List<Element> foundElements = elements.getElementsByTag("a");

        assertThat(foundElements, hasSize(44));

        assertThat(foundElements.get(32).getName(), equalTo("a"));
        assertThat(foundElements.get(32).getXPath(), equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > div > a"));
        assertThat(foundElements.get(32).getAttributes(), equalTo(map("class", "btn test-link-ok",
                                                                      "href", "#ok",
                                                                      "title", "Make-Button",
                                                                      "rel", "next",
                                                                      "onclick", "javascript:window.okComplete(); return false;")));
    }

    @Test
    public void testThirdFile() throws IOException
    {
        Elements elements = parser.parse("a", getTestResource("examples/sample-3-the-escape.html"));
        List<Element> foundElements = elements.getElementsByTag("a");

        assertThat(foundElements, hasSize(44));

        assertThat(foundElements.get(32).getName(), equalTo("a"));
        assertThat(foundElements.get(32).getXPath(), equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > a"));
        assertThat(foundElements.get(32).getAttributes(), equalTo(map("class", "btn btn-warning",
                                                                      "href", "#ok",
                                                                      "rel", "next",
                                                                      "onclick", "javascript:window.close(); return false;")));

        assertThat(foundElements.get(33).getName(), equalTo("a"));
        assertThat(foundElements.get(33).getXPath(), equalTo("html > body > div > div > div[3] > div[1] > div > div[3] > a"));
        assertThat(foundElements.get(33).getAttributes(), equalTo(map("class", "btn btn-success",
                                                                      "href", "#ok",
                                                                      "title", "Do-Link",
                                                                      "rel", "next",
                                                                      "onclick", "javascript:window.okDone(); return false;")));
    }

    @Test
    public void testFourthFile() throws IOException
    {
        Elements elements = parser.parse("a", getTestResource("examples/sample-4-the-mash.html"));
        List<Element> foundElements = elements.getElementsByTag("a");

        assertThat(foundElements, hasSize(43));

        assertThat(foundElements.get(32).getName(), equalTo("a"));
        assertThat(foundElements.get(32).getXPath(), equalTo("html > body > div > div > div[3] > div[1] > div > div[3] > a"));
        assertThat(foundElements.get(32).getAttributes(), equalTo(map("class", "btn btn-success",
                                                                      "href", "#ok",
                                                                      "title", "Make-Button",
                                                                      "rel", "next",
                                                                      "onclick", "javascript:window.okFinalize(); return false;")));
    }
}
