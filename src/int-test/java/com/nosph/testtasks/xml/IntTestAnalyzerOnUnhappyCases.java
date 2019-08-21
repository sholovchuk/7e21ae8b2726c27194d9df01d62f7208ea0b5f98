package com.nosph.testtasks.xml;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class IntTestAnalyzerOnUnhappyCases extends IntTestSupport
{
    @Test
    public void testNonEnoughArguments() throws Exception
    {
        ProcessResult executionResult = runApplication(absPath("sample-0-origin.html"));

        assertThat(executionResult.getExitCode(), is(ProcessResult.EXIT_FAILURE));
        assertThat(executionResult.getOutput(), equalTo("Not enough arguments"));
    }

    @Test
    public void testFileNotFound() throws Exception
    {
        String fileNotExists = "not-found.html";
        ProcessResult executionResult = runApplication(absPath("sample-0-origin.html"), absPath(fileNotExists));

        assertThat(executionResult.getExitCode(), is(ProcessResult.EXIT_FAILURE));
        assertThat(executionResult.getOutput(), equalTo(String.format("There is not file: '%s'", absPath(fileNotExists))));
    }

    @Test
    public void testEmptyFile() throws Exception
    {
        ProcessResult executionResult = runApplication(absPath("sample-0-origin.html"), absPath("empty.html"));

        assertThat(executionResult.getExitCode(), is(ProcessResult.EXIT_FAILURE));
        assertThat(executionResult.getOutput(), equalTo("Most similar element not found"));
    }

    @Test
    public void testNonExistentTargetElementId() throws Exception
    {
        String noId = "no-id";
        ProcessResult executionResult = runApplication(absPath("sample-0-origin.html"), absPath("sample-1-evil-gemini.html"), noId);

        assertThat(executionResult.getExitCode(), is(ProcessResult.EXIT_FAILURE));
        assertThat(executionResult.getOutput(), equalTo(String.format("Target element not found by id '%s'", noId)));
    }
}
