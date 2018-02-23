package bankexercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NavigateMenu {
	public static JMenu navigateMenu;
	public static JMenuItem nextItem, prevItem, firstItem, lastItem, findByAccount, findBySurname, listAll;
	public static JButton firstItemButton, lastItemButton, nextItemButton, prevItemButton;
	public static JTable jTable;
	
	public NavigateMenu() {
		
		initComponents();
	}

	public void initComponents() {
		
		navigateMenu = new JMenu("Navigate");
		
		nextItem = new JMenuItem("Next Item");
    	prevItem = new JMenuItem("Previous Item");
    	firstItem = new JMenuItem("First Item");
    	lastItem = new JMenuItem("Last Item");
    	findByAccount = new JMenuItem("Find by Account Number");
    	findBySurname = new JMenuItem("Find by Surname");
    	listAll = new JMenuItem("List All Records");
    	
    	nextItemButton = new JButton(new ImageIcon("next.png"));
		prevItemButton = new JButton(new ImageIcon("prev.png"));
		firstItemButton = new JButton(new ImageIcon("first.png"));
		lastItemButton = new JButton(new ImageIcon("last.png"));
    	
    	getNavigateMenu().add(getNextItem());
    	getNavigateMenu().add(getPrevItem());
    	getNavigateMenu().add(getFirstItem());
    	getNavigateMenu().add(getLastItem());
    	getNavigateMenu().add(getFindByAccount());
    	getNavigateMenu().add(getFindBySurname());
    	getNavigateMenu().add(getListAll());
		
	}

	public JMenu getNavigateMenu() {
		return navigateMenu;
	}

	public JMenuItem getListAll() {
		return listAll;
	}

	public JMenuItem getNextItem() {
		return nextItem;
	}

	public JMenuItem getPrevItem() {
		return prevItem;
	}

	public JMenuItem getFirstItem() {
		return firstItem;
	}

	public JMenuItem getLastItem() {
		return lastItem;
	}
	
	public JButton getNextItemButton() {
		return nextItemButton;
	}

	public JButton getPrevItemButton() {
		return prevItemButton;
	}
	
	public JButton getFirstItemButton() {
		return firstItemButton;
	}

	public JButton getLastItemButton() {
		return lastItemButton;
	}

	public JMenuItem getFindByAccount() {
		return findByAccount;
	}

	public JMenuItem getFindBySurname() {
		return findBySurname;
	}
	
	public static void listAllItems()
	{
		JFrame frame = new JFrame("TableDemo");
	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String col[] = {"ID","Number","Name", "Account Type", "Balance", "Overdraft"};
		
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		jTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(jTable);
		jTable.setAutoCreateRowSorter(true);
		
		for (Map.Entry<Integer, BankAccount> entry : BankApplication.table.entrySet()) {
		   
		    
		    Object[] objs = {entry.getValue().getAccountID(), entry.getValue().getAccountNumber(), 
		    				entry.getValue().getFirstName().trim() + " " + entry.getValue().getSurname().trim(), 
		    				entry.getValue().getAccountType(), entry.getValue().getBalance(), 
		    				entry.getValue().getOverdraft()};

		    tableModel.addRow(objs);
		}
		frame.setSize(600,500);
		frame.add(scrollPane);
        frame.setVisible(true);
	}
	
	public static void findSurname()
	{
		if(!BankApplication.table.isEmpty())
		{
		String surname = JOptionPane.showInputDialog("Search for surname: ");
		boolean found = false;
		
		 for (Map.Entry<Integer, BankAccount> entry : BankApplication.table.entrySet()) {
			   
			 if(surname.equalsIgnoreCase((entry.getValue().getSurname().trim()))){
				 found = true;
				 BankApplication.accountIDTextField.setText(entry.getValue().getAccountID()+"");
				 BankApplication.accountNumberTextField.setText(entry.getValue().getAccountNumber());
				 BankApplication.surnameTextField.setText(entry.getValue().getSurname());
				 BankApplication.firstNameTextField.setText(entry.getValue().getFirstName());
				 BankApplication.accountTypeTextField.setText(entry.getValue().getAccountType());
				 BankApplication.balanceTextField.setText(entry.getValue().getBalance()+"");
				 BankApplication.overdraftTextField.setText(entry.getValue().getOverdraft()+"");
			 }
		 }		
		 if(found)
			 JOptionPane.showMessageDialog(null, "Surname  " + surname + " found.");
		 else
			 JOptionPane.showMessageDialog(null, "Surname " + surname + " not found.");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No fields in Table");
		}
	}
	
	public static void findAccount()
	{
		if(!BankApplication.table.isEmpty())
		{
		String accNum = JOptionPane.showInputDialog("Search for account number: ");
		boolean found = false;
	
		 for (Map.Entry<Integer, BankAccount> entry : BankApplication.table.entrySet()) {
			   
			 if(accNum.equals(entry.getValue().getAccountNumber().trim())){
				 found = true;
				 BankApplication.accountIDTextField.setText(entry.getValue().getAccountID()+"");
				 BankApplication.accountNumberTextField.setText(entry.getValue().getAccountNumber());
				 BankApplication.surnameTextField.setText(entry.getValue().getSurname());
				 BankApplication.firstNameTextField.setText(entry.getValue().getFirstName());
				 BankApplication.accountTypeTextField.setText(entry.getValue().getAccountType());
				 BankApplication.balanceTextField.setText(entry.getValue().getBalance()+"");
				 BankApplication.overdraftTextField.setText(entry.getValue().getOverdraft()+"");						
				 
			 }			 
		 }
		 if(found)
			 JOptionPane.showMessageDialog(null, "Account number " + accNum + " found.");
		 else
			 JOptionPane.showMessageDialog(null, "Account number " + accNum + " not found.");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No fields in Table");
		}
	}
	
	public static void nextItem()
	{
		if(!BankApplication.table.isEmpty())
		{
		ArrayList<Integer> keyList = new ArrayList<Integer>();
		int i=0;

		while(i<BankApplication.TABLE_SIZE){
			i++;
			
			if(BankApplication.table.containsKey(i))
				keyList.add(i);
		}
		
		int maxKey = Collections.max(keyList);

		BankApplication.saveOpenValues();

			if(BankApplication.currentItem<maxKey){
				BankApplication.currentItem++;
				while(!BankApplication.table.containsKey(BankApplication.currentItem)){
					BankApplication.currentItem++;
				}
			}
			BankApplication.displayDetails(BankApplication.currentItem);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No fields in Table");
		}
	}
	
	public static void prevItem(){
		
		if(!BankApplication.table.isEmpty())
		{
			ArrayList<Integer> keyList = new ArrayList<Integer>();
			int i=0;

			while(i<BankApplication.TABLE_SIZE){
				i++;
				if(BankApplication.table.containsKey(i))
					keyList.add(i);
			}

			int minKey = Collections.min(keyList);

			if(BankApplication.currentItem>minKey){
				BankApplication.currentItem--;
				while(!BankApplication.table.containsKey(BankApplication.currentItem)){

					BankApplication.currentItem--;
				}
			}
			BankApplication.displayDetails(BankApplication.currentItem);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No fields in Table");
		}
	}
	
	public static void firstItemInList()
	{

		if(!BankApplication.table.isEmpty())
		{
			BankApplication.saveOpenValues();
			BankApplication.currentItem=0;
			while(!BankApplication.table.containsKey(BankApplication.currentItem)){
				BankApplication.currentItem++;
			}
			BankApplication.displayDetails(BankApplication.currentItem);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No fields in Table");
		}

		
	}
	
	public static void lastItemInList()
	{
		if(!BankApplication.table.isEmpty())
		{
			BankApplication.saveOpenValues();

			BankApplication.currentItem = BankApplication.TABLE_SIZE;

			while(!BankApplication.table.containsKey(BankApplication.currentItem)){
				BankApplication.currentItem--;

			}
		
		BankApplication.displayDetails(BankApplication.currentItem);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No fields in Table");
		}
	}

}
