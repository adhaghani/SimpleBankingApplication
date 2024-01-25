public class Account extends Person {

    //attribtues

    private String AccountID;
    private String AccountUserName;
    private String AccountPassword;
    private String accountSecureWord;

    private double AccountBalance;
    private double MonthlySpendingLimit;
    private double currentMonthlySpendature;
    
    private double currentBalance;

    private int NumberOfTransactions;

    private boolean isBlocked;

    //constructor

    public Account(){}

    public Account(String name, String address, String ic, String phone, String EmailAddress, String id, String userName, String password, String secureWord, double balance, double currentbalance,double MonthlySpendingLimit, double currentMonthlySpendature, int transactionNo, boolean isblocked){
        super(name, address, ic, phone, EmailAddress);
        this.AccountID = id;
        this.AccountUserName = userName;
        this.AccountPassword = password;
        this.accountSecureWord = secureWord;

        this.currentBalance = currentbalance;
        this.AccountBalance = balance;
        this.MonthlySpendingLimit = MonthlySpendingLimit;
        this.currentMonthlySpendature = currentMonthlySpendature;

        this.NumberOfTransactions = transactionNo;

        this.isBlocked = false;
    }

    //setter 
    public void setAccountID(String NewID){this.AccountID = NewID;}
    public void setAccountUserName(String NewUserName){this.AccountUserName = NewUserName;}
    public void setAccountPassword(String NewPassword){this.AccountPassword =  NewPassword;}
    public void setAccountSecureWord(String NewSecureWord){this.accountSecureWord = NewSecureWord;}
    public void setAccountBalance(Double NewBalance){this.AccountBalance = NewBalance;}
    public void setNumberOfTransaction(int NewTransactionNumber){this.NumberOfTransactions =  NewTransactionNumber;}
    public void setAccountStatus(boolean NewAccountStatus){this.isBlocked =NewAccountStatus;}
    public void setMonthlySpendingLimit(double newLimit){this.MonthlySpendingLimit = newLimit;}
    public void setCurrentMonthlySpendature(double newSpending){this.currentMonthlySpendature = newSpending;}
    public void setCurrentBalance(double newBalance){this.currentBalance = newBalance;}

    //getter

    public String getAccountID(){return this.AccountID;}
    public String getAccountUserName(){return this.AccountUserName;}
    public String getAccountPassword(){return this.AccountPassword;}
    public String getAccountSecureWord(){return this.accountSecureWord;}
    public double getAccountBalance(){return this.AccountBalance;}
    public double getMonthlySpendingLimit(){return this.MonthlySpendingLimit;}
    public double getMonthlySpendature(){return this.currentMonthlySpendature;}
    public int getNumberOfTransaction(){return this.NumberOfTransactions;}
    public boolean getAccountStatus(){return this.isBlocked;}
    public double getCurrentBalance(){return this.currentBalance;}

    //processor

    public void BlockAccount(){
        this.isBlocked = true;
    }
    public void UnBlockedAccount(){
        this.isBlocked = false;
    }

    public void addNewTransaction(){
        this.NumberOfTransactions += 1;
    }

    public void DepositMoney(double Amount){
        if(isBlocked == false){
            this.AccountBalance += Amount;
            this.currentBalance += Amount;
            this.addNewTransaction();
        }
        if(isBlocked == true){
            System.out.println("+------------------------------------------------+");
            System.out.println("|  ACCOUNT IS BLOCKED. UNABLE TO DO TRANSACTION  |");
            System.out.println("+------------------------------------------------+");
        }    
    }
    public void updateUseableBalance(double Amount){
        this.currentBalance -= Amount;
    }
    public void UpdateBalanceAfterApproval(){
        this.AccountBalance = currentBalance;
    }
    public void addMonthlyExpendature(Double Amount){
        this.currentMonthlySpendature += Amount;
    }
    public void WithdrawMoney(double Amount){
        if(isBlocked == false && (this.AccountBalance - Amount) >= 0 && Amount <= MonthlySpendingLimit){
            this.AccountBalance -= Amount;
            this.currentBalance -= Amount;
            this.addNewTransaction();
            addMonthlyExpendature(Amount);
        }
        if(isBlocked == true){
            System.out.println("+------------------------------------------------+");
            System.out.println("|  ACCOUNT IS BLOCKED. UNABLE TO DO TRANSACTION  |");
            System.out.println("+------------------------------------------------+");
        }
        if(AccountBalance - Amount < 0){
            System.out.println("+------------------------------------------------+");
            System.out.println("|  INSUFFICIENT FUNDS. UNABLE TO DO TRANSACTION  |");
            System.out.println("+------------------------------------------------+");
        }
        if(Amount > MonthlySpendingLimit){
            System.out.println("+------------------------------------------------+");
            System.out.println("| EXCEED MONTHLY LIMIT. UNABLE TO DO TRANSACTION |");
            System.out.println("+------------------------------------------------+");
        }
        
    }

    public void TransferMoney(Double Amount, Account TargetAccount){
        if(isBlocked == false && (this.AccountBalance - Amount) >= 0 && Amount <= MonthlySpendingLimit){
        this.WithdrawMoney(Amount);
        TargetAccount.DepositMoney(Amount);
        this.addMonthlyExpendature(Amount);
        }
        if(isBlocked == true){
            System.out.println("+------------------------------------------------+");
            System.out.println("|  ACCOUNT IS BLOCKED. UNABLE TO DO TRANSACTION  |");
            System.out.println("+------------------------------------------------+");
        }
        if(AccountBalance - Amount < 0){
            System.out.println("+------------------------------------------------+");
            System.out.println("|  INSUFFICIENT FUNDS. UNABLE TO DO TRANSACTION  |");
            System.out.println("+------------------------------------------------+");
        }
        if(Amount > MonthlySpendingLimit){
            System.out.println("+------------------------------------------------+");
            System.out.println("| EXCEED MONTHLY LIMIT. UNABLE TO DO TRANSACTION |");
            System.out.println("+------------------------------------------------+");
        }
    }

    public String toString(){
        String info = "+---------------------------------+" +
                    "\n|       PERSONAL INFORMATION      |" +
                    "\n+---------------------------------+" +
                    "\n| Full Name             : " + this.Name +
                    "\n| Home Address          : " + this.Address +
                    "\n| IC Number             : " + this.IC +
                    "\n| Phone Number          : " + this.Phone +
                    "\n| Email Address         : " + this.EmailAddress       +
                    "\n+---------------------------------+" +
                    "\n|       ACCOUNT INFORMATION       |" +
                    "\n+---------------------------------+" +
                    "\n| Account ID            : " + this.AccountID +
                    "\n| Account Username      : " + this.AccountUserName +
                    "\n| Account SecureWord    : " + this.accountSecureWord +
                    "\n| Account Status        : " + this.isBlocked +
                    "\n|" +
                    "\n| Balance               : " + this.AccountUserName +
                    "\n| Number of Transaction : " + this.accountSecureWord +
                    "\n+---------------------------------+";

                    return info;
    }

    public String PrintAccount(){
        String info =
                    "+---------------------------------+" +
                    "\n|       ACCOUNT INFORMATION       |" +
                    "\n+---------------------------------+" +
                    "\n| Account ID            : " + this.AccountID +
                    "\n| Account Username      : " + this.AccountUserName +
                    "\n| Account SecureWord    : " + this.accountSecureWord +
                    "\n| Account Status        : " + this.isBlocked +
                    "\n|" +
                    "\n| Balance               : " + this.AccountUserName +
                    "\n| Number of Transaction : " + this.accountSecureWord +
                    "\n+---------------------------------+";

                    return info;
    }

    public String PrintPersonalInformation(){
        String info = "+---------------------------------+" +
                    "\n|       PERSONAL INFORMATION      |" +
                    "\n+---------------------------------+" +
                    "\n| Full Name             : " + this.Name +
                    "\n| Home Address          : " + this.Address +
                    "\n| IC Number             : " + this.IC +
                    "\n| Phone Number          : " + this.Phone+
                    "\n| Email Address         : " + this.EmailAddress+
                    "\n+---------------------------------+";

                    return info;
    }

    public String WriteString(){
        String info = this.Name + ";" + this.Address + ";" + this.IC + ";" + this.Phone + ";"+this.EmailAddress +";" + this.AccountID + ";" + this.AccountUserName + ";" + this.AccountPassword + ";" + this.accountSecureWord + ";" + this.AccountBalance +";"+this.currentBalance + ";" + this.MonthlySpendingLimit +";"+this.currentMonthlySpendature +";" + this.NumberOfTransactions + ";" + this.isBlocked;
        return info;
    }
}
