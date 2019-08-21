package com.nosph.testtasks.xml.parser;

import java.io.File;

import com.nosph.testtasks.xml.model.XmlElements;

public interface Parser
{
    XmlElements parse(File xml);
}
