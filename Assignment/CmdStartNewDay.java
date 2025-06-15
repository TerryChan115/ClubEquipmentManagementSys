public class CmdStartNewDay extends RecordedCommand{
    private SystemDate d;
    private String lastd;
    private String currentd;
    
    @Override
    public void execute(String[] cmdParts){
        int year, month, day;
        final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
        try{
        if(cmdParts.length < 2) throw new ExInsufficientCommandArgs();

        d = SystemDate.getInstance();
        lastd = d.toString(); 
        currentd = cmdParts[1];
        String[] check_date = currentd.split("-");
        if(check_date.length < 3) {
            throw new ExInvalidDate();
        };
        year = Integer.parseInt(check_date[2]);
        month = MonthNames.indexOf(check_date[1]);
        if(month == -1) {
            throw new ExInvalidDate();
        }
        month = month / 3 + 1;
        day = Integer.parseInt(check_date[0]);
        if(Day.valid(year, month, day)){
            if(year < Integer.parseInt(lastd.substring(6,10))){
                throw new ExInvalidNewDay();
            }
            else if(year == Integer.parseInt(lastd.substring(6,10))){ 
                if(month < (MonthNames.indexOf(lastd.substring(2,5)) / 3 + 1)){
                    throw new ExInvalidNewDay();
                }
                else if(month == (MonthNames.indexOf(lastd.substring(2,5)) / 3 + 1)){
                    if(day < Integer.parseInt(lastd.substring(0,1))) {
                        throw new ExInvalidNewDay();
                    }
                }
            }
        }
        else{
            throw new ExInvalidDate();
        }

        d.set(currentd);

        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
        }
        catch(ExInvalidDate e){
            System.out.println("Invalid date.");
        }
        catch(ExInvalidNewDay e){
            System.out.printf("Invalid new day.  The new day has to be later than the current date %s.\n", lastd);
        }
        catch(ExInsufficientCommandArgs e){
            System.out.println("Insufficient command arguments.");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid date.");
        }
    }
    @Override
    public void undoMe(){
        d.set(lastd);
        addRedoCommand(this);
    }
    @Override
    public void redoMe(){
        d.set(currentd);
        addUndoCommand(this);
    }
}
