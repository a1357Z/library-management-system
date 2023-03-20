import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    String userId;
    List<BookCopy> borrowedCopies;

    public User(String userId){
        this.userId = userId;
        borrowedCopies = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean isBorrowingAllowed() {
        return borrowedCopies.size() < 5;
    }

    public void borrowBookCopy(BookCopy bookCopy, String dueDate) {
        assert  bookCopy != null;
        borrowedCopies.add(bookCopy);
        bookCopy.getBorrowed(this, dueDate);
    }

    public BookCopy getIssuedBookCopy(String bookCopyId) {
        for(BookCopy bookCopy : borrowedCopies){
            if(Objects.equals(bookCopy.getBookCopyId(), bookCopyId)){
                return bookCopy;
            }
        }
        return null;
    }

    public void returnBookCopy(BookCopy bookCopy) {
        borrowedCopies.remove(bookCopy);
        bookCopy.getReturned();
    }

    public void printBorrowed() {
        for(BookCopy bookCopy : borrowedCopies){
            System.out.println("Book Copy: " + bookCopy.getBookCopyId() + " " + bookCopy.getDueDate());
        }
    }

    public void printBorrowedBooks() {
        for(BookCopy bookCopy : borrowedCopies){
            bookCopy.print();
        }
    }
}
