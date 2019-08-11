package com.nosph.testtasks.xml.parser.action;

import com.nosph.testtasks.xml.parser.SearchingParser;
import com.nosph.testtasks.xml.parser.ParserContext;

public class ParsingCommentOrDoctype extends AbstractParsingAction
{
    public ParsingCommentOrDoctype(ParserContext context)
    {
        super(context);
    }

    @Override
    public void accept(Character nextChar)
    {
        if(nextChar == '>')
        {
            context.setState(SearchingParser.State.SEARCH_NEXT_ELEMENT);
        }
    }
}
