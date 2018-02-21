package bankexercise;

import javax.swing.*;

public interface RecordMenuInterface {

	void initComponents();

	JMenu getRecordsMenu();

	JMenuItem getCreateItem();

	JMenuItem getModifyItem();

	JMenuItem getDeleteItem();

	JMenuItem getSetOverdraft();

	JMenuItem getSetInterest();
}
