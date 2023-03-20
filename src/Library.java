import java.util.*;

public class Library {
    private final String libraryId;
    private int rackCount;
    private int rackNumber = 0;
    private List<Rack> racks;
    private List<Book> books;
    private List<User> users;

    private Map<String, List<String>> author2BookIds, publisher2BookIds;


    public Library(String libraryId, int rackCount) {
        // first initialize all variables
        this.libraryId = libraryId;
        this.rackCount = rackCount;
        racks = new ArrayList<>();
        books = new ArrayList<>();
        users = new ArrayList<>();
        author2BookIds = new HashMap<>();
        publisher2BookIds = new HashMap<>();

        //actions
        addRacks(rackCount);

        System.out.println("Created library with " + rackCount + " racks");
    }

    private int getRackNumber(){
        return ++rackNumber;
    }

    private Rack createRack(){
        int rackNo = getRackNumber();
        return new Rack(rackNo);
    }

    private void addRacks(int rackCount) {


        while(rackCount > 0){
            //create and add a new rack to library
            Rack rack = createRack();
            racks.add(rack);

            rackCount--;
        }
    }

    public static void main(String [] args){
        Library l = new Library("123abc", 10);

//        System.out.println(getRackNumber());
//        System.out.println(getRackNumber());
//        System.out.println(getRackNumber());
//        System.out.println(getRackNumber());
    }

    public void addBook(String bookId, String bookTitle, String[] authorsList, String[] publishersList, String[] copyIdsList) {

        // check if book with given id already exits ?

        // find the count of empty racks
        int count = getVacantRackCount();

        // if insufficient racks return
        if(count < copyIdsList.length){
            System.out.println("Rack not available");
            return;
        }

        // create book
        Book book = new Book(bookId, bookTitle, authorsList, publishersList);

        // add it to books list
        books.add(book);
        System.out.print("Added Book to racks: ");

        // add to author2BookIds
        for(String author: authorsList){
            author2BookIds.computeIfAbsent(author, k -> new ArrayList<>());
            List<String> bookIds = author2BookIds.get(author);
            bookIds.add(book.getBookId());
            author2BookIds.put(author, bookIds);
        }

        // add to publisher2BookIds
        for(String publisher: publishersList){
            publisher2BookIds.computeIfAbsent(publisher, k -> new ArrayList<>());
            List<String> bookIds = publisher2BookIds.get(publisher);
            bookIds.add(book.getBookId());
            publisher2BookIds.put(publisher, bookIds);
        }

        // create copies of it and add to racks
        int i=0;
        for(Rack rack : racks){
            if(rack.isEmpty() && i < copyIdsList.length){
                BookCopy bookCopy = new BookCopy(book, copyIdsList[i++], rack, this);
                rack.add(bookCopy);



                System.out.print(rack.getRackNumber() + ", ");
            }
        }

    }

    private int getVacantRackCount() {
        int count = 0;
        for(Rack rack : racks){
            if(rack.isEmpty()){
               count++;
            }
        }
        return count;
    }

    public void removeBookCopy(String bookCopyId) {
        for(Rack rack : racks){
            if(rack.hasBookCopyId(bookCopyId)){
                rack.removeCopy();
                return;
            }
        }

        System.out.println("Invalid Book Copy ID");
    }

    public void borrowBook(String bookId, String userId, String dueDate) {
        // check if bookId is valid
        Book book = getBook(bookId);
        if(book == null){
            System.out.println("Invalid Book ID");
            return;
        }

        // check if bookCopy is available
        BookCopy bookCopy = getBookCopy(bookId);
        if(bookCopy == null){
            System.out.println("Not available");
            return;
        }

        // check if user exists in system
        User user = getUser(userId);

        // if user does not exist, then create user and add to list
        if(user == null){
            user = new User(userId);
            users.add(user);
        }

        // if borrowing not allowed
        if(!user.isBorrowingAllowed()){
            System.out.println("Overlimit");
            return;
        }

        // if allowed, borrow book
        user.borrowBookCopy(bookCopy, dueDate);

    }

    private User getUser(String userId) {
        for(User user : users){
            if(Objects.equals(user.getUserId(), userId)){
                return user;
            }
        }
        return null;
    }

    private BookCopy getBookCopy(String bookId) {
        for(Rack rack : racks){
            if(rack.hasBookId(bookId)){
                return rack.getCopy();
            }
        }
        return null;
    }

    private Book getBook(String bookId) {
        for(Book book : books){
            if(Objects.equals(book.getBookId(), bookId)){
                return book;
            }
        }
        return null;
    }

    public void returnBookCopy(String bookCopyId) {
        for(User user : users){
            BookCopy bookCopy = user.getIssuedBookCopy(bookCopyId);
            if(bookCopy != null){
                // check for fine

                //return the book
                user.returnBookCopy(bookCopy);
                return;
            }
        }

        // book not found
        System.out.println("Invalid Book Copy ID");
    }

    public void assignToRack(BookCopy bookCopy) {
        for(Rack rack : racks){
            if(rack.isEmpty()){
                rack.add(bookCopy);
                System.out.println("Returned book copy " + bookCopy.getBookCopyId() + " and added to rack: " + rack.getRackNumber());
                return;
            }
        }
    }

    public void printBorrowed(String userId) {
        for(User user: users){
            if(Objects.equals(user.getUserId(), userId)){
                user.printBorrowed();
                return;
            }
        }
    }

    public void searchBookId(String bookId) {
        //print all books copies on rack
        for(Rack rack : racks){
            if(rack.hasBookId(bookId)){
                rack.printBook();
            }
        }

        //print all books copies issued by users
        for(User user: users){
            user.printBorrowedBooks();
        }
    }

    public void searchAuthor(String authorName) {
        for( String author: author2BookIds.keySet()){
            if(!Objects.equals(authorName, author))continue;
            List<String> bookIds = author2BookIds.get(author);
            for(String bookId : bookIds){
                searchBookId(bookId);
            }
            return;
        }
    }

    public void searchPublisher(String publisherName) {
        for( String publisher: publisher2BookIds.keySet()){
            if(!Objects.equals(publisherName, publisher))continue;
            List<String> bookIds = publisher2BookIds.get(publisher);
            for(String bookId : bookIds){
                searchBookId(bookId);
            }
            return;
        }
    }
}
