package cz.cvut.fit.ddw;

import java.util.HashMap;
import java.util.Map;

public class RowContainer {

    private int rowSum;
    private Map<Integer, Integer> links;

    public RowContainer(String[] patterns) {
        links = new HashMap<Integer, Integer>();
        loadLinks(patterns);
    }

    private void loadLinks(String[] patterns) {
        for (String pattern : patterns) {
            String[] link = pattern.split(":");
            int numOfLinks = parseInt(link[1]);
            if (numOfLinks > 0) {
                links.put(parseInt(link[0]), numOfLinks);
                rowSum += numOfLinks;
            }
        }
    }

    private int parseInt(String value) {
        return Integer.parseInt(value);
    }

    public Map<Integer, Integer> getLinks() {
        return links;
    }

    public int getRowSum() {
        return rowSum;
    }
}
