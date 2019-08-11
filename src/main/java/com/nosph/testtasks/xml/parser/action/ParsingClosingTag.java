package com.nosph.testtasks.xml.parser.action;

import com.nosph.testtasks.xml.parser.ParserContext;
import com.nosph.testtasks.xml.parser.SearchingParser.State;

public class ParsingClosingTag extends AbstractParsingAction
{
    public ParsingClosingTag(ParserContext context)
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
