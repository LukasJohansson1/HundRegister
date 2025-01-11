// Lukas Johansson lujo0128
import java.util.*;
import java.io.*;

public class InputReader {
    private static Map<InputStream, Boolean> usedStreams = new HashMap<>(); // HashMap spårar använda InputStream. Om en ström redan är i bruk, kastas ett IllegalStateException.
    private Scanner scanner; 

    // Konstruktor som använder System.in (standard input)
    public InputReader() {
        this(System.in); 
    }

    // Konstruktor som tar en InputStream som parameter
    public InputReader(InputStream inputStream) {
        // Kontrollera om strömmen redan är i användning
        if (usedStreams.getOrDefault(inputStream, false)) {
            throw new IllegalStateException("Fel: Denna InputStream används redan.");
        }

        this.scanner = new Scanner(inputStream); // Initiera Scanner med strömmen
        usedStreams.put(inputStream, true); // Markera att strömmen används
    }

    //för att läsa ett heltal från användaren
    public int readInt(String prompt) {
        System.out.print(prompt + " ?>"); 
        int value = scanner.nextInt();  
        scanner.nextLine();             // Töm bufferten 
        return value;                  
    }

    //för att läsa ett decimaltal från användaren
    public double readDouble(String prompt) {
        System.out.print(prompt + " ?>"); 
        double value = scanner.nextDouble(); 
        scanner.nextLine();                 
        return value;                       
    }

    // Metod för att läsa en textsträng 
    public String readString(String prompt) {
        System.out.print(prompt + " ?>"); 
        return scanner.nextLine();      
    }
 
}
