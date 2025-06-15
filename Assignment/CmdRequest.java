public class CmdRequest extends RecordedCommand{
    private Club c;
    private Member m;
    private Equipment e;
    private EquipmentSet equipment_set;
    private String Memberrecord;
    private String Equipmentrecord;
    private Day startDay;
    private Day endDay;
    public boolean isValidDate(String date){
        int year, month, day;
        final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
        try{
        String[] check_date = date.split("-");
        if(check_date.length < 3) return false;
        year = Integer.parseInt(check_date[2]);
        month = MonthNames.indexOf(check_date[1]);
        if(month == -1) return false;
        month = month / 3 + 1;
        day = Integer.parseInt(check_date[0]);
        return Day.valid(year, month, day);
        }catch(NumberFormatException e){
            return false;
        }
    }
    @Override
    public void execute(String[] cmdParts){
        try{
        if(cmdParts.length < 5) throw new ExInsufficientCommandArgs();
        if(Integer.parseInt(cmdParts[4]) < 1) throw new ExInvalidPeriodException();
        c = Club.getInstance();
        m = c.findMember(cmdParts[1]);
        e = c.findEquipment(cmdParts[2]);
        if(m == null) throw new ExMemberNotFound();
        if(e == null) throw new ExEquipmentNotFound();
        if(!isValidDate(cmdParts[3])) throw new ExInvalidDate();
        startDay = new Day(cmdParts[3]);
        endDay = startDay.clone();
        endDay.advance(Integer.parseInt(cmdParts[4]));
        if(m.isOverlap(e, startDay, endDay)) throw new ExOverlapsPeriod();
        equipment_set = e.findAvailableSet(startDay, endDay);
        if(equipment_set == null) throw new ExNoAvailableEquipment();
        
        Memberrecord = "requests " + e.getcode() + "_" + equipment_set.getSet_number() + " (" + e.getname() + ") for " + startDay.toString() + " to " + endDay.toString();
        Equipmentrecord = startDay.toString() + " to " + endDay.toString();
        c.request(m, Equipmentrecord, equipment_set);
        m.addrecord(Memberrecord);
        System.out.printf("%s %s %s\n", cmdParts[1], m.getname(), Memberrecord);

        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
        }catch(ExInsufficientCommandArgs e){
            System.out.println("Insufficient command arguments.");
        }catch(ExMemberNotFound e){
            System.out.println("Member not found.");
        }catch(ExEquipmentNotFound e){
            System.out.println("Equipment record not found.");
        }catch(ExInvalidPeriodException e){
            System.out.println("The number of days must be at least 1.");
        }catch(NumberFormatException e) {
			System.out.println("Please provide an integer for the number of days.");
		}catch(ExOverlapsPeriod e){
            System.out.println("The period overlaps with a current period that the member borrows / requests the equipment.");
        }catch(ExNoAvailableEquipment e){
            System.out.println("There is no available set of this equipment for the command.");
        }catch(ExInvalidDate e){
            System.out.println("Invalid date.");
        }
    }
    @Override
    public void undoMe(){
        c.cancel_request(m, Equipmentrecord, equipment_set);
        m.deleterecord(Memberrecord);
        addRedoCommand(this);
    }
    @Override
    public void redoMe(){
        c.request(m, Equipmentrecord, equipment_set);
        m.addrecord(Memberrecord);
        addUndoCommand(this);
    }
}
