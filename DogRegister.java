//Lukas Johansson lujo0128

import java.util.*;

public class DogRegister {
    private ArrayList<Dog> dogs = new ArrayList<>();
    private ArrayList<Owner> owners = new ArrayList<>();

    // Lägg till en hund till registret
    public void addDog(String name, String breed, int age, int weight) {
        Dog newDog = new Dog(name, breed, age, weight);
        dogs.add(newDog);
        System.out.println("Added: " + newDog);
    }

    // Lista alla hundar
    public void listDogs() {
        if (dogs.isEmpty()) {
            System.out.println("No dogs registered yet.");
        } else {
            System.out.println("Registered dogs:");
            for (Dog dog : dogs) {
                System.out.println(dog);
            }
        }
    }

    // Lägg till en ägare
    public void addOwner(String name) {
        Owner newOwner = new Owner(name);
        owners.add(newOwner);
        System.out.println("Added owner: " + newOwner);
    }

    // Lista alla ägare
    public void listOwners() {
        if (owners.isEmpty()) {
            System.out.println("No owners registered yet.");
        } else {
            System.out.println("Registered owners:");
            for (Owner owner : owners) {
                System.out.println(owner);
            }
        }
    }

    // Koppla en hund till en ägare
    public void assignDogToOwner(String dogName, String ownerName) {
        Dog dog = findDogByName(dogName);
        Owner owner = findOwnerByName(ownerName);

        if (dog == null) {
            System.out.println("No dog found with the name: " + dogName);
            return;
        }
        if (owner == null) {
            System.out.println("No owner found with the name: " + ownerName);
            return;
        }

        boolean success = dog.setOwner(owner);
        if (success) {
            System.out.println("Assigned dog " + dogName + " to owner " + ownerName);
        } else {
            System.out.println("Error: Failed to assign dog. Dog may already have an owner or there was another issue.");
        }
    }

    // Hjälpmetod för att hitta en hund baserat på namn
    private Dog findDogByName(String name) {
        for (Dog dog : dogs) {
            if (dog.getName().equalsIgnoreCase(name)) {
                return dog;
            }
        }
        return null;
    }

    // Hjälpmetod för att hitta en ägare baserat på namn
    private Owner findOwnerByName(String name) {
        for (Owner owner : owners) {
            if (owner.getName().equalsIgnoreCase(name)) {
                return owner;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        DogRegister dogRegister = new DogRegister();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Dog Register!");
        System.out.println("Commands: adddog, listdogs, addowner, listowners, assign, exit >");

        while (true) {
            command = scanner.nextLine().trim();

            try {
                switch (command.toLowerCase()) {
                    case "adddog":
                        System.out.print("Enter dog's name: >");
                        String name = scanner.nextLine();

                        System.out.print("Enter dog's breed: >");
                        String breed = scanner.nextLine();

                        System.out.print("Enter dog's age: >");
                        int age = Integer.parseInt(scanner.nextLine());
                        if (age < 0) throw new IllegalArgumentException("Age must be non-negative.");

                        System.out.print("Enter dog's weight: >");
                        int weight = Integer.parseInt(scanner.nextLine());
                        if (weight < 0) throw new IllegalArgumentException("Weight must be non-negative.");

                        dogRegister.addDog(name, breed, age, weight);
                        break;

                    case "listdogs":
                        dogRegister.listDogs();
                        break;

                    case "addowner":
                        System.out.print("Enter owner's name: >");
                        String ownerName = scanner.nextLine();
                        dogRegister.addOwner(ownerName);
                        break;

                    case "listowners":
                        dogRegister.listOwners();
                        break;

                    case "assign":
                        System.out.print("Enter dog's name: >");
                        String dogName = scanner.nextLine();

                        System.out.print("Enter owner's name: >");
                        String ownerAssignName = scanner.nextLine();

                        dogRegister.assignDogToOwner(dogName, ownerAssignName);
                        break;

                    case "exit":
                        System.out.println("Exiting program. Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid command: \"" + command + "\". Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
            }
        }
    }
}
