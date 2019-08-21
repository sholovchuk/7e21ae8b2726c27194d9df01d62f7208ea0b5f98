package com.nosph.testtasks.xml.model;

import java.util.List;
import java.util.Optional;

public interface XmlElements
{
    Optional<XmlElement> getElementById(String id);

    List<XmlElement> getElements();
}
