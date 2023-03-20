public class BookCopy {
    private final Book book;
    private final Library library;

    public String getBookCopyId() {
        return bookCopyId;
    }

    private final String bookCopyId;
    private User user;
    private String dueDate;
    private Rack rack;



    public BookCopy(Book book, String bookCopyId, Rack rack, Library library) {
        assert book != null;
        this.book = book;
        this.bookCopyId = bookCopyId;
        this.rack = rack;
        this.library = library;

        // copy is not yet borrowed
        user = null;
        dueDate = "";
    }

    public Book getBook() {
        return book;
    }

    public void getBorrowed(User user, String dueDate) {
        assert rack != null;

        // assign to user and set due date
        this.user = user;
        this.dueDate = dueDate;

        // remove from rack
        this.rack.removeCopy();
        System.out.println("Borrowed Book from rack: " + this.rack.getRackNumber());
        this.rack = null;
    }

    public String getBookId() {
        return book.getBookId();
    }


    public void getReturned() {
        //unassign user and due date
        this.user = null;
        this.dueDate = "";

        //assign to rack
        library.assignToRack(this);
    }

    public String getDueDate() {
        return dueDate;
    }

    public void print() {
        if(rack != null){
            System.out.println("Book Copy: " + " " + bookCopyId + " " + getBookId() + " " + getBookTitle() + " " + getAuthors() + " " + getPublishers() + " " + rack.getRackNumber());
        }else{
            System.out.println("Book Copy: " + " " + bookCopyId + " " + getBookId() + " " + getBookTitle() + " " + getAuthors() + " " + getPublishers() + " "  + getBorrowedById() + " " + getDueDate());
        }

    }

    private String getBorrowedById() {
        return user.getUserId();
    }

    private String getPublishers() {
        return book.getPublishers();
    }

    private String getAuthors() {
        return book.getAuthors();
    }

    private String getBookTitle() {
        return book.getTitle();
    }
}
