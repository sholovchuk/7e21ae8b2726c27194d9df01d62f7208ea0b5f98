package com.nosph.testtasks.xml;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.parser.SearchingParser;
import com.nosph.testtasks.xml.parser.impl.ParserImpl;

public class TestParserOnSimpleCases extends ParserTestSupport
{
    private SearchingParser parser = new ParserImpl();

    @Test
    public void testSimpleHtmlPage() throws IOException
    {
        parser.search("a", getTestResource("simple-cases/simpleHtmlPage.html"));
    }

    @Test
    public void testOneButtonOneAttribute() throws IOException
    {
        List<Element> foundElements = parser.search("a", getTestResource("simple-cases/oneButtonOneAttribute.html"));

        assertThat(foundElements, hasSize(1));

        assertThat(foundElements.get(0).getName(), equalTo("a"));
        assertThat(foundElements.get(0).getXPath(), equalTo("html > body > a"));
        assertThat(foundElements.get(0).getAttributes(), equalTo(map("href", "#")));
    }

    @Test
    public void testOneButtonTwoAttributes() throws IOException
    {
        List<Element> foundElements = parser.search("a", getTestResource("simple-cases/oneButtonTwoAttributes.html"));

        assertThat(foundElements, hasSize(1));

        assertThat(foundElements.get(0).getName(), equalTo("a"));
        assertThat(foundElements.get(0).getXPath(), equalTo("html > body > a"));
        assertThat(foundElements.get(0).getAttributes(), equalTo(map("href", "#",
                                                                     "class", "super")));
    }

    @Test
    public void testTwoButtons() throws IOException
    {
        List<Element> foundElements = parser.search("a", getTestResource("simple-cases/twoButtons.html"));

        assertThat(foundElements, hasSize(2));

        assertThat(foundElements.get(0).getName(), equalTo("a"));
        assertThat(foundElements.get(0).getXPath(), equalTo("html > body > a[1]"));
        assertThat(foundElements.get(0).getAttributes(), equalTo(map("href", "#")));

        assertThat(foundElements.get(1).getName(), equalTo("a"));
        assertThat(foundElements.get(1).getXPath(), equalTo("html > body > a[2]"));
        assertThat(foundElements.get(1).getAttributes(), equalTo(map("href", "#")));
    }

    @Test
    public void testThreeButtons() throws IOException
    {
        List<Element> foundElements = parser.search("a", getTestResource("simple-cases/threeButtons.html"));

        assertThat(foundElements, hasSize(3));

        assertThat(foundElements.get(0).getName(), equalTo("a"));
        assertThat(foundElements.get(0).getXPath(), equalTo("html > body > div[1] > a"));
        assertThat(foundElements.get(0).getAttributes(), equalTo(map("href", "#")));

        assertThat(foundElements.get(1).getName(), equalTo("a"));
        assertThat(foundElements.get(1).getXPath(), equalTo("html > body > div[2] > a[1]"));
        assertThat(foundElements.get(1).getAttributes(), equalTo(map("href", "#")));

        assertThat(foundElements.get(2).getName(), equalTo("a"));
        assertThat(foundElements.get(2).getXPath(), equalTo("html > body > div[2] > a[2]"));
        assertThat(foundElements.get(2).getAttributes(), equalTo(map("href", "#")));
    }

    @Test
    public void testTwoButtonsTwoComplexAttributes() throws IOException
    {
        List<Element> foundElements = parser.search("a", getTestResource("simple-cases/twoButtonsTwoComplexAttributes.html"));

        assertThat(foundElements, hasSize(2));

        assertThat(foundElements.get(0).getName(), equalTo("a"));
        assertThat(foundElements.get(0).getXPath(), equalTo("html > body > a[1]"));
        assertThat(foundElements.get(0).getAttributes(), equalTo(map("href", "#",
                                                                     "class", "super",
                                                                     "data", "some data")));

        assertThat(foundElements.get(1).getName(), equalTo("a"));
        assertThat(foundElements.get(1).getXPath(), equalTo("html > body > a[2]"));
        assertThat(foundElements.get(1).getAttributes(), equalTo(map("href", "#",
                                                                     "class", "super super-puper")));
    }

    @Test
    public void testOneLineTag() throws IOException
    {
        List<Element> foundElements = parser.search("a", getTestResource("simple-cases/oneLineTag.html"));

        assertThat(foundElements, hasSize(2));

        assertThat(foundElements.get(0).getName(), equalTo("a"));
        assertThat(foundElements.get(0).getXPath(), equalTo("html > body > a[1]"));
        assertThat(foundElements.get(0).getAttributes(), equalTo(map("href", "#")));

        assertThat(foundElements.get(1).getName(), equalTo("a"));
        assertThat(foundElements.get(1).getXPath(), equalTo("html > body > a[2]"));
        assertThat(foundElements.get(1).getAttributes(), equalTo(map()));
    }

    @Test
    public void testOneLineTagWithWhitespace() throws IOException
    {
        List<Element> foundElements = parser.search("a", getTestResource("simple-cases/oneLineTagWithWhitespace.html"));

        assertThat(foundElements, hasSize(2));

        assertThat(foundElements.get(0).getName(), equalTo("a"));
        assertThat(foundElements.get(0).getXPath(), equalTo("html > body > a[1]"));
        assertThat(foundElements.get(0).getAttributes(), equalTo(map("href", "#")));

        assertThat(foundElements.get(1).getName(), equalTo("a"));
        assertThat(foundElements.get(1).getXPath(), equalTo("html > body > a[2]"));
        assertThat(foundElements.get(1).getAttributes(), equalTo(map()));
    }
}
