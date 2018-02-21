package bankexercise;

import javax.swing.*;

public class TransactionMenu {
	
	private JMenu transactionsMenu;
	private JMenuItem deposit, withdraw, calcInterest;
	
	public TransactionMenu() {
		initComponents();
	}

	public void initComponents() {

		transactionsMenu = new JMenu("Transactions");

		deposit = new JMenuItem("Deposit");
		withdraw = new JMenuItem("Withdraw");
		calcInterest = new JMenuItem("Calculate Interest");

		transactionsMenu.add(deposit);
		transactionsMenu.add(withdraw);
		transactionsMenu.add(calcInterest);

	}

	public JMenu getTransactionsMenu() {
		return transactionsMenu;
	}

	public JMenuItem getDeposit() {
		return deposit;
	}

	public JMenuItem getWithdraw() {
		return withdraw;
	}

	public JMenuItem getCalcInterest() {
		return calcInterest;
	}
}