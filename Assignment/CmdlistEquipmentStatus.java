public class CmdlistEquipmentStatus implements Command{
    public void execute(String[] cmdParts){
        Club c = Club.getInstance();
        c.listEquipmentStatus();
    }
}
