import java.util.ArrayList;
import java.util.Collections; 

public class Club {

    private ArrayList<Member> allMembers;
    private ArrayList<Equipment> allEquipments;
    private static Club instance = new Club();

    private Club() { 
        allMembers = new ArrayList<>(); 
        allEquipments = new ArrayList<>();
    }

    public static Club getInstance() { return instance; }

    public void request(Member m, String record, EquipmentSet equipment_set){
        m.addrequest();
        equipment_set.requestTo(record);
    }
    public void cancel_request(Member m, String record, EquipmentSet equipment_set){
        m.removerequest();
        equipment_set.Notrequest(record);
    }
    public void lend(Member m, Equipment e, EquipmentSet equipment_set){
        equipment_set.lendTo(m.getid());
        m.addborrow(e.getcode());
    }
    public void cancel_lend(Member m, Equipment e, EquipmentSet equipment_set){
        equipment_set.cancel();
        m.reduceborrow(e.getcode());
    }
    public Equipment findEquipment(String equipment_code){
        for(Equipment e:allEquipments){
            if(e.getcode().equals(equipment_code)){
                return e;
            }
        }
        return null;
    }

    public Member findMember(String id){
        for(Member m:allMembers){
            if(m.getid().equals(id)){
                return m;
            }
        }
        return null;
    }

    public void addMember(Member m) {// See how it is called in Member constructor (Step 3)
        this.allMembers.add(m);
        Collections.sort(allMembers); // allMembers
    }

    public void removeMember(Member m) {// See how it is called in Member constructor (Step 3)
        this.allMembers.remove(m); // allMembers
    }

    public void addEquipment(Equipment e) {// See how it is called in Member constructor (Step 3)
        this.allEquipments.add(e); 
    }

    public void removeEquipment(Equipment e){
        this.allEquipments.remove(e);
    }

    public void listClubMembers() {
        Member.list(this.allMembers);
    }
    public void listEquipment(){
        Equipment.list(this.allEquipments);
    }

    public void listMemberStatus(){
        Member.listrecord(this.allMembers);
    }
    public void listEquipmentStatus(){
        Equipment.listrecord(this.allEquipments);
    }
}
