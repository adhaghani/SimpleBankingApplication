import java.util.*;
import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BankQueueApp {
    
    private static final String STAFF_FILE = "Staff.txt";
    private static final String ACCOUNT_FILE = "Account.txt";
    private static final String BILL_FILE = "Bill.txt";

    private static String CurrentUserID = null;

    static boolean shouldbreak = false;
    

    public static void main(String[] args) {

        Queue BankStaffAccountLL = new Queue();
        Queue CustomerAccountLL = new Queue();
        Queue BillLL = new Queue();
        Bank bank = new Bank();
    
    
        Scanner input = new Scanner(System.in);
        char answer = 'N';
    
            readStaff(bank, BankStaffAccountLL);
            readAccount(bank, CustomerAccountLL);
            readBill(bank, BillLL);
    
    
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            do {
                bank.PrintStartupMenu();
                bank.printAccountMenu();
                String UserAnswer = input.next();
                answer = UserAnswer.toUpperCase().charAt(0);
                if(answer == 'X'){
                    break;
                }
                else{
    
                    if(answer == 'U' || answer == 'S'){
                        char UserType = answer;
                        bank.printUserStaffAccountMenu();
                        UserAnswer = input.next();
                        answer = UserAnswer.toUpperCase().charAt(0);
                        if(answer == 'R'){
                            RegisterUser(UserType, BankStaffAccountLL, CustomerAccountLL, bank);
                        }
                        else if(answer == 'L'){
                            if(LoginUser(UserType, BankStaffAccountLL, CustomerAccountLL, bank) == true ){
                                formattedDateTime = currentDateTime.format(formatter);
                                if(UserType == 'U'){
                                    UserMenu(formattedDateTime,BillLL,BankStaffAccountLL,CustomerAccountLL, bank,CurrentUserID);
                                    writeAccount(bank, CustomerAccountLL);
                                }
                                else if(UserType == 'S'){
                                    StaffMenu(formattedDateTime,BillLL,BankStaffAccountLL, CustomerAccountLL, bank,CurrentUserID);
                                }
                            }
                        }
                        else if(answer == 'X'){
            
                        }
                        else{
                            System.out.println("| INVALID INPUT. TRY AGAIN.");
                        }
                    }
                }
    
                
            } while (answer != 'Y');
        }
           

public static void readStaff(Bank Bank, Queue BankStaffAccountLL) {
    try{
        File StaffImport = new File(STAFF_FILE);
        FileReader StaffReader = new FileReader(StaffImport);
        BufferedReader bufferRead = new BufferedReader(StaffReader);

        String line;
        while((line = bufferRead.readLine()) != null){
            StringTokenizer tokenizer = new StringTokenizer(line,";");
            if(tokenizer.countTokens() == 9){
                Bank.addNumberOfStaff();
                String name = tokenizer.nextToken();
                String address = tokenizer.nextToken();
                String ic = tokenizer.nextToken();
                String phone = tokenizer.nextToken();
                String email = tokenizer.nextToken();
                String id = tokenizer.nextToken();
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                int transactionNum = Integer.parseInt(tokenizer.nextToken());
                BankStaff temporaryStaff = new BankStaff(name, address, ic, phone,email, id, username, password);
                Bank.addNumberOfStaff();
                BankStaffAccountLL.enqueue(temporaryStaff);
            }
        }

}   
catch(Exception ex){}
}
public static void readAccount(Bank Bank, Queue CustomerAccountLL) {
    try{
        File AccountImport = new File(ACCOUNT_FILE);
        FileReader AccountReader = new FileReader(AccountImport);
        BufferedReader bufferRead = new BufferedReader(AccountReader);

        String line;
        while((line = bufferRead.readLine()) != null){
            StringTokenizer tokenizer = new StringTokenizer(line,";");
            if(tokenizer.countTokens() == 15){
                Bank.addNumberOfAccount();
                String name = tokenizer.nextToken();
                String address = tokenizer.nextToken();
                String ic = tokenizer.nextToken();
                String phone = tokenizer.nextToken();
                String email = tokenizer.nextToken();
                String id = tokenizer.nextToken();
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                String SecureWord = tokenizer.nextToken();
                Double Balance = Double.parseDouble(tokenizer.nextToken());
                Double CurrentBalance = Double.parseDouble(tokenizer.nextToken());
                Double MonthlySpendingLimit = Double.parseDouble(tokenizer.nextToken());
                Double MonthlySpendature = Double.parseDouble(tokenizer.nextToken());
                int NumberOfTransaction = Integer.parseInt(tokenizer.nextToken());
                Boolean isBlocked = Boolean.parseBoolean(tokenizer.nextToken());
                Account temp = new Account(name, address, ic, phone,email, id, username, password, SecureWord, Balance,CurrentBalance,MonthlySpendingLimit,MonthlySpendature, NumberOfTransaction, isBlocked);
                Bank.addNumberOfAccount();
                CustomerAccountLL.enqueue(temp);
            }
        }

}   
catch(Exception ex){}
}
public static void readBill(Bank Bank, Queue BillLL) {
    try{
        File BillImport = new File(BILL_FILE);
        FileReader BillReader = new FileReader(BillImport);
        BufferedReader bufferRead = new BufferedReader(BillReader);

        String line;
        while((line = bufferRead.readLine()) != null){
            StringTokenizer tokenizer = new StringTokenizer(line,";");
            if(tokenizer.countTokens() == 9){
                Bank.addNumberOfBill();
                String date = tokenizer.nextToken();
                String BilLID = tokenizer.nextToken();
                String bankStaffID = tokenizer.nextToken();
                String FromAccountID = tokenizer.nextToken();
                String ToAccountID = tokenizer.nextToken();
                String approveName = tokenizer.nextToken();
                String Status =  tokenizer.nextToken();
                double amount = Double.parseDouble(tokenizer.nextToken());
                char transaction = tokenizer.nextToken().charAt(0);
                Bill temp = new Bill(date,BilLID, bankStaffID, FromAccountID, ToAccountID,approveName,Status, amount, transaction);
                Bank.addNumberOfBill();
                Bank.addTotalTransactionValue(transaction);
                if(transaction == 'D'){
                    Bank.addNumberOfDeposit();
                    Bank.addTotalDepositValue(amount);
                }
                else if(transaction == 'W'){
                    Bank.addNumberOfWithdraw();
                    Bank.addTotalWithdrdawValue(amount);
                }
                else if(transaction == 'T'){
                    Bank.addNumberOfTransfer();
                    Bank.addTotalTransferValue(amount);
                }
                BillLL.enqueue(temp);
            }
        }

}   
catch(Exception ex){}
}

public static void writeStaff(Bank Bank, Queue BankStaffAccountLL){

    try{
        // EXPORT STAFF
        File ExportStaff = new File(STAFF_FILE);
        FileWriter WriteStaff = new FileWriter(ExportStaff);
        PrintWriter PrintStaff = new PrintWriter(WriteStaff);
        Queue TemporaryStaffLL = new Queue();
        while(!BankStaffAccountLL.isEmpty()){
            BankStaff temp = (BankStaff) BankStaffAccountLL.dequeue();
            TemporaryStaffLL.enqueue(temp);
            PrintStaff.println(temp.WriteString());
        }
       while(!TemporaryStaffLL.isEmpty()){
            BankStaffAccountLL.enqueue(TemporaryStaffLL.dequeue());
       }
        PrintStaff.close();
}   
catch(Exception ex){}

}
public static void writeAccount(Bank Bank, Queue CustomerAccountLL){

    try{
        // EXPORT ACOUNT
        File ExportAccount = new File(ACCOUNT_FILE);
        FileWriter WriteAccount = new FileWriter(ExportAccount);
        PrintWriter PrintAccount = new PrintWriter(WriteAccount);
        Queue TemporaryAccountLL = new Queue();
        while(!CustomerAccountLL.isEmpty()){
            Account temp = (Account) CustomerAccountLL.dequeue();
            TemporaryAccountLL.enqueue(temp);
            PrintAccount.println(temp.WriteString());
            
        }
       while(!TemporaryAccountLL.isEmpty()){
            CustomerAccountLL.enqueue(TemporaryAccountLL.dequeue());
       }
        PrintAccount.close();
}   
catch(Exception ex){}

}
public static void WriteBill(Bank Bank, Queue BillLL) {
    try{
        File ExportBill = new File(BILL_FILE);
        FileWriter WriteBill = new FileWriter(ExportBill);
        PrintWriter PrintBill = new PrintWriter(WriteBill);
        Queue TemporaryBillLL = new Queue();
        while(!BillLL.isEmpty()){
            Bill temp = (Bill) BillLL.dequeue();
            TemporaryBillLL.enqueue(temp);
            PrintBill.println(temp.WriteString());
        }
       while(!TemporaryBillLL.isEmpty()){
            BillLL.enqueue(TemporaryBillLL.dequeue());
       }
        PrintBill.close();

}   
catch(Exception ex){}
}

public static void RegisterUser(char UserRegisterType, Queue BankStaffAccountLL, Queue CustomerAccountLL, Bank bank){
    Scanner Input = new Scanner(System.in);
    Scanner In = new Scanner(System.in);
    if (UserRegisterType == 'S'){
     //enter basic user information
        System.out.println("+------------------------------------------------+");
        System.out.println("|             REGISTER A NEW STAFF               |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
          System.out.print("| ENTER YOUR FULL NAME    : ");
        String name = In.nextLine();
          System.out.print("| ENTER YOUR ADDRESS      : ");
        String address = In.nextLine();
          System.out.print("| ENTER YOUR IC           : ");
        String ic = In.nextLine();
        System.out.print("| ENTER YOUR PHONE NUMBER   : ");
        String phone = In.nextLine();
        System.out.print("| ENTER YOUR EMAIL ADDRESS  : ");
        String email = In.nextLine();
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
        //verify in CustomerAccountLL to see if the user with the same IC number exist or not
        if(IsAccountAlreadyExist(BankStaffAccountLL, CustomerAccountLL, ic) == true){
            System.out.println("|        STAFF ACCOUNT ALREADY EXISTED           |");
            System.out.println("+------------------------------------------------+");
        }
        //If not proceed with creating an account for the user
        else {
        String accountID = generateUniqueStaffID(1);
        System.out.println("+------------------------------------------------+");
        System.out.println("|             REGISTER A NEW STAFF               |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
          System.out.println("| ACCOUNT NUMBER GIVEN        : " + accountID);
          System.out.print("| ENTER ACCOUNT USERNAME      : ");
        String Username = In.nextLine();
          System.out.print("| CREATE ACCOUNT PASSWORD     : ");
        String password = In.nextLine();
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
        int numoftransaction = 0;
        BankStaff temp = new BankStaff(name, address, ic, phone,email, accountID, Username, password);
        BankStaffAccountLL.enqueue(temp);
        writeStaff(bank, BankStaffAccountLL);
        System.out.println("|             STAFF CREATED SUCCESSFULLY         |");
        System.out.println("+------------------------------------------------+");
        }
    }
    else if(UserRegisterType == 'U'){
        //enter basic user information
        System.out.println("+------------------------------------------------+");
        System.out.println("|             REGISTER AN ACCOUNT                |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
          System.out.print("| ENTER YOUR FULL NAME    : ");
        String name = In.nextLine();
          System.out.print("| ENTER YOUR ADDRESS      : ");
        String address = In.nextLine();
          System.out.print("| ENTER YOUR IC           : ");
        String ic = In.nextLine();
          System.out.print("| ENTER YOUR PHONE NUMBER : ");
        String phone = In.nextLine();
        System.out.print("| ENTER YOUR EMAIL ADDRESS  : ");
        String email = In.nextLine();
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
        //verify in CustomerAccountLL to see if the user with the same IC number exist or not
        if(IsAccountAlreadyExist(BankStaffAccountLL, CustomerAccountLL, ic) == true){
            System.out.println("|      USER WITH THE SAME IC HAS REGISTERED      |");
            System.out.println("+------------------------------------------------+");
        }
        //If not proceed with creating an account for the user
        else {
        String accountID = generateUniqueUserID(1);
        System.out.println("+------------------------------------------------+");
        System.out.println("|             REGISTER AN ACCOUNT                |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
          System.out.println("| ACCOUNT NUMBER GIVEN        : " + accountID);
          System.out.print("| ENTER ACCOUNT USERNAME      : ");
        String Username = In.nextLine();
          System.out.print("| CREATE ACCOUNT PASSWORD     : ");
        String password = In.nextLine();
          System.out.print("| CREATE ACCOUNT SECUREWORD   : ");
        String secureword = In.nextLine();
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
        double MonthlySpendingLimitNewUser = 200000.00;
        double AccountBalance = 0;
        double CurrentBalance = 0;
        double MonthlySpendature = 0;
        int numoftransaction = 0;
        boolean AccountBlockedStatus = false;
        Account newAccount = new Account(name, address, ic, phone,email, accountID, Username, password, secureword, AccountBalance,CurrentBalance, MonthlySpendingLimitNewUser, MonthlySpendature, numoftransaction, AccountBlockedStatus);
        CustomerAccountLL.enqueue(newAccount);
        writeAccount(bank, CustomerAccountLL);
        System.out.println("|             ACCOUNT CREATED SUCCESSFULLY       |");
        System.out.println("+------------------------------------------------+");
        }
        
        
        
    }

}
public static boolean LoginUser(char UserRegisterType, Queue BankStaffAccountLL, Queue CustomerAccountLL, Bank bank){
    
    Scanner In = new Scanner(System.in);
    Scanner Input = new Scanner(System.in);

    Queue tempAccountLL = new Queue();
    Queue tempStaffLL = new Queue();

    boolean Successful = false;
    
    int Chance = 3;
    while (Chance != 0){
        System.out.println("+------------------------------------------------+");
        System.out.println("|                 LOGIN ACCOUNT                  |");
        System.out.println("+------------------------------------------------+");
        System.out.print("| ENTER YOUR USERNAME : ");
        String Username = Input.nextLine();
        System.out.print("| ENTER YOUR PASSWORD : ");
        String Password = Input.nextLine();
        if(UserRegisterType == 'S'){
            while(!BankStaffAccountLL.isEmpty()){
                BankStaff temp = (BankStaff) BankStaffAccountLL.dequeue();
                tempStaffLL.enqueue(temp);
                if(temp.getStaffUserName().equals(Username) && temp.getStaffPassword().equals(Password)){
                    Successful = true;
                    Chance = 0;
                    System.out.println("+------------------------------------------------+");
                    System.out.println("|               LOGIN SUCCESSFULLY               |");
                    System.out.println("+------------------------------------------------+");
                    shouldbreak = false;
                    CurrentUserID = temp.getStaffID();
                }
                
            }
            if(!Successful){
                    System.out.println("+------------------------------------------------+");
                    System.out.println("|      WRONG USERNAME OR PASSWORD, TRY AGAIN     |");
                      System.out.println("|                 TRY " + (Chance-1) + " MORE TIME                |");
                    System.out.println("+------------------------------------------------+");
                    Chance--;
                
            }
            while(!tempStaffLL.isEmpty()){
                BankStaffAccountLL.enqueue(tempStaffLL.dequeue());
            }
        }

    else if(UserRegisterType == 'U'){
        while(!CustomerAccountLL.isEmpty()){
            Account temp = (Account) CustomerAccountLL.dequeue();
            tempAccountLL.enqueue(temp);
            if(temp.getAccountUserName().equals(Username) && temp.getAccountPassword().equals(Password)){
                Successful = true;
                
                Chance = 0;
                System.out.println("+------------------------------------------------+");
                System.out.println("|               LOGIN SUCCESSFULLY               |");
                System.out.println("+------------------------------------------------+");
                CurrentUserID = temp.getAccountID();
                break;
            }
        }
        if(!Successful){
                    System.out.println("+------------------------------------------------+");
                    System.out.println("|      WRONG USERNAME OR PASSWORD, TRY AGAIN     |");
                      System.out.println("|                 TRY " + (Chance-1) + " MORE TIME                |");
                    System.out.println("+------------------------------------------------+");
                    Chance--;
            }
        while(!tempAccountLL.isEmpty()){
            CustomerAccountLL.enqueue(tempAccountLL.dequeue());
        }
    }
}

    return Successful;
    
}
public static boolean IsAccountAlreadyExist(Queue BankStaffAccountLL, Queue CustomerAccountLL, String IC){

    Queue tempAccountLL = new Queue();
    Queue tempStaffLL = new Queue();

    while(!BankStaffAccountLL.isEmpty()){
        BankStaff temp = (BankStaff) BankStaffAccountLL.dequeue();
        tempStaffLL.enqueue(temp);
        if ( temp.getPersonIC().equals(IC) ) {
            return true;
        }
    }

    while(!CustomerAccountLL.isEmpty()){
        Account temp = (Account) CustomerAccountLL.dequeue();
        tempAccountLL.enqueue(temp);
        if ( temp.getPersonIC().equals(IC) ) {
            return true;
        }
        
    }
    
    while(!tempAccountLL.isEmpty()){
        CustomerAccountLL.enqueue(tempAccountLL.dequeue());
    }
    while(!tempStaffLL.isEmpty()){
        BankStaffAccountLL.enqueue(tempStaffLL.dequeue());
    }

    return false;

}
public static String generateUniqueUserID(int count) {
    // Create a Random object
    Random random = new Random();

    // Create a Set to store unique positive integers
    Set<Integer> uniquePositiveIntegers = new HashSet<>();

    // Generate unique positive random integers
    while (uniquePositiveIntegers.size() < count) {
        int randomNumber = random.nextInt(Integer.MAX_VALUE);
        if (randomNumber > 0) {
            uniquePositiveIntegers.add(randomNumber);
        }
    }

    // Convert the Set to a string with "U" prefixed to each number
    StringBuilder stringBuilder = new StringBuilder();
    for (int number : uniquePositiveIntegers) {
        stringBuilder.append("U").append(number).append(" ");
    }
    String uniqueRandomPositiveIntegers = stringBuilder.toString().trim();

    return uniqueRandomPositiveIntegers;
}
public static String generateUniqueStaffID(int count) {
    // Create a Random object
    Random random = new Random();

    // Create a Set to store unique positive integers
    Set<Integer> uniquePositiveIntegers = new HashSet<>();

    // Generate unique positive random integers
    while (uniquePositiveIntegers.size() < count) {
        int randomNumber = random.nextInt(Integer.MAX_VALUE);
        if (randomNumber > 0) {
            uniquePositiveIntegers.add(randomNumber);
        }
    }

    // Convert the Set to a string with "U" prefixed to each number
    StringBuilder stringBuilder = new StringBuilder();
    for (int number : uniquePositiveIntegers) {
        stringBuilder.append("S").append(number).append(" ");
    }
    String uniqueRandomPositiveIntegers = stringBuilder.toString().trim();

    return uniqueRandomPositiveIntegers;
}
public static String generateUniqueBillID(int count) {
    // Create a Random object
    Random random = new Random();

    // Create a Set to store unique positive integers
    Set<Integer> uniquePositiveIntegers = new HashSet<>();

    // Generate unique positive random integers
    while (uniquePositiveIntegers.size() < count) {
        int randomNumber = random.nextInt(Integer.MAX_VALUE);
        if (randomNumber > 0) {
            uniquePositiveIntegers.add(randomNumber);
        }
    }

    // Convert the Set to a string with "U" prefixed to each number
    StringBuilder stringBuilder = new StringBuilder();
    for (int number : uniquePositiveIntegers) {
        stringBuilder.append("B").append(number).append(" ");
    }
    String uniqueRandomPositiveIntegers = stringBuilder.toString().trim();

    return uniqueRandomPositiveIntegers;
}
public static void UserMenu(String DateAndTime,Queue BillLL,Queue BankStaffAccountLL,Queue CustomerAccountLL, Bank bank, String CurrentUserID){

    Scanner input = new Scanner(System.in);
    char userOption = 'A';

    Queue tempAccountLL = new Queue();

    

    Account DummyAccount = new Account();
    while(!CustomerAccountLL.isEmpty()){
        Account temp = (Account) CustomerAccountLL.dequeue();
        tempAccountLL.enqueue(temp);
        if(temp.getAccountID().equals(CurrentUserID)){
            DummyAccount = temp;
        }
    }
    while(!tempAccountLL.isEmpty()){
        CustomerAccountLL.enqueue(tempAccountLL.dequeue());
    }
    

           while (userOption != 'X') {
            if(shouldbreak == true){
                break;
            }
            System.out.println("+------------------------------------------------+");
            System.out.println("|             WELCOME TO BERNAMA BANK            |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|                                                |");
            System.out.println("|           [T] = MAKE A TRANSACTION             |");
            System.out.println("|           [H] = CHECK TRANSACTION HISTORY      |");
            System.out.println("|           [B] = CHECK ACCOUNT BALANCE          |");
            System.out.println("|           [S] = ACCOUNT SETTINGS               |");
            System.out.println("|                                                |");
            System.out.println("|           [X] = LOG OUT                        |");
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");
            System.out.print("| ACTION :  ");
            userOption = input.next().toUpperCase().charAt(0);
            
                switch (userOption) {
            case 'T':
                 AccountTransactionMenu(DateAndTime,BillLL,CustomerAccountLL,DummyAccount, bank, 'U');
                 writeAccount(bank, CustomerAccountLL);
                break;
            case 'H':
                AccountTransactionHistory(DummyAccount, BillLL);
                writeAccount(bank, CustomerAccountLL);
                break;
            case 'B':
                AccountBalance(DummyAccount, bank);
                break;
            case 'S':
                AccountSettings(userOption,CustomerAccountLL,BankStaffAccountLL,null,DummyAccount,bank,'U');
                writeAccount(bank, CustomerAccountLL);
                break;
            case 'X':
                System.out.println("+------------------------------------------------+");
                System.out.println("|                LOG OUT SUCCESSFULLY             |");
                System.out.println("+------------------------------------------------+");
                break;
            default:
                System.out.println("+------------------------------------------------+");
                System.out.println("|                INVALID INPUT                   |");
                System.out.println("+------------------------------------------------+");
                }
                } 
            }
public static void AccountSettings(char Option,Queue CustomerAccountLL,Queue BankStaffAccountLL,BankStaff StaffAccount,Account Account, Bank bank , char UserType){

    Scanner input = new Scanner(System.in);
    Scanner InputUser = new Scanner(System.in);
    char AccountSettingsOption = 'A';
    
    while (AccountSettingsOption != 'X') {
        System.out.println("+------------------------------------------------+");
        System.out.println("|               ACCOUNT SETTINGS                 |");
        System.out.println("+------------------------------------------------+");
        if(UserType == 'S'){
            int chance = 3;
            System.out.println("+------------------------------------------------+");
            System.out.println("|                                                |");
            System.out.println("|           [N] = UPDATE PERSONAL INFORMATION    |");
            System.out.println("|                                                |");
            System.out.println("|           [U] = UPDATE ACCOUNT USERNAME        |");
            System.out.println("|           [P] = UPDATE ACCOUNT PASSWORD        |");
            System.out.println("|                                                |");
            System.out.println("|           [D] = CLOSE ACCOUNT                  |");
            System.out.println("|                                                |");
            System.out.println("|           [X] = EXIT                           |");
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");
            System.out.print("| ACTION :  ");
            AccountSettingsOption = input.next().toUpperCase().charAt(0);
            if(AccountSettingsOption == 'X'){
                break;
            }
            else if(AccountSettingsOption == 'N'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|          UPDATE PERSONAL INFORMATION           |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|           [N] = UPDATE FULL NAME               |");
                        System.out.println("|           [A] = UPDATE HOME ADDRESS            |");
                        System.out.println("|           [E] = UPDATE EMAIL ADDRESS           |");
                        System.out.println("|           [O] = UPDATE PHONE NUMBER            |");
                        System.out.println("|                                                |");
                        System.out.println("|           [Z] = GO BACK                        |");
                        System.out.println("+------------------------------------------------+");
                        System.out.print("| ACTION :  ");
                        AccountSettingsOption = input.next().toUpperCase().charAt(0);
                        if(AccountSettingsOption == 'A'){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|           UPDATE HOME ADDRESS                 |");
                            System.out.println("+------------------------------------------------+");
                            System.out.println("| CURRENT HOME ADDRESS : " + StaffAccount.getPersonAddress());
                            System.out.println("+------------------------------------------------+");
                            System.out.print("| ENTER NEW ADDRESS     : ");
                            String newAddress = InputUser.nextLine();
                            StaffAccount.setPersonAddress(newAddress);
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|       HOME ADDRESS UPDATED SUCCESSFULLY        |");
                            System.out.println("+------------------------------------------------+");
                            writeStaff(bank, BankStaffAccountLL);


                        }
                        else if(AccountSettingsOption == 'E'){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|           UPDATE EMAIL ADDRESS                 |");
                            System.out.println("+------------------------------------------------+");
                            System.out.println("| CURRENT EMAIL ADDRESS : " + StaffAccount.getEmailAddress());
                            System.out.println("+------------------------------------------------+");
                            System.out.print("| ENTER NEW EMAIL ADDRESS     : ");
                            String newAddress = InputUser.nextLine();
                            StaffAccount.setEmailAddress(newAddress);
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|       EMAIL ADDRESS UPDATED SUCCESSFULLY       |");
                            System.out.println("+------------------------------------------------+");
                            writeStaff(bank, BankStaffAccountLL);
                        }
                        else if(AccountSettingsOption == 'O'){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|            UPDATE PHONE NUMBER                 |");
                            System.out.println("+------------------------------------------------+");
                            System.out.println("| CURRENT PHONE NUBMER : " + StaffAccount.getPersonPhone());
                            System.out.println("+------------------------------------------------+");
                            System.out.print("| ENTER NEW PHONE NUMBER     : ");
                            String newAddress = InputUser.nextLine();
                            StaffAccount.setPersonPhone(newAddress);
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|        PHONE NUMBER UPDATED SUCCESSFULLY       |");
                            System.out.println("+------------------------------------------------+");
                            writeStaff(bank, BankStaffAccountLL);
                        }
                        else if(AccountSettingsOption == 'N'){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|           UPDATE FULL NAME                    |");
                            System.out.println("+------------------------------------------------+");
                            System.out.println("| CURRENT FULL NAME : " + StaffAccount.getPersonName());
                            System.out.println("+------------------------------------------------+");
                            System.out.print("| ENTER NEW FULL NAME     : ");
                            String newName = InputUser.nextLine();
                            StaffAccount.setPersonName(newName);
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|        FULL NAME UPDATED SUCCESSFULLY         |");
                            System.out.println("+------------------------------------------------+");
                            writeStaff(bank, BankStaffAccountLL);
                        }
                        else if(AccountSettingsOption == 'Z'){
                        }
                    }
            else if(AccountSettingsOption == 'U'){
                System.out.println("+------------------------------------------------+");
                System.out.println("|           UPDATE ACCOUNT USERNAME              |");
                System.out.println("+------------------------------------------------+");
                System.out.println("| CURRENT ACCOUNT USERNAME : " + StaffAccount.getStaffUserName());
                System.out.println("+------------------------------------------------+");
                System.out.print("| ENTER NEW USERNAME     : ");
                String newUserName = InputUser.nextLine();
                StaffAccount.setStaffUserName(newUserName);
                System.out.println("+------------------------------------------------+");
                System.out.println("|     ACCOUNT USERNAME UPDATED SUCCESSFULLY      |");
                System.out.println("+------------------------------------------------+");
                 writeStaff(bank, BankStaffAccountLL);
            }
            else if(AccountSettingsOption == 'P'){
                System.out.println("+------------------------------------------------+");
                System.out.println("|           UPDATE ACCOUNT PASSWORD              |");
                System.out.println("+------------------------------------------------+");
                while (chance != 0) {
                    Scanner InputPassword = new Scanner(System.in);
                    System.out.print("| ENTER CURRENT PASSWORD  : ");
                    String CurrentPassword = InputPassword.nextLine();
                  if(CurrentPassword.equals(StaffAccount.getStaffPassword())){
                    System.out.print("| ENTER NEW PASSWORD     : ");
                    String newPassword = InputPassword.nextLine();
                    System.out.print("| CONFIRM NEW PASSWORD   : ");
                    String ConfirmNewPassword = InputPassword.nextLine();
                    if(newPassword.equals(ConfirmNewPassword)){
                        
                      StaffAccount.setStaffPassword(newPassword);
                      System.out.println("+------------------------------------------------+");
                      System.out.println("|     ACCOUNT PASSWORD UPDATED SUCCESSFULLY      |");
                      System.out.println("+------------------------------------------------+");
                      writeStaff(bank, BankStaffAccountLL);
                      break;
                    }
                    else {
                      System.out.println("| PASSWORD DID NOT MATCH. PLEASE TRY AGAIN");
                      chance--;
                    }
                    
                    
                  }
                  else{
                    System.out.println("| INCORRECT PASSWORD. TRY AGAIN");
                    chance--;
                  }
                }
            }


        }
        else if(UserType == 'U'){
                    int chance = 3;
                    System.out.println("+------------------------------------------------+");
                    System.out.println("|                                                |");
                    System.out.println("|           [N] = UPDATE PERSONAL INFORMATION    |");
                    System.out.println("|                                                |");
                    System.out.println("|           [U] = UPDATE ACCOUNT USERNAME        |");
                    System.out.println("|           [P] = UPDATE ACCOUNT PASSWORD        |");
                    System.out.println("|           [S] = UPDATE ACCOUNT SECURE WORD     |");
                    System.out.println("|           [L] = SET MONTHLY SPENDING LIMIT     |");
                    System.out.println("|                                                |");
                    System.out.println("|           [D] = CLOSE ACCOUNT                  |");
                    System.out.println("|                                                |");
                    System.out.println("|           [X] = EXIT                           |");
                    System.out.println("|                                                |");
                    System.out.println("+------------------------------------------------+");
                    System.out.print("| ACTION :  ");
                    AccountSettingsOption = input.next().toUpperCase().charAt(0);
                    if(AccountSettingsOption == 'X'){
                        break;
                    }
                    else if(AccountSettingsOption == 'N'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|          UPDATE PERSONAL INFORMATION           |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|           [N] = UPDATE FULL NAME               |");
                        System.out.println("|           [A] = UPDATE HOME ADDRESS            |");
                        System.out.println("|           [E] = UPDATE EMAIL ADDRESS           |");
                        System.out.println("|           [O] = UPDATE PHONE NUMBER            |");
                        System.out.println("|                                                |");
                        System.out.println("|           [Z] = GO BACK                        |");
                        System.out.println("+------------------------------------------------+");
                        System.out.print("| ACTION :  ");
                        AccountSettingsOption = input.next().toUpperCase().charAt(0);
                        if(AccountSettingsOption == 'A'){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|           UPDATE HOME ADDRESS                 |");
                            System.out.println("+------------------------------------------------+");
                            System.out.println("| CURRENT HOME ADDRESS : " + Account.getPersonAddress());
                            System.out.println("+------------------------------------------------+");
                            System.out.print("| ENTER NEW ADDRESS     : ");
                            String newAddress = InputUser.nextLine();
                            Account.setPersonAddress(newAddress);
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|       HOME ADDRESS UPDATED SUCCESSFULLY        |");
                            System.out.println("+------------------------------------------------+");
                            writeAccount(bank, CustomerAccountLL);


                        }
                        else if(AccountSettingsOption == 'E'){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|           UPDATE EMAIL ADDRESS                 |");
                            System.out.println("+------------------------------------------------+");
                            System.out.println("| CURRENT EMAIL ADDRESS : " + Account.getEmailAddress());
                            System.out.println("+------------------------------------------------+");
                            System.out.print("| ENTER NEW EMAIL ADDRESS     : ");
                            String newAddress = InputUser.nextLine();
                            Account.setEmailAddress(newAddress);
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|       EMAIL ADDRESS UPDATED SUCCESSFULLY       |");
                            System.out.println("+------------------------------------------------+");
                            writeAccount(bank, CustomerAccountLL);
                        }
                        else if(AccountSettingsOption == 'O'){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|            UPDATE PHONE NUMBER                 |");
                            System.out.println("+------------------------------------------------+");
                            System.out.println("| CURRENT PHONE NUBMER : " + Account.getPersonPhone());
                            System.out.println("+------------------------------------------------+");
                            System.out.print("| ENTER NEW PHONE NUMBER     : ");
                            String newAddress = InputUser.nextLine();
                            Account.setPersonPhone(newAddress);
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|        PHONE NUMBER UPDATED SUCCESSFULLY       |");
                            System.out.println("+------------------------------------------------+");
                            writeAccount(bank, CustomerAccountLL);
                        }
                        else if(AccountSettingsOption == 'N'){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|           UPDATE FULL NAME                    |");
                            System.out.println("+------------------------------------------------+");
                            System.out.println("| CURRENT FULL NAME : " + Account.getPersonName());
                            System.out.println("+------------------------------------------------+");
                            System.out.print("| ENTER NEW FULL NAME     : ");
                            String newName = InputUser.nextLine();
                            Account.setPersonName(newName);
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|        FULL NAME UPDATED SUCCESSFULLY         |");
                            System.out.println("+------------------------------------------------+");
                            writeAccount(bank, CustomerAccountLL);
                        }
                        else if(AccountSettingsOption == 'Z'){
                        }
                    }
                    else if(AccountSettingsOption == 'U'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|           UPDATE ACCOUNT USERNAME              |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("| CURRENT ACCOUNT USERNAME : " + Account.getAccountUserName());
                        System.out.println("+------------------------------------------------+");
                        System.out.print("| ENTER NEW USERNAME     : ");
                        String newUserName = InputUser.nextLine();
                        Account.setAccountUserName(newUserName);
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|     ACCOUNT USERNAME UPDATED SUCCESSFULLY      |");
                        System.out.println("+------------------------------------------------+");
                        writeAccount(bank, CustomerAccountLL);
                    }
                    else if(AccountSettingsOption == 'P'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|           UPDATE ACCOUNT PASSWORD              |");
                        System.out.println("+------------------------------------------------+");
                        while (chance != 0) {
                            Scanner InputPassword = new Scanner(System.in);
                            System.out.print("| ENTER CURRENT PASSWORD  : ");
                            String CurrentPassword = InputPassword.nextLine();
                          if(CurrentPassword.equals(Account.getAccountPassword())){
                            System.out.print("| ENTER NEW PASSWORD     : ");
                            String newPassword = InputPassword.nextLine();
                            System.out.print("| CONFIRM NEW PASSWORD   : ");
                            String ConfirmNewPassword = InputPassword.nextLine();
                            if(newPassword.equals(ConfirmNewPassword)){
                                
                              Account.setAccountPassword(newPassword);
                              System.out.println("+------------------------------------------------+");
                              System.out.println("|     ACCOUNT PASSWORD UPDATED SUCCESSFULLY      |");
                              System.out.println("+------------------------------------------------+");
                              writeAccount(bank, CustomerAccountLL);
                              break;
                            }
                            else {
                              System.out.println("| PASSWORD DID NOT MATCH. PLEASE TRY AGAIN");
                              chance--;
                            }
                            
                            
                          }
                          else{
                            System.out.println("| INCORRECT PASSWORD. TRY AGAIN");
                            chance--;
                          }
                        }

                    }
                    else if(AccountSettingsOption == 'S'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|           UPDATE ACCOUNT SECURE WORD           |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("| CURRENT ACCOUNT SECURE WORD : " + Account.getAccountSecureWord());
                        System.out.println("+------------------------------------------------+");
                        System.out.print("| ENTER NEW SECURE WORD  : ");
                        String newSecureWord = InputUser.nextLine();
                        Account.setAccountSecureWord(newSecureWord);
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|     ACCOUNT SECURE WORD UPDATED SUCCESSFULLY   |");
                        System.out.println("+------------------------------------------------+");
                        writeAccount(bank, CustomerAccountLL);
                    }
                    else if(AccountSettingsOption == 'L'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|              UPDATE SPENDING LIMIT             |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("| CURRENT MONTHLY SPENDING LIMIT : RN" + Account.getAccountSecureWord());
                        System.out.println("+------------------------------------------------+");
                        System.out.print("| ENTER NEW SPENDING LIMIT  : RM");
                        double newSpendingLimit = InputUser.nextDouble();
                        Account.setMonthlySpendingLimit(newSpendingLimit);
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|    MONTHLY SPENDING LIMITUPDATED SUCCESSFULLY  |");
                        System.out.println("+------------------------------------------------+");
                        writeAccount(bank, CustomerAccountLL);


                    }
                    else if(AccountSettingsOption == 'D'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|                  CLOSE ACCOUNT                 |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|    ARE YOU SURE YOU WANT TO CLOSE ACCOUNT ?    |");
                        System.out.println("|    YOU WILL LOST ALL MONEY IN YOUR BALANCE     |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|    [Y] = YES                                   |");
                        System.out.println("|    [N] = NO                                    |");
                        System.out.println("+------------------------------------------------+");
                        System.out.print("|    ANSWER : ");
                        String ans = InputUser.next();
                        char answer = ans.charAt(0);
                        if(answer == 'Y'){
                            Queue tempAccount = new Queue();
                            while(!CustomerAccountLL.isEmpty()){
                                Account temp = (Account) CustomerAccountLL.dequeue();
                                if(temp.getAccountID().equals(Account.getAccountID())){
                                    shouldbreak = true;
                                }
                                else{
                                    tempAccount.enqueue(temp);
                                }
                            }
                            while(!tempAccount.isEmpty()){
                                CustomerAccountLL.enqueue(tempAccount.dequeue());
                            }
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|         ACCOUNT CLOSED SUCCESSFULLY            |");
                            System.out.println("+------------------------------------------------+");
                            break;
                        }
                    }
                    else{
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|                INVALID INPUT                   |");
                        System.out.println("+------------------------------------------------+");
                    }


                }
            

            
        }
        
    }
public static void AccountTransactionMenu(String DateAndTime,Queue BillLL,Queue CustomerAccountLL,Account Account, Bank bank, char UserType){
    Scanner input = new Scanner(System.in);
    Scanner InputUser = new Scanner(System.in);
    char AccountTransactionOption = 'A';

    while (AccountTransactionOption != 'X') {
        System.out.println("+------------------------------------------------+");
        System.out.println("|                ACCOUNT TRANSACTION             |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
        System.out.println("| ACCOUNT BALANCE : RM" + Account.getAccountBalance());
        System.out.println("|                                                |");
        System.out.println("|           [D] = DEPOSIT                        |");
        System.out.println("|           [W] = WITHDRAW                       |");
        System.out.println("|           [T] = TRANSFER                       |");
        System.out.println("|                                                |");
        System.out.println("|           [X] = BACK TO MENU                   |");
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
        System.out.print("| ACTION :  ");
        AccountTransactionOption = input.nextLine().charAt(0);
        System.out.println("+------------------------------------------------+");
        if(AccountTransactionOption == 'D'){

            System.out.print("| ENTER AMOUNT TO DEPOSIT : RM");
            double Amount = InputUser.nextDouble();
            System.out.println("+------------------------------------------------+");
            System.out.println("| BALANCE AFTER DEPOSIT : RM" + (Amount + Account.getAccountBalance()));
            System.out.println("+------------------------------------------------+");
            System.out.print("| PROCEED TO DEPOSIT [Y = YES | N = NO] :  ");
            AccountTransactionOption = input.nextLine().charAt(0);
            System.out.println("+------------------------------------------------+");
            if(AccountTransactionOption == 'Y'){
                
                System.out.println("+------------------------------------------------+");
                System.out.println("|       DEPOSIT SUCCESSFULLY COMPLETED          |");
                System.out.println("+------------------------------------------------+");
                Bill newBill = new Bill(DateAndTime,generateUniqueBillID(1),"COMPUTER GENERATED",Account.getAccountID(),"NULL","COMPUTER","APPROVED",Amount,'D');
                BillLL.enqueue(newBill);
                bank.Deposit(Account, Amount);
                WriteBill(bank, BillLL);
                writeAccount(bank, CustomerAccountLL);

            }
            else if(AccountTransactionOption == 'N'){
                
            }
        }
        else if(AccountTransactionOption == 'W'){
            System.out.print("| ENTER AMOUNT TO WITHDRAW : RM");
            double Amount = InputUser.nextDouble();
            if(Amount > Account.getAccountBalance() || Account.getMonthlySpendingLimit() < (Account.getMonthlySpendature() + Amount)){
                System.out.println("+------------------------------------------------+");
                System.out.println("|      UNABLE TO DO TRANSACTION. TRY AGAIN       |");
                System.out.println("+------------------------------------------------+");
            }
            else {
                if(Amount > 10000){
                    System.out.println("+------------------------------------------------+");
                    System.out.println("|    TRANSACTION REQUIRED APPROVAL, PROCEED ?    |");
                    System.out.println("+------------------------------------------------+");
                    System.out.print("| PROCEED TO WITHDRAW [Y = YES | N = NO] :  ");
                    AccountTransactionOption = input.nextLine().charAt(0);
                    System.out.println("+------------------------------------------------+");
                    if(AccountTransactionOption == 'Y'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|          WITHDRAWAL REQUEST COMPLETED          |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("+------------------------------------------------+");
                          System.out.println("| BALANCE AFTER WITHDRAWAL : RM" + (Account.getAccountBalance() - Amount));
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|          YOUR BALANCE WILL BE DEDUCTED         |");
                        System.out.println("|   ONCE THE TRANSACTION IS APPROVED BY A STAFF  |");
                        System.out.println("+------------------------------------------------+");
                        Bill newBill = new Bill(DateAndTime,generateUniqueBillID(1),"NULL",Account.getAccountID(),"NULL","NULL","PENDING",Amount,'W');
                        BillLL.enqueue(newBill);
                        Account.updateUseableBalance(Amount);
                        Account.addNewTransaction();
                        Account.addMonthlyExpendature(Amount);
                        bank.addNumberOfWithdraw();
                        bank.addNumberOfTransaction();
                        bank.addTotalTransactionValue(Amount);
                        bank.addTotalWithdrdawValue(AccountTransactionOption);
                        writeAccount(bank, CustomerAccountLL);
                        WriteBill(bank, BillLL);
                        
                    }
                }
                else {
                    System.out.println("+------------------------------------------------+");
                    System.out.println("| BALANCE AFTER WITHDRAWAL : RM" + (Account.getAccountBalance() - Amount));
                    System.out.println("+------------------------------------------------+");
                    System.out.print("| PROCEED TO WITHDRAW [Y = YES | N = NO] :  ");
                    AccountTransactionOption = input.nextLine().charAt(0);
                    System.out.println("+------------------------------------------------+");
                    if(AccountTransactionOption == 'Y'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|       WITHDRAWAL SUCCESSFULLY COMPLETED        |");
                        System.out.println("+------------------------------------------------+");
                        Bill newBill = new Bill(DateAndTime,generateUniqueBillID(1),"COMPUTER GENERATED",Account.getAccountID(),"NULL","COMPUTER","APPROVED",Amount,'W');
                        BillLL.enqueue(newBill);
                        bank.Withdraw(Account, Amount);
                        WriteBill(bank, BillLL);
                        writeAccount(bank, CustomerAccountLL);
                    }
            else if(AccountTransactionOption == 'N'){
                
            }
                }
            }

        }
        else if (AccountTransactionOption == 'T') {
            System.out.print("| ENTER AMOUNT TO TRANSFER : RM");
            double Amount = InputUser.nextDouble();
            System.out.print("| ENTER TARGET ACCOUNT ID  : ");
            String TargetAccountID = input.nextLine();
            if(Amount > Account.getAccountBalance() || Account.getMonthlySpendingLimit() < (Account.getMonthlySpendature() + Amount)){
                System.out.println("+------------------------------------------------+");
                System.out.println("|      UNABLE TO DO TRANSACTION. TRY AGAIN       |");
                System.out.println("+------------------------------------------------+");
            }
            else {

            Queue temporaryLL = new Queue();

            if(Amount > 10000){
                System.out.println("+------------------------------------------------+");
                System.out.println("|    TRANSACTION REQUIRED APPROVAL, PROCEED ?    |");
                System.out.println("+------------------------------------------------+");
                System.out.print("| PROCEED TO TRANSACTION [Y = YES | N = NO] :  ");
                AccountTransactionOption = input.nextLine().charAt(0);
                System.out.println("+------------------------------------------------+");
                if(AccountTransactionOption == 'Y'){
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|         TRANSACTION REQUEST COMPLETED          |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("+------------------------------------------------+");
                          System.out.println("| BALANCE AFTER TRANSACITON : RM" + (Account.getAccountBalance() - Amount));
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|          YOUR BALANCE WILL BE DEDUCTED         |");
                        System.out.println("|   ONCE THE TRANSACTION IS APPROVED BY A STAFF  |");
                        System.out.println("+------------------------------------------------+");
                        Bill newBill = new Bill(DateAndTime,generateUniqueBillID(1),"NULL",Account.getAccountID(),TargetAccountID,"NULL","PENDING",Amount,'T');
                        BillLL.enqueue(newBill);
                        Account.updateUseableBalance(Amount);
                        Account.addNewTransaction();
                        Account.addMonthlyExpendature(Amount);
                        bank.addNumberOfWithdraw();
                        bank.addNumberOfTransaction();
                        bank.addTotalTransactionValue(Amount);
                        bank.addTotalWithdrdawValue(Amount);
                        WriteBill(bank, BillLL);
                        writeAccount(bank, CustomerAccountLL);
                        
                    }
            }
            else {

                System.out.println("+------------------------------------------------+");
                System.out.println("| BALANCE AFTER TRANSFER : RM" + (Account.getAccountBalance() - Amount));
                System.out.println("+------------------------------------------------+");
                System.out.print("| PROCEED TO TRANSFER [Y = YES | N = NO] :  ");
                AccountTransactionOption = input.nextLine().charAt(0);
                System.out.println("+------------------------------------------------+");
                if(AccountTransactionOption == 'Y'){
                    while(!CustomerAccountLL.isEmpty()){
                        Account temp = (Account) CustomerAccountLL.dequeue();
                        temporaryLL.enqueue(temp);
                        if(temp.getAccountID().equals(TargetAccountID)){
                            bank.Transfer(Account, temp, Amount);
                        }
                    }
                    while(!temporaryLL.isEmpty()){
                        Account temp = (Account) temporaryLL.dequeue();
                        CustomerAccountLL.enqueue(temp);
                    }
                System.out.println("+------------------------------------------------+");
                System.out.println("|       TRANSFER SUCCESSFULLY COMPLETED         |");
                System.out.println("+------------------------------------------------+");
                Bill newBill = new Bill(DateAndTime,generateUniqueBillID(1),"COMPUTER GENERATED",Account.getAccountID(),TargetAccountID,"COMPUTER","APPROVED",Amount,'T');
                BillLL.enqueue(newBill);
                WriteBill(bank, BillLL);
                writeAccount(bank, CustomerAccountLL);
            }

            }
        }
    }
}
}
public static void AccountBalance(Account DummyAccount, Bank bank){
    System.out.println("+------------------------------------------------+");
    System.out.println("|                                                  |");
    System.out.println("| ACCOUNT BALANCE : RM" + DummyAccount.getAccountBalance());
  System.out.println("|                                                  |");
    System.out.println("| CURREnT BALANCE : RM" + DummyAccount.getCurrentBalance());
    System.out.println("|                                                  |");
    System.out.println("+------------------------------------------------+");

}
public static void AccountTransactionHistory(Account Account, Queue BillLL){

    boolean HasTransaction = false;
    System.out.println("+------------------------------------------------+");
    System.out.println("|                                                |");
    System.out.println("| ACCOUNT TRANSACTION HISTORY : ");
    while(!BillLL.isEmpty()){
        Bill temp = (Bill) BillLL.dequeue();
        if (temp.getFromAccountID().equals(Account.getAccountID()) || temp.getToAccountID().equals(Account.getAccountID())){
            HasTransaction = true;
            if(temp.getTransactionType() == 'W' || temp.getTransactionType() == 'D'){
                System.out.println(temp.PrintSimpleBill());
            }
            else {
                System.out.println(temp.PrintTransationBill());
            }
        }
    }
    if(HasTransaction == false){
    System.out.println("|                                                |");
    System.out.println("|          NO TRANSACTION HISTORY FOUND          |");
    System.out.println("|                                                |");
    }
    System.out.println("|                                                |");
    System.out.println("+------------------------------------------------+");
}
public static void StaffMenu(String currentTime,Queue BillLL, Queue BankStaffAccountLL, Queue CustomerAccountLL, Bank bank, String currentUserID){

    Scanner input = new Scanner(System.in);
    char StaffOption = 'A';

    Queue tempAccountLL = new Queue();

    

    BankStaff StaffAccount = new BankStaff();
    while(!BankStaffAccountLL.isEmpty()){
        BankStaff temp = (BankStaff) BankStaffAccountLL.dequeue();
        tempAccountLL.enqueue(temp);
        if(temp.getStaffID().equals(CurrentUserID)){
            StaffAccount = temp;
        }
    }
    while(!tempAccountLL.isEmpty()){
        BankStaffAccountLL.enqueue(tempAccountLL.dequeue());
    }
    

    while (StaffOption != 'X') {
        System.out.println("+------------------------------------------------+");
        System.out.println("|             WELCOME TO BERNAMA BANK            |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
        System.out.println("|           [U] = USER SECTION                   |");
        System.out.println("|           [T] = TRANSACTION SECTION            |");
        System.out.println("|           [S] = ACCOUNT SETTINGS               |");
        System.out.println("|                                                |");
        System.out.println("|           [X] = LOG OUT                        |");
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
        System.out.print("| ACTION :  ");
        StaffOption = input.next().toUpperCase().charAt(0);

        if (StaffOption == 'U') {
            AdminUserMenu(currentTime, BillLL, CustomerAccountLL, bank, currentUserID);
        } else if (StaffOption == 'T') {
            AdminTransactionMenu(currentTime, BillLL, CustomerAccountLL, bank, currentUserID, BankStaffAccountLL);
        } else if (StaffOption == 'S') {
            AccountSettings(StaffOption, CustomerAccountLL, BankStaffAccountLL, StaffAccount,null, bank, StaffOption);
        } else if (StaffOption == 'X') {
            System.out.println("+------------------------------------------------+");
            System.out.println("|               LOG OUT SUCCESSFULLY              |");
            System.out.println("+------------------------------------------------+");
        }
        else {
            System.out.println("+------------------------------------------------+");
            System.out.println("|                INVALID INPUT                    |");
            System.out.println("+------------------------------------------------+");
        }
            
        }



    }
public static void AdminUserMenu(String currentTime, Queue BillLL, Queue CustomerAccountLL, Bank bank, String currentUserID){

    Scanner input = new Scanner(System.in);
    Scanner inputStaff = new Scanner(System.in);

    char UserOption = 'A';

    Queue tempAccount = new Queue();
    Queue tempBill = new Queue();

    while (UserOption != 'X') {
        System.out.println("+------------------------------------------------+");
        System.out.println("|                   USER MENU                    |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
        System.out.println("|           [D] = DISPLAY USER INFORMATION       |");
        System.out.println("|           [B] = BLOCK SUSPICIOUS ACCOUNT       |");
        System.out.println("|           [R] = DELETE AN ACCOUNT              |");
        System.out.println("|                                                |");
        System.out.println("|           [X] = MAIN MENU                      |");
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
        System.out.print("| ACTION :  ");
        UserOption = input.next().toUpperCase().charAt(0);

        if (UserOption == 'D') {
            System.out.println("+------------------------------------------------+");
            System.out.println("|                   USER MENU                    |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|                                                |");
            System.out.println("|           [D] = DISPLAY USER TRANSACTION       |");
            System.out.println("|           [U] = DISPLAY USER INFORMATION       |");
            System.out.println("|                                                |");
            System.out.println("|           [X] = MAIN MENU                      |");
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");
            System.out.print("| ACTION :  ");
            UserOption = input.next().toUpperCase().charAt(0);

            if(UserOption == 'D'){
                System.out.print("| ENTER USER ID : ");
                String userID = inputStaff.nextLine();
                while(!BillLL.isEmpty()){
                    Bill temp = (Bill) BillLL.dequeue();
                    tempBill.enqueue(temp);
                    if(temp.getFromAccountID().equals(userID)){
                        System.out.println("+------------------------------------------------+");
                        System.out.println(temp.printData());
                        System.out.println("+------------------------------------------------+");
                    }
                }
                while(!tempAccount.isEmpty()){
                    Account temp = (Account) tempAccount.dequeue();
                    CustomerAccountLL.enqueue(temp);
                }
            }
            else if(UserOption == 'U'){
                System.out.print("| ENTER USER ID : ");
                String userID = inputStaff.nextLine();
                while(!CustomerAccountLL.isEmpty()){
                    Account temp = (Account) CustomerAccountLL.dequeue();
                    tempAccount.enqueue(temp);
                    if(temp.getAccountID().equals(userID)){
                        System.out.println(temp.toString());
                    }
                }
                while(!tempAccount.isEmpty()){
                    Account temp = (Account) tempAccount.dequeue();
                    CustomerAccountLL.enqueue(temp);
                }

            }
            else if(UserOption == 'X'){}
            else{
                System.out.println("+------------------------------------------------+");
                System.out.println("|                INVALID INPUT                   |");
                System.out.println("+------------------------------------------------+");
            }

        }
        else if(UserOption == 'B'){
            System.out.println("+------------------------------------------------+");
            System.out.println("|                 BLOCK ACCOUNT                  |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|                                                |");
            System.out.println("|      WARNING : BLOCKING ACCOUNT WILL PREVENT   |");
            System.out.println("|               TRANSACTION ACITVITY             |");
            System.out.println("|                                                |");
            System.out.println("|            [C] = CONTINUE                      |");
            System.out.println("|                                                |");
            System.out.println("|            [X] = GO BACK                       |");
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");
            System.out.print("| ACTION :  ");
            UserOption = input.next().toUpperCase().charAt(0);

            if (UserOption == 'C') {
                System.out.print("| ENTER USER ID : ");
                String userID = inputStaff.nextLine();
                while(!CustomerAccountLL.isEmpty()){
                    Account temp = (Account) CustomerAccountLL.dequeue();
                    if(temp.getAccountID().equals(userID)){
                        if(temp.getAccountStatus() == true){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|                ACCOUNT UNBLOCKED               |");
                            System.out.println("+------------------------------------------------+");
                            temp.BlockAccount();
                        }
                        else {
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|                ACCOUNT BLOCKED                 |");
                            System.out.println("+------------------------------------------------+");
                            temp.UnBlockedAccount();
                        }
                    }
                    tempAccount.enqueue(temp);
                }
                while(!tempAccount.isEmpty()){
                    Account temp = (Account) tempAccount.dequeue();
                    CustomerAccountLL.enqueue(temp);
                }
            }

            else if(UserOption == 'X'){}
            else {
                System.out.println("+------------------------------------------------+");
                System.out.println("|                INVALID INPUT                   |");
                System.out.println("+------------------------------------------------+");
            }

            
        }
        else if(UserOption == 'R'){
            System.out.println("+------------------------------------------------+");
            System.out.println("|               DELETE USER ACCOUNT              |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|                                                |");
            System.out.println("|      WARNING : DELETING ACCOUNT WILL WIPE      |");
            System.out.println("|               ALL USER INFORMATION             |");
            System.out.println("|                                                |");
            System.out.println("|            [C] = CONTINUE                      |");
            System.out.println("|                                                |");
            System.out.println("|            [X] = GO BACK                       |");
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");
            System.out.print("| ACTION :  ");
            UserOption = input.next().toUpperCase().charAt(0);

             if(UserOption == 'C') {
                
                System.out.print("| ENTER USER ID : ");
                String userID = input.nextLine();
                while(!CustomerAccountLL.isEmpty()){
                    Account temp = (Account) CustomerAccountLL.dequeue();
                    if(temp.getAccountID().equals(userID)){
                        System.out.println(temp.toString());
                        System.out.println("|                                                |");
                        System.out.println("|            [D] = DELETE                        |");
                        System.out.println("|            [X] = GO BACK                       |");
                        System.out.println("+------------------------------------------------+");
                        System.out.print("| ACTION :  ");
                        char Answer = input.next().toUpperCase().charAt(0);
                        if(Answer == 'D'){
                            System.out.println("+------------------------------------------------+");
                            System.out.println("|                DELETE SUCCESSFULLY             |");
                            System.out.println("+------------------------------------------------+");
                        }
                        else {
                            tempAccount.enqueue(temp);
                        }
                    }
                    else {
                        tempAccount.enqueue(temp);
                    }
                }
                while(!tempAccount.isEmpty()){
                    Account temp = (Account) tempAccount.dequeue();
                    CustomerAccountLL.enqueue(temp);
                }
            }
            else if(UserOption == 'X'){}
            else {
                System.out.println("+------------------------------------------------+");
                System.out.println("|                INVALID INPUT                   |");
                System.out.println("+------------------------------------------------+");
            }
        }
        else if (UserOption == 'X') {
            System.out.println("+------------------------------------------------+");
            System.out.println("|               LOG OUT SUCCESSFULLY             |");
            System.out.println("+------------------------------------------------+");
        }
        else {
            System.out.println("+------------------------------------------------+");
            System.out.println("|                INVALID INPUT                   |");
            System.out.println("+------------------------------------------------+");
        }
    }

    writeAccount(bank, CustomerAccountLL);

}
public static void AdminTransactionMenu(String currentTime, Queue BillLL, Queue CustomerAccountLL, Bank bank, String currentUserID, Queue BankStaffAccountLL){
    Scanner input = new Scanner(System.in);
    Scanner inputStaff = new Scanner(System.in);

    char TransactionOption = 'A';

    while (TransactionOption != 'X') {
        System.out.println("+------------------------------------------------+");
        System.out.println("|                TRANSACTION MENU                |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|                                                |");
        System.out.println("|           [D] = DISPLAY NUMBER OF TRANSACTION  |");
        System.out.println("|           [B] = BILL SECTION                   |");
        System.out.println("|           [A] = APPROVED REQUESTED TRANSACTION |");
        System.out.println("|                                                |");
        System.out.println("|           [X] = MAIN MENU                      |");
        System.out.println("|                                                |");
        System.out.println("+------------------------------------------------+");
        System.out.print("| ACTION :  ");
        TransactionOption = input.next().toUpperCase().charAt(0);

        if (TransactionOption == 'D') {
            System.out.println("+------------------------------------------------+");
            System.out.println("|                TRANSACTION MENU                |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|                                                |");
              System.out.println("| NUMBER OF DEPOSITS     : " + bank.getTotalDepositNumber() + " DEPOSIT");
              System.out.println("| NUMBER OF WITHDRAWS    : " + bank.getTotalWithdrawNumber() + " WITHDRAW");
              System.out.println("| NUMBER OF TRANSFERS    : " + bank.getTotalTransferNumber() + " TRANSFER");
            System.out.println("|                                                |");
              System.out.println("| TRANSFER TOTAL VALUE   : RM" + bank.getTransferValue());
              System.out.println("| DEPOSIT TOTAL VALUE    : RM" + bank.getDepositValue());
              System.out.println("| WITHDRAW TOTAL VALUE   : RM" + bank.getWithdrawValue());
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|                                                |");
              System.out.println("| TOTAL NUMBER OF TRANSACTIONS : "+ bank.getTotalTransactionNumber() + " TRANSACTIONS");
              System.out.println("| TOTAL TRANSACTIONS VALUE     : RM" + bank.getTransactionValue());  
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");

        }

        else if (TransactionOption == 'B') {
            System.out.println("+------------------------------------------------+");
            System.out.println("|                  BILL SECTION                  |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|                                                |");
            System.out.println("|      [S] = SEARH ACCOUNT BILL HISTORY          |");
            System.out.println("|      [D] = DISPLAY BILL                        |");
            System.out.println("|                                                |");
            System.out.println("|           [X] = MAIN MENU                      |");
            System.out.println("|                                                |");
            System.out.println("+------------------------------------------------+");
            System.out.print("| ACTION :  ");
            TransactionOption = input.next().toUpperCase().charAt(0);
            if(TransactionOption == 'S'){
                System.out.println("+------------------------------------------------+");
                System.out.print("| ENTER ACCOUNT ID :  ");
                String AccountID = inputStaff.nextLine();
                //write a code to search for bill ONLY for the ACCOUNT ID Entered above and display it
                Queue TempLL = new Queue();
                while(!BillLL.isEmpty()){
                    Bill temp = (Bill) BillLL.dequeue();
                    TempLL.enqueue(temp);
                    if(temp.getFromAccountID().equals(AccountID) || temp.getToAccountID().equals(AccountID)){
                        System.out.println("+------------------------------------------------+");
                        System.out.println(temp.printData());
                        System.out.println("+------------------------------------------------+");
                    }
                }
                while(!TempLL.isEmpty()){
                    Bill temp = (Bill) TempLL.dequeue();
                    BillLL.enqueue(temp);
                }

            }
            else if(TransactionOption == 'D'){
                System.out.println("+------------------------------------------------+");
                System.out.println("|                  BILL SECTION                  |");
                System.out.println("+------------------------------------------------+");
                System.out.println("|                                                |");
                System.out.println("|      [Z] = DISPLAY ALL BILL                    |");
                System.out.println("|      [M] = DISPLAY BILL BY DATE                |");
                System.out.println("|      [V] = DISPLAY BILL BY AMOUNT              |");
                System.out.println("|      [A] = DISPLAY BILL BY ACCOUNT             |");
                System.out.println("|                                                |");
                System.out.println("|               [X] = MAIN MENU                  |");
                System.out.println("|                                                |");
                System.out.println("+------------------------------------------------+");
                System.out.print("| ACTION :  ");
                TransactionOption = input.next().toUpperCase().charAt(0);

                if(TransactionOption == 'Z'){
                    DisplayBill(TransactionOption, BillLL);
                }
                else if(TransactionOption == 'M'){
                    //write a function to take all bill in linkedlist, sort it by date and then display it
                    DisplayBill(TransactionOption, BillLL);
                }
                else if(TransactionOption == 'V'){
                    //write a function to take all bill in linkedlist, sort it by amount from highest to lowest and display it
                    DisplayBill(TransactionOption, BillLL);
                }
                else if(TransactionOption == 'A'){
                    //write a function to take all bill in linkedlist, sort it by accountID and then display it
                    DisplayBill(TransactionOption, BillLL);

                }
                else if(TransactionOption == 'X'){}
                else {
                    System.out.println("+------------------------------------------------+");
                    System.out.println("|                INVALID INPUT                   |");
                    System.out.println("+------------------------------------------------+");
                }
            }
            else if(TransactionOption == 'X'){}
            else {
                System.out.println("+------------------------------------------------+");
                System.out.println("|                INVALID INPUT                   |");
                System.out.println("+------------------------------------------------+");
            }

        }

        else if (TransactionOption == 'A') {
            int numberOfBillsApproved = 0;
            Queue tempBill = new Queue();
            Queue TempBankStaffLL = new Queue();
            Queue tempCustomer = new Queue();
            String staffName = null;

            while(!BankStaffAccountLL.isEmpty()){
                BankStaff temp = (BankStaff) BankStaffAccountLL.dequeue();
                if(temp.getStaffID().equals(currentUserID)){
                    staffName = temp.getPersonName();
                }
                TempBankStaffLL.enqueue(temp);
            }
            while(!TempBankStaffLL.isEmpty()){
                BankStaff temp = (BankStaff) TempBankStaffLL.dequeue();
                BankStaffAccountLL.enqueue(temp);
            }

            while(!BillLL.isEmpty()){
                Bill temp = (Bill) BillLL.dequeue();
                
                if(temp.getApproved() == "PENDING"){
                    temp.setApproved("APPROVED");
                    temp.setStaffID(currentUserID);
                    temp.setApproveName(staffName);
                    numberOfBillsApproved++;
                        while(!CustomerAccountLL.isEmpty()){
                        Account CustTemp = (Account) CustomerAccountLL.dequeue();
                        if(temp.getToAccountID().equals(CustTemp.getAccountID())){
                            CustTemp.DepositMoney(temp.getAmount());
                        }
                        if(temp.getFromAccountID().equals(CustTemp.getAccountID())){
                            CustTemp.UpdateBalanceAfterApproval();
                        }
                        tempCustomer.enqueue(CustTemp);
                    }
                    while(!tempCustomer.isEmpty()){
                        Account CustTemp = (Account) tempCustomer.dequeue();
                        CustomerAccountLL.enqueue(CustTemp);
                    }
                }
                tempBill.enqueue(temp);
            }
            while(!tempBill.isEmpty()){
                Bill temp = (Bill) tempBill.dequeue();
                BillLL.enqueue(temp);
            }
            WriteBill(bank, BillLL);
            writeAccount(bank, CustomerAccountLL);
        }

        else if (TransactionOption == 'X') {
            System.out.println("+------------------------------------------------+");
            System.out.println("|               LOG OUT SUCCESSFULLY             |");
            System.out.println("+------------------------------------------------+");
        }
        else {
            System.out.println("+------------------------------------------------+");
            System.out.println("|                INVALID INPUT                   |");
            System.out.println("+------------------------------------------------+");
        }

    }
}
public static void DisplayBill(char TransID, Queue BillLL){

    if(TransID == 'Z'){
        Queue TempBill = new Queue();
        while(!BillLL.isEmpty()){
            Bill temp = (Bill) BillLL.dequeue();
            TempBill.enqueue(BillLL);
            System.out.println("+------------------------------------------------+");
            System.out.println(temp.printData());
            System.out.println("+------------------------------------------------+");
        }
        while(!TempBill.isEmpty()){
            BillLL.enqueue(TempBill.dequeue());
        }
    }
    else if(TransID == 'D'){
        //create a function to take all bill in linkedlist, sort it by date and then display it
            // Sort the bills by date

        ArrayList tempArrayList = new ArrayList();
        Queue TempBill = new Queue();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        
        while(!BillLL.isEmpty()){
            Bill temp = (Bill) BillLL.dequeue();
            TempBill.enqueue(temp);
            tempArrayList.add(temp);
        }
        for(int i = 0 ; i < tempArrayList.size();i++){
            Bill currentBill = (Bill)tempArrayList.get(i);
            int index;

            try{
                Date date1 = dateFormat.parse(currentBill.getDate());
                for(index = i-1;index>=0 && date1.compareTo(dateFormat.parse(((Bill)tempArrayList.get(index)).getDate())) > 0;index--){
                tempArrayList.set(index+1, tempArrayList.get(index));
            }
            tempArrayList.set(index+1, currentBill);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
        }
        for(int i=0;i<tempArrayList.size();i++){
            Bill temp = (Bill)tempArrayList.get(i);
            System.out.println("+------------------------------------------------+");
            System.out.println(temp.printData());
            System.out.println("+------------------------------------------------+");
        }
        while(!TempBill.isEmpty()){
            Bill temp = (Bill) TempBill.dequeue();
            BillLL.enqueue(temp);
        }
        
    }
    else if(TransID == 'V'){
        ArrayList tempArrayList = new ArrayList();
        Queue TempBill = new Queue();

        while(!BillLL.isEmpty()){
            Bill temp = (Bill) BillLL.dequeue();
            TempBill.enqueue(temp);
            tempArrayList.add(temp);
        }
        for(int i = 0 ; i < tempArrayList.size();i++){
            Bill currentBill = (Bill)tempArrayList.get(i);
            int index;
            for(index = i-1;index>=0 && currentBill.getAmount() > ((Bill)tempArrayList.get(index)).getAmount();index--){
                tempArrayList.set(index+1, tempArrayList.get(index));
            }
            tempArrayList.set(index+1, currentBill);
        }
        for(int i=0;i<tempArrayList.size();i++){
            Bill temp = (Bill)tempArrayList.get(i);
            System.out.println("+------------------------------------------------+");
            System.out.println(temp.printData());
            System.out.println("+------------------------------------------------+");
        }
        while(!TempBill.isEmpty()){
            Bill temp = (Bill) TempBill.dequeue();
            BillLL.enqueue(temp);
        }
    }
    else if(TransID == 'A'){

        ArrayList tempArrayList = new ArrayList();
        Queue TempBill = new Queue();

        while(!BillLL.isEmpty()){
            Bill temp = (Bill) BillLL.dequeue();
            TempBill.enqueue(temp);
            tempArrayList.add(temp);
        }
        for(int i = 0 ; i < tempArrayList.size();i++){
            Bill currentBill = (Bill)tempArrayList.get(i);
            int index;
            
            for(index = i-1;index>=0 && currentBill.getFromAccountID().equals(((Bill)tempArrayList.get(index)).getFromAccountID());index--){
            tempArrayList.set(index+1, tempArrayList.get(index));
            }
            tempArrayList.set(index+1, currentBill);
            }
        for(int i=0;i<tempArrayList.size();i++){
            Bill temp = (Bill)tempArrayList.get(i);
            System.out.println("+------------------------------------------------+");
            System.out.println(temp.printData());
            System.out.println("+------------------------------------------------+");
        }
        while(!TempBill.isEmpty()){
            Bill temp = (Bill) TempBill.dequeue();
            BillLL.enqueue(temp);
        }

    }
}

}   