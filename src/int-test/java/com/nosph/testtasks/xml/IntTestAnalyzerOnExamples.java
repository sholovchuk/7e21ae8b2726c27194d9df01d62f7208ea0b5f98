package com.nosph.testtasks.xml;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class IntTestAnalyzerOnExamples extends IntTestSupport
{
    @Test
    public void testFirstFile() throws Exception
    {
       ProcessResult executionResult = runApplication(absPath("sample-0-origin.html"), absPath("sample-1-evil-gemini.html"));

       assertThat(executionResult.getExitCode(), is(ProcessResult.EXIT_SUCCESS));
       assertThat(executionResult.getOutput(), equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > a[2]"));
    }

    @Test
    public void testSecondFile() throws Exception
    {
       ProcessResult executionResult = runApplication(absPath("sample-0-origin.html"), absPath("sample-2-container-and-clone.html"));

       assertThat(executionResult.getExitCode(), is(ProcessResult.EXIT_SUCCESS));
       assertThat(executionResult.getOutput(), equalTo("html > body > div > div > div[3] > div[1] > div > div[2] > div > a"));
    }

    @Test
    public void testThirdFile() throws Exception
    {
       ProcessResult executionResult = runApplication(absPath("sample-0-origin.html"), absPath("sample-3-the-escape.html"));

       assertThat(executionResult.getExitCode(), is(ProcessResult.EXIT_SUCCESS));
       assertThat(executionResult.getOutput(), equalTo("html > body > div > div > div[3] > div[1] > div > div[3] > a"));
    }

    @Test
    public void testFourthFile() throws Exception
    {
       ProcessResult executionResult = runApplication(absPath("sample-0-origin.html"), absPath("sample-4-the-mash.html"));

       assertThat(executionResult.getExitCode(), is(ProcessResult.EXIT_SUCCESS));
       assertThat(executionResult.getOutput(), equalTo("html > body > div > div > div[3] > div[1] > div > div[3] > a"));
    }
}
