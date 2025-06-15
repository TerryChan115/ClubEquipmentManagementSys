public class ExDuplicateBorrowRequest extends Exception{
    public ExDuplicateBorrowRequest(){super("Duplicate borrow request!");}
    public ExDuplicateBorrowRequest(String message){super(message);}
}