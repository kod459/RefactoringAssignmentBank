package bankexercise;

import javax.swing.*;

public class RecordMenu {
	
private JMenu recordsMenu;
	
	private JMenuItem createItem, modifyItem, deleteItem, setOverdraft, setInterest;
	static int currentItem = 1;
	
	public RecordMenu() {

		initComponents();
	}

	public void initComponents() {
		
		recordsMenu = new JMenu("Records");
		
		createItem = new JMenuItem("Create Item");
    	modifyItem = new JMenuItem("Modify Item");
    	deleteItem = new JMenuItem("Delete Item");
    	setOverdraft = new JMenuItem("Set Overdraft");
    	setInterest = new JMenuItem("Set Interest");
    	
    	recordsMenu.add(createItem);
    	recordsMenu.add(modifyItem);
    	recordsMenu.add(deleteItem);
    	recordsMenu.add(setOverdraft);
    	recordsMenu.add(setInterest);
	
	}

	public JMenu getRecordsMenu() {
		return recordsMenu;
	}

	public JMenuItem getCreateItem() {
		return createItem;
	}

	public JMenuItem getModifyItem() {
		return modifyItem;
	}

	public JMenuItem getDeleteItem() {
		return deleteItem;
	}

	public JMenuItem getOverdraft() {
		return setOverdraft;
	}

	public JMenuItem getInterest() {
		return setInterest;
	}
	
	public static void setInterestRate()
	{
		if (BankApplication.table.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No Accounts selected");
		}
		else
		{
			String interestRateStr = JOptionPane.showInputDialog("Enter Interest Rate:");
			if(interestRateStr.contains("%")) {
				JOptionPane.showMessageDialog(null, "Please do not enter in the % sign");
			}
			else 
			{
				if ( interestRateStr == null || (interestRateStr != null && ("".equals(interestRateStr)))) {
					JOptionPane.showMessageDialog(null, "No intrest rate entered");
				}
				else 
				{
					int interest = Integer.parseInt(interestRateStr);
					if (interest <= 0 || interest >=100) {
						JOptionPane.showMessageDialog(null, "Intrest rate cannot be 0% or a minus number, it also cannot be greater than 100%");
					}
					else 
					{
						interestRateStr = Integer.toString(interest);
						BankApplication.interestRate = Double.parseDouble(interestRateStr);
						JOptionPane.showMessageDialog(null, "Intrest rate set at " + interestRateStr + "%");
					} 
				}
			}
		}
	}

	
	public static void overdraftSetting()
	{
		if(!BankApplication.table.isEmpty())
		{
			if(BankApplication.table.get(BankApplication.currentItem).getAccountType().trim().equals("Current")){
				String newOverdraftStr = JOptionPane.showInputDialog(null, "Enter new Overdraft", JOptionPane.OK_CANCEL_OPTION);
				BankApplication.overdraftTextField.setText(newOverdraftStr);
				BankApplication.table.get(BankApplication.currentItem).setOverdraft(Double.parseDouble(newOverdraftStr));
			}
			else
				JOptionPane.showMessageDialog(null, "Overdraft only applies to Current Accounts");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No Accounts selected");
		}
		
	}
	
	public static void createItem()
	{
		new CreateBankDialog(BankApplication.table);
	}
	
	public static void deleteTheItem()
	{
		if (!BankApplication.table.isEmpty()) {
			BankApplication.table.remove(currentItem);
			JOptionPane.showMessageDialog(null, "Account Deleted");


			currentItem=0;
			while(!BankApplication.table.containsKey(currentItem)){
				currentItem++;
			}

			BankApplication.displayDetails(currentItem);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No Accounts selected");
		}
		
					
	}
	
	public static void modifyTheItem()
	{
		if (!BankApplication.table.isEmpty()) {
			BankApplication.surnameTextField.setEditable(true);
			BankApplication.firstNameTextField.setEditable(true);

			BankApplication.openValues = true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No Accounts selected");
		}
	}
	
	
}