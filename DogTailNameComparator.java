//Lukas Johansson lujo0128
import java.util.Comparator;

public class DogTailNameComparator implements Comparator<Dog>{
    private DogTailComparator tailComparator = new DogTailComparator();
    private DogNameComparator nameComparator = new DogNameComparator();

    @Override
    public int compare(Dog dogFirst, Dog dogSecond) {
        int tailComparison = tailComparator.compare(dogFirst, dogSecond);

        if(tailComparison != 0) {
            return tailComparison;
        }
        return nameComparator.compare(dogFirst, dogSecond);
    }
}
