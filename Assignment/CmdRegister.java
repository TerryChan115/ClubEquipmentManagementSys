class CmdRegister extends RecordedCommand{
    private Member m;
    private Club c;
    @Override
    public void execute(String[] cmdParts){
        try{
        if(cmdParts.length < 3)
            throw new ExInsufficientCommandArgs();
        c = Club.getInstance();
        if(c.findMember(cmdParts[1]) != null) throw new ExMemberIDInUse();
        m = new Member(cmdParts[1], cmdParts[2]);

        addUndoCommand(this);
		clearRedoList();
        System.out.println("Done.");
        }catch(ExInsufficientCommandArgs e){
            System.out.println("Insufficient command arguments.");
        }catch(ExMemberIDInUse e){
            System.out.printf("Member ID already in use: %s %s\n", cmdParts[1], c.findMember(cmdParts[1]).getname());
        }

    }
    @Override
    public void undoMe(){
        c.removeMember(m);
        addRedoCommand(this);
    }
    @Override
    public void redoMe(){
        c.addMember(m);
        addUndoCommand(this);
    }
}