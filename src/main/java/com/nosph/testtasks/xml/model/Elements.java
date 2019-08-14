package com.nosph.testtasks.xml.model;

import java.util.List;
import java.util.Optional;

public interface Elements
{
    Optional<Element> getElementById(String id);

    List<Element> getElementsByTag(String tag);
}
