import java.time.LocalDate;

/**
 * Account has Bank ID that it is associated with, account number, amount available and a Cash card
 */

public class Account {
    private String bankID;
    private int accountNumber;
    private int amountAvailable;
    private CashCard cashCard;

    /**
     * Construct an Account with Default values and a new Cash Card
     */
    public Account(){
        this.cashCard = new CashCard();
    }

    /**
     * Construct an Account with account number, amount available, expiration date
     * and password of Cash Card
     * @param accountNumber - account number
     * @param amountAvailable - amount available
     * @param expDate - expiration date of Cash Card
     * @param password - password of Cash Card
     */
    public Account(int accountNumber, int amountAvailable, LocalDate expDate, String password) {
        this.accountNumber = accountNumber;
        this.amountAvailable = amountAvailable;
        setCashCard(accountNumber, expDate, password);
    }

    /**
     * Set information of Card Card
     * @param accountNumber - Account number that Card is associated with
     * @param expDate - expiration date of Cash Card
     * @param password - password of Cash Card
     */
    public void setCashCard(int accountNumber, LocalDate expDate, String password) {
        this.cashCard = new CashCard();
        this.cashCard.setAccountNumber(accountNumber);
        this.cashCard.setExpDate(expDate);
        this.cashCard.setPassword(password);
    }

    /**
     * Set Bank ID and the Account is associated with
     * @param bankID - bank ID
     */
    public void setBankID(String bankID) { this.bankID = bankID; }

    /**
     * Set ammount available in Account
     * @param amountAvailable - amount available
     */
    public void setAmountAvailable(int amountAvailable) { this.amountAvailable = amountAvailable; }

    /**
     * Return Account Balance
     * @return Account Balance
     */
    public int getAmountAvailable() { return amountAvailable; }

    /**
     * Return Cash Card of Account
     * @return Cash Card of Account
     */
    public CashCard getCashCard() { return cashCard; }

    /**
     * Check if withdraw amount requested exceed the amount available in account
     * @param withdrawAmount - withdraw amount requested
     * @return true if exceed, false is under or at limit
     */
    public boolean exceedAmountAvailable(int withdrawAmount) {
        if (withdrawAmount > this.amountAvailable) return true;
        else return false;
    }

    /**
     * Deduct money from amount available for withdraw request
     * @param withdrawAmount
     * @return
     */
    public int withdraw(int withdrawAmount){
        return amountAvailable - withdrawAmount;
    }

    /**
     * Display Cash Card information
     */
    public void printCard(){
        cashCard.print();
    }
}
