import java.util.Objects;

public class Rack {
    int rackNo;
    BookCopy copy;
    public Rack(int rackNo) {
        this.rackNo = rackNo;
    }

    public boolean hasBookCopyId(String bookCopyId) {
        if(copy == null)return false;
        return Objects.equals(copy.getBookCopyId(), bookCopyId);
    }

    public boolean hasBookId(String bookId) {
        if(copy == null)return false;
        return Objects.equals(copy.getBookId(), bookId);
    }

    public void add(BookCopy bookCopy) {
        // return if book already on rack
        if(copy != null)return;

        copy = bookCopy;
    }

    public int getRackNumber() {
        return rackNo;
    }

    public boolean isEmpty() {
        return copy == null;
    }

    public BookCopy getCopy() {
        return copy;
    }

    public void removeCopy() {
        if(copy == null)return;

        System.out.println("Removed book copy: " + copy.getBookCopyId() + " from rack: " + rackNo);
        copy = null;
    }

    public void printBook() {
        if(copy == null)return;

        copy.print();
    }
}
