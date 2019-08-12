package com.nosph.testtasks.xml.parser.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
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
        stack.offer(new StackElement(tag, stack));
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
        SelectedElemet selectedElement = toSelectedElement(stackElement);
        selectedElement.setMyPath(buildDataForXPathForCurrentElement());
        selectedElements.add(selectedElement);
    }

    @Override
    public void saveCurrentElementAttributes(Map<String, String> attrs)
    {
        selectedElements.get(selectedElements.size() - 1).setAttrs(attrs);
    }

    @Override
    public List<Element> getFoundElements()
    {
        List<SelectedElemet> foundElements = selectedElements;
        selectedElements = new ArrayList<>();
        return foundElements.stream().map(selectedElement ->
        {
            String path = selectedElement.getMyPath().stream().map(e ->
            {
                AtomicInteger neighborCount = e.getSameTagNeighborCount();
                if(neighborCount == null || neighborCount.get() == 1)
                {
                    return e.getTag();
                }

                return e.getTag() + "[" + e.getOrder() + "]";
            }).collect(Collectors.joining(" > "));

            return ElementImpl.create(selectedElement.getTag(), path, selectedElement.getAttrs());
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Character> peekLastFromReadingBuffer()
    {
        return readingBuffer.length() >  0? Optional.ofNullable(readingBuffer.charAt(readingBuffer.length() - 1)) : Optional.empty();
    }

    @Override
    public void removeLastCharFromReadingBuffer()
    {
        readingBuffer.deleteCharAt(readingBuffer.length() - 1);
    }

    private List<SelectedElemet> buildDataForXPathForCurrentElement()
    {
        return stack.stream()
                    .map(this::toSelectedElement)
                    .collect(Collectors.toList());
    }

    private SelectedElemet toSelectedElement(StackElement stackElement)
    {
        SelectedElemet selectedElement = new SelectedElemet(stackElement.getTag());

        if(stackElement.getParent().isPresent())
        {
            StackElement parent = stackElement.getParent().get();
            AtomicInteger tagCounter = parent.getChildrenCounts().get(stackElement.getTag());
            selectedElement.setOrder(tagCounter.get());
            selectedElement.setSameTagNeighborCount(tagCounter);
        }
        return selectedElement;
    }

    private static class StackElement
    {
        private StackElement parent;
        private String tag;
        private Map<String, AtomicInteger> childrenCounts;

        public StackElement(String tag, Deque<StackElement> stack)
        {
            this.tag = tag;
            this.childrenCounts = new HashMap<>();

            parent = stack.peekLast();
            if(parent != null)
            {
                AtomicInteger cnt = parent.childrenCounts.get(tag);
                if(cnt != null)
                {
                    cnt.getAndIncrement();
                }
                else
                {
                    parent.childrenCounts.put(tag, new AtomicInteger(1));
                }
            }
        }

        public String getTag()
        {
            return tag;
        }

        public Map<String, AtomicInteger> getChildrenCounts()
        {
            return childrenCounts;
        }

        public Optional<StackElement> getParent()
        {
            return Optional.ofNullable(this.parent);
        }

        @Override
        public String toString()
        {
            return "<" + tag + ", " + childrenCounts + ">";
        }
    }

    private static class SelectedElemet
    {
        private String tag;
        private List<SelectedElemet> myPath;
        private AtomicInteger sameTagNeighborCount;
        private int order;
        private Map<String, String> attrs = Collections.emptyMap();

        public SelectedElemet(String tag)
        {
            this.tag = tag;
        }

        public String getTag()
        {
            return tag;
        }

        public AtomicInteger getSameTagNeighborCount()
        {
            return sameTagNeighborCount;
        }

        public void setSameTagNeighborCount(AtomicInteger sameTagNeighborCount)
        {
            this.sameTagNeighborCount = sameTagNeighborCount;
        }

        public int getOrder()
        {
            return order;
        }

        public void setOrder(int order)
        {
            this.order = order;
        }

        public List<SelectedElemet> getMyPath()
        {
            return myPath;
        }

        public void setMyPath(List<SelectedElemet> myPath)
        {
            this.myPath = myPath;
        }

        public Map<String, String> getAttrs()
        {
            return attrs;
        }

        public void setAttrs(Map<String, String> attrs)
        {
            this.attrs = attrs;
        }
    }
}
