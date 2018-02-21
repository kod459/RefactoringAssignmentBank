package bankexercise;

import javax.swing.*;

public interface TransactionMenuInterface {

	void initComponents();

	JMenu getTransactionsMenu();

	JMenuItem getDeposit();

	JMenuItem getWithdraw();

	JMenuItem getCalcInterest();

}
