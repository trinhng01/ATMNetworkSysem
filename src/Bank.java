import java.util.ArrayList;
import java.util.HashMap;

/**
 * A bank has an ID, multiple accounts and ATMs
 */

public class Bank {
    private String bankID;
    private HashMap<Integer,Account> accounts = new HashMap<Integer, Account>();
    private ArrayList<ATM> ATMs = new ArrayList<ATM>();

    /**
     * Construct a Bank with its ID
     * @param bankID - ID of the new Bank
     */
    public Bank(String bankID) {
        this.bankID = bankID;
    }

    /**
     * All accounts of a bank
     * @return All accounts of a bank
     */
    public HashMap<Integer, Account> getAccounts() { return this.accounts; }

    /**
     * One specific account of the bank given its account number
     * @param key - account number
     * @return One account of a bank
     */
    public Account getAccounts(int key) { return this.accounts.get(key); }

    /**
     * Add a new Account into the bank
     * @param accountNumber - account number of new account
     * @param newAccount - new account to be added
     */
    public void addNewAccounts(int accountNumber, Account newAccount) {
        newAccount.setBankID(this.bankID);
        newAccount.getCashCard().setBankID(this.bankID);
        this.accounts.put(accountNumber, newAccount);
    }

    /**
     * All ATMs of Bank
     * @return All ATMs of Bank
     */
    public ArrayList<ATM> getATMs() { return ATMs; }

    /**
     * One ATM of Bank given its location
     * @param index - atm location in ArrayList
     * @return One ATM of Bank
     */
    public ATM getATMs(int index) { return ATMs.get(index); }

    /**
     * Add a new ATM into the bank
     * @param newATM - new ATM to be added
     */
    public void addNewATM(ATM newATM) {
        newATM.setBankID(this.bankID);
        this.ATMs.add(newATM);
    }

    /**
     *  Display all ATMs in the Bank
     */
    public void displayATMs(){
        for (int i = 0; i < ATMs.size(); i++)
            ATMs.get(i).print(i+1);
    }

    /**
     * Validate Password of Cash card in an Account
     * @param accountNumber - account number of an Account that contains Cash Card
     * @param password - password to validate
     * @return
     */
    public boolean validPassword (int accountNumber, String password){
        if (accounts.get(accountNumber).getCashCard().validPassword(password))
            return true;
        else return false;
    }

    /**
     *  Display all Cash Cards in the Bank
     */
    public void printCard(){
        StringBuilder buff = new StringBuilder();
        buff.append("\nBankof").append(bankID);
        buff.append(" (").append(accounts.size()).append(" customer(s))");
        System.out.println(buff);
        for (Account account : accounts.values())
            account.printCard();
    }
}
