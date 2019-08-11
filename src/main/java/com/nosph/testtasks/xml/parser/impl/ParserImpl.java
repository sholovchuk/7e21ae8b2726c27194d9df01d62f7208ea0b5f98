package com.nosph.testtasks.xml.parser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

import com.nosph.testtasks.xml.model.Element;
import com.nosph.testtasks.xml.parser.SearchingParser;
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
    private EnumMap<State, Consumer<Character>> stateActions = new EnumMap<>(State.class);

    @Override
    public List<Element> search(String targetElementTag, File file) throws InvalidDocumentException, FileNotFoundException, IOException
    {
        try(Reader reader = new BufferedReader(new FileReader(file)))
        {
            int next;
            while((next = reader.read()) != -1)
            {
                process((char)next);
            }
        }
        return Collections.emptyList();
    }

    private void process(char nextChar)
    {
    }
}
