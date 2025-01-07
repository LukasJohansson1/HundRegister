// Lukas Johansson lujo0128
import java.util.*;

public class DogCollection {

    private ArrayList<Dog> dogs = new ArrayList<>();


    public boolean addDog(Dog dog) {
        for (Dog existingDog : dogs) {
            if (existingDog.getName().equals(dog.getName())) {
                return false;  
            }
        }
        dogs.add(dog);
        return true;
    }

    // Tar bort en hund baserat på namn

    public boolean removeDog(String name) {
        Dog dog = getDog(name);
        if (dog != null && dog.getOwner() != null) {
            return false; // Hunden kan inte tas bort eftersom den har en ägare.
        }

        // Om hunden inte har en ägare eller inte finns, ta bort den
        if (dog != null) {
            dogs.remove(dog);
            return true;
        }

        return false; 
    }


    // Tar bort en hund baserat på objekt
    public boolean removeDog(Dog dog) {
        if (dog.getOwner() != null) {
            return false; // Hunden kan inte tas bort eftersom den har en ägare.
        }
    
        return dogs.remove(dog);
    }

    // Kollar om en hund med angivet namn finns
    public boolean containsDog(String name) {
        return getDog(name) != null;
    }

    // Kollar om en specifik hund finns i samlingen
    public boolean containsDog(Dog dog) {
        return dogs.contains(dog);
    }


    public Dog getDog(String name) {
        for (Dog dog : dogs) {
            if (dog.getName().equals(name)) {
                return dog;
            }
        }
        return null;
    }

    public ArrayList<Dog> getDogs() {
        ArrayList<Dog> sortedDogs = new ArrayList<>(dogs);
        DogSorter.sortDogs((d1, d2) -> d1.getName().compareTo(d2.getName()), sortedDogs);
        return sortedDogs;
    }
    

    // Hämtar alla hundar som har en svans längre än en viss längd
    public ArrayList<Dog> getDogsByTail(double length) {
        ArrayList<Dog> result = new ArrayList<>();
        for (Dog dog : dogs) {
            if (dog.getTailLength() >= length) {
                result.add(dog);
            }
        }

        // Sortera baserat på svanslängd och namn om svansen är lika lång
        DogSorter.sortDogs((d1, d2) -> {
            if (Double.compare(d1.getTailLength(), d2.getTailLength()) == 0) {
                return d1.getName().compareTo(d2.getName()); // Om svanslängderna är lika, sortera på namn
            }
            return Double.compare(d1.getTailLength(), d2.getTailLength()); // Sortera på svanslängd annars
        }, result);
        
        return result;
    }
}
