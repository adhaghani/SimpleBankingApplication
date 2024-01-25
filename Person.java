import java.time.YearMonth;

public class Person {
    
    protected String Name;
    protected String Address;
    protected String IC;
    protected String Phone;
    protected String EmailAddress;

    protected int Age;
    public Person(){};

    public Person(String name, String address, String ic, String phone, String Email){
        this.Name = name;
        this.Address = address;
        this.IC = ic;
        this.Phone = phone;
        this.EmailAddress = Email;
        this.Age = 0;
    }

    public void setPersonAge(int age){this.Age = age;}
    public void setPersonAddress(String address){this.Address = address;}
    public void setPersonPhone(String newPHone){this.Phone = newPHone;}
    public void setEmailAddress(String newEmail){this.EmailAddress = newEmail;}
    public void setPersonName(String newName){this.Name = newName;}

    public String getPersonName(){return this.Name;}
    public String getPersonIC(){return this.IC;}
    public int getPersonAge(){return this.Age;}
    public String getPersonAddress(){return this.Address;}
    public String getPersonPhone(){return this.Phone;}
    public String getEmailAddress(){return this.EmailAddress;}


    public int calcAge(){                //declare age
            int age;
            //get first 2 digit of ic
            String yearBorn = this.getPersonIC().substring(0,2);
            int yearIC = Integer.parseInt(yearBorn);
            //get the year born
            if(yearIC >= 0 && yearIC <= 23 ){
                yearIC = 2000 + yearIC;
            }
            else {
                yearIC = 1900 + yearIC;
            }
            //calculate the age by subtracting from current year
            age = YearMonth.now().getYear()-yearIC;
            //returning the age value
            setPersonAge(age);
            return age;

    }



}
