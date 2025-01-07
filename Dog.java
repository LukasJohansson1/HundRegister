//Lukas Johansson lujo0128
public class Dog {
    private final String name;
    private final String breed;
    private final int weight;
    private int age;
    private Owner owner;
    private final double dachshundTailLength = 3.7;

    // Konstruktor som tar emot hundens namn, ras, ålder och vikt.
    public Dog(String name, String breed, int age, int weight){
        this.name = normalizeName(name);  // Normalisera namn till korrekt format.
        this.breed = normalizeName(breed); // Normalisera rasen till korrekt format.
        this.age = age;
        this.weight = weight;
    }

    // Normaliserar namn genom att göra första bokstaven stor och resten små.
    private String normalizeName(String input) {
        if (input == null || input.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }

    // Getter för hundens namn.
    public String getName(){
        return name;
    }

    // Getter för hundens vikt.
    public int getWeight(){
        return weight;
    }

    // Getter för hundens ålder.
    public int getAge(){
        return age;
    }

    // Getter för hundens ras.
    public String getBreed(){
        return breed;
    }

    // Beräknar hundens svanslängd beroende på ras och andra faktorer.
    public double getTailLength(){
        if (breed.equalsIgnoreCase("tax") || breed.equalsIgnoreCase("dachshund")) {
            return dachshundTailLength; // Specifik svanslängd för taxar.
        }
        return (age * weight) / 10.0; // För andra raser beräknas längden baserat på ålder och vikt.
    }

    // Ökar hundens ålder med ett år, om möjligt.
    public void increaseAge(){
        if (age < Integer.MAX_VALUE) { // Kollar om åldern kan ökas.
            age++;
        }
    }

    public Owner getOwner(){
        return owner;
    }

    public boolean setOwner(Owner newOwner) {
        // Om hunden redan har den ägaren vi försöker sätta, gör inget och returnera false
        if (this.owner == newOwner) {
            return false;  
        }
    
        if (this.owner!= null && newOwner != null) {
            return false;
        }

        // Ta bort hunden från den gamla ägaren om det finns en
        if (this.owner != null) {
            this.owner.removeDog(this);
        }
    
        // Sätt den nya ägaren
        this.owner = newOwner;
    
        // Om det finns en ny ägare, lägg till hunden till ägarens lista
        if (newOwner != null) {
            return newOwner.addDog(this);
        }
    
        return true;
    }
    
    

    @Override
    public String toString(){
        if(name == null){
            return "Hund []";
        }

        // Skapa ägarinformationen om ägaren finns
        String ownerInfo = (owner != null) ? ", ägare=" + owner.getName() : "";
        
        // Returnera hundens information, inklusive ägarens namn om det finns
        return String.format("Hund [namn=%s, ras=%s ,ålder=%d years, vikt=%d, svanslängd=%.1f%s]", 
                              name, breed, age, weight, getTailLength(), ownerInfo);
    }
    
}
