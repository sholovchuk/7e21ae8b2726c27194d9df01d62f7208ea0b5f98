package com.nosph.testtasks.xml;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class ParserTestSupport
{
    private ClassLoader classLoader;

    protected ParserTestSupport()
    {
        classLoader = getClass().getClassLoader();
    }

    protected File getTestResource(String path)
    {
        URL url = classLoader.getResource(path);

        Objects.requireNonNull(url, String.format("File '%s' not found in test resources", path));

        return new File(url.getFile());
    }
}
