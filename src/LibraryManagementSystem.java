import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryManagementSystem {
    Library library;
    List<String> allowedCommands;
    public LibraryManagementSystem(){
        allowedCommands = new ArrayList<>();
        allowedCommands.add("create_library <library_id> <no_of_racks>");
        allowedCommands.add("add_book <book_id> <title> <comma_separated_authors> <comma_separated_publishers> <comma_separated_book_copy_ids>");
        allowedCommands.add("remove_book_copy <book_copy_id>");
        allowedCommands.add("borrow_book <book_id> <user_id> <due_date>");
        allowedCommands.add("borrow_book_copy <book_copy_id> <user_id> <due_date>");
        allowedCommands.add("return_book_copy <book_copy_id>");
        allowedCommands.add("print_borrowed <user_id>");
        allowedCommands.add("search <attribute> <attribute_value> Possible values of attribute: book_id, author, publisher");
        allowedCommands.add("exit");

    }

    /*
    * return boolean stopTakingCommands
    * */
    public boolean processCommand(String command){
        boolean stopTakingCommands = false;
        String [] commands = command.split(" ");

        if(commands.length == 0)return stopTakingCommands;

        // exit system
        if(Objects.equals(commands[0], "exit")){
            return true;
        }

        // create library
        if(Objects.equals(commands[0], "create_library")){
            createLibrary(commands);
        }

        // add book
        if(Objects.equals(commands[0], "add_book")){
            addBook(commands);
        }

        //remove book copy
        if(Objects.equals(commands[0], "remove_book_copy")){
            removeBookCopy(commands);
        }

        // borrow book
        if(Objects.equals(commands[0], "borrow_book")){
            borrowBook(commands);
        }

        // return book
        if(Objects.equals(commands[0], "return_book_copy")){
            returnBookCopy(commands);
        }

        // print borrowed
        if(Objects.equals(commands[0], "print_borrowed")){
            printBorrowed(commands);
        }

        // search
        if(Objects.equals(commands[0], "search")){
            search(commands);
        }

        return stopTakingCommands;
    }

    private void search(String[] commands) {
        assert commands.length == 3;
        String attribute = commands[1];
        String attributeValue = commands[2];


        if(Objects.equals(attribute, "book_id")){
            library.searchBookId(attributeValue);
        }else if(Objects.equals(attribute, "author_id")){
            library.searchAuthor(attributeValue);
        }else if(Objects.equals(attribute, "publisher")){
            library.searchPublisher(attributeValue);
        }
    }

    private void printBorrowed(String[] commands) {
        assert commands.length == 2;
        String userId = commands[1];
        library.printBorrowed(userId);
    }

    private void returnBookCopy(String[] commands) {
        assert commands.length == 2;
        String bookCopyId = commands[1];
        library.returnBookCopy(bookCopyId);

    }

    private void borrowBook(String[] commands) {
        assert commands.length == 4;
        String bookId = commands[1];
        String userId = commands[2];
        String dueDate = commands[3];

        library.borrowBook(bookId, userId, dueDate);
    }

    private void removeBookCopy(String[] commands) {
        assert commands.length == 2;

        String bookCopyId = commands[1];
        library.removeBookCopy(bookCopyId);
    }

    private void addBook(String[] commands) {
        assert commands.length == 6;
        String bookId = commands[1];
        String bookTitle = commands[2];

        String authors = commands[3];
        String [] authorsList = authors.split(",");

        String publishers = commands[4];
        String [] publishersList = publishers.split(",");

        String copyIds = commands[5];
        String [] copyIdsList = copyIds.split(",");

        library.addBook(bookId, bookTitle, authorsList, publishersList, copyIdsList);
    }

    private void createLibrary(String[] commands) {
        assert commands.length == 3;

        String libraryId = commands[1];
        int rackCount = Integer.parseInt(commands[2]);
        library = new Library(libraryId, rackCount);
    }

    public List<String> getAllowedCommands(){
        return allowedCommands;
    }
}
