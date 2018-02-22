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
	final static int TABLE_SIZE = 29;
	static private final String newline = "\n";
	
	NavigateMenu navigateMenu = new NavigateMenu();
	RecordMenu recordsMenu = new RecordMenu();
	TransactionMenu transactionsMenu = new TransactionMenu();
	FileMenu fileMenu = new FileMenu();

	
	JMenuBar menuBar;
	JMenu exitMenu;
	JMenuItem closeApp;
	
	JLabel accountIDLabel, accountNumberLabel, firstNameLabel, surnameLabel, accountTypeLabel, balanceLabel, overdraftLabel;
	static JTextField accountIDTextField;
	static JTextField accountNumberTextField;
	static JTextField firstNameTextField;
	static JTextField surnameTextField;
	static JTextField accountTypeTextField;
	static JTextField balanceTextField;
	static JTextField overdraftTextField;
	
	static JFileChooser fc;
	JTable jTable;
	static double interestRate;
	
	static int currentItem = 0;
	
	
	static boolean openValues;
	
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
				NavigateMenu.firstItemInList();
			}
		};
		
		ActionListener last = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NavigateMenu.lastItemInList();
			}
		};
		
		ActionListener next = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				NavigateMenu.nextItem();
			}
				
		};
		
		ActionListener prev = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NavigateMenu.prevItem();
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
				RecordMenu.deleteTheItem();
			}
		});
		
		recordsMenu.getCreateItem().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new CreateBankDialog(table);		
			}
		});
		
		
		recordsMenu.getModifyItem().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				RecordMenu.modifyTheItem();
			}
		});
		
		recordsMenu.getInterest().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				RecordMenu.setInterestRate();
			}
		});
		
		navigateMenu.getListAll().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NavigateMenu.listAllItems();
			}
		});
		
		fileMenu.getOpen().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileMenu.openAFile();
			}
		});
		
		fileMenu.getSave().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileMenu.saveFile();
			}
		});
		
		fileMenu.getSaveAs().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileMenu.saveFileAs();
			}
		});
		
		closeApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeApp();
			}
		});	
		
		navigateMenu.getFindBySurname().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NavigateMenu.findSurname();
			}
		});
		
		navigateMenu.getFindByAccount().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NavigateMenu.findAccount();
			}
		});
		
		transactionsMenu.getDeposit().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				TransactionMenu.accountDeposit();
			}
		});
		
		transactionsMenu.getWithdraw().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				TransactionMenu.accountWithdraw();
			}
		});
		
		transactionsMenu.getCalcInterest().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				TransactionMenu.calculateInterest();
			}
		});		
	}
	
	
	public static void saveOpenValues(){		
		if (openValues){
			surnameTextField.setEditable(false);
			firstNameTextField.setEditable(false);
				
			table.get(currentItem).setSurname(surnameTextField.getText());
			table.get(currentItem).setFirstName(firstNameTextField.getText());
		}
	}	
	
	 public static void displayDetails(int currentItem) {

	        accountIDTextField.setText(table.get(currentItem).getAccountID() + "");
	        accountNumberTextField.setText(table.get(currentItem).getAccountNumber());
	        surnameTextField.setText(table.get(currentItem).getSurname());
	        firstNameTextField.setText(table.get(currentItem).getFirstName());
	        accountTypeTextField.setText(table.get(currentItem).getAccountType());
	        balanceTextField.setText(table.get(currentItem).getBalance() + "");
	        if (accountTypeTextField.getText().trim().equals("Current"))
	            overdraftTextField.setText(table.get(currentItem).getOverdraft() + "");
	        else
	            overdraftTextField.setText("Only applies to current accs");
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
	
	
	private void closeApp()
	{
		int answer = JOptionPane.showConfirmDialog(BankApplication.this, "Do you want to save before quitting?");
		if (answer == JOptionPane.YES_OPTION) {
			FileMenu.saveFileAs();
			dispose();
		}
		else if(answer == JOptionPane.NO_OPTION)
		{
			dispose();
		}
	}
	
	
}