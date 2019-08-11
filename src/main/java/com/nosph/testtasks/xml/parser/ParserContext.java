package com.nosph.testtasks.xml.parser;

import java.util.List;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.parser.SearchingParser.State;

public interface ParserContext
{
    State getState();
    void setState(State state);

    String getTargetElementTag();
    void setTargetElementTag(String tag);

    void flushAndConsume(char nextChar);
    void consume(char nextChar);

    List<Element> getFoundElements();
}
