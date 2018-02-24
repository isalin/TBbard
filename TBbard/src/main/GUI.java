package main;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

public class GUI {

	Notes n;
	MidiParser midi;
	
	String filePath = "";
	/** Field to hold the keybind that should stop the playback
	 * TODO Make this configurable by the user
	 */
	int stopPlayback = KeyEvent.VK_ESCAPE;

	public GUI() {
		JFrame frame = new JFrame();

		JPanel pnPanel0;
		JButton btPlayButton;
		JButton btStopButton; // Button to stop the performance
		JTextArea taText;
		JScrollPane spText;

		JPanel pnPanel1;
		JLabel lbCd;
		JSpinner spnCd;
		JLabel lbFps;
		JSpinner spnFpsSpinner;
		JLabel lbLabel4;
		JComboBox cmbOctaveTargetCombo;
		JLabel lbDelayLabel;
		JSpinner spnDelaySpinner;
		JCheckBox loopCheckBox;
		JComboBox cmbSelectedInstrument;
		JLabel lbLabel5;
		
		pnPanel0 = new JPanel();
		GridBagLayout gbPanel0 = new GridBagLayout();
		GridBagConstraints gbcPanel0 = new GridBagConstraints();
		pnPanel0.setLayout( gbPanel0 );

		btPlayButton = new JButton( "Play"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 18;
		gbcPanel0.gridwidth = 10;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btPlayButton, gbcPanel0 );
		pnPanel0.add( btPlayButton );
		
		btStopButton = new JButton( "Stop"  );
		gbcPanel0.gridx = 10;
		gbcPanel0.gridy = 18;
		gbcPanel0.gridwidth = 10;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btStopButton, gbcPanel0 );
		pnPanel0.add( btStopButton );

		taText = new JTextArea(2,10);
		
		spText = new JScrollPane( taText );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 6;
		gbcPanel0.gridwidth = 20;
		gbcPanel0.gridheight = 12;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( spText, gbcPanel0 );
		spText.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		pnPanel0.add( spText );

		pnPanel1 = new JPanel();
		pnPanel1.setBorder( BorderFactory.createTitledBorder( "Settings" ) );
		GridBagLayout gbPanel1 = new GridBagLayout();
		GridBagConstraints gbcPanel1 = new GridBagConstraints();
		pnPanel1.setLayout( gbPanel1 );

		lbCd = new JLabel( "WaitMultiplier:"  );
		gbcPanel1.gridx = 0;
		gbcPanel1.gridy = 0;
		gbcPanel1.gridwidth = 4;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 1;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( lbCd, gbcPanel1 );
		pnPanel1.add( lbCd );

		SpinnerNumberModel m = new SpinnerNumberModel(1.0, 0, 100.0, 0.1);
		spnCd = new JSpinner(m);
		
		gbcPanel1.gridx = 4;
		gbcPanel1.gridy = 0;
		gbcPanel1.gridwidth = 4;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 0;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( spnCd, gbcPanel1 );
		pnPanel1.add( spnCd );
		
		

		lbFps = new JLabel( "   Min FPS (Delay=0):"  );
		gbcPanel1.gridx = 8;
		gbcPanel1.gridy = 0;
		gbcPanel1.gridwidth = 4;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 1;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( lbFps, gbcPanel1 );
		pnPanel1.add( lbFps );

		spnFpsSpinner = new JSpinner( );
		gbcPanel1.gridx = 12;
		gbcPanel1.gridy = 0;
		gbcPanel1.gridwidth = 3;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 0;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( spnFpsSpinner, gbcPanel1 );
		pnPanel1.add( spnFpsSpinner );

		lbDelayLabel = new JLabel( "   Start delay:"  );
		gbcPanel1.gridx = 15;
		gbcPanel1.gridy = 0;
		gbcPanel1.gridwidth = 2;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 1;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( lbDelayLabel, gbcPanel1 );
		pnPanel1.add( lbDelayLabel );

		spnDelaySpinner = new JSpinner( );
		gbcPanel1.gridx = 17;
		gbcPanel1.gridy = 0;
		gbcPanel1.gridwidth = 3;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 0;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( spnDelaySpinner, gbcPanel1 );
		pnPanel1.add( spnDelaySpinner );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 0;
		gbcPanel0.gridwidth = 20;
		gbcPanel0.gridheight = 6;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( pnPanel1, gbcPanel0 );
		pnPanel0.add( pnPanel1 );


