package bankexercise;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

public class BankApplication extends JFrame {
	
	ArrayList<BankAccount> accountList = new ArrayList<BankAccount>();
	static HashMap<Integer, BankAccount> table = new HashMap<Integer, BankAccount>();
	private final static int TABLE_SIZE = 29;
	static private final String newline = "\n";
	
	NavigateMenu navigateMenu = new NavigateMenu();
	RecordMenu recordsMenu = new RecordMenu();
	TransactionMenu transactionsMenu = new TransactionMenu();
	FileMenu fileMenu = new FileMenu();

	
	JMenuBar menuBar;
	JMenu exitMenu;
	JMenuItem closeApp;
	
	JLabel accountIDLabel, accountNumberLabel, firstNameLabel, surnameLabel, accountTypeLabel, balanceLabel, overdraftLabel;
	JTextField accountIDTextField, accountNumberTextField, firstNameTextField, surnameTextField, accountTypeTextField, balanceTextField, overdraftTextField;
	
	static JFileChooser fc;
	JTable jTable;
	double interestRate;
	
	int currentItem = 0;
	
	
	boolean openValues;
	
	public BankApplication() {
		
		super("Bank Application");
		
		
		initComponents();
	}
	
	public void initComponents() {
		setLayout(new BorderLayout());
		JPanel displayPanel = new JPanel(new MigLayout());
		
		accountIDLabel = new JLabel("Account ID: ");
		accountIDTextField = new JTextField(15);
		accountIDTextField.setEditable(false);
		
		displayPanel.add(accountIDLabel, "growx, pushx");
		displayPanel.add(accountIDTextField, "growx, pushx, wrap");
		
		accountNumberLabel = new JLabel("Account Number: ");
		accountNumberTextField = new JTextField(15);
		accountNumberTextField.setEditable(false);
		
		displayPanel.add(accountNumberLabel, "growx, pushx");
		displayPanel.add(accountNumberTextField, "growx, pushx, wrap");

		surnameLabel = new JLabel("Last Name: ");
		surnameTextField = new JTextField(15);
		surnameTextField.setEditable(false);
		
		displayPanel.add(surnameLabel, "growx, pushx");
		displayPanel.add(surnameTextField, "growx, pushx, wrap");

		firstNameLabel = new JLabel("First Name: ");
		firstNameTextField = new JTextField(15);
		firstNameTextField.setEditable(false);
		
		displayPanel.add(firstNameLabel, "growx, pushx");
		displayPanel.add(firstNameTextField, "growx, pushx, wrap");

		accountTypeLabel = new JLabel("Account Type: ");
		accountTypeTextField = new JTextField(5);
		accountTypeTextField.setEditable(false);
		
		displayPanel.add(accountTypeLabel, "growx, pushx");
		displayPanel.add(accountTypeTextField, "growx, pushx, wrap");

		balanceLabel = new JLabel("Balance: ");
		balanceTextField = new JTextField(10);
		balanceTextField.setEditable(false);
		
		displayPanel.add(balanceLabel, "growx, pushx");
		displayPanel.add(balanceTextField, "growx, pushx, wrap");
		
		overdraftLabel = new JLabel("Overdraft: ");
		overdraftTextField = new JTextField(10);
		overdraftTextField.setEditable(false);
		
		displayPanel.add(overdraftLabel, "growx, pushx");
		displayPanel.add(overdraftTextField, "growx, pushx, wrap");
		
		add(displayPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		
		buttonPanel.add(navigateMenu.getFirstItemButton());
		buttonPanel.add(navigateMenu.getPrevItemButton());
		buttonPanel.add(navigateMenu.getNextItemButton());
		buttonPanel.add(navigateMenu.getLastItemButton());
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		menuBar = new JMenuBar();
    	setJMenuBar(menuBar);
		
    	
    	menuBar.add(navigateMenu.getNavigateMenu());
    	menuBar.add(recordsMenu.getRecordsMenu());
    	menuBar.add(fileMenu.getFileMenu());
    	menuBar.add(transactionsMenu.getTransactionsMenu());
    	
    	exitMenu = new JMenu("Exit");
    	
    	closeApp = new JMenuItem("Close Application");
    	
    	exitMenu.add(closeApp);
    	
    	menuBar.add(exitMenu);
    	
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
	
		recordsMenu.getOverdraft().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				overdraftSetting();
			
			}
		});
	
		ActionListener first = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstItemInList();
			}
		};
		
		ActionListener last = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lastItemInList();
			}
		};
		
		
		
		
		ActionListener next = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				nextItem();
			}
				
		};
		
		ActionListener prev = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prevItem();
			}
		};
		
		
		navigateMenu.getNextItemButton().addActionListener(next);
		navigateMenu.getNextItem().addActionListener(next);

		navigateMenu.getPrevItemButton().addActionListener(prev);
		navigateMenu.getPrevItem().addActionListener(prev);

		navigateMenu.getFirstItemButton().addActionListener(first);
		navigateMenu.getFirstItem().addActionListener(first);

		navigateMenu.getLastItemButton().addActionListener(last);
		navigateMenu.getLastItem().addActionListener(last);
		
		recordsMenu.getDeleteItem().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deleteTheItem();
			}
		});
		
		recordsMenu.getCreateItem().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new CreateBankDialog(table);		
			}
		});
		
		
		recordsMenu.getModifyItem().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				modifyTheItem();
			}
		});
		
		recordsMenu.getInterest().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setInterestRate();
			}
		});
		
		navigateMenu.getListAll().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listAllItems();
			}
		});
		
		fileMenu.getOpen().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				openFile();
			}
		});
		
		fileMenu.getSave().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveFile();
			}
		});
		
		fileMenu.getSaveAs().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveFileAs();
			}
		});
		
		closeApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeApp();
			}
		});	
		
		navigateMenu.getFindBySurname().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				findSurname();
			}
		});
		
		navigateMenu.getFindByAccount().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				findAccount();
			}
		});
		
		transactionsMenu.getDeposit().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				accountDeposit();
			}
		});
		
		transactionsMenu.getWithdraw().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				accountWithdraw();
			}
		});
		
		transactionsMenu.getCalcInterest().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calculateInterest();
			}
		});		
	}
	
	
	
	private void saveOpenValues(){		
		if (openValues){
			surnameTextField.setEditable(false);
			firstNameTextField.setEditable(false);
				
			table.get(currentItem).setSurname(surnameTextField.getText());
			table.get(currentItem).setFirstName(firstNameTextField.getText());
		}
	}	
	
	private void displayDetails(int currentItem) {	
				
		accountIDTextField.setText(table.get(currentItem).getAccountID()+"");
		accountNumberTextField.setText(table.get(currentItem).getAccountNumber());
		surnameTextField.setText(table.get(currentItem).getSurname());
		firstNameTextField.setText(table.get(currentItem).getFirstName());
		accountTypeTextField.setText(table.get(currentItem).getAccountType());
		balanceTextField.setText(table.get(currentItem).getBalance()+"");
		if(accountTypeTextField.getText().trim().equals("Current"))
			overdraftTextField.setText(table.get(currentItem).getOverdraft()+"");
		else
			overdraftTextField.setText("Only applies to current accs");
	
	}
	
	private static RandomAccessFile input;
	private static RandomAccessFile output;

	
	private void findSurname()
	{
		String surname = JOptionPane.showInputDialog("Search for surname: ");
		boolean found = false;
		
		 for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
			   
			 if(surname.equalsIgnoreCase((entry.getValue().getSurname().trim()))){
				 found = true;
				 accountIDTextField.setText(entry.getValue().getAccountID()+"");
				 accountNumberTextField.setText(entry.getValue().getAccountNumber());
				 surnameTextField.setText(entry.getValue().getSurname());
				 firstNameTextField.setText(entry.getValue().getFirstName());
				 accountTypeTextField.setText(entry.getValue().getAccountType());
				 balanceTextField.setText(entry.getValue().getBalance()+"");
				 overdraftTextField.setText(entry.getValue().getOverdraft()+"");
			 }
		 }		
		 if(found)
			 JOptionPane.showMessageDialog(null, "Surname  " + surname + " found.");
		 else
			 JOptionPane.showMessageDialog(null, "Surname " + surname + " not found.");
	}
	
	private void overdraftSetting()
	{
		if(table.get(currentItem).getAccountType().trim().equals("Current")){
			String newOverdraftStr = JOptionPane.showInputDialog(null, "Enter new Overdraft", JOptionPane.OK_CANCEL_OPTION);
			overdraftTextField.setText(newOverdraftStr);
			table.get(currentItem).setOverdraft(Double.parseDouble(newOverdraftStr));
		}
		else
			JOptionPane.showMessageDialog(null, "Overdraft only applies to Current Accounts");
	}
	
	private void findAccount()
	{
		String accNum = JOptionPane.showInputDialog("Search for account number: ");
		boolean found = false;
	
		 for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
			   
			 if(accNum.equals(entry.getValue().getAccountNumber().trim())){
				 found = true;
				 accountIDTextField.setText(entry.getValue().getAccountID()+"");
				 accountNumberTextField.setText(entry.getValue().getAccountNumber());
				 surnameTextField.setText(entry.getValue().getSurname());
				 firstNameTextField.setText(entry.getValue().getFirstName());
				 accountTypeTextField.setText(entry.getValue().getAccountType());
				 balanceTextField.setText(entry.getValue().getBalance()+"");
				 overdraftTextField.setText(entry.getValue().getOverdraft()+"");						
				 
			 }			 
		 }
		 if(found)
			 JOptionPane.showMessageDialog(null, "Account number " + accNum + " found.");
		 else
			 JOptionPane.showMessageDialog(null, "Account number " + accNum + " not found.");
	}
	
	private void nextItem()
	{
		ArrayList<Integer> keyList = new ArrayList<Integer>();
		int i=0;

		while(i<TABLE_SIZE){
			i++;
			
			if(table.containsKey(i))
				keyList.add(i);
		}
		
		int maxKey = Collections.max(keyList);

		saveOpenValues();

			if(currentItem<maxKey){
				currentItem++;
				while(!table.containsKey(currentItem)){
					currentItem++;
				}
			}
			displayDetails(currentItem);
	}
	
	private void prevItem(){

		ArrayList<Integer> keyList = new ArrayList<Integer>();
		int i=0;

		while(i<TABLE_SIZE){
			i++;
			if(table.containsKey(i))
				keyList.add(i);
		}
		
		int minKey = Collections.min(keyList);
		
		if(currentItem>minKey){
			currentItem--;
			while(!table.containsKey(currentItem)){
			
				currentItem--;
			}
		}
		displayDetails(currentItem);
		
	}
	
	private void firstItemInList()
	{
		saveOpenValues();
		
		currentItem=0;
		while(!table.containsKey(currentItem)){
			currentItem++;
		}
		displayDetails(currentItem);

		
	}
	
	private void lastItemInList()
	{
		saveOpenValues();
		
		currentItem = TABLE_SIZE;
						
		while(!table.containsKey(currentItem)){
			currentItem--;
			
		}
		
		displayDetails(currentItem);
	}
	
	private void deleteTheItem()
	{
		table.remove(currentItem);
		JOptionPane.showMessageDialog(null, "Account Deleted");


		currentItem=0;
		while(!table.containsKey(currentItem)){
			currentItem++;
		}
		displayDetails(currentItem);
					
	}
	
	private void modifyTheItem()
	{
		surnameTextField.setEditable(true);
		firstNameTextField.setEditable(true);
		
		openValues = true;
	}
	
	private void setInterestRate()
	{
		String interestRateStr = JOptionPane.showInputDialog("Enter Interest Rate: (do not type the % sign)");
		if(interestRateStr!=null)
		{
			interestRate = Double.parseDouble(interestRateStr);
		}
	}
	
	private void listAllItems()
	{
		JFrame frame = new JFrame("TableDemo");
	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String col[] = {"ID","Number","Name", "Account Type", "Balance", "Overdraft"};
		
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		jTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(jTable);
		jTable.setAutoCreateRowSorter(true);
		
		for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
		   
		    
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
	
	private void closeApp()
	{
		int answer = JOptionPane.showConfirmDialog(BankApplication.this, "Do you want to save before quitting?");
		if (answer == JOptionPane.YES_OPTION) {
			saveFileAs();
			dispose();
		}
		else if(answer == JOptionPane.NO_OPTION)
		{
			dispose();
		}
	}
	
	private void accountDeposit()
	{
		String accNum = JOptionPane.showInputDialog("Account number to deposit into: ");
		boolean found = false;
		
		for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
			if(accNum.equals(entry.getValue().getAccountNumber().trim())){
				found = true;
				String toDeposit = JOptionPane.showInputDialog("Account found, Enter Amount to Deposit: ");
				entry.getValue().setBalance(entry.getValue().getBalance() + Double.parseDouble(toDeposit));
				displayDetails(entry.getKey());
				
			}
		}
		if (!found)
			JOptionPane.showMessageDialog(null, "Account number " + accNum + " not found.");
	}
	
	private void accountWithdraw()
	{
		String accNum = JOptionPane.showInputDialog("Account number to withdraw from: ");
		String toWithdraw = JOptionPane.showInputDialog("Account found, Enter Amount to Withdraw: ");
		
		
		for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
			

			if(accNum.equals(entry.getValue().getAccountNumber().trim())){
				
				
				
				if(entry.getValue().getAccountType().trim().equals("Current")){
					if(Double.parseDouble(toWithdraw) > entry.getValue().getBalance() + entry.getValue().getOverdraft())
						JOptionPane.showMessageDialog(null, "Transaction exceeds overdraft limit");
					else{
						entry.getValue().setBalance(entry.getValue().getBalance() - Double.parseDouble(toWithdraw));
						displayDetails(entry.getKey());
					}
				}
				else if(entry.getValue().getAccountType().trim().equals("Deposit")){
					if(Double.parseDouble(toWithdraw) <= entry.getValue().getBalance()){
						entry.getValue().setBalance(entry.getValue().getBalance()-Double.parseDouble(toWithdraw));
						displayDetails(entry.getKey());
					}
					else
						JOptionPane.showMessageDialog(null, "Insufficient funds.");
				}
			}					
		}
	}
	
	private void calculateInterest()
	{
		for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
			if(entry.getValue().getAccountType().equals("Deposit")){
				double equation = 1 + ((interestRate)/100);
				entry.getValue().setBalance(entry.getValue().getBalance()*equation);
				JOptionPane.showMessageDialog(null, "Balances Updated");
				displayDetails(entry.getKey());
			}
		}
	}
	
	private void openFile()
	{
		readFile();
		currentItem=0;
		while(!table.containsKey(currentItem)){
			currentItem++;
		}
		displayDetails(currentItem);
	}
	

	
	private static void openFileRead()
	{
		table.clear();

		fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

            try 
            {
            	if(fc.getSelectedFile()!=null)
            		input = new RandomAccessFile( fc.getSelectedFile(), "r" );
            }
            catch ( IOException ioException )
            {
            	JOptionPane.showMessageDialog(null, "File Does Not Exist.");
            }
    			
    	}

        
	
	static String fileToSaveAs = "";
	
	private static void openFileWrite()
	   {
		if(fileToSaveAs!=""){
	      try
	      {
	         output = new RandomAccessFile( fileToSaveAs, "rw" );
	         JOptionPane.showMessageDialog(null, "Accounts saved to " + fileToSaveAs);
	      }
	      catch ( IOException ioException )
	      {
	    	  JOptionPane.showMessageDialog(null, "File does not exist.");
	      }
		}
		else
			saveToFileAs();
	   }
	
	
	
	private static void saveToFileAs(){

		fc = new JFileChooser();

		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			fileToSaveAs = file.getName();
			JOptionPane.showMessageDialog(null, "Accounts saved to " + file.getName());
		} 
		
		else 
		{
			JOptionPane.showMessageDialog(null, "Save cancelled by user");
		}
		
		try {
			if(fc.getSelectedFile()==null){
				JOptionPane.showMessageDialog(null, "Cancelled");
			}
			output = new RandomAccessFile(fc.getSelectedFile(), "rw" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void closeFile() 
	   {
	      try
	      {
	         if ( input != null )
	            input.close();
	      }
	      catch ( IOException ioException )
	      {
	         
	    	  JOptionPane.showMessageDialog(null, "Error closing file.");
	      } 
	   }
	
	private static void readRecords()
	{

		RandomAccessBankAccount record = new RandomAccessBankAccount();

		try
		{
			while ( true )
			{
				do
				{
					if(input!=null)
						record.read( input );
				} while ( record.getAccountID() == 0 );



				BankAccount ba = new BankAccount(record.getAccountID(), record.getAccountNumber(), record.getFirstName(),
						record.getSurname(), record.getAccountType(), record.getBalance(), record.getOverdraft());


				Integer key = Integer.valueOf(ba.getAccountNumber().trim());

				int hash = (key%TABLE_SIZE);


				while(table.containsKey(hash)){

					hash = hash+1;
				}

				table.put(hash, ba);


			} 
		} 
		catch ( EOFException eofException )
		{
			return;
		} 
		catch ( IOException ioException )
		{
			JOptionPane.showMessageDialog(null, "Error reading file.");
			System.exit( 1 );
		}
	}

	private static void saveToFile(){
		
		RandomAccessBankAccount record = new RandomAccessBankAccount();

		Scanner input = new Scanner( System.in );


		for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
			record.setAccountID(entry.getValue().getAccountID());
			record.setAccountNumber(entry.getValue().getAccountNumber());
			record.setFirstName(entry.getValue().getFirstName());
			record.setSurname(entry.getValue().getSurname());
			record.setAccountType(entry.getValue().getAccountType());
			record.setBalance(entry.getValue().getBalance());
			record.setOverdraft(entry.getValue().getOverdraft());

			if(output!=null){

				try {
					record.write( output );
				} catch (IOException u) {
					u.printStackTrace();
				}
			}

		}

	}

	private static void saveFile(){
		openFileWrite();
		saveToFile();
		closeFile();
	}
	
	private static void saveFileAs(){
		saveToFileAs();
		saveToFile();	
		closeFile();
	}
	
	private static void readFile(){
	    openFileRead();
	    readRecords();
	    closeFile();		
	}
	
	private void put(int key, BankAccount value){
		int hash = (key%TABLE_SIZE);
	
		while(table.containsKey(key)){
			hash = hash+1;
		
		}
		table.put(hash, value);

	}
}

