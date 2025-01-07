// Lukas Johansson lujo0128

import java.util.*;

public class DogTailComparator implements Comparator<Dog> {

   
    @Override
    public int compare(Dog first, Dog second) {
       
        if (first.getTailLength() < second.getTailLength()) {
            return -1;  
        } else if (first.getTailLength() > second.getTailLength()) {
            return 1;   
        }
        return 0;  
    }
}
