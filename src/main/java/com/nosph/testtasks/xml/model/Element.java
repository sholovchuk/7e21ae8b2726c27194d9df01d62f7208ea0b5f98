package com.nosph.testtasks.xml.model;

import java.util.Map;

public interface Element
{
    String getXPath();

    String getName();

    Map<String, String> getAttributes();
}
