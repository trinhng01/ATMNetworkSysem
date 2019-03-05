import org.apache.commons.lang3.math.NumberUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;

/**
 * Interaction class manage the system
 */

public class Interaction {

    private HashMap<String, Bank> system = new HashMap<String, Bank>();

    /**
     * Create and manage ATM Network System
     * @throws IOException
     */
    public void startInteraction() throws IOException {
        CreateATMNetworkSystem();
        DisplayCards();
        DisplayATMs();
        InteractiveMode();
    }

    /**
     * Create ATM Network System
     */
    public void CreateATMNetworkSystem(){
        //Bank A
        Bank bankA = new Bank("A");

        //Add 2 new Accounts
        LocalDate expDate = LocalDate.of(2022, 9, 9);
        Account account100 = new Account(100, 500, expDate, "0000");
        expDate = LocalDate.of(2010, 1, 1);
        Account account101 = new Account(101, 1500, expDate, "0001");
        bankA.addNewAccounts(100, account100);
        bankA.addNewAccounts(101, account101);

        //Add 2 ATMs
        ATM atm1 = new ATM(1000);
        ATM atm2 = new ATM(2000);
        bankA.addNewATM(atm1);
        bankA.addNewATM(atm2);

        //Bank B
        Bank bankB = new Bank("B");

        //Add 2 new Accounts
        expDate = LocalDate.of(2022, 9, 9);
        Account account200 = new Account(200, 1000, expDate, "0002");
        expDate = LocalDate.of(2010, 4, 12);
        Account account201 = new Account(201, 5000, expDate, "0003");
        bankB.addNewAccounts(200, account200);
        bankB.addNewAccounts(201, account201);

        //Add 2 ATMs
        atm1 = new ATM(3000);
        atm2 = new ATM(4000);
        bankB.addNewATM(atm1);
        bankB.addNewATM(atm2);

        //Add 2 Banks to System
        system.put("A", bankA);
        system.put("B", bankB);

    }

    /**
     * Display all Cash cards for each Bank in the System
     */
    public void DisplayCards(){
        System.out.println("\nStates of " + system.size() +" Banks");
        for (Bank bank : system.values())
            bank.printCard();
    }

    /**
     * Display all ATMs for each Bank in the System
     */
    public void DisplayATMs(){
        int numATM = 0;
        for (Bank bank : system.values())
            numATM += bank.getATMs().size();
        System.out.println("\n\nStates of " + numATM +" ATMs");
        for (Bank bank : system.values())
            bank.displayATMs();
    }

    /**
     * Manage Authorization and Transaction Dialog
     * @throws IOException
     */
    public void InteractiveMode() throws IOException {
        System.out.println("\n----------------------------------------------------------------------------------------");
        Scanner s = new Scanner(System.in);
        String input, bankID_ATM, bankID_Card = "";
        int accountNumber = 0;

        do {
            System.out.println("\nEnter your choice of ATM: ");
            input = s.next().toUpperCase();
            bankID_ATM = input.substring(5);
            if (!system.containsKey(bankID_ATM)) System.out.println("ATM does not exist!");
        }while (!system.containsKey(bankID_ATM));

        int atmIndex = Integer.parseInt(input.substring(3,4)) - 1;
        ATM atmChosen = system.get(bankID_ATM).getATMs(atmIndex);
        atmChosen.print(Integer.parseInt(input.substring(3,4)));

        boolean invalidInput;
        do {
            System.out.println("\nEnter your card: ");
            input = s.next();
            invalidInput = false;

            try {
                bankID_Card = input.toUpperCase().substring(0, 1);
                accountNumber = Integer.parseInt(input.substring(1).trim());
                if (!bankID_Card.equals(bankID_ATM) || !system.get(bankID_Card).getAccounts().containsKey(accountNumber)) {
                    System.out.println("This card is not supported by this ATM.");
                    invalidInput = true;
                } else if (!system.get(bankID_Card).getAccounts().get(accountNumber).getCashCard().validExpDate()) {
                    System.out.println("This card is expired and returned to you.");
                    invalidInput = true;
                }
            }catch (NullPointerException| NumberFormatException e){
                System.out.println("Invalid Input!");
                invalidInput = true;
            }
        }while(invalidInput);

        System.out.println("The card is accepted. Please enter your password.");

        boolean validPassword;
        do {
            input = s.next();
            validPassword = system.get(bankID_Card).validPassword(accountNumber, input);
            if (!validPassword)
                System.out.println("This is a wrong password. Enter your password.");
        }while(!validPassword);

        System.out.println("Authorization is accepted. Start your transaction by entering the amount to withdraw.");

        TransactionDialog(s, bankID_Card, accountNumber, atmIndex);
    }

