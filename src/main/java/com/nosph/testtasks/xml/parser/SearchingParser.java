package com.nosph.testtasks.xml.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.nosph.testtasks.xml.model.Element;
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

    List<Element> search(String targetElementTag, File file) throws FileNotFoundException, IOException, InvalidDocumentException;
}
