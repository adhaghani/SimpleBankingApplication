
public class Bank {
    
    //Operational

    private int totalDailyTransaction;
    private int totalDailyWithdraw;
    private int totalDailyDeposit;
    private int totalDailyTransfer;

    private double TotalWithdrawValue;
    private double TotalDepositValue;
    private double TotalTransactionValue;
    private double TotalTransferValue;

    // informational

    private int NumberOfAccount;
    private int NumberOfStaff;
    private int NumberOfBills;
    

    public Bank() {
        
        
        this.totalDailyWithdraw = 0;
        this.totalDailyDeposit = 0;
        this.totalDailyTransfer = 0;
        this.totalDailyTransaction = 0;
        
        this.TotalWithdrawValue = 0;
        this.TotalDepositValue = 0;
        this.TotalTransactionValue = 0;
        this.TotalTransferValue = 0;

        this.NumberOfBills = 0;
        this.NumberOfAccount = 0;
        this.NumberOfStaff = 0;

    }

    //setter 

    public void setTotalTransactionNumber(int newValue){this.totalDailyTransaction = newValue;}
    public void setTotalWithdrawNumber(int newValue){this.totalDailyWithdraw = newValue;}
    public void setTotalDepositNumber(int newValue){this.totalDailyDeposit = newValue;}
    public void setTotalTransferNumber(int newValue){this.totalDailyTransfer = newValue;}

    public void setTransactionValue(double newValue){this.TotalTransactionValue = newValue;}
    public void setWithdrawValue(double newValue){this.TotalWithdrawValue = newValue;}
    public void setDepositValue(double newValue){this.TotalDepositValue = newValue;}
    public void setTransferValue(double newValue){this.TotalTransferValue = newValue;}

    public void setNumberOfBills(int NewValue){this.NumberOfBills = NewValue;}
    public void setNumberOfAccount(int NewValue){this.NumberOfAccount = NewValue;}
    public void setNumberOfStaff(int NewValue){this.NumberOfStaff = NewValue;}

    //getter

    public int getTotalTransactionNumber(){return this.totalDailyTransaction;}
    public int getTotalDepositNumber(){return this.totalDailyDeposit;}
    public int getTotalWithdrawNumber(){return this.totalDailyWithdraw;}
    public int getTotalTransferNumber(){return this.totalDailyTransfer;}

    public double getTransactionValue(){return this.TotalTransactionValue;}
    public double getDepositValue(){return this.TotalDepositValue;}
    public double getWithdrawValue(){return this.TotalWithdrawValue;}
    public double getTransferValue(){return this.TotalTransferValue;}

    public int getNumberOfBill(){return this.NumberOfBills;}
    public int getNumberOfAccount(){return this.NumberOfAccount;}
    public int getNumberOfStaff(){return this.NumberOfStaff;}

    //processor 

    public void addNumberOfAccount(){
        this.NumberOfAccount += 1;
    }
    public void addNumberOfBill(){
        this.NumberOfBills += 1;
    }
    
    public void addNumberOfStaff(){
        this.NumberOfStaff += 1;
    }

    public void addTotalWithdrdawValue(double value){
        this.TotalWithdrawValue += value;
    }
    public void addTotalDepositValue(double value){
        this.TotalDepositValue += value;
    }
    public void addTotalTransferValue(double value){
        this.TotalTransferValue += value;
    }
    public void addTotalTransactionValue(double value){
        this.TotalTransactionValue += value;
    }

    public void addNumberOfWithdraw(){
        this.totalDailyWithdraw += 1;
    }
    public void addNumberOfDeposit(){
        this.totalDailyDeposit += 1;
    }
    public void addNumberOfTransfer(){
        this.totalDailyTransfer += 1;
    }
    public void addNumberOfTransaction(){
        this.totalDailyTransaction += 1;
    }

    public void Deposit(Account account, Double Amount){
        account.DepositMoney(Amount);
        addTotalDepositValue(Amount);
        addTotalTransactionValue(Amount);
        addNumberOfDeposit();
    }

    public void Withdraw(Account account, Double Amount){
        account.WithdrawMoney(Amount);
        addTotalWithdrdawValue(Amount);
        addTotalTransactionValue(Amount);
        addNumberOfWithdraw();
    }

    public void Transfer(Account account, Account TargetAccount, Double Amount){
        account.TransferMoney(Amount, TargetAccount);
        addTotalTransferValue(Amount);
        addTotalTransactionValue(Amount);
        addNumberOfTransfer();

    }


    //printer

    public void PrintStartupMenu(){
        System.out.println("+------------------------------------------------+");
        System.out.println("|                 BERNAMA BANK                   |");
        System.out.println("+------------------------------------------------+");
    }

    public void printAccountMenu(){
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
        System.out.println("|           [U] = USER ACCOUNT                   |");
        System.out.println("|           [S] = STAFF ACCOUNT                  |");
        System.out.println("|                                                |");
        System.out.println("|           [X] = EXIT SYSTEM                    |");
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
          System.out.print("| ACTION :  ");
    }
    public void printUserStaffAccountMenu(){
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
        System.out.println("|           [R] = REGUSTER NEW ACCOUNT           |");
        System.out.println("|           [L] = LOGIN EXISTING ACCOUNT         |");
        System.out.println("|                                                |");
        System.out.println("|           [X] = BACK TO MENU                   |");
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
          System.out.print("| ACTION :  ");
    }

    public void PrintTotalActivity(){
        System.out.println("+------------------------------------------------+");
        System.out.println("|            TOTAL TRANSACTION VALUE             |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
        System.out.println("| TOTAL WITHDRAW VALUE : RM" + this.TotalWithdrawValue);
        System.out.println("| TOTAL NUMBER OF WITHDRAW : " + this.totalDailyWithdraw + " Withdraws");  
        System.out.println("|                                                |");
        System.out.println("| TOTAL DEPOSIT VALUE : RM" + this.TotalDepositValue);
        System.out.println("| TOTAL NUMBER OF DEPOSIT : " + this.totalDailyDeposit + " Deposits");
        System.out.println("|                                                |");
        System.out.println("| TOTAL TRANSFER VALUE : RM" + this.TotalTransferValue);
        System.out.println("| TOTAL NUMBER OF TRANSFER : " + this.totalDailyTransfer + " Transfers");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
        System.out.println("| TOTAL TRANSACTION VALUE : RM" + this.TotalTransactionValue);
        System.out.println("| TOTAL NUMBER OF TRANSACTION : " + this.totalDailyTransaction + " Transactions");
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
        
    }

    public void PrintSystemInformation(){
        System.out.println("+------------------------------------------------+");
        System.out.println("|                 SYSTEM INFORMATION             |");
        System.out.println("+------------------------------------------------+");
        System.out.println("| NUMBER OF ACCOUNT : " + this.NumberOfAccount);
        System.out.println("| NUMBER OF BILL : " + this.NumberOfBills);
        System.out.println("| NUMBER OF STAFF : " + this.NumberOfStaff);
        System.out.println("+------------------------------------------------+");
    }
    

}
