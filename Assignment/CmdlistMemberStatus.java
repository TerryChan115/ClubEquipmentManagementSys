public class CmdlistMemberStatus implements Command{
    public void execute(String[] cmdParts){
        Club c = Club.getInstance();
        c.listMemberStatus();
    }
}
