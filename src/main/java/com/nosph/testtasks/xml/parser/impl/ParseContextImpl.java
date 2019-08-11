package com.nosph.testtasks.xml.parser.impl;

import java.util.Collections;
import java.util.List;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.parser.SearchingParser.State;
import com.nosph.testtasks.xml.parser.ParserContext;
import com.nosph.testtasks.xml.util.HasLogger;

public class ParseContextImpl implements ParserContext, HasLogger
{
    private State state;
    private String targetElementTag;
    private StringBuilder readingBuffer;

    @Override
    public State getState()
    {
        return state;
    }

    @Override
    public void setState(State state)
    {
        this.state = state;
    }

    @Override
    public String getTargetElementTag()
    {
        return targetElementTag;
    }

    @Override
    public void setTargetElementTag(String tag)
    {
        targetElementTag = tag;
    }

    @Override
    public String flushAndReturn()
    {
        String buffContent = readingBuffer.toString();
        readingBuffer = new StringBuilder();
        return buffContent;
    }

    @Override
    public void flushAndConsume(char nextChar)
    {
        readingBuffer = new StringBuilder().append(nextChar);
    }

    @Override
    public void consume(char nextChar)
    {
        readingBuffer.append(nextChar);
    }

    @Override
    public List<Element> getFoundElements()
    {
        return Collections.emptyList();
    }
}
