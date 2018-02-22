package bankexercise;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import javax.swing.*;

public class FileMenu {
	JMenu fileMenu;
	JMenuItem open, save, saveAs;
	private static RandomAccessFile input;
	private static RandomAccessFile output;
	static String fileToSaveAs = "";
	static JFileChooser fc;
		
	public FileMenu(){
		
		initComponents();
	}
	
	public void initComponents() {
		
		fileMenu = new JMenu("File");

		open = new JMenuItem("Open File");
		save = new JMenuItem("Save File");
		saveAs = new JMenuItem("Save As");

		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveAs);
	}
	
	public JMenu getFileMenu() {
		return fileMenu;
	}
	
	public JMenuItem getOpen() {
		return open;
	}

	public JMenuItem getSave() {
		return save;
	}

	public JMenuItem getSaveAs() {
		return saveAs;
	}
	
	public static void openAFile()
	{
		readFile();
		BankApplication.currentItem=0;
		while(!BankApplication.table.containsKey(BankApplication.currentItem)){
			BankApplication.currentItem++;
		}
		NavigateMenu.firstItemInList();
	}

	    public static void openFileRead() {
	    	
	        BankApplication.table.clear();
	        
	        fc = new JFileChooser();
	        
	        int returnVal = fc.showOpenDialog(null);
	        
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	        }
	        openFile();
	    }

	    public static void openFile() {
	        try
	        {
	            if (fc.getSelectedFile() != null)
	                input = new RandomAccessFile(fc.getSelectedFile(), "r");
	        }
	        catch (IOException ioException) {
	            JOptionPane.showMessageDialog(null, "File Does Not Exist.");
	        }
	    }

	    public static void openFileWrite() {
	    	if (fileToSaveAs != "") {
	    		try
	    		{
	    			output = new RandomAccessFile(fileToSaveAs, "rw");
	    			JOptionPane.showMessageDialog(null, "Accounts saved to " + fileToSaveAs);
	    		}
	    		catch (IOException ioException) {
	    			JOptionPane.showMessageDialog(null, "File does not exist.");
	    		}
	    	} 
	    	else
	    	{
	    		saveToFileAs();
	    	}

	    }

	    public static void saveToFileAs() {
	        fc = new JFileChooser();
	        int returnVal = fc.showSaveDialog(null);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            fileToSaveAs = file.getName();
	            JOptionPane.showMessageDialog(null, "Accounts saved to " + file.getName());
	        } else {
	            JOptionPane.showMessageDialog(null, "Save cancelled by user");
	        }
	        validateSaveToFileAs();
	    }

	    public static void validateSaveToFileAs() {
	        try {
	            if (fc.getSelectedFile() == null) {
	                JOptionPane.showMessageDialog(null, "Cancelled");
	            } else
	                output = new RandomAccessFile(fc.getSelectedFile(), "rw");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void closeFile() {
	        try
	        {
	            if (input != null)
	                input.close();
	        }
	        catch (IOException ioException) {
	            JOptionPane.showMessageDialog(null, "Error closing file.");//System.exit( 1 );
	        }
	    }
	    
	    public static void readRecords() {
	        RandomAccessBankAccount record = new RandomAccessBankAccount();
	        readRecord(record);
	    }

	    public static void readRecord(RandomAccessBankAccount record) {
	        try
	        {
	            while (true) {
	                if (input != null)
	                    record.read(input);
	                displayRecords(record);
	            }
	        }
	        catch (EOFException eofException)
	        {
	            return;
	        }
	        catch (IOException ioException) {
	            JOptionPane.showMessageDialog(null, "Error reading file.");
	            System.exit(1);
	        }
	    }

	    public static void displayRecords(RandomAccessBankAccount record) {
	        BankAccount ba = new BankAccount(record.getAccountID(),record.getAccountNumber(), record.getFirstName(),
	                record.getSurname(), record.getAccountType(), record.getBalance(), record.getOverdraft());
	        BankApplication.table.put(ba.getAccountID(), ba);
	    }

	    public static void saveToFile() {

	        RandomAccessBankAccount record = new RandomAccessBankAccount();

	        for (Map.Entry<Integer, BankAccount> entry : BankApplication.table.entrySet()) {
	            record.setAccountNumber(entry.getValue().getAccountNumber());
	            record.setFirstName(entry.getValue().getFirstName());
	            record.setAccountID(entry.getValue().getAccountID());
	            record.setSurname(entry.getValue().getSurname());
	            record.setAccountType(entry.getValue().getAccountType());
	            record.setBalance(entry.getValue().getBalance());
	            record.setOverdraft(entry.getValue().getOverdraft());
	            if (output != null) {
	                try {
	                    record.write(output);
	                } catch (IOException u) {
	                    u.printStackTrace();
	                }
	            }
	        }
	    }

	    public static void saveFile() {
	        openFileWrite();
	        saveToFile();
	        closeFile();
	    }

	    public static void saveFileAs() {
	        saveToFileAs();
	        saveToFile();
	        closeFile();
	    }

	    public static void readFile() {
	        openFileRead();
	        readRecords();
	        closeFile();
	    }
	
	
}