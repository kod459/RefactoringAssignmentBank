package bankexercise;

import java.util.Map;

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
	
	public static void accountDeposit()
	{
		String accNum = JOptionPane.showInputDialog("Account number to deposit into: ");
		boolean found = false;
		
		for (Map.Entry<Integer, BankAccount> entry : BankApplication.table.entrySet()) {
			if(accNum.equals(entry.getValue().getAccountNumber().trim())){
				found = true;
				String toDeposit = JOptionPane.showInputDialog("Account found, Enter Amount to Deposit: ");
				entry.getValue().setBalance(entry.getValue().getBalance() + Double.parseDouble(toDeposit));
				BankApplication.displayDetails(entry.getKey());
				
			}
		}
		if (!found)
		{
			JOptionPane.showMessageDialog(null, "Account number " + accNum + " not found.");
		}
	}
	
	public static void accountWithdraw()
	{
		String accNum = JOptionPane.showInputDialog("Account number to withdraw from: ");
		
		boolean found = false;
		
		for (Map.Entry<Integer, BankAccount> entry : BankApplication.table.entrySet()) {

			if(accNum.equals(entry.getValue().getAccountNumber().trim())){
				String toWithdraw = JOptionPane.showInputDialog("Account found, Enter Amount to Withdraw: ");
				if (!toWithdraw.contains("-")) {
					found = true;
					if(entry.getValue().getAccountType().trim().equals("Current")){
						if(Double.parseDouble(toWithdraw) > entry.getValue().getBalance() + entry.getValue().getOverdraft())
							JOptionPane.showMessageDialog(null, "Transaction exceeds overdraft limit");
						else{
							entry.getValue().setBalance(entry.getValue().getBalance() - Double.parseDouble(toWithdraw));
							BankApplication.displayDetails(entry.getKey());
						}
					}
					else if(entry.getValue().getAccountType().trim().equals("Deposit")){
						if(Double.parseDouble(toWithdraw) <= entry.getValue().getBalance()){
							entry.getValue().setBalance(entry.getValue().getBalance()-Double.parseDouble(toWithdraw));
							BankApplication.displayDetails(entry.getKey());
						}
						else
							JOptionPane.showMessageDialog(null, "Insufficient funds.");
					}
				}
			}else {
				JOptionPane.showMessageDialog(null, "You cannot withdraw a negitive number");
				found = true;
			}

		}
		if (accNum == null || (accNum != null && ("".equals(accNum)))) {
			JOptionPane.showMessageDialog(null, "No account number entered");
		} else if (!found) {
			JOptionPane.showMessageDialog(null, "Account number " + accNum + " not found.");
		}
	}
	
	public static void calculateInterest()
	{
		if(BankApplication.table.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No Accounts selected");
		}
		else 
		{
			if(!(BankApplication.interestRate == 0)) {
				for (Map.Entry<Integer, BankAccount> entry : BankApplication.table.entrySet()) {
					if(entry.getValue().getAccountType().equals("Deposit")){
						double equation = 1 + ((BankApplication.interestRate)/100);
						entry.getValue().setBalance(entry.getValue().getBalance()*equation);
						JOptionPane.showMessageDialog(null, "Balances Updated");
						BankApplication.displayDetails(entry.getKey());
					}
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No interest rate set");
			}
		}
	}
}