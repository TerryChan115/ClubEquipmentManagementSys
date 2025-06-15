import java.util.*;
public class Equipment{
    private String code;
    private String name;
    private ArrayList<EquipmentSet> equipmentSets;

    public Equipment(String code, String name){
        this.code = code;
        this.name = name;
        equipmentSets = new ArrayList<>();

        Club.getInstance().addEquipment(this);
    }
    public String getcode(){
        return code;
    }
    public String getname(){
        return name;
    }
    public String toString(){
        String ret_str = "";
        for(EquipmentSet e: equipmentSets){
            if(!e.available())
                ret_str += (code + "_" + e.toString() + ", ");
        } 
        if (ret_str.endsWith(", ")) { ret_str = ret_str.substring(0, ret_str.length() - 2);}
        return ret_str;
    }
    public EquipmentSet addEquipmentSet(){
        EquipmentSet e = new EquipmentSet(String.valueOf(equipmentSets.size() + 1));
        equipmentSets.add(e);
        return e;
    }
    public void addEquipmentSet(EquipmentSet e){
        equipmentSets.add(e);
    }
    public void removeEquipmentSet(EquipmentSet e){
        equipmentSets.remove(e);
    }
    public static void list(ArrayList<Equipment> allEquipments){
        System.out.printf("%-5s%-18s%5s\n", "Code", "Name", "#sets");
        for(Equipment e: allEquipments){
            System.out.printf("%-5s%-18s%3d", e.code, e.name, e.equipmentSets.size());
            if(!e.toString().equals(""))
                System.out.printf("  (Borrowed set(s): %s)",e.toString());
            System.out.println();
        }
    }
    public static void listrecord(ArrayList<Equipment> allEquipments){
        for(Equipment e: allEquipments){
            System.out.printf("[%s %s]\n", e.code, e.name);
            if(e.equipmentSets.isEmpty()) {
                System.out.println("  We do not have any sets for this equipment.");
                System.out.println();
                continue;
            }

            EquipmentSet.printrecord(e.equipmentSets, e.code);
            System.out.println();
        }
    }
    public EquipmentSet findAvailableSet(){
        for(EquipmentSet e: equipmentSets){
            if(e.available()) return e;
        }
        return null;
    }
    public EquipmentSet findAvailableSet(Day start, Day end){
        for(EquipmentSet e: equipmentSets){
            if(!e.Overlap(start, end)) return e;
        }
        return null;
    }

}
