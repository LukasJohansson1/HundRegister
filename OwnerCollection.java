//Lukas Johansson lujo0128
import java.util.*;

public class OwnerCollection {

    private static final int INITIAL_CAPACITY = 10;
    private Owner[] owners; 
    private int size; // hur många ägare som finns i samlingen

    

    public OwnerCollection() {
        this.owners = new Owner[INITIAL_CAPACITY];
        this.size = 0;
    }

    // Lägger till en ny ägare
    public boolean addOwner(Owner owner) {
        if (owner == null || containsOwner(owner.getName())) {
            return false; 
        }

        if (size == owners.length) {
            expandArray(); 
        }

        owners[size++] = owner; // Lägg till ägaren och öka storleken
        sortOwners(); 
        return true;
    }

    // Tar bort en ägare baserat på namn
    public boolean removeOwner(String name) {
        for (int i = 0; i < size; i++) {

            if (owners[i].getName().equals(name)) {
                // Kontrollera om ägaren har hundar, om ja, kan inte tas bort
                if (!owners[i].getDogs().isEmpty()) {
                    return false; 
                }
                // Ta bort ägaren om den inte har några hundar
                owners[i] = owners[size - 1]; // Flytta sista ägaren till den tomma platsen
                owners[size - 1] = null; // Rensa sista platsen
                size--; 
                sortOwners(); 
                return true;
            }
        }
        return false; 
    }
    

    // Tar bort en ägare baserat på objekt
    public boolean removeOwner(Owner owner) {
        for (int i = 0; i < size; i++) {

            if (owners[i].equals(owner)) {
                if (!owners[i].getDogs().isEmpty()) {
                    return false; 
                }

                owners[i] = owners[size - 1]; 
                owners[size - 1] = null; 
                size--; 
                sortOwners(); 
                return true;
            }
        }
        return false; 
    }
    

    // Kollar om en ägare med ett specifikt namn finns
    public boolean containsOwner(String name) {
        for (int i = 0; i < size; i++) {
            if (owners[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Kollar om en specifik ägare finns i samlingen
    public boolean containsOwner(Owner owner) {
        for (int i = 0; i < size; i++) {
            if (owners[i].equals(owner)) {
                return true;
            }
        }
        return false;
    }

    // Hämtar en ägare baserat på namn
    public Owner getOwner(String name) {
        for (int i = 0; i < size; i++) {
            if (owners[i].getName().equals(name)) {
                return owners[i];
            }
        }
        return null; 
    }


    public ArrayList<Owner> getOwners() {
        ArrayList<Owner> ownerList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ownerList.add(owners[i]);
        }
        return ownerList;
    }

    // Sorterar ägarna alfabetiskt efter namn
    private void sortOwners() {
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (owners[j].getName().compareTo(owners[minIndex].getName()) < 0) {
                    minIndex = j;
                }
            }
            // Byt plats på elementen
            Owner temp = owners[i];
            owners[i] = owners[minIndex];
            owners[minIndex] = temp;
        }
    }

    // Utökar arrayens kapacitet
    private void expandArray() {
        int newCapacity = owners.length + 1; // öka kapaciteten
        Owner[] newArray = new Owner[newCapacity];
        System.arraycopy(owners, 0, newArray, 0, owners.length); // Kopiera gamla arrayen
        owners = newArray;
    }

}
