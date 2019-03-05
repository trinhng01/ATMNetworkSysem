import java.io.IOException;

/**
 * ATM Network System
 * @author Trinh Nguyem
 * @version 1.0
 * @IDE: IntelliJ IDEA
 * 03/04/2019
 */

/**
 * ATMNetworkSystem consist of banks, accounts, ATMs and cash cards
 */

public class ATMNetworkSystem {
    public static void main(String[] args) throws IOException {
        Interaction startSystem = new Interaction();
        startSystem.startInteraction();
    }
}
