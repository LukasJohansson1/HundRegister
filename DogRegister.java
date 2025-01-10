//Lukas Johansson lujo0128
import java.util.*;

public class DogRegister {

    private InputReader inputReader;
    private DogCollection dogCollection;
    private OwnerCollection ownerCollection;

    public DogRegister(InputReader inputReader) {
        this.inputReader = inputReader;
        this.dogCollection = new DogCollection();
        this.ownerCollection = new OwnerCollection();
    }

    public boolean isInputEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
    

    private boolean checkCollectionsForDogAndOwner() {
        if (dogCollection.getDogs().isEmpty()) {
            System.out.println("Fel: Inga hundar är registrerade.");
            return false;
        }
    
        if (ownerCollection.getOwners().isEmpty()) {
            System.out.println("Fel: Inga ägare är registrerade.");
            return false;
        }
    
        return true;
    }

private String getValidatedInput(String prompt, String errorMessage, String specificError) {
    while (true) {
        String input = inputReader.readString(prompt);
        if (isInputEmpty(input)) {
            System.out.println(errorMessage);
        } else if (dogCollection.getDog(input) != null && specificError != null) {
            System.out.println(specificError);
            return null;
        } else {
            return input;
        }
    }
}

private int getValidatedNumber(String prompt, String errorMessage, int minValue) {
    int input = inputReader.readInt(prompt);
    if (input < minValue) {
        System.out.println(errorMessage);
        return -1;
    }
    return input;
}

    public void registerNewOwner() {
        String ownerName;
        
        // Be om namn tills ett giltigt namn anges
        while (true) {
            ownerName = inputReader.readString("Ange namn på ny ägare?> ");
            
            if (isInputEmpty(ownerName)) {
                System.out.println("Fel: Ett tomt namn är inte tillåtet, försök igen.");
                continue;  // Be om ett nytt namn
            }
    
            if (ownerCollection.containsOwner(ownerName)) {
                System.out.println("Fel: Ägaren " + ownerName + " finns redan i registret.");
                return;  
            } else {
                Owner newOwner = new Owner(ownerName);
                ownerCollection.addOwner(newOwner);
                System.out.println("Ägaren " + ownerName + " har lagts till i registret.");
                break;  // Avsluta loopen när ett giltigt namn har angetts
            }
        }
    }
    
    
    
    public void removeOwner() {
        // Kontrollera om det finns några ägare i registret
        if (ownerCollection.getOwners().isEmpty()) {
            System.out.println("Fel: Inga ägare är registrerade.");
            return; // Om inga ägare finns, återgår programmet till att fråga efter nästa kommando
        }
    
        // Om det finns ägare, fråga om vilken ägare som ska tas bort
        String ownerName = inputReader.readString("Ange namnet på ägaren du vill ta bort");
    
        if (isInputEmpty(ownerName)) {
            System.out.println("Fel: Namnet får inte vara tomt eller innehålla endast blanksteg.");
            return;
        }
    
        Owner owner = ownerCollection.getOwner(ownerName);
        if (owner == null) {
            System.out.println("Fel: Ägaren " + ownerName + " finns inte.");
            return;
        }
    
        // Hämta hundar kopplade till ägaren
        ArrayList<Dog> dogs = owner.getDogs();

        

        if (!dogs.isEmpty()) {
            // Om ägaren har hundar, ta bort kopplingen mellan hundarna och ägaren
            for (Dog dog : dogs) {
                dog.setOwner(null); // Sätt hundens ägare till null
                dogCollection.removeDog(dog);
            }


            System.out.println("Hundarna som tillhörde " + ownerName + " har nu ingen ägare.");
        }
    
        // Ta bort ägaren från samlingen
        if (ownerCollection.removeOwner(ownerName)) {
            System.out.println("Ägaren " + ownerName + " har tagits bort.");
        } else {
            System.out.println("Fel: Ett oväntat fel inträffade vid borttagning av ägaren.");
        }
    }
    
    
    
