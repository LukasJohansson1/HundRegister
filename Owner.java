//Lukas Johansson lujo0128
import java.util.*;

public class Owner implements Comparable<Owner> {
    private final String name;
    private final ArrayList<Dog> dogs = new ArrayList<>();

    public Owner(String name) {
        this.name = normalizeName(name);
    }

    public String getName() {
        return name;
    }

    public boolean addDog(Dog dog) {
        if (dog == null || dogs.contains(dog)) {
            return false; // Avoid adding the same dog twice
        }
        if (dog.getOwner() != null && dog.getOwner() != this) {
            return false; // Hunden har redan en annan ägare
        }

        dogs.add(dog);
        sortDogs();
        return true;
    }

    public boolean removeDog(Dog dog) {
        if (dog != null && dogs.contains(dog)) {
            dogs.remove(dog); // Ta bort hunden från listan över hundar
            dog.setOwner(null); // Nollställ hundens ägare
            return true; 
        }
        return false; 
    }
    

    public ArrayList<Dog> getDogs() {
        return new ArrayList<>(dogs); // Returnera en kopia för att förhindra direkt manipulation.
    }

    private void sortDogs() {
        Collections.sort(dogs, (d1, d2) -> d1.getName().compareTo(d2.getName()));
    }

    @Override
    public String toString(){
        return String.format("Ägare [namn=%s, hundar=%s]", name, dogs);
    }

    private String normalizeName(String name) {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @Override
    public int compareTo(Owner other) {
        if (other == null) {
            throw new NullPointerException("The other owner can't be null");
        }
        return this.name.compareTo(other.name);
    }
}
