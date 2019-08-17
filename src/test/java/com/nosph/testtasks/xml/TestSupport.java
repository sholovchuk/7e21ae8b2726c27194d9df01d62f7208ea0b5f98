package com.nosph.testtasks.xml;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestSupport
{
    private ClassLoader classLoader;

    protected TestSupport()
    {
        classLoader = getClass().getClassLoader();
    }

    protected File getTestResource(String path)
    {
        URL url = classLoader.getResource(path);

        Objects.requireNonNull(url, String.format("File '%s' not found in test resources", path));

        return new File(url.getFile());
    }

    protected Map<String, String> map(String ... args)
    {
        assert args.length % 2 == 0;

        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < args.length; i += 2)
        {
            map.put(args[i], args[i+1]);
        }
        return map;
    }
}
