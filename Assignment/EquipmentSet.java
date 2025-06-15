import java.util.*;
public class EquipmentSet {
    private boolean isAvailable;
    private String set_number;
    private String borrow_member_ID;
    private String status;
    private ArrayList<String> reservations;

    public EquipmentSet(String num){
        isAvailable = true;
        set_number = num;
        borrow_member_ID = "";
        status = "";
        reservations = new ArrayList<>();
    }
    public void requestTo(String request){
        reservations.add(request);
    }
    public void Notrequest(String request){
        reservations.remove(request);
    }
    public void lendTo(String id){
        borrow_member_ID = id;
        isAvailable = false;
    }
    public void cancel(){
        borrow_member_ID = "";
        status = "";
        isAvailable = true;
    }
    public void record(String r){
        status = r;
    }
    public static void printrecord(ArrayList<EquipmentSet> equipmentSets, String code){
        for(EquipmentSet Equipmentset: equipmentSets){
            System.out.printf("  %s_%s\n",code, Equipmentset.set_number);
            if(Equipmentset.isAvailable) System.out.println("    Current status: Available");
            else System.out.printf("    Current status: %s\n", Equipmentset.status);
            if(!Equipmentset.reservations.isEmpty()){
                System.out.printf("    Requested period(s): ");
                Equipmentset.print();
            }

        }

    }
    public void sortRecordList() {
        Collections.sort(reservations, new Comparator<String>() {
            @Override
            public int compare(String r1, String r2) {
                String[] parts1 = r1.split(" ");
                String[] parts2 = r2.split(" ");
                
                Day startDate1 = new Day(parts1[0]);
                Day startDate2 = new Day(parts2[0]);
                Day endDate1 = new Day(parts1[2]);
                Day endDate2 = new Day(parts2[2]);

                int startDateComparison = Day.compare(startDate1, startDate2);
                if (startDateComparison != 0) {
                    return startDateComparison;
                }
                return Day.compare(endDate1, endDate2);
            }
        });
    }
    public void print(){
        String message = "";
        this.sortRecordList();
        for(String r: reservations){
            message += r;
            message += ", ";
        }
        if(message.endsWith(", ")){
            message = message.substring(0, message.length() - 2);
        }
        System.out.println(message);
    }
    public String getSet_number(){
        return set_number;
    }
    public String toString(){
        return set_number + "(" + borrow_member_ID + ")";
    }
    public boolean available(){
        return isAvailable;
    }
    public boolean Overlap(Day start, Day end) {
        String[] statusParts = null; 
        Day startDay = null;
        Day endDay = null;

        if(status.equals("")){
            startDay = new Day(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
            endDay = new Day(0,0,0);
        }
        else{
            statusParts = status.split(" ");
            startDay = new Day(statusParts[4]);
            endDay = new Day(statusParts[6]);
        }

        if(Day.compare(end, startDay) == -1 || Day.compare(endDay, start) == -1){
            for(String s:reservations){
                statusParts = s.split(" ");
                startDay = new Day(statusParts[0]);
                endDay = new Day(statusParts[2]);
                if(!(Day.compare(end, startDay) == -1 || Day.compare(endDay, start) == -1)) return true;
            }
            return false;
        }
        return true;
    }
    
}
