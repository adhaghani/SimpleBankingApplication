public class Bill {
    
    //only needed for a transfer type of transaction;

    private String BillID, StaffID, FromAccountID, ToAccountID;


    private double Amount;

    private String DateGenerated;

    //2 names for approveName, either a BANK STAFF NAME or APPROVED BY COMPUTER;
    private String approveName;
    //3 types of Status, APPROVED, REJECTED, PENDING
    private String Status;
    //3 Types of Transaction, W, D, T
    private char TransactionType;
     // default constructor
     public Bill(){}

     // normal constructor;

     //transfer bill

     public Bill(String Date ,String Billid, String BankStaffID, String FromAccountDataID, String ToAccountDataID,String ApproveName,String Status, double Amount, char transactionType){
        this.DateGenerated = Date;
        
        this.BillID = Billid;
        this.StaffID = BankStaffID;
        this.FromAccountID = FromAccountDataID;
        this.ToAccountID = ToAccountDataID;
        this.approveName = ApproveName;
        this.Amount = Amount;

        this.Status = Status;

        this.TransactionType = transactionType;


     }

     //processes

     


     public void setBillID(String NewID){this.BillID = NewID;}
     public void setStaffID(String NewID){this.StaffID = NewID;}
     public void setFromAccountID(String NewID){this.FromAccountID = NewID;}
     public void setToAccountID(String NewID){this.ToAccountID = NewID;}
     public void setApproveName(String NewName){this.approveName = NewName;}
     public void setApproved(String NewStatus){this.Status = NewStatus;}
     public void setAmount(double NewAmount){this.Amount = NewAmount;}
     public void setTransactionType(char NewType){this.TransactionType = NewType;}

     //getter

     public String getDate(){return this.DateGenerated;}

     public String getBillID(){return this.BillID;}
     public String getStaffID(){return this.StaffID;}
     public String getFromAccountID(){return this.FromAccountID;}
     public String getToAccountID(){return this.ToAccountID;}
     public String getApproveName(){return this.approveName;}
     public String getApproved(){return this.Status;}
     public double getAmount(){return this.Amount;}
     public char getTransactionType(){return this.TransactionType;}


    // determine transaction type, add a string to print what kind of type it is
    public String determineTransactionType(){
        String Type;
        if(this.TransactionType == 'W'){
           Type = "WITHDRAW";
        }
        else if(this.TransactionType == 'D'){
            Type = "DEPOSIT";
        }
        else if(this.TransactionType == 'T'){
            Type = "TRANSFER";
        }
        else{
            Type = "NULL";
        }
        return Type;
    }

    public boolean requiredApproval(){
        if(Amount > 10000){
            this.Status = "PENDING";
            return true;
        }
        else{;
            return false;
        }
    }
    

    public String PrintSimpleBill(){
        String info = "+------------------------------------------------+"+
                    "\n|" +
                    "\n| BILL ID        : " + this.BillID +
                    "\n|" +
                    "\n| DATE GENERATED : " + this.DateGenerated +
                    "\n| AMOUNT         : " + this.Amount +
                    "\n|" +
                    "\n| STATUS         : " + this.Status +
                    "\n| APPROVED BY    : " + this.approveName +
                    "\n|" +
                    "\n| TYPE           : " + this.determineTransactionType() +
                    "\n|" +
                    "\n+------------------------------------------------+";

        return info;
    }

    public String PrintTransationBill(){
        String info = "+------------------------------------------------+"+
                    "\n|" +
                    "\n| BILL ID         : " + this.BillID +
                    "\n|" +
                    "\n| FROM ACCOUNT ID : " + this.FromAccountID +
                    "\n| TO ACCOUNT ID   : " + this.ToAccountID +
                    "\n|" +
                    "\n| DATE GENERATED  : " + this.DateGenerated +
                    "\n| AMOUNT          : " + this.Amount +
                    "\n|" +
                    "\n| STATUS          : " + this.Status +
                    "\n| APPROVED BY     : " + this.approveName +
                    "\n|" +
                    "\n| TYPE            : " + this.determineTransactionType() +
                    "\n|" +
                    "\n+------------------------------------------------+";

        return info;
    }

    public String printData(){
        if(this.TransactionType == 'W' || this.TransactionType == 'D'){
            String info = 
                      "| Bill ID                 : " + this.BillID +
                    "\n| Account ID              : " + this.FromAccountID +
                    "\n| Date Generated          : " + this.DateGenerated +
                    "\n| Amount                  : " + this.Amount +
                    "\n| Status                  : " + this.Status +
                    "\n| Type                    : " + this.determineTransactionType();
        return info;
        }
        else{
            String info = 
                      "| Bill ID                 : " + this.BillID +
                    "\n| sender Account ID       : " + this.FromAccountID +
                    "\n| receiver Account ID     : " + this.ToAccountID +
                    "\n| Date Generated          : " + this.DateGenerated +
                    "\n| Amount                  : " + this.Amount +
                    "\n| Status                  : " + this.Status +
                    "\n| Type                    : " + this.determineTransactionType();
        return info;
        }
        
    }

    public String WriteString(){
        String info = this.DateGenerated + ";"+this.BillID+";" + this.StaffID + ";" + this.FromAccountID + ";" + this.ToAccountID + ";"+this.approveName+";"+this.Status+";" + this.Amount + ";" + this.TransactionType;
        return info;
     }






}
