package com.nosph.testtasks.xml.parser.action;

import java.util.HashMap;
import java.util.Map;

import com.nosph.testtasks.xml.parser.SearchingParser.State;
import com.nosph.testtasks.xml.parser.ParserContext;

public class ParsingAttributes extends AbstractParsingAction
{
    public ParsingAttributes(ParserContext context)
    {
        super(context);
    }

    @Override
    public void accept(Character nextChar)
    {
        if(nextChar == '>')
        {
            context.setState(State.SEARCH_NEXT_ELEMENT);
        }
    }
}