/*
The task for your second assignment is to construct a system that will allow users to define a data structure representing a collection of records that can be displayed both by means of a dialog that can be scrolled through and by means of a table to give an overall view of the collection contents. 
The user should be able to carry out tasks such as adding records to the collection, modifying the contents of records, and deleting records from the collection, as well as being able to save and retrieve the contents of the collection to and from external random access files.
The records in the collection will represent bank account records with the following fields:

AccountID (this will be an integer unique to a particular account and 
will be automatically generated when a new account record is created)

AccountNumber (this will be a string of eight digits and should also 
be unique - you will need to check for this when creating a new record)

Surname (this will be a string of length 20)

FirstName (this will be a string of length 20)

AccountType (this will have two possible options - "Current " and 
"Deposit" - and again will be selected from a drop down list when 
entering a record)

Balance (this will a real number which will be initialised to 0.0 
and can be increased or decreased by means of transactions)

Overdraft (this will be a real number which will be initialised 
to 0.0 but can be updated by means of a dialog - it only applies 
to current accounts)

You may consider whether you might need more than one class to deal with bank accounts.
The system should be menu-driven, with the following menu options:

Navigate: First, Last, Next, Previous, Find By Account Number 
(allows you to find a record by account number entered via a 
dialog box), Find By Surname (allows you to find a record by 
surname entered via a dialog box),List All (displays the 
contents of the collection as a dialog containing a JTable)

Records: Create, Modify, Delete, Set Overdraft (this should 
use a dialog to allow you to set or update the overdraft for 
a current account), Set Interest Rate (this should allow you 
to set the interest rate for deposit accounts by means of a 
dialog)

Transactions: Deposit, Withdraw (these should use dialogs which
allow you to specify an account number and the amount to withdraw
or deposit, and should check that a withdrawal would not cause
the overdraft limit for a current account to be exceeded, or be 
greater than the balance in a deposit account, before the balance 
is updated), Calculate Interest (this calculates the interest rate 
for all deposit accounts and updates the balances)

File: Open, Save, Save As (these should use JFileChooser dialogs. 
The random access file should be able to hold 25 records. The position 
in which a record is stored and retrieved will be determined by its account 
number by means of a hashing procedure, with a standard method being used when 
dealing with possible hash collisions)

Exit Application (this should make sure that the collection is saved - or that 
the user is given the opportunity to save the collection - before the application closes)

When presenting the results in a JTable for the List All option, the records should be sortable on all fields, but not editable (changing the records or adding and deleting records can only be done through the main dialog).
For all menu options in your program, you should perform whatever validation, error-checking and exception-handling you consider to be necessary.
The programs Person.java and PersonApplication.java (from OOSD2) and TableDemo.java may be of use to you in constructing your interfaces. The set of Java programs used to create, edit and modify random access files will also provide you with a basis for your submission.

*/