package com.nosph.testtasks.xml.parser;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.parser.SearchingParser.State;

public interface ParserContext
{
    State getState();
    void setState(State state);

    String getTargetElementTag();
    void setTargetElementTag(String tag);

    String flushAndReturn();
    void flushAndConsume(char nextChar);
    void consume(char nextChar);

    void pushTag(String tag);
    String popTag();
    String peekTag();
    boolean isEmpty();

    void saveCurrentElement();
    void saveCurrentElementAttributes(Map<String, String> attrs);

    List<Element> getFoundElements();

    Optional<Character> peekLastFromReadingBuffer();
    void removeLastCharFromReadingBuffer();
}
