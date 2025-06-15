public class CmdBorrow extends RecordedCommand{
    private Club c;
    private Member m;
    private Equipment e;
    private EquipmentSet equipment_set;
    private String Memberrecord;
    private String Equipmentrecord;
    private Day borrowDay;
    private Day returnDay;
    @Override
    public void execute(String[] cmdParts){
        try{
        if(cmdParts.length < 3) throw new ExInsufficientCommandArgs();
        int d = 7; 
        c = Club.getInstance();
        m = c.findMember(cmdParts[1]);
        if(m == null) throw new ExMemberNotFound();

        e = c.findEquipment(cmdParts[2]);
        if(e == null) throw new ExEquipmentNotFound();

        if(m.isDuplicated(e)) throw new ExDuplicateBorrowRequest();

        borrowDay = SystemDate.getInstance().clone();
        returnDay = SystemDate.getInstance().clone();

        if(cmdParts.length == 4) {
            if(Integer.parseInt(cmdParts[3]) < 1) throw new ExInvalidPeriodException();
            d = Integer.parseInt(cmdParts[3]);
        }
        returnDay.advance(d);

        if(m.isOverlap(e, borrowDay, returnDay)) throw new ExOverlapsPeriod();
        equipment_set = e.findAvailableSet(borrowDay, returnDay);
        if(equipment_set == null) throw new ExNoAvailableEquipment();
        c.lend(m, e, equipment_set);
        Memberrecord = "borrows " + e.getcode() + "_" + equipment_set.getSet_number() + " (" + e.getname() + ") for " + borrowDay.toString() + " to " + returnDay.toString();
        Equipmentrecord = m.getid() + " " + m.getname() + " borrows for " +borrowDay.toString() + " to " + returnDay.toString();
        m.addrecord(Memberrecord);
        equipment_set.record(Equipmentrecord);
        System.out.printf("%s %s %s\n", cmdParts[1], m.getname(), Memberrecord);
        System.out.println("Done.");

        addUndoCommand(this);
        clearRedoList();
        }catch(ExInsufficientCommandArgs e){
            System.out.println("Insufficient command arguments.");
        }catch(ExMemberNotFound e){
            System.out.println("Member not found.");
        }catch(ExEquipmentNotFound e){
            System.out.println("Equipment record not found.");
        }catch(ExDuplicateBorrowRequest e){
            System.out.println("The member is currently borrowing a set of this equipment. He/she cannot borrow one more at the same time.");
        }catch(ExNoAvailableEquipment e){
            System.out.println("There is no available set of this equipment for the command.");
        }catch(ExInvalidPeriodException e){
            System.out.println("The number of days must be at least 1.");
        }catch(ExOverlapsPeriod e){
            System.out.println("The period overlaps with a current period that the member requests the equipment.");
        }

    }
    @Override
    public void undoMe(){
        c.cancel_lend(m, e, equipment_set);
        m.deleterecord(Memberrecord);
        addRedoCommand(this);
    }
    @Override
    public void redoMe(){
        c.lend(m, e, equipment_set);
        m.addrecord(Memberrecord);
        equipment_set.record(Equipmentrecord);
        addUndoCommand(this);
    }
}
