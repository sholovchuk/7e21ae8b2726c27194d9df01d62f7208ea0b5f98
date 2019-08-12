package com.nosph.testtasks.xml;

import java.io.IOException;

import org.junit.Test;

import com.nosph.testtasks.xml.parser.SearchingParser;
import com.nosph.testtasks.xml.parser.impl.ParserImpl;

public class TestParserOnExamples extends ParserTestSupport
{
    private SearchingParser parser = new ParserImpl();

    @Test
    public void testOriginFile() throws IOException
    {
        parser.search("a", getTestResource("examples/sample-0-origin.html"));
    }

    @Test
    public void testFirstFile() throws IOException
    {
        parser.search("a", getTestResource("examples/sample-1-evil-gemini.html"));
    }

    @Test
    public void testSecondFile() throws IOException
    {
        parser.search("a", getTestResource("examples/sample-2-container-and-clone.html"));
    }

    @Test
    public void testThirdFile() throws IOException
    {
        parser.search("a", getTestResource("examples/sample-3-the-escape.html"));
    }

    @Test
    public void testFourthFile() throws IOException
    {
        parser.search("a", getTestResource("examples/sample-4-the-mash.html"));
    }
}
