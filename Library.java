import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.*;

public class Library {
    private static String openingHours = "9 AM to 5 PM";
    private String address;
    private ArrayList<Book> books = new ArrayList<>();; // Collection of books in the library
    private HashMap<String, Integer> copiesOfBooks = new HashMap<>();

    // Constructor for a library without a catalog file
    public Library(String address) {
        this.address = address;
    }

    // Constructor for a library with a catalog file
    public Library(String address, String catalogFile) {
        this.address = address;
        importBooksFromCatalog(catalogFile);
    }

    // Add a book to the library
    public void addBook(Book book) {
        if (copiesOfBooks.get(book.getTitle ()) == null){
            books.add(book);
            copiesOfBooks.put(book.getTitle(), 1);
        }else {
           copiesOfBooks.put(book.getTitle(), copiesOfBooks.get(book.getTitle()) + 1);
        }
    }

    // Borrow a book from the library
    public void borrowBook(String title) {

            if (copiesOfBooks.get(title) == null) {
                System.out.println ("We do not have this book in the catalog");
            } else {
                if (copiesOfBooks.get(title) == 0) {
                    System.out.println("Books is in catalog, but all copies are borrowed.");
                } else {
                    copiesOfBooks.put(title, copiesOfBooks.get(title) - 1);
                    System.out.println("You successfully borrowed " + title +
                            ", remaining number of copies: " + copiesOfBooks.get(title));
                }
            }
    }

    // Return a book to the library
    public void returnBook(String title) {
        if (copiesOfBooks.get(title) == null) {
            System.out.println ("We do not have this book in the catalog");
        } else {

                copiesOfBooks.put(title, copiesOfBooks.get(title) + 1);
                System.out.println("You successfully returned " + title +
                        ", remaining number of copies: " + copiesOfBooks.get(title));

        }
    }

    // Print available books in the library
    public void printAvailableBooks() {
        for (Book book : books) {
            System.out.println(book.getTitle() + ", remaining number of copies: "
                    + getRemainingCopies(book.getTitle()));
        }
    }

    // Print the library's opening hours
    public static void printOpeningHours() {
        System.out.println(openingHours);
    }

    // Print the library's address
    public void printAddress() {
        System.out.println(address);
    }

    // Import books from a catalog file (if provided)
    private void importBooksFromCatalog(String catalogFile) {
        try {
            File file = new File("catalog.csv");
            BufferedReader b = new BufferedReader(new FileReader(file));
            String st;
            while((st = b.readLine()) != null){
                String[] splitString = st.split("[,]", 0);
                String title = splitString[0];
                Integer numberOfCopies = Integer.valueOf(splitString[1]);
                for(int i = 0; i < numberOfCopies; i++){
                    addBook(new Book(title));
                }

            }
        } catch (Exception e) {
            System.out.println("File not found.");
        }
    }


    // Get the remaining number of copies for a specific book
    private int getRemainingCopies(String title) {
        return copiesOfBooks.get(title);
    }

    public static void main(String[] args) {
        // Create two libraries
        Library firstLibrary = new Library("10 Main St.");
        Library secondLibrary = new Library("228 Liberty St.");
        Library thirdLibrary = new Library("12 Broadway St.", "catalog.csv");

        // Add four books to the first library
        firstLibrary.addBook(new Book("The Da Vinci Code"));
        firstLibrary.addBook(new Book("The Da Vinci Code")); // second copy
        firstLibrary.addBook(new Book("Le Petite Prince"));
        firstLibrary.addBook(new Book("A Tale of Two Cities"));
        firstLibrary.addBook(new Book("The Lord of the Rings"));
        firstLibrary.addBook(new Book("The Lord of the Rings")); // second copy

        // Print opening hours and the addresses
        System.out.println("Library hours:");
        printOpeningHours();
        System.out.println();

        System.out.println("Library addresses:");
        firstLibrary.printAddress();
        secondLibrary.printAddress();
        thirdLibrary.printAddress();
        System.out.println();

        // Try to borrow The Lord of the Rings from both libraries
        System.out.println("Borrowing The Lord of the Rings:");
        firstLibrary.borrowBook("The Lord of the Rings");
        firstLibrary.borrowBook("The Lord of the Rings");
        firstLibrary.borrowBook("The Lord of the Rings");
        secondLibrary.borrowBook("The Lord of the Rings");
        System.out.println();

        // Print the titles of all available books from both libraries
        System.out.println("Books available in the first library:");
        firstLibrary.printAvailableBooks();
        System.out.println();
        System.out.println("Books available in the second library: 0\n");
        secondLibrary.printAvailableBooks();
        System.out.println("Books available in the third library:");
        thirdLibrary.printAvailableBooks();
        System.out.println();

        // Return The Lord of the Rings to the first library
        System.out.println("Returning The Lord of the Rings:");
        firstLibrary.returnBook("The Lord of the Rings");
        System.out.println();

        // Print the titles of available from the first library
        System.out.println("Books available in the first library:");
        firstLibrary.printAvailableBooks();
    }
}
