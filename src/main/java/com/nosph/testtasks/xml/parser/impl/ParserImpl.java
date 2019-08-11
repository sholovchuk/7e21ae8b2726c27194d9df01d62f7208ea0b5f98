package com.nosph.testtasks.xml.parser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.parser.SearchingParser;
import com.nosph.testtasks.xml.parser.ParserContext;
import com.nosph.testtasks.xml.parser.action.ParsingCommentOrDoctype;
import com.nosph.testtasks.xml.parser.action.ParsingAttributes;
import com.nosph.testtasks.xml.parser.action.ParsingClosingTag;
import com.nosph.testtasks.xml.parser.action.ParsingOpeningTag;
import com.nosph.testtasks.xml.parser.action.SearchNextTag;
import com.nosph.testtasks.xml.parser.action.StartedElementParsing;
import com.nosph.testtasks.xml.parser.exception.InvalidDocumentException;

/**
 * STATES:
 *   - SEARCH_NEXT_ELEMENT
 *   - STARTED_ELEMENT_PARSING
 *   - PARSING_OPENING_TAG
 *   - PARSING_ATTRIBUTES
 *   - PARSING_CLOSING_TAG
 *   - PARSING_COMMENT_OR_DOCTYPE
 *
 *  +---------------------------------SEARCH_NEXT_ELEMENT<-------------------------------+
 *  |                                                                                    |
 *  |                                                                                    |
 *  |                                +-->PARSING_OPENING_TAG--+---->PARSING_ATTRIBUTES---+
 *  |                                |                        |                          |
 *  +---->STARTED_ELEMENT_PARSING----+                        +--------------------------+
 *                                   |                                                   |
 *                                   +-->PARSING_CLOSING_TAG-----------------------------+
 *                                   |                                                   |
 *                                   |                                                   |
 *                                   +-->PARSING_COMMENT_OR_DOCTYPE----------------------+
 */

public class ParserImpl implements SearchingParser
{
    private ParserContext context = new ParseContextImpl();

    private EnumMap<State, Consumer<Character>> stateActions = new EnumMap<>(State.class);

    {
        stateActions.put(State.SEARCH_NEXT_ELEMENT,        new SearchNextTag(context));
        stateActions.put(State.STARTED_ELEMENT_PARSING,    new StartedElementParsing(context));
        stateActions.put(State.PARSING_OPENING_TAG,        new ParsingOpeningTag(context));
        stateActions.put(State.PARSING_ATTRIBUTES,         new ParsingAttributes(context));
        stateActions.put(State.PARSING_CLOSING_TAG,        new ParsingClosingTag(context));
        stateActions.put(State.PARSING_COMMENT_OR_DOCTYPE, new ParsingCommentOrDoctype(context));
    }

    @Override
    public List<Element> search(String targetElementTag, File file) throws InvalidDocumentException, FileNotFoundException, IOException
    {
        context.setState(State.SEARCH_NEXT_ELEMENT);
        context.setTargetElementTag(targetElementTag);

        try(Reader reader = new BufferedReader(new FileReader(file)))
        {
            int next;
            while((next = reader.read()) != -1)
            {
                process((char)next);
            }
        }
        return context.getFoundElements();
    }

    private void process(char nextChar)
    {
        stateActions.get(context.getState()).accept(nextChar);
    }
}
