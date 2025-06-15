public class CmdArrive extends RecordedCommand{
    private Club c;
    private Equipment equipment;
    private EquipmentSet equipmentset;
    @Override
    public void execute(String[] cmdParts){
        try{
            if(cmdParts.length < 2) throw new ExInsufficientCommandArgs();
            c = Club.getInstance();
            equipment = c.findEquipment(cmdParts[1]);
            if(equipment == null) throw new ExEquipmentNotFound();
            equipmentset = equipment.addEquipmentSet();
            System.out.println("Done.");

            addUndoCommand(this);
		    clearRedoList();
        }catch(ExInsufficientCommandArgs e){
            System.out.println("Insufficient command arguments.");
        }catch(ExEquipmentNotFound e){
            System.out.printf("Missing record for Equipment %s.  Cannot mark this item arrival.\n", cmdParts[1]);
        }
    }
    @Override
    public void undoMe(){
        c.findEquipment(equipment.getcode()).removeEquipmentSet(equipmentset);
        addRedoCommand(this);
    }
    @Override
    public void redoMe(){
        c.findEquipment(equipment.getcode()).addEquipmentSet(equipmentset);
        addUndoCommand(this);
    }
}
