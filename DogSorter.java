//Lukas Johansson lujo0128
import java.util.*;

public class DogSorter {
    
    private static void swapDogs(ArrayList<Dog> dogs, int indexOne, int indexTwo) {
        if (dogs == null || indexOne < 0 || indexTwo < 0 || indexOne >= dogs.size() || indexTwo >= dogs.size()) {
            throw new IllegalArgumentException("Ogiltig index eller lista");
        }
        if (indexOne == indexTwo){ // Inga ändringar behövs om indexen är desamma
            return;
        }
        Dog temp = dogs.get(indexOne);
        dogs.set(indexOne, dogs.get(indexTwo));
        dogs.set(indexTwo, temp);
    }

    private static int pickNextDog(Comparator<Dog> comparator, ArrayList<Dog> dogs, int startIndex) {
        if (comparator == null || dogs == null || startIndex < 0 || startIndex >= dogs.size()) {
            throw new IllegalArgumentException("Ogiltiga argument");
        }

        int bestIndex = startIndex;
        for (int i = startIndex + 1; i < dogs.size(); i++) {
            if (comparator.compare(dogs.get(i), dogs.get(bestIndex)) < 0) {
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    public static int sortDogs(Comparator<Dog> comparator, ArrayList<Dog> dogs) {
        if (comparator == null || dogs == null) {
            throw new IllegalArgumentException("Fel: Comparator eller lista är null");
        } 
        int swapCount = 0;

        for(int i = 0; i < dogs.size(); i++) {
            int bestIndex = pickNextDog(comparator, dogs, i); // Hitta indexet för det "bästa" (minsta/största beroende på comparator) elementet

            if(bestIndex != i) {
                swapDogs(dogs, i, bestIndex);
                swapCount++;
            }
        }
        return swapCount;
    }
}
