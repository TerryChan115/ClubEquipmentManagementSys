public class CmdListEquipment implements Command{
    public void execute(String[] cmdParts){
        Club c = Club.getInstance();
        c.listEquipment();
    }
}
