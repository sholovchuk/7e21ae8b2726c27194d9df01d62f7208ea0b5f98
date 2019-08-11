package com.nosph.testtasks.xml.parser.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.parser.SearchingParser.State;
import com.nosph.testtasks.xml.parser.ParserContext;
import com.nosph.testtasks.xml.util.HasLogger;

public class ParseContextImpl implements ParserContext, HasLogger
{
    private State state;
    private String targetElementTag;
    private StringBuilder readingBuffer;
    private Deque<StackElement> stack = new LinkedList<>();
    private List<SelectedElemet> selectedElements = new ArrayList<>();

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
    public void pushTag(String tag)
    {
        stack.offer(new StackElement(tag));
    }

    @Override
    public String popTag()
    {
        return stack.pollLast().getTag();
    }

    @Override
    public String peekTag()
    {
        return stack.peekLast().getTag();
    }

    @Override
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    @Override
    public void saveCurrentElement()
    {
        StackElement stackElement = stack.peekLast();
        SelectedElemet selectedElement = new SelectedElemet(stackElement.getTag());
        selectedElements.add(selectedElement);
    }

    @Override
    public List<Element> getFoundElements()
    {
        List<SelectedElemet> foundElements = selectedElements;
        selectedElements = new ArrayList<>();
        return foundElements.stream()
                            .map(selectedElement -> ElementImpl.create(selectedElement.getTag(), "", new HashMap<>()))
                            .collect(Collectors.toList());
    }

    private static class StackElement
    {
        private String tag;

        public StackElement(String tag)
        {
            this.tag = tag;
        }

        public String getTag()
        {
            return tag;
        }
    }

    private static class SelectedElemet
    {
        private String tag;

        public SelectedElemet(String tag)
        {
            this.tag = tag;
        }

        public String getTag()
        {
            return tag;
        }
    }
}
