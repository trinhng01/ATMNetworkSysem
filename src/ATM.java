/**
 * ATM class has Bank ID that it is associated with and a withdraw limit
 */

public class ATM {
    private String bankID;
    private int withdrawLimit;

    /**
     * Construct an ATM with its withdraw limit per transaction
     * @param withdrawLimit - withdraw limit per transaction
     */
    public ATM(int withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    /**
     * Set Bank ID that ATM is associated with
     * @param bankID - bank ID
     */
    public void setBankID(String bankID) { this.bankID = bankID; }

    /**
     * Check if withdraw amount requested exceed the withdraw limit of ATM
     * @param withdrawAmount - withdraw amount requested
     * @return true if exceed, false is under or at limit
     */
    public boolean exceedWithdrawLimit(int withdrawAmount){
        if (withdrawAmount > this.withdrawLimit) return true;
        else return false;
    }

    /**
     * Display ATM information
     * @param index - number of ATM
     */
    public void print(int index){
        StringBuilder buff = new StringBuilder();
        buff.append("\nATM").append(index).append("_").append(bankID).append(": ");
        buff.append("(ATM").append(index).append(" from Bankof").append(bankID).append(")");
        buff.append("\n\tThe maximum amount of cash a card can widthraw per transaction: $").append(withdrawLimit);
        System.out.println(buff);
    }
}
