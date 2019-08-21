package com.nosph.testtasks.xml.model;

import java.util.Map;

public interface XmlElement
{
    String getXPath();

    String getName();

    Map<String, String> getAttributes();
}
