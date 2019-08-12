package com.nosph.testtasks.xml.parser.action;

import java.util.function.Consumer;

import com.nosph.testtasks.xml.parser.ParserContext;

public abstract class AbstractParsingAction implements Consumer<Character>
{
    protected final ParserContext context;

    public AbstractParsingAction(ParserContext context)
    {
        this.context = context;
    }

    protected boolean isOneLineTag()
    {
        return context.peekLastFromReadingBuffer().filter(prevChar -> prevChar == '/').isPresent();
    }
}
