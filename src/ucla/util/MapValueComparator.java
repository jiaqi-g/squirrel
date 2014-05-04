package ucla.util;

import java.util.Comparator;
import java.util.Map;

public class MapValueComparator<T> implements Comparator<T> {
    Map<T, Double> theMapToSort;

    /*
    public MapValueComparator() {
    }*/
    
    public MapValueComparator(Map<T, Double> theMapToSort) {
        setMap(theMapToSort);
    }
    
    private void setMap(Map<T, Double> theMapToSort) {
    	this.theMapToSort = theMapToSort;
    }

    public int compare(T key1, T key2) {
        Double val1 = (Double) theMapToSort.get(key1);
        Double val2 = (Double) theMapToSort.get(key2);
        
        if (val1 < val2) {
            return 1;
        } else {
            return -1;
        }
    }
}