package com.zuora.kthitem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

public class FrequencyCounter {

    private Map<String, List<String>> userMap = new HashMap<String, List<String>>();
    private Map<String, Integer> multiple_hits = new HashMap<String, Integer>();

    FrequencyCounter(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        while ((line = reader.readLine()) != null) {
            StringTokenizer st  = new StringTokenizer(line, " ");
            String user = st.nextToken();
            String page = st.nextToken();
            List<String> pages = userMap.get(user);
            if(pages == null){
                userMap.put(user, pages = new ArrayList<String>());
            }
            pages.add(page);
            if(pages.size() == 3)  {
                String csv = String.join(",", pages);
                if(multiple_hits.containsKey(csv)){
                    int k = multiple_hits.get(csv);
                    multiple_hits.put(csv, k + 1);
                } else {
                    multiple_hits.put(csv, 1);
                }
                pages.remove(0);
            }

        }

        //System.out.println(userMap);
    }



    private void PrintTopN() {
        int maxValue = Collections.max(multiple_hits.values());

        for(Entry<String, Integer> entry : multiple_hits.entrySet()) {
            if(entry.getValue() == maxValue) {
                System.out.println(entry.getKey());
            }
        }
    }

    public static void main(String[] args) throws IOException {
	    FrequencyCounter fc = new FrequencyCounter(FrequencyCounter.class.getResourceAsStream("weblog.txt"));
        fc.PrintTopN();
    }
}
