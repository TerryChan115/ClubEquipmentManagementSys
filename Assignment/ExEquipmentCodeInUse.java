public class ExEquipmentCodeInUse extends Exception{
    public ExEquipmentCodeInUse(){super("Used Equipment code!");}
    public ExEquipmentCodeInUse(String message){super(message);}
}