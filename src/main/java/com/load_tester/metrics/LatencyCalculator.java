package com.load_tester.metrics;

import java.util.Collections;
import java.util.List;

public class LatencyCalculator {
    public static long percentile(List<Long> latencies, double percentile){
        if(latencies==null || latencies.isEmpty())
            return -1;
        List<Long> sorted=new java.util.ArrayList<>(latencies);
        Collections.sort(sorted);
        int index=(int) Math.ceil(percentile/100.00 * sorted.size())-1;
        index=Math.max(0,Math.min(index,sorted.size()-1));
        return sorted.get(index);
    }

}
