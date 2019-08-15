package com.nosph.testtasks.xml.model.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        idIndex = elements.stream()
                          .filter(e -> e.getAttributes().containsKey("id"))
                          .collect(Collectors.toMap(e -> e.getAttributes().get("id"), Function.identity()));
    }

    @Override
    public Optional<Element> getElementById(String id)
    {
        return Optional.ofNullable(idIndex.get(id));
    }

    @Override
    public List<Element> getElementsByTag(String tag)
    {
        return elements;
    }
}
