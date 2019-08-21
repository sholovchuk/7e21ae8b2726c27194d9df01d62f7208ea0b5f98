package com.nosph.testtasks.xml.text.impl;

import java.util.Arrays;

import com.nosph.testtasks.xml.text.TextSimilarityCalculator;
// TODO change algo?
public class JaroWinkler implements TextSimilarityCalculator
{
    private static final double WINKLERS_THRESHOLD = 0.7;

    public double compare(String first, String second)
    {
        final double defaultScalingFactor = 0.1;
        final double percentageRoundValue = 100.0;

        int[] mtp = matches(first, second);
        double m = mtp[0];
        if (m == 0)
        {
            return 0.0;
        }

        double j = ((m / first.length() + m / second.length() + (m - mtp[1]) / m)) / 3;
        double jw = j < WINKLERS_THRESHOLD ? j : j + Math.min(defaultScalingFactor, 1D / mtp[3]) * mtp[2] * (1D - j);
        return Math.round(jw * percentageRoundValue) / percentageRoundValue;
    }

    private int[] matches(final String first, final String second)
    {
        String max, min;
        if (first.length() > second.length())
        {
            max = first;
            min = second;
        }
        else
        {
            max = second;
            min = first;
        }

        int range = Math.max(max.length() / 2 - 1, 0);

        int[] matchIndexes = new int[min.length()];

        Arrays.fill(matchIndexes, -1);

        boolean[] matchFlags = new boolean[max.length()];

        int matches = 0;
        for (int mi = 0; mi < min.length(); mi++)
        {
            char c1 = min.charAt(mi);
            for (int xi = Math.max(mi - range, 0), xn = Math.min(mi + range + 1, max.length()); xi < xn; xi++)
            {
                if (!matchFlags[xi] && c1 == max.charAt(xi))
                {
                    matchIndexes[mi] = xi;
                    matchFlags[xi] = true;
                    matches++;
                    break;
                }
            }
        }

        char[] ms1 = new char[matches];
        char[] ms2 = new char[matches];
        for (int i = 0, si = 0; i < min.length(); i++)
        {
            if (matchIndexes[i] != -1)
            {
                ms1[si] = min.charAt(i);
                si++;
            }
        }

        for (int i = 0, si = 0; i < max.length(); i++)
        {
            if (matchFlags[i])
            {
                ms2[si] = max.charAt(i);
                si++;
            }
        }

        int transpositions = 0;
        for (int mi = 0; mi < ms1.length; mi++)
        {
            if (ms1[mi] != ms2[mi])
            {
                transpositions++;
            }
        }

        int prefix = 0;
        for (int mi = 0; mi < min.length(); mi++)
        {
            if (first.charAt(mi) == second.charAt(mi))
            {
                prefix++;
            }
            else
            {
                break;
            }
        }

        return new int[] { matches, transpositions / 2, prefix, max.length() };
    }
}
