public class ExInsufficientCommandArgs extends Exception{
    public ExInsufficientCommandArgs(){super("Insufficient Command Arguments");}
    public ExInsufficientCommandArgs(String message){super(message);}
}
