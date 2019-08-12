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
            if(isOneLineTag())
            {
                saveCurrentOneLineElement();
            }
            else
            {
                saveCurrentElement();
            }

            context.setState(State.SEARCH_NEXT_ELEMENT);
        }
        else if (Character.isWhitespace(nextChar))
        {
            saveCurrentElement();
            context.setState(State.PARSING_ATTRIBUTES);
        }
        else
        {
            context.consume(nextChar);
        }
    }

    private void saveCurrentElement()
    {
        String tag = context.flushAndReturn();
        context.pushTag(tag);

        if(context.getTargetElementTag().equalsIgnoreCase(tag))
        {
            context.saveCurrentElement();
        }
    }

    private void saveCurrentOneLineElement()
    {
        context.removeLastCharFromReadingBuffer();
        saveCurrentElement();
        context.popTag();
    }
}