		spnDelaySpinner.setValue(5000);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spnCd,"0.00"); 
		spnCd.setEditor(editor);
		editor = new JSpinner.NumberEditor(spnDelaySpinner, "#"); 
		spnDelaySpinner.setEditor(editor);
		spnCd.setValue(1.0);
		
	    JComponent comp = spnFpsSpinner.getEditor();
	    JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
	    DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
	    formatter.setCommitsOnValidEdit(true);
	    spnFpsSpinner.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	        	int fps = (int) spnFpsSpinner.getValue();
	        	if(fps == 0) fps = 1;
	        	lbFps.setText("  Min FPS (Delay=" + (int) Math.ceil((double) 1000/fps) + "):");
	        }
	    });
	    spnFpsSpinner.setValue(59);
	    
	    lbLabel4 = new JLabel( "Octave Target:"  );
	    gbcPanel1.gridx = 0;
	    gbcPanel1.gridy = 1;
	    gbcPanel1.gridwidth = 4;
	    gbcPanel1.gridheight = 1;
	    gbcPanel1.fill = GridBagConstraints.BOTH;
	    gbcPanel1.weightx = 1;
	    gbcPanel1.weighty = 1;
	    gbcPanel1.anchor = GridBagConstraints.NORTH;
	    gbPanel1.setConstraints( lbLabel4, gbcPanel1 );
	    pnPanel1.add( lbLabel4 );

	    String []dataOctaveTargetCombo = {"-1", "0", "1", "2", "3", "4", "5 (Default)", "6", "7", "8", "9"};
	    cmbOctaveTargetCombo = new JComboBox( dataOctaveTargetCombo );
	    gbcPanel1.gridx = 4;
	    gbcPanel1.gridy = 1;
	    gbcPanel1.gridwidth = 6;
	    gbcPanel1.gridheight = 1;
	    gbcPanel1.fill = GridBagConstraints.BOTH;
	    gbcPanel1.weightx = 1;
	    gbcPanel1.weighty = 1;
	    gbcPanel1.anchor = GridBagConstraints.NORTH;
	    gbPanel1.setConstraints( cmbOctaveTargetCombo, gbcPanel1 );
	    pnPanel1.add( cmbOctaveTargetCombo );
	    cmbOctaveTargetCombo.setSelectedIndex(6);
	    
	    loopCheckBox = new JCheckBox( "Loop"  );
	    gbcPanel1.gridx = 18;
	    gbcPanel1.gridy = 1;
	    gbcPanel1.gridwidth = 4;
	    gbcPanel1.gridheight = 1;
	    gbcPanel1.fill = GridBagConstraints.BOTH;
	    gbcPanel1.weightx = 1;
	    gbcPanel1.weighty = 0;
	    gbcPanel1.anchor = GridBagConstraints.NORTH;
	    gbPanel1.setConstraints( loopCheckBox, gbcPanel1 );
	    pnPanel1.add( loopCheckBox );
	    

	    String []dataSelectedInstrument = { "" };
	    cmbSelectedInstrument = new JComboBox( dataSelectedInstrument );
	    gbcPanel1.gridx = 3;
	    gbcPanel1.gridy = 2;
	    gbcPanel1.gridwidth = 17;
	    gbcPanel1.gridheight = 1;
	    gbcPanel1.fill = GridBagConstraints.BOTH;
	    gbcPanel1.weightx = 1;
	    gbcPanel1.weighty = 0;
	    gbcPanel1.anchor = GridBagConstraints.NORTH;
	    gbPanel1.setConstraints( cmbSelectedInstrument, gbcPanel1 );
	    pnPanel1.add( cmbSelectedInstrument );

	    lbLabel5 = new JLabel( "Instrument:"  );
	    gbcPanel1.gridx = 0;
	    gbcPanel1.gridy = 2;
	    gbcPanel1.gridwidth = 3;
	    gbcPanel1.gridheight = 1;
	    gbcPanel1.fill = GridBagConstraints.BOTH;
	    gbcPanel1.weightx = 1;
	    gbcPanel1.weighty = 1;
	    gbcPanel1.anchor = GridBagConstraints.NORTH;
	    gbPanel1.setConstraints( lbLabel5, gbcPanel1 );
	    pnPanel1.add( lbLabel5 );
	    

		taText.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					filePath = droppedFiles.get(0).getPath();
					frame.setTitle("Processing...");
					midi = new MidiParser(filePath);
					midi.getInstruments(filePath);
					//InstrumentSelector is = new InstrumentSelector(midi.instruments);
					
					//Update instruments
					cmbSelectedInstrument.removeAllItems();
					for(String instrument : midi.instruments){
						cmbSelectedInstrument.addItem(instrument);
					}
				    //cmbSelectedInstrument = new JComboBox(midi.instruments);
				    
				    
					//int selectedInstrument = is.showDialogue();
					System.out.println(cmbSelectedInstrument.getSelectedIndex());
					midi.getNotes(filePath, cmbSelectedInstrument.getSelectedIndex(), cmbOctaveTargetCombo.getSelectedIndex()-1);
					
					taText.setText(midi.getSheet(0));
					setOpenFile(frame, new File(filePath).getName());
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});					

		
		cmbSelectedInstrument.addItemListener(new ItemListener() {
			
			
	        public void itemStateChanged(ItemEvent arg0) {
	        	System.out.println("Action: " + cmbSelectedInstrument.getSelectedIndex());
	        	taText.setText(midi.getSheet(cmbSelectedInstrument.getSelectedIndex()));
	        }
	    });
		
		btPlayButton.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread() { // Making a new thread instead of sleeping the old one
					@Override
					public void run() {
						try {
							Thread.sleep((int) spnDelaySpinner.getValue());
							do{
							n = new Notes((int) spnFpsSpinner.getValue());
							Notes.waitMultiplier = (double) spnCd.getValue();


							String text = taText.getText().replace("[", "(").replace("]",")").replace(",","");



							for (String line : text.split("\\n")){
								for (String l : line.split(" ")){
									n.play(l);
								}
							}
							Thread.sleep(1000); //This lets the game's music buffer catch up if the looping song has a very high tempo
							
							}while(loopCheckBox.isSelected() && n.running);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}.start(); // We're using a new thread to be able to access Stop still.
			}
		});
		
		/**
		 * Action Listener for the stop button
		 */
		btStopButton.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				n.running = false;
			}
		});

		frame.add(pnPanel0);
		frame.setSize(600, 500);
		frame.setTitle("TBbard");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);	
		frame.requestFocusInWindow();
	}
	
	public void setOpenFile(JFrame frame, String fileName){
		System.out.println("Setting title to: " + fileName);
		if(fileName.equals("")) frame.setTitle("TBbard");
		else frame.setTitle("TBbard - " + fileName);
	}

	
}
