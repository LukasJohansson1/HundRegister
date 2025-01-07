//Lukas Johansson lujo0128
import java.util.*;

public class DogNameComparator implements Comparator<Dog> {

    @Override
    public int compare(Dog dogFirst, Dog dogSecond) {
        return dogFirst.getName().compareToIgnoreCase(dogSecond.getName());
    }
}
    