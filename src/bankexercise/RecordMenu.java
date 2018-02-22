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
		String interestRateStr = JOptionPane.showInputDialog("Enter Interest Rate: (do not type the % sign)");
		if(interestRateStr!=null)
		{
			BankApplication.interestRate = Double.parseDouble(interestRateStr);
		}
	}
	
	public static void overdraftSetting()
	{
		if(BankApplication.table.get(BankApplication.currentItem).getAccountType().trim().equals("Current")){
			String newOverdraftStr = JOptionPane.showInputDialog(null, "Enter new Overdraft", JOptionPane.OK_CANCEL_OPTION);
			BankApplication.overdraftTextField.setText(newOverdraftStr);
			BankApplication.table.get(BankApplication.currentItem).setOverdraft(Double.parseDouble(newOverdraftStr));
		}
		else
			JOptionPane.showMessageDialog(null, "Overdraft only applies to Current Accounts");
	}
	
	public static void deleteTheItem()
	{
		BankApplication.table.remove(currentItem);
		JOptionPane.showMessageDialog(null, "Account Deleted");


		currentItem=0;
		while(!BankApplication.table.containsKey(currentItem)){
			currentItem++;
		}
		BankApplication.displayDetails(currentItem);
					
	}
	
	public static void modifyTheItem()
	{
		BankApplication.surnameTextField.setEditable(true);
		BankApplication.firstNameTextField.setEditable(true);
		
		BankApplication.openValues = true;
	}
	
	
}