package com.nosph.testtasks.xml.parser.impl;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;

import com.nosph.testtasks.xml.model.XmlElements;
import com.nosph.testtasks.xml.model.impl.JsoupElements;
import com.nosph.testtasks.xml.parser.Parser;

public class JsoupParser implements Parser
{
    @Override
    public XmlElements parse(File xml)
    {
        try
        {
            return new JsoupElements(Jsoup.parse(xml, "UTF-8"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e); // TODO ???
        }
    }
}
