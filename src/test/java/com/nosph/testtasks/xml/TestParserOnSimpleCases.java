package com.nosph.testtasks.xml;

import java.io.IOException;

import org.junit.Test;

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
}
