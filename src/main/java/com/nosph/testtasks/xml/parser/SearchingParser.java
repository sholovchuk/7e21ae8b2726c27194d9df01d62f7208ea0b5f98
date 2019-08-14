package com.nosph.testtasks.xml.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.nosph.testtasks.xml.model.Elements;
import com.nosph.testtasks.xml.parser.exception.InvalidDocumentException;

public interface SearchingParser
{
    public enum State
    {
        SEARCH_NEXT_ELEMENT,
        STARTED_ELEMENT_PARSING,
        PARSING_OPENING_TAG,
        PARSING_ATTRIBUTES,
        PARSING_CLOSING_TAG,
        PARSING_COMMENT_OR_DOCTYPE;
    }

    Elements parse(String targetElementTag, File file) throws FileNotFoundException, IOException, InvalidDocumentException;
}