    /**
     * Handle Withdrawal Transaction Dialog
     * @param s - Scanner
     * @param bankID - ID of the bank user want to withdraw money from
     * @param accountNumber - account number user want to withdraw money from
     * @param atmIndex - ATM user is withdrawing money from
     * @throws IOException
     */
    public void TransactionDialog(Scanner s, String bankID, int accountNumber, int atmIndex) throws IOException {
        String input, transaction;
        boolean exceedLimit, exceedAmountAvailable;
        int withdrawAmount = 0, remainBalance = 0;
        input = s.next().toUpperCase();

        //Save transaction history to a file
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        StringBuilder record = new StringBuilder();
        record.append(formatter.format(LocalDate.now()));
        record.append("\nBankof").append(bankID).append(" - Account #: ").append(accountNumber);
        record.append(" - Balance: $").append(system.get(bankID).getAccounts(accountNumber).getAmountAvailable());
        transactionRecord(record.toString());

        do {
            do {
                exceedLimit = false;
                exceedAmountAvailable = false;
                if (NumberUtils.isCreatable(input)) {
                    withdrawAmount = Integer.parseInt(input);
                    exceedLimit = system.get(bankID).getATMs().get(atmIndex).exceedWithdrawLimit(withdrawAmount);
                    exceedAmountAvailable = system.get(bankID).getAccounts().get(accountNumber).exceedAmountAvailable(withdrawAmount);

                    if (exceedLimit) {
                        System.out.println("This amount exceeds the maximum amount you can withdraw per transaction. " +
                                "Please enter the amount or quit.");
                        input = s.next().toUpperCase();
                    }
                    if (exceedAmountAvailable) {
                        System.out.println("The amount exceeds the current balance. Enter another amount or quit.");
                        input = s.next().toUpperCase();
                    }
                }
            }while (!input.toUpperCase().equals("QUIT") && (exceedLimit || exceedAmountAvailable));

            if (!input.toUpperCase().equals("QUIT")) {
                remainBalance = system.get(bankID).getAccounts(accountNumber).withdraw(withdrawAmount);
                system.get(bankID).getAccounts(accountNumber).setAmountAvailable(remainBalance);

                transaction = "\nWithdraw $" + withdrawAmount;
                addTransactionToFile(transaction);

                System.out.println("$" + withdrawAmount + " is withdrawn from  your account. " +
                        "The remaining balance of this account is $" + remainBalance + ".");
                System.out.println("If you have more transactions, enter the amount or quit.");
            }
            input = s.next().toUpperCase();
            if (input.toUpperCase().equals("QUIT")) {
                transaction = "\nCurrent balance: $" + remainBalance;
                addTransactionToFile(transaction);
            }
        }while (!input.toUpperCase().equals("QUIT") || NumberUtils.isCreatable(input));

    }

    /**
     * Create output file to save all transaction history
     * @param accountInfo
     * @throws IOException
     */
    public void transactionRecord(String accountInfo) throws IOException {
        try {
            FileWriter f = new FileWriter("src/transaction_record.txt");
            BufferedWriter b = new BufferedWriter(f);
            b.write(accountInfo);
            b.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Add each transaction into record file
     * @param transaction
     */
    public static void addTransactionToFile(String transaction)
    {
        try {
            BufferedWriter append = new BufferedWriter(new FileWriter("src/transaction_record.txt", true));
            append.write(transaction);
            append.close();
        }
        catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }
}

