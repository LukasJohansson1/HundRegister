//Lukas Johansson lujo0128
import java.util.*;

public class DogRegister {

    private static final String REGISTER_NEW_OWNER = "register new owner";
    private static final String REMOVE_OWNER = "remove owner";
    private static final String REGISTER_NEW_DOG = "register new dog";
    private static final String REMOVE_DOG = "remove dog";
    private static final String LIST_DOGS = "list dogs";
    private static final String LIST_OWNERS = "list owners";
    private static final String INCREASE_AGE = "increase age";
    private static final String GIVE_DOG_TO_OWNER = "give dog to owner";
    private static final String REMOVE_DOG_FROM_OWNER = "remove dog from owner";
    private static final String EXIT = "exit";
    private static final String SHOW_VALID_COMMANDS = "show valid commands";

    private static final Set<String> VALID_COMMANDS = Set.of(
        REGISTER_NEW_OWNER, REMOVE_OWNER, REGISTER_NEW_DOG, REMOVE_DOG,
        LIST_DOGS, LIST_OWNERS, INCREASE_AGE, GIVE_DOG_TO_OWNER,
        REMOVE_DOG_FROM_OWNER, EXIT, SHOW_VALID_COMMANDS
    );

    private InputReader inputReader;
    private DogCollection dogCollection;
    private OwnerCollection ownerCollection;

    private DogRegister(InputReader inputReader) {
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

    private void showValidCommands() {
        System.out.println("Giltiga kommandon:");
        for (String command : VALID_COMMANDS) {
            System.out.println("- " + command);
        }
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

    private void registerNewOwner() {
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
    
    
    
    private void removeOwner() {
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
    
    
    
    private void registerNewDog() {
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
    
    
    
    private void removeDog() {
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
    
    
    private void listDogs() {
        // Kontrollera om det finns några hundar i registret
        if (dogCollection.getDogs().isEmpty()) {
            System.out.println("Fel: Inga hundar i registeret");
            return;
        }
    
        // Be användaren om minsta svanslängd
        double minTailLength = inputReader.readDouble("Ange minsta svanslängd?");
    
        // Hämta alla hundar som har en svanslängd >= minTailLength
        ArrayList<Dog> dogsWithMinTail = dogCollection.getDogsWithMinTailLength(minTailLength);
    
        if (dogsWithMinTail.isEmpty()) {
            System.out.println("Fel: Inga hundar matchar den angivna svanslängden."); //
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
    
    
    
    private void listOwners() {
        // Kontrollera om det finns några ägare
        if (ownerCollection.getOwners().size() == 0) {
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
    
    
    
    private void increaseAge() {
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
    
    
    private void giveDogToOwner() {

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
    
    
    private void removeDogFromOwner() {

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
    
    
    

    private void mainMenu() {
        System.out.println("Välkommen till Hundregistret!");
    
        while (true) {
            String command = inputReader.readString("Ange kommando ('show valid commands' för att se alla giltiga kommandon)"
            ).toLowerCase().trim();
    
            if (command.isEmpty()) {
                System.out.println("Input får inte vara tomt");
                continue;
            }
        
            switch (command) {
            case REGISTER_NEW_OWNER:
                registerNewOwner();
                break;
            case REMOVE_OWNER:
                removeOwner();
                break;
            case REGISTER_NEW_DOG:
                registerNewDog();
                break;
            case REMOVE_DOG:
                removeDog();
                break;
            case LIST_DOGS:
                listDogs();
                break;
            case LIST_OWNERS:
                listOwners();
                break;
            case INCREASE_AGE:
                increaseAge();
                break;
            case GIVE_DOG_TO_OWNER:
                giveDogToOwner();
                break;
            case REMOVE_DOG_FROM_OWNER:
                removeDogFromOwner();
                break;
            case SHOW_VALID_COMMANDS:
                showValidCommands();
                break;
            case EXIT:
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
