package com.nosph.testtasks.xml.app.impl;

import java.io.File;
import java.util.Optional;

import com.nosph.testtasks.xml.analyzer.Analyzer;
import com.nosph.testtasks.xml.analyzer.impl.ElementAttributesSimilarityAnalyzer;
import com.nosph.testtasks.xml.app.CommandLineProgram;
import com.nosph.testtasks.xml.model.XmlElement;
import com.nosph.testtasks.xml.model.XmlElements;
import com.nosph.testtasks.xml.parser.Parser;
import com.nosph.testtasks.xml.parser.impl.JsoupParser;

public class XmlMostSimilarElementFinder implements CommandLineProgram
{
    public static final String DEFAULT_ID = "make-everything-ok-button";

    private Parser parser = new JsoupParser();
    private Analyzer analyzer = new ElementAttributesSimilarityAnalyzer();

    @Override
    public void run(String[] args)
    {
        String id = DEFAULT_ID;
        switch (args.length)
        {
            case 0:
            case 1:
                System.out.println("Not enough arguments");
                System.exit(1);
            case 3:
                id = args[2];
        }

        File originFile = getFile(args[0]);
        File diffFile = getFile(args[1]);

        XmlElements first = parser.parse(originFile); //TODO wrap and exit if failed to parse
        XmlElements second = parser.parse(diffFile);

        Optional<XmlElement> targetElementOpt = first.getElementById(id);

        if(!targetElementOpt.isPresent())
        {
            System.out.println("Target element not found by id '" + id + "'");
            System.exit(1);
        }

        XmlElement targetElement = targetElementOpt.get();

        Optional<XmlElement> mostSimilarElementOpt = analyzer.findMostSimilarElement(targetElement, second.getElements());

        if(!mostSimilarElementOpt.isPresent())
        {
            System.out.println("Most similar element not found");
            System.exit(1);
        }

        XmlElement mostSimilarElement = mostSimilarElementOpt.get();

        System.out.println(mostSimilarElement.getXPath());
    }

    private File getFile(String path)
    {
        File file = new File(path);
        if(!file.exists())
        {
            System.out.printf("There is not file: '%s'", file.getAbsolutePath());
            System.exit(1);
        }
        return file;
    }
}
