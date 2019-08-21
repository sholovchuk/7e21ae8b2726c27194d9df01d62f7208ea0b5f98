package com.nosph.testtasks.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;

public class IntTestSupport
{
    private static final String DIR = System.getProperty("user.dir");
    private static final String RESOURCES = "/src/test/resources/examples/";
    private static final String TARGET = "/target/";

    private String executable;

    @Before
    public void resolveExecutable() throws IOException
    {
        executable = Files.walk(Paths.get(DIR, TARGET), 1)
                    .filter(p -> p.toString().endsWith("-jar-with-dependencies.jar"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Executable is not found"))
                    .toAbsolutePath().toString();
    }

    public String absPath(String resourceFilePath)
    {
        return Paths.get(DIR, RESOURCES, resourceFilePath).toString();
    }

    public ProcessResult runApplication(String ... args) throws IOException, InterruptedException
    {
        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-jar");
        command.add(executable);
        command.addAll(Arrays.asList(args));

        ProcessBuilder pb = new ProcessBuilder().command(command);

        Process p = pb.start();
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())))
        {
            String output = null;
            while ((output = reader.readLine()) != null)
            {
                sb.append(output);
            }
            p.waitFor(1, TimeUnit.SECONDS);
        }

        return ProcessResult.create(p.exitValue(), sb.toString());
    }

    public static class ProcessResult
    {
        public static final int EXIT_SUCCESS = 0;
        public static final int EXIT_FAILURE = 1;

        public static ProcessResult create(int exitCode, String output)
        {
            return new ProcessResult(exitCode, output);
        }

        private int exitCode;
        private String output;

        private ProcessResult(int exitCode, String output)
        {
            this.exitCode = exitCode;
            this.output = output;
        }

        public int getExitCode()
        {
            return exitCode;
        }

        public void setExitCode(int exitCode)
        {
            this.exitCode = exitCode;
        }

        public String getOutput()
        {
            return output;
        }

        public void setOutput(String output)
        {
            this.output = output;
        }
    }
}
