public class Book {
    private String bookId;
    private String bookTitle;
    private String[] authorsList;
    private String[] publishersList;

    public Book(String bookId, String bookTitle, String[] authorsList, String[] publishersList) {

        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.authorsList = authorsList;
        this.publishersList = publishersList;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return bookTitle;
    }

    public String getAuthors() {
        StringBuilder authors = new StringBuilder();
        for(String a : authorsList){
            authors.append(a);
            authors.append(", ");
        }
        return authors.toString();
    }

    public String getPublishers() {
        StringBuilder publishers = new StringBuilder();
        for(String a : publishersList){
            publishers.append(a);
            publishers.append(", ");
        }
        return publishers.toString();
    }
}
