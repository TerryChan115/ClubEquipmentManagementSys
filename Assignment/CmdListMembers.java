public class CmdListMembers implements Command{
    public void execute(String[] cmdParts){
        Club c = Club.getInstance();
        c.listClubMembers();
    }
}
