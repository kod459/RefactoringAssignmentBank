package bankexercise;

import javax.swing.*;

public class RecordMenu {
	
private JMenu recordsMenu;
	
	private JMenuItem createItem, modifyItem, deleteItem, setOverdraft, setInterest;
	
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
}