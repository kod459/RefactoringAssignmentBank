package bankexercise;

import javax.swing.*;

public interface FileMenuInterface {

	void initComponents();

	JMenu getFileMenu();

	JMenuItem getOpen();

	JMenuItem getSave();

	JMenuItem getSaveAs();
	
}
