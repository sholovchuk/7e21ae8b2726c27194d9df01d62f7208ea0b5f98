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
            String topTag = context.peekTag();
            if(isOneLineTag())
            {
                context.removeLastCharFromReadingBuffer();
                context.popTag();
            }

            String attrs = context.flushAndReturn();
            if(context.getTargetElementTag().equalsIgnoreCase(topTag))
            {
                context.saveCurrentElementAttributes(parseAttrs(attrs));
            }

            context.setState(State.SEARCH_NEXT_ELEMENT);
        }
        else
        {
            context.consume(nextChar);
        }
    }

    private Map<String, String> parseAttrs(String attrs)
    {
        Map<String, String> attrsParsed = new HashMap<>();

        int initIndex = 0;
        int equalIndex = initIndex;

        while((equalIndex = attrs.indexOf('=', initIndex + 1)) != -1)
        {
            int secondQuote = attrs.indexOf('"', initIndex + 1);
            secondQuote = attrs.indexOf('"', secondQuote + 1);

            String key = attrs.substring(initIndex, equalIndex).trim();
            String val = attrs.substring(equalIndex + 2, secondQuote).trim();

            attrsParsed.put(key, val);
            initIndex = secondQuote + 2;
        }

        return attrsParsed;
    }
}
