package com.nosph.testtasks.xml;

import java.io.IOException;

import com.nosph.testtasks.xml.app.impl.XmlMostSimilarElementFinder;

public class App
{
    public static void main(String[] args) throws IOException
    {
        new XmlMostSimilarElementFinder().run(args);
    }
}
