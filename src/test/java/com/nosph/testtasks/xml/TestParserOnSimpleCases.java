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
    }

    @Test
    public void testOneButtonTwoAttributes() throws IOException
    {
        List<Element> foundElements = parser.search("a", getTestResource("simple-cases/oneButtonTwoAttributes.html"));

        assertThat(foundElements, hasSize(1));

        assertThat(foundElements.get(0).getName(), equalTo("a"));
    }
}
