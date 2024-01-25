
public class BankStaff extends Person {

    //attribtues

    private String StaffID , StaffUserName, StaffPassword;


    //constructor

    public BankStaff(){}

    public BankStaff(String name, String address, String ic, String phone,String email, String id, String userName, String password){
        super(name, address, ic, phone, email);
        this.StaffID = id;
        this.StaffUserName = userName;
        this.StaffPassword = password;
    }

    //setter 
    public void setStaffID(String NewID){this.StaffID = NewID;}
    public void setStaffUserName(String NewUserName){this.StaffUserName = NewUserName;}
    public void setStaffPassword(String NewPassword){this.StaffPassword =  NewPassword;}

    //getter

    public String getStaffID(){return this.StaffID;}
    public String getStaffUserName(){return this.StaffUserName;}
    public String getStaffPassword(){return this.StaffPassword;}

    //processor


    //add ability to approve transaction that are above 10k, below that are automated.

    public String WriteString(){
        String write = this.Name + ";" + this.Address + ";" + this.IC + ";" + this.Phone + ";" +this.EmailAddress + ";"+ this.StaffID + ";" + this.StaffUserName + ";" + this.StaffPassword;
        return write;
    }


    
}
