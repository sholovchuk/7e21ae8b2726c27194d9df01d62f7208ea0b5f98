package com.nosph.testtasks.xml.parser.action;

import com.nosph.testtasks.xml.parser.SearchingParser;
import com.nosph.testtasks.xml.parser.ParserContext;

public class SearchNextTag extends AbstractParsingAction
{
    public SearchNextTag(ParserContext context)
    {
        super(context);
    }

    @Override
    public void accept(Character nextChar)
    {
        if(nextChar == '<')
        {
            context.setState(SearchingParser.State.STARTED_ELEMENT_PARSING);
        }
    }
}
