package com.nosph.testtasks.xml;

import org.junit.Test;

public class TestParserOnExamples extends ParserTestSupport
{
    @Test
    public void testOriginFile()
    {
        getTestResource("examples/sample-0-origin.html");
    }

    @Test
    public void testFirstFile()
    {
        getTestResource("examples/sample-1-evil-gemini.html");
    }

    @Test
    public void testSecondFile()
    {
        getTestResource("examples/sample-2-container-and-clone.html");
    }

    @Test
    public void testThirdFile()
    {
        getTestResource("examples/sample-3-the-escape.html");
    }

    @Test
    public void testFourthFile()
    {
        getTestResource("examples/sample-4-the-mash.html");
    }
}
