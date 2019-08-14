package com.nosph.testtasks.xml.model.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.model.Elements;
import com.nosph.testtasks.xml.parser.ParserContext;

public class ElementsOneTagImpl implements Elements
{
    private Map<String, Element> idIndex;
    private List<Element> elements;

    public ElementsOneTagImpl(ParserContext context)
    {
        elements = context.getFoundElements();
    }

    @Override
    public Optional<Element> getElementById(String id)
    {
        return Optional.empty();
    }

    @Override
    public List<Element> getElementsByTag(String tag)
    {
        return elements;
    }
}
