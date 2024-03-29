package com.nosph.testtasks.xml.text.impl;

import com.nosph.testtasks.xml.text.TextSimilarityCalculator;

public class StringLengthSimilarityCalculator implements TextSimilarityCalculator
{
    private TextSimilarityCalculator delegate;

    public StringLengthSimilarityCalculator(TextSimilarityCalculator delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public double compare(String first, String second)
    {
        if(first.length() == 0 || second.length() == 0)
        {
            return 0.0;
        }

        double max = Math.max(first.length(), second.length());
        double min = Math.min(first.length(), second.length());

        double coefficient = min / max;

        return coefficient * delegate.compare(first, second);
    }
}
