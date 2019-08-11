package com.nosph.testtasks.xml.parser.impl;

import java.util.Map;

import com.nosph.testtasks.xml.model.Element;

public class ElementImpl implements Element
{
    public static Element create(String name, String pathToElement, Map<String, String> attrs)
    {
        return new ElementImpl(name, pathToElement, attrs);
    }

    private String name;
    private String xPath;
    private Map<String, String> attributes;

    private ElementImpl(String name, String pathToElement, Map<String, String> attrs)
    {
        this.name = name;
        this.xPath = pathToElement;
        this.attributes = attrs;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Map<String, String> getAttributes()
    {
        return attributes;
    }

    @Override
    public String getXPath()
    {
        return xPath;
    }

    @Override
    public String toString()
    {
        return String.format("<%s attrs=%s></%s>, xPath: %s", name, attributes, name, xPath);
    }
}
