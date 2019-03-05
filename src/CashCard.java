import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Cash Card has has a Bank ID and an account number that it is associated with,
 * expiration date and password
 */

public class CashCard {
    private String bankID;
    private int accountNumber;
    private LocalDate expDate;
    private String password;

    /**
     * Construct a Cash Card with default values
     */
    public CashCard(){}

    /**
     * Set Bank ID of the Bank that the Card is associated with
     * @param bankID - bank ID
     */
    public void setBankID(String bankID) { this.bankID = bankID; }

    /**
     * Set Account number of the Account that the Card is associated with
     * @param accountNumber - account number
     */
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }

    /**
     * Set expiration date of the Card
     * @param expDate - expiration date
     */
    public void setExpDate(LocalDate expDate) { this.expDate = expDate; }

    /**
     * Set password of the Card
     * @param password - password
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Check if the password user entered match with the one is system
     * @param passwordToCheck - password user entered
     * @return true if matched, false if not matched
     */
    public boolean validPassword(String passwordToCheck){
        if (passwordToCheck.equals(password)) return true;
        else return false;
    }

    /**
     * Check is the Card is expired
     * @return true if expired, false is valid
     */
    public boolean validExpDate(){
        LocalDate today = LocalDate.now();
        if (expDate.isAfter(today)) return true;
        else return false;
    }

    /**
     * Display Card information
     */
    public void print(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        StringBuilder buff = new StringBuilder();
        buff.append("Customer with Cash Card");
        buff.append("(Bank ID: ").append(bankID);
        buff.append(", Account #: ").append(accountNumber);
        buff.append(", Expiration Date: ").append(expDate.format(formatter));
        buff.append(", Password: ").append(password).append(")");
        System.out.println(buff);
    }
}
