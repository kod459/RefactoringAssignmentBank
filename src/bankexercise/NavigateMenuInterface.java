package bankexercise;

import javax.swing.*;

public interface NavigateMenuInterface {
	
	
	void initComponents();

	
	JMenu getNavigateMenu();

	JMenuItem getListAll();

	JMenuItem getNextItem();

	JMenuItem getPrevItem();

	JMenuItem getFirstItem();

	JMenuItem getLastItem();
	
	JButton getNextItemButton();

	JButton getPrevItemButton();
	
	JButton getFirstItemButton();

	JButton getLastItemButton();

	JMenuItem getFindByAccount();

	JMenuItem getFindBySurname();

	
}
