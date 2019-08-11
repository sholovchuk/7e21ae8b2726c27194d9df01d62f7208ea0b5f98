package com.nosph.testtasks.xml.parser.action;

import com.nosph.testtasks.xml.parser.ParserContext;
import com.nosph.testtasks.xml.parser.SearchingParser.State;

public class StartedElementParsing extends AbstractParsingAction
{
    public StartedElementParsing(ParserContext context)
    {
        super(context);
    }

    @Override
    public void accept(Character nextChar)
    {
        if(nextChar == '/')
        {
            context.setState(State.PARSING_CLOSING_TAG);
        }
        else if(nextChar == '!')
        {
            context.setState(State.PARSING_COMMENT_OR_DOCTYPE);
        }
        else
        {
            context.setState(State.PARSING_OPENING_TAG);
        }
    }
}
