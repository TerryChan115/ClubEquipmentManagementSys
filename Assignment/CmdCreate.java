public class CmdCreate extends RecordedCommand{
    private Club c;
    private Equipment e;
    @Override
    public void execute(String[] cmdParts){
        try{
            if(cmdParts.length < 3) throw new ExInsufficientCommandArgs();
            c = Club.getInstance();
            if(c.findEquipment(cmdParts[1]) != null) throw new ExEquipmentCodeInUse();
            e = new Equipment(cmdParts[1],cmdParts[2]);

            addUndoCommand(this);
		    clearRedoList();
            System.out.println("Done.");
        }catch(ExInsufficientCommandArgs e){
            System.out.println("Insufficient command arguments.");
        }catch(ExEquipmentCodeInUse e){
            System.out.printf("Equipment code already in use: %s %s\n", cmdParts[1], c.findEquipment(cmdParts[1]).getname());
        }
    }
    @Override
    public void undoMe(){
        c.removeEquipment(e);
        addRedoCommand(this);
    }
    @Override
    public void redoMe(){
        c.addEquipment(e);
        addUndoCommand(this);
    }
}
