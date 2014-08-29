package csvHasher.gui;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import csvHasher.logic.HasherLogic;



public class Gui {

	private Frame fFrame;
	private Panel fPanel;
	private JLabel fFileNameLabel;
	private HasherLogic fHasherLogic = new HasherLogic();
	
	
	public Gui(){
		// basic GUI setup
		fFrame = new JFrame("Csv Hasher");
		fFrame.setSize(800, 600);
		fPanel = new Panel();
		fPanel.setLayout(new BoxLayout(fPanel, BoxLayout.Y_AXIS));
		fFrame.add(fPanel);

		// add buttons for file selection
		JButton buttonforOlderFile = new JButton("CSV Datei auswählen");
		buttonforOlderFile.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonforOlderFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleClickOnOldFileButton();
			}
		});
		fPanel.add(buttonforOlderFile);
		
		//file label
		fFileNameLabel = new JLabel("Es wurde noch keine CSV Datei ausgewählt");
		fFileNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		fPanel.add(fFileNameLabel);
		
		// add buttons for starting the hashing
		JButton startHashingButton = new JButton("Hash");
		startHashingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startHashingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleClickOnHashButton();
			}
		});
		fPanel.add(startHashingButton);
		
		// close on exit
		fFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	private void handleClickOnOldFileButton(){
		
		String chosenFilename = "";
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Bitte eine csv Datei auswählen", "csv");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(fFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			chosenFilename = chooser.getSelectedFile().getAbsolutePath();
		} 
		
		if (chosenFilename.equals("")) {
			return;
		}
		fFileNameLabel.setText(chosenFilename);
		fHasherLogic.setInputFile(chosenFilename);
	}
	
	private void handleClickOnHashButton(){
		fHasherLogic.startHashing();
	}
	
	public void show() {
		fFrame.setVisible(true);
	}
}