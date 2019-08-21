package com.nosph.testtasks.xml.model.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.nosph.testtasks.xml.model.XmlElement;
import com.nosph.testtasks.xml.model.XmlElements;

public class JsoupElements implements XmlElements
{
    private Document root;

    public JsoupElements(Document root)
    {
        this.root = root;
    }

    @Override
    public Optional<XmlElement> getElementById(String id)
    {
        Element elem = root.getElementById(id);

        if(elem == null)
        {
            return Optional.empty();
        }

        return Optional.of(toXmlElement(elem));
    }

    @Override
    public List<XmlElement> getElements()
    {
        return root.getAllElements().stream().map(this::toXmlElement).collect(Collectors.toList());
    }
// TODO refactor
    private XmlElement toXmlElement(Element jsoupElement)
    {
        String tag = jsoupElement.tagName();
        Map<String, String> attrs = new HashMap<>();
        jsoupElement.attributes().forEach(attr -> attrs.put(attr.getKey(), attr.getValue()));

        String xPath = "";
        if(jsoupElement.hasParent())
        {
            LinkedList<String> xPathElements = new LinkedList<>();
            Element parent = jsoupElement;
            while((parent = parent.parent()) != null)
            {
                int sameTagNameCount = 0;
                int xPathIndex = 1;
                for(Element element : parent.children())
                {
                    if(jsoupElement.tagName().equals(element.tagName()))
                    {
                        sameTagNameCount++;
                        if(jsoupElement == element)
                        {
                            xPathIndex = sameTagNameCount;
                        }
                    }
                }
                if(sameTagNameCount > 1)
                {
                    xPathElements.addFirst(String.format("%s[%d]", jsoupElement.tagName(), xPathIndex));
                }
                else
                {
                    xPathElements.addFirst(String.format("%s", jsoupElement.tagName()));
                }
                jsoupElement = parent;
            }
            xPath = String.join(" > ", xPathElements);
        }
        else
        {
            xPath = tag;
        }
        
        return XmlElementImpl.create(tag, xPath, attrs);
    }
}
