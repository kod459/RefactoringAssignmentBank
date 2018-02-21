package bankexercise;

import javax.swing.*;

public class FileMenu implements FileMenuInterface {
	JMenu fileMenu;
	JMenuItem open, save, saveAs;
		
	public FileMenu(){
		
		initComponents();
	}
	
	@Override
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
}