    public void registerNewDog() {
        String dogName = getValidatedInput("Ange hundens namn?", 
            "Fel: En tom eller blank sträng är inte tillåten, försök igen.", 
            "Fel: Hunden finns redan i registret.");
    
        if (dogName == null) return;
    
        String dogBreed = getValidatedInput("Ange hundens ras?", 
            "Fel: En tom eller blank sträng är inte tillåten, försök igen.", 
            null);
    
        if (dogBreed == null) return;
    
        int dogAge = getValidatedNumber("Ange hundens ålder?", "Fel: Åldern kan inte vara negativ.", 0);
        if (dogAge == -1) return;
    
        int dogWeight = getValidatedNumber("Ange hundens vikt?", "Fel: Vikten måste vara större än 0.", 1);
        if (dogWeight == -1) return;
    
        Dog newDog = new Dog(dogName, dogBreed, dogAge, dogWeight);
        dogCollection.addDog(newDog);
    
        System.out.println("Hunden " + dogName + " har lagts till i registret.");
    }
    
    
    
    public void removeDog() {
        // Kontrollera om det finns några hundar i registret
        if (dogCollection.getDogs().isEmpty()) {
            System.out.println("Fel: Inga hundar i registret");
            return;  // Avsluta metoden om inga hundar finns
        }
    
        // Be användaren om hundens namn
        String dogName = inputReader.readString("Ange hundens namn?");
    
        Dog dogToRemove = dogCollection.getDog(dogName);
    
        if (dogToRemove == null) {
            System.out.println("Fel: Hunden med namnet " + dogName + " finns inte i registret.");
            return;
        }
    
        // Om hunden har en ägare, ta bort hunden från ägarens lista
        if (dogToRemove.getOwner() != null) {
            dogToRemove.getOwner().removeDog(dogToRemove);
        }
    
        // Ta bort hunden från hundregistret
        dogCollection.removeDog(dogToRemove);
        System.out.println("Hunden " + dogName + " har tagits bort från registret.");
    }
    
    
    public void listDogs() {
        // Kontrollera om det finns några hundar i registret
        if (dogCollection.getDogs().isEmpty()) {
            System.out.println("Fel: Inga hundar i registeret");
            return;
        }
    
        // Be användaren om minsta svanslängd
        double minTailLength = inputReader.readDouble("Ange minsta svanslängd?");
    
        // Hämta alla hundar som har en svanslängd >= minTailLength
        ArrayList<Dog> dogsWithMinTail = dogCollection.getDogsByTail(minTailLength);
    
        if (dogsWithMinTail.isEmpty()) {
            System.out.println("Inga hundar matchar den angivna svanslängden.");
            return;
        }
    
        // Skriv ut listan med hundar
        System.out.println("Name  Breed    Age Weight Tail Owner");
        System.out.println("====================================");
        for (Dog dog : dogsWithMinTail) {
            // Om hunden har en ägare, skriv ut ägarens namn, annars lämna tomt
            String ownerName = (dog.getOwner() != null) ? dog.getOwner().getName() : "";
            System.out.printf("%-5s %-9s %-3d %-3d %-4.1f %s\n",
                dog.getName(), dog.getBreed(), dog.getAge(),
                dog.getWeight(), dog.getTailLength(), ownerName);
        }
    }
    
    
    
    public void listOwners() {
        // Kontrollera om det finns några ägare
        if (ownerCollection.size() == 0) {
            System.out.println("Fel: Inga owners i registret");
            return;
        }
    
        // Sortera ägarna innan vi skriver ut dem
        ArrayList<Owner> ownersList = ownerCollection.getOwners();
        ownersList.sort(Comparator.comparing(Owner::getName));
    
        // Skriv ut rubriker för listan
        System.out.println("Name     Dogs");
        System.out.println("===================");
    
        // Gå igenom alla ägare och skriv ut deras namn och ägda hundar
        for (Owner owner : ownersList) {
            String ownerName = owner.getName();
            ArrayList<Dog> dogs = owner.getDogs();
    
            // Skapa en lista med hundnamn för varje ägare
            String dogNames = dogs.isEmpty() ? "" : String.join(", ", dogs.stream().map(Dog::getName).toArray(String[]::new));
    
            // Skriv ut ägarens namn och deras hundar
            System.out.println(ownerName + "    " + dogNames);
        }
    }
    
    
    
