package com.nosph.testtasks.xml.parser.action;

import com.nosph.testtasks.xml.parser.SearchingParser.State;
import com.nosph.testtasks.xml.parser.ParserContext;

public class ParsingOpeningTag extends AbstractParsingAction
{
    public ParsingOpeningTag(ParserContext context)
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
        else if (Character.isWhitespace(nextChar))
        {
            context.setState(State.PARSING_ATTRIBUTES);
        }
    }
}
