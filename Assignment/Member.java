import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Member implements Comparable<Member>{
    private String id;
    private String name;
    private ArrayList<String> borrowlist;
    private int requestnum;
    private ArrayList<String> recordList;
    private Day joinDate;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        borrowlist = new ArrayList<>();
        requestnum = 0;
        recordList = new ArrayList<>();
        this.joinDate = SystemDate.getInstance().clone();

        Club.getInstance().addMember(this);
    }

    public String getid(){
        return id;
    }

    public String getname(){
        return name;
    }

    public boolean isDuplicated(Equipment borrowEquipment){
        for(String e:borrowlist){
            if(e.equals(borrowEquipment.getcode())){
                return true;
            }
        }
        return false;
    }
    public void addrecord(String record){
        recordList.add(record);
    }
    public void deleterecord(String record){
        recordList.remove(record);
    }
    public void printrecord(){
        this.sortRecordList();
        System.out.printf("[%s %s]\n", id, name);
        if(recordList.isEmpty()) System.out.println("No record.");
        else{
            for(String r:recordList){
                System.out.printf("- %s\n", r);
            }
        }
    }
    public void addborrow(String code){
        borrowlist.add(code);
    }
    public void reduceborrow(String code){
        borrowlist.remove(code);
    }

    public void addrequest(){
        requestnum++;
    }
    public void removerequest(){
        requestnum--;
    }
    public static void list(ArrayList<Member> allMembers) {
        // Learn: "-" means left-aligned
        System.out.printf("%-5s%-9s%11s%11s%13s\n", "ID", "Name",
                "Join Date ", "#Borrowed", "#Requested");
        for (Member m : allMembers) {
            System.out.printf("%-5s%-9s%11s%7d%13d\n", m.id, m.name,
                    m.joinDate, m.borrowlist.size(), m.requestnum);
        }
    }
    public static void listrecord(ArrayList<Member> allMembers){
        for(Member m:allMembers){
            m.printrecord();
            System.out.println();
        }
    }
    public boolean isOverlap(Equipment e, Day start, Day end){
        String[] split_str = null;
        Day startday = null;
        Day endday = null;
            for(String s:recordList){
                split_str = s.split(" ");
                startday = new Day(split_str[4]);
                endday = new Day(split_str[6]);
                if(split_str[1].substring(0,2).equals(e.getcode())){
                    if(!(Day.compare(end, startday) == -1 || Day.compare(endday, start) == -1)) return true;
                }
            }
            return false;
    }
    public void sortRecordList() {
    Collections.sort(recordList, new Comparator<String>() {
        @Override
        public int compare(String r1, String r2) {
            String[] parts1 = r1.split(" ");
            String[] parts2 = r2.split(" ");
            

            String type1 = parts1[0];
            String type2 = parts2[0];
            String code1 = parts1[1];
            String code2 = parts2[1];
            Day startDate1 = new Day(parts1[4]);
            Day startDate2 = new Day(parts2[4]);
            Day endDate1 = new Day(parts1[6]);
            Day endDate2 = new Day(parts2[6]);

            int typeComparison = type1.compareTo(type2);
            if (typeComparison != 0) {
                return typeComparison;
            }
            int codeComparison = code1.substring(0,2).compareTo(code2.substring(0,2));
            if (codeComparison != 0) {
                return codeComparison;
            }
            int setComparison = code1.substring(3,4).compareTo(code2.substring(3,4));
            if (setComparison != 0) {
                return setComparison;
            }
            int startDateComparison = Day.compare(startDate1, startDate2);
            if (startDateComparison != 0) {
                return startDateComparison;
            }

            return Day.compare(endDate1, endDate2);
        }
    });
}
    @Override
    public int compareTo (Member another) {
        return this.id.compareTo(another.id);
    }
}
