package main;

import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

public class GUI {

	Notes n;
	MidiParser midi;

	public GUI() {
		JFrame frame = new JFrame();

		JPanel pnPanel0;
		JButton btPlayButton;
		JTextArea taText;

		JPanel pnPanel1;
		JLabel lbCd;
		JSpinner spnCd;
		JLabel lbFps;
		JSpinner spnFpsSpinner;
		JLabel lbDelayLabel;
		JSpinner spnDelaySpinner;

		pnPanel0 = new JPanel();
		GridBagLayout gbPanel0 = new GridBagLayout();
		GridBagConstraints gbcPanel0 = new GridBagConstraints();
		pnPanel0.setLayout( gbPanel0 );

		btPlayButton = new JButton( "Play"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 18;
		gbcPanel0.gridwidth = 20;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btPlayButton, gbcPanel0 );
		pnPanel0.add( btPlayButton );

		taText = new JTextArea(2,10);
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 6;
		gbcPanel0.gridwidth = 20;
		gbcPanel0.gridheight = 12;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( taText, gbcPanel0 );
		pnPanel0.add( taText );

		pnPanel1 = new JPanel();
		pnPanel1.setBorder( BorderFactory.createTitledBorder( "Settings" ) );
		GridBagLayout gbPanel1 = new GridBagLayout();
		GridBagConstraints gbcPanel1 = new GridBagConstraints();
		pnPanel1.setLayout( gbPanel1 );

		lbCd = new JLabel( "WaitMultiplier:"  );
		gbcPanel1.gridx = 0;
		gbcPanel1.gridy = 0;
		gbcPanel1.gridwidth = 4;
		gbcPanel1.gridheight = 2;
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
		gbcPanel1.gridheight = 2;
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
		gbcPanel1.gridheight = 2;
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
		gbcPanel1.gridheight = 2;
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
		gbcPanel1.gridheight = 2;
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
		gbcPanel1.gridheight = 2;
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

		taText.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					String filePath = droppedFiles.get(0).getPath();
					midi = new MidiParser(filePath);
					midi.getInstruments(filePath);
					InstrumentSelector is = new InstrumentSelector(midi.instruments);
					int selectedInstrument = is.showDialogue();
					System.out.println(selectedInstrument);
					
					taText.setText(midi.getNotes(filePath, selectedInstrument));
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		btPlayButton.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread.sleep((int)spnDelaySpinner.getValue());
					n = new Notes((int) spnFpsSpinner.getValue());
					Notes.waitMultiplier = (double) spnCd.getValue();


					String text = taText.getText().replace("[", "(").replace("]",")").replace(",","");
					n.parseSlowdownConstant(text);



					for (String line : text.split("\\n")){
						for (String l : line.split(" ")){
							n.play(l);
						}
					};

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
}
