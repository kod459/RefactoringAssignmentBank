package bankexercise;

import javax.swing.*;

public class NavigateMenu implements NavigateMenuInterface {
	private JMenu navigateMenu;
	private JMenuItem nextItem, prevItem, firstItem, lastItem, findByAccount, findBySurname, listAll;
	private JButton firstItemButton, lastItemButton, nextItemButton, prevItemButton;
	
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

}