    public void increaseAge() {
        // Kontrollera om det finns några hundar i registret
        if (dogCollection.size() == 0) {
            System.out.println("Fel: Inga hundar finns i registret");
            return;  // Avbryt om inga hundar finns
        }
        
        // Be användaren om hundens namn
        String dogName = inputReader.readString("Enter dog name?>");
    
        // Hämta hunden baserat på namnet
        Dog dog = dogCollection.getDog(dogName);
    
        if (dog == null) {
            // Om hunden inte finns, skriv ut ett felmeddelande och avbryt
            System.out.println("Fel: Hunden " + dogName + " finns inte");
            return;
        }
    
        // Öka hundens ålder med ett
        dog.increaseAge();
    
        // Skriv ut ett meddelande om att hunden har blivit ett år äldre
        System.out.println("The dog " + dogName + " is now one year older");
    }
    
    
    public void giveDogToOwner() {

        if (!checkCollectionsForDogAndOwner()) return;
        String dogName = inputReader.readString("Ange namnet på hunden som ska ges till en ägare");
        if (isInputEmpty(dogName)) {
            System.out.println("Fel: Hundens namn får inte vara tomt eller innehålla endast blanksteg.");
            return; // Återgår till att be om nästa kommando
        }
    
        Dog dog = dogCollection.getDog(dogName);
        if (dog == null) {
            System.out.println("Fel: Hunden med namnet " + dogName + " finns inte.");
            return;
        }

        // Kontrollera om hunden redan har en ägare
        if (dog.getOwner() != null) {
            System.out.println("Fel: Hunden " + dogName + " har redan en ägare.");
            return; // Återgår till att be om nästa kommando
        }
        String ownerName = inputReader.readString("Ange namnet på ägaren");
        if (isInputEmpty(ownerName)) {
            System.out.println("Fel: Ägarens namn får inte vara tomt eller innehålla endast blanksteg.");
            return; // Återgår till att be om nästa kommando
        }
    
        Owner owner = ownerCollection.getOwner(ownerName);
        System.out.println(dog.setOwner(owner)
            ? "Hunden " + dogName + " har tilldelats ägaren " + ownerName + "."
            : "Fel: Kunde inte tilldela hunden till ägaren.");
    }
    
    
    public void removeDogFromOwner() {

        if (dogCollection.size() == 0) {
            System.out.println("Fel: Inga hundar registrerade");
            return; // Om inga hundar finns, avsluta metoden utan att försöka ta bort någon hund
        }

        if (ownerCollection.getOwners().isEmpty()) {
            System.out.println("Fel: Inga ägare är registrerade.");
            return; // Återgår till att be om nästa kommando
        }

        // Be användaren om hundens namn
        String dogName = inputReader.readString("Ange hundens namn?>");
    
        // Hitta hunden i registret
        Dog dogToRemoveOwner = dogCollection.getDog(dogName);
        
        // Om hunden inte finns, skriv ut ett felmeddelande och återgå till nästa kommando
        if (dogToRemoveOwner == null) {
            System.out.println("Fel: Hunden " + dogName + " finns inte i registret");
            return;  // Avbryt om hunden inte finns
        }
    
        // Om hunden finns, kolla om den har en ägare
        if (dogToRemoveOwner.getOwner() != null) {
            Owner owner = dogToRemoveOwner.getOwner();
            
            // Ta bort hunden från ägarens lista
            if (owner.removeDog(dogToRemoveOwner)) {
                // Ta bort hundens ägare
                dogToRemoveOwner.setOwner(null);
                System.out.println("Hunden " + dogName + " har nu ingen ägare");
            }
        } else {
            System.out.println("Hunden " + dogName + " har redan ingen ägare");
        }
    }
    
    
    

    public void mainMenu() {
        System.out.println("Välkommen till Hundregistret!");
    
        while (true) {
            String command = inputReader.readString("Ange kommando (register new owner, remove owner, register new dog, remove dog, list dogs, list owners, increase age, give dog to owner, remove dog from owner, exit)").toLowerCase().trim();
    
            if (command.isEmpty()) {
                System.out.println("Input får inte vara tomt");
                continue;
            }
        
            switch (command) {
                case "register new owner":
                    registerNewOwner();
                    break;
                case "remove owner":
                    removeOwner();
                    break;
                case "register new dog":
                    registerNewDog();
                    break;
                case "remove dog":
                    removeDog();
                    break;
                case "list dogs":
                    listDogs();
                    break;
                case "list owners":
                    listOwners();
                    break;
                case "increase age":
                    increaseAge();
                    break;
                case "give dog to owner":
                    giveDogToOwner();
                    break;
                case "remove dog from owner":
                    removeDogFromOwner();
                    break;
                case "exit":
                    System.out.println("Avslutar programmet. Hej då!");
                    return;
                default:
                    System.out.println("Fel: Ogiltigt kommando. Försök igen.");
            }
        }
    }    

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        DogRegister register = new DogRegister(inputReader);
        register.mainMenu();
    }
}
