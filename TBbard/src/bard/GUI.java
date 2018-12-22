package bard;

import javafx.event.EventHandler;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
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
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.DefaultFormatter;

public class GUI {

	// Notes n;
	Thread playingThread;
	Thread countdownThread;
	MidiParser midi;

	String filePath = "";
	boolean fileLoaded = false;

	/**
	 * Field to hold the keybind that should stop the playback TODO Make this
	 * configurable by the user
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
		JCheckBox keyboardCheckBox;
		JCheckBox holdCheckBox;
		JCheckBox loopCheckBox;
		JCheckBox trueTimingsCheckBox;
		JComboBox cmbSelectedInstrument;
		JLabel lbLabel5;
		JLabel lbLabel6;

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


		//spnDelaySpinner.setValue(3);
		spnDelaySpinner.setValue(Settings.LoadInt("delay"));
		if((int)spnDelaySpinner.getValue() <= 0) spnDelaySpinner.setValue(3);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spnCd,"0.00"); 
		spnCd.setEditor(editor);
		editor = new JSpinner.NumberEditor(spnDelaySpinner, "#"); 
		spnDelaySpinner.setEditor(editor);
		//spnCd.setValue(1.0);
		spnCd.setValue(Settings.LoadDouble("waitmult"));
		if((double)spnCd.getValue() <= 0) spnCd.setValue((double)1);


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
		spnFpsSpinner.setValue(Settings.LoadInt("fps"));
		if((int)spnFpsSpinner.getValue() <= 0) spnFpsSpinner.setValue(59);

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
		loopCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Settings.SaveBool("loop", loopCheckBox.isSelected());
			}
		});
		loopCheckBox.setSelected(Settings.LoadBool("loop"));

		trueTimingsCheckBox = new JCheckBox( "True Timings"  );
		gbcPanel1.gridx = 20;
		gbcPanel1.gridy = 1;
		gbcPanel1.gridwidth = 4;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 0;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( trueTimingsCheckBox, gbcPanel1 );
		pnPanel1.add( trueTimingsCheckBox );
		trueTimingsCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Settings.SaveBool("truetimings", trueTimingsCheckBox.isSelected());
			}
		});
		trueTimingsCheckBox.setSelected(Settings.LoadBool("truetimings"));

		keyboardCheckBox = new JCheckBox( "Use full keyboard layout"  );
		keyboardCheckBox.setSelected(false);
		gbcPanel1.gridx = 10;
		gbcPanel1.gridy = 1;
		gbcPanel1.gridwidth = 1;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 0;
		gbcPanel1.weighty = 0;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( keyboardCheckBox, gbcPanel1 );
		pnPanel1.add( keyboardCheckBox );
		keyboardCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Settings.SaveBool("fullkeyboard", keyboardCheckBox.isSelected());
			}
		});
		keyboardCheckBox.setSelected(Settings.LoadBool("fullkeyboard"));

		lbLabel6 = new JLabel("<html><a href=\"" + Keyboard.IMG + "\">[?]</a></html>");
		lbLabel6.setCursor(new Cursor(Cursor.HAND_CURSOR));
		gbcPanel1.gridx = 11;
		gbcPanel1.gridy = 1;
		gbcPanel1.gridwidth = 1;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 0;
		gbcPanel1.weighty = 0;
		gbcPanel1.anchor = GridBagConstraints.SOUTH;
		gbPanel1.setConstraints( lbLabel6, gbcPanel1 );
		pnPanel1.add( lbLabel6 );
		lbLabel6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					JFrame keyFrame = new JFrame();
					JPanel panel = new JPanel(); 
					panel.setSize(500,640);
					//panel.setBackground(Color.CYAN); 
					ImageIcon icon = new ImageIcon(new ImageIcon(Main.class.getResource("/keyboardLayout.png")).getImage()); 
					JLabel label = new JLabel(); 
					label.setIcon(icon); 
					panel.add(label);
					keyFrame.add(panel);
					keyFrame.setAlwaysOnTop(true);
					keyFrame.setSize(icon.getIconWidth()+25, icon.getIconHeight()+50);
					keyFrame.setTitle("TBbard - Full keyboard layout");
					keyFrame.setIconImage(new ImageIcon(Main.class.getResource("/icon.png")).getImage());
					keyFrame.setVisible(true);                        
				} catch (Exception ex) {
					//It looks like there's a problem
				}
			}
		});

		holdCheckBox = new JCheckBox( "Hold long notes"  );
		holdCheckBox.setSelected(true);
		gbcPanel1.gridx = 14;
		gbcPanel1.gridy = 1;
		gbcPanel1.gridwidth = 4;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 0;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( holdCheckBox, gbcPanel1 );
		pnPanel1.add( holdCheckBox );
		holdCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Settings.SaveBool("hold", holdCheckBox.isSelected());
			}
		});
		holdCheckBox.setSelected(Settings.LoadBool("hold"));


		String []dataSelectedInstrument = { "" };
		cmbSelectedInstrument = new JComboBox( dataSelectedInstrument );
		gbcPanel1.gridx = 3;
		gbcPanel1.gridy = 2;
		gbcPanel1.gridwidth = 16;
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

		JButton openBtn = new JButton("Open");
		gbcPanel1.gridx = 17;
		gbcPanel1.gridy = 2;
		gbcPanel1.gridwidth = 18;
		gbcPanel1.gridheight = 1;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 1;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( openBtn, gbcPanel1 );
		pnPanel1.add( openBtn );

		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		JFileChooser fc = new JFileChooser(new File(s));
		fc.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "MIDI Files (.mid)";
			}

			@Override
			public boolean accept(File f) {
				if(f.getName().toLowerCase().matches(".+\\.mid") || f.isDirectory()) return true;
				return false;
			}
		});
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		fc.setPreferredSize(new Dimension((int)(width*0.5), (int)(height*0.5)));
		openBtn.addActionListener( new ActionListener()
		{
			@Override
			public synchronized void actionPerformed(ActionEvent e)
			{
				try {
					int selection = fc.showOpenDialog(frame);
					if(selection == JFileChooser.CANCEL_OPTION) return;


					File selFile = fc.getSelectedFile();

					filePath = selFile.getAbsolutePath();
					frame.setTitle("Processing...");
					midi = new MidiParser(filePath, trueTimingsCheckBox.isSelected());
					midi.getInstruments(filePath);
					//InstrumentSelector is = new InstrumentSelector(midi.instruments);


					//cmbSelectedInstrument = new JComboBox(midi.instruments);


					//int selectedInstrument = is.showDialogue();
					System.out.println(cmbSelectedInstrument.getSelectedIndex());
					midi.getNotes(filePath, cmbSelectedInstrument.getSelectedIndex(), cmbOctaveTargetCombo.getSelectedIndex()-1, holdCheckBox.isSelected());
					//Update instruments
					cmbSelectedInstrument.removeAllItems();
					for(String instrument : midi.shownInstruments){
						if(instrument == null) instrument = "";
						cmbSelectedInstrument.addItem(instrument);
					}

					taText.setText(midi.getSheet((String)cmbSelectedInstrument.getItemAt(0), cmbOctaveTargetCombo.getSelectedIndex()));

					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(midi.getOctaveQuality((String)cmbSelectedInstrument.getSelectedItem()));
					cmbOctaveTargetCombo.setModel(model);
					cmbOctaveTargetCombo.setSelectedIndex(midi.getHighestQualityOctave((String)cmbSelectedInstrument.getSelectedItem()));
					taText.setText(midi.getSheet((String)cmbSelectedInstrument.getSelectedItem(), cmbOctaveTargetCombo.getSelectedIndex()));


					setOpenFile(frame, new File(filePath).getName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});




		taText.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					filePath = droppedFiles.get(0).getPath();
					frame.setTitle("Processing...");
					midi = new MidiParser(filePath, trueTimingsCheckBox.isSelected());
					midi.getInstruments(filePath);
					//InstrumentSelector is = new InstrumentSelector(midi.instruments);


					//cmbSelectedInstrument = new JComboBox(midi.instruments);


					//int selectedInstrument = is.showDialogue();
					System.out.println(cmbSelectedInstrument.getSelectedIndex());
					midi.getNotes(filePath, cmbSelectedInstrument.getSelectedIndex(), cmbOctaveTargetCombo.getSelectedIndex()-1, holdCheckBox.isSelected());

					//Update instruments
					cmbSelectedInstrument.removeAllItems();
					for(String instrument : midi.instruments){
						System.out.println("--- REAL instrument: " + instrument);
						
					}
					
					for(String instrument : midi.shownInstruments){
						System.out.println("Shown instrument: " + instrument);
						if(instrument == null) instrument = "";
						cmbSelectedInstrument.addItem(instrument);
					}

					taText.setText(midi.getSheet((String)cmbSelectedInstrument.getItemAt(0), cmbOctaveTargetCombo.getSelectedIndex()));


					DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(midi.getOctaveQuality((String)cmbSelectedInstrument.getSelectedItem()));
					cmbOctaveTargetCombo.setModel(model);

					cmbOctaveTargetCombo.setSelectedIndex(midi.getHighestQualityOctave((String)cmbSelectedInstrument.getSelectedItem()));
					taText.setText(midi.getSheet((String)cmbSelectedInstrument.getSelectedItem(), cmbOctaveTargetCombo.getSelectedIndex()));


					setOpenFile(frame, new File(filePath).getName());

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});					


		cmbSelectedInstrument.addItemListener(new ItemListener() {	
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println("Instrument dropdown selection: " + cmbSelectedInstrument.getSelectedIndex());
				taText.setText(midi.getSheet((String)cmbSelectedInstrument.getSelectedItem(), cmbOctaveTargetCombo.getSelectedIndex()));
				if(cmbSelectedInstrument.getSelectedIndex() == -1) return;
				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(midi.getOctaveQuality((String)cmbSelectedInstrument.getSelectedItem()));
				cmbOctaveTargetCombo.setModel(model);
				cmbOctaveTargetCombo.setSelectedIndex(midi.getHighestQualityOctave((String)cmbSelectedInstrument.getSelectedItem()));
				taText.setText(midi.getSheet((String)cmbSelectedInstrument.getSelectedItem(), cmbOctaveTargetCombo.getSelectedIndex()));
			}
		});

		cmbOctaveTargetCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fileLoaded == false) return;
				System.out.println("Octave target dropdown selection: " + cmbOctaveTargetCombo.getSelectedIndex());
				taText.setText(midi.getSheet((String)cmbSelectedInstrument.getSelectedItem(), cmbOctaveTargetCombo.getSelectedIndex()));
			}
		});

		btPlayButton.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				taText.requestFocusInWindow();
				if(countdownThread != null && countdownThread.isAlive()) {
					return;
				}

				if(btPlayButton.getText().equals("Playing")) {
					playingThread.suspend();
					btPlayButton.setText("Paused");
					return;
				} else
					if(btPlayButton.getText().equals("Paused")) {
						countdownThread = new Thread() { // Making a new thread instead of sleeping the old one
							@Override
							public void run() {
								try {
									double countdown = Math.ceil(((int)spnDelaySpinner.getValue()));
									while(countdown > 0){
										System.out.println("Countdown (ms): " + countdown);
										btPlayButton.setText((int)countdown + "...");
										Thread.sleep(1000);
										countdown--;
									}
								} catch(Exception e) {
									
								}
								playingThread.resume();
								btPlayButton.setText("Playing");
							}
						};
						countdownThread.start();

						
						return;
					}

				playingThread = new Thread() { // Making a new thread instead of sleeping the old one
					@Override
					public void run() {
						try {
							Settings.SaveInt("fps", (int)spnFpsSpinner.getValue());
							Settings.SaveInt("delay", (int)spnDelaySpinner.getValue());
							Settings.SaveDouble("waitmult", (double)spnCd.getValue());
							Notes n = new Notes((int) spnFpsSpinner.getValue());
							n.running = true;
							n.holdNotes = holdCheckBox.isSelected();
							n.fullKeyboard = keyboardCheckBox.isSelected();
							n.waitMultiplier = (double)spnCd.getValue();
							n.slowdownConstant = (int) Math.ceil((double) 1000/(int)spnFpsSpinner.getValue());
							double countdown = Math.ceil(((int)spnDelaySpinner.getValue()));
							countdownThread = this;
							while(countdown > 0){
								if(n.running == false){
									System.out.println("Stopping countdown.");
									btPlayButton.setText("Play");
									return;
								}
								System.out.println("Countdown (ms): " + countdown);
								btPlayButton.setText((int)countdown + "...");
								Thread.sleep(1000);
								countdown--;
							}
							countdownThread = null;
							btPlayButton.setText("Playing");



							do{
								//Notes.waitMultiplier = (double) spnCd.getValue();

								int charsProcessed = 0;
								String text = taText.getText().replace("[", "(").replace("]",")").replace(",","");
								taText.getCaret().setSelectionVisible(true);
								String[] splitLines = text.split("\\n");
								for (int i = 0; i < splitLines.length; i++){
									if(n.running == false) break;
									int noteLength = splitLines[i].length() + 1;
									System.out.println("charsProcessed=" + charsProcessed + ", noteLength=" + noteLength);
									taText.requestFocusInWindow();
									//taText.requestFocus();
									taText.select(charsProcessed, charsProcessed + noteLength);
									String nextNote = null;
									if(i+1 < splitLines.length) nextNote = splitLines[i+1];
									n.play(splitLines[i], nextNote);
									charsProcessed += noteLength;
								}
								Thread.sleep(1000); //This lets the game's music buffer catch up if the looping song has a very high tempo

							}while(loopCheckBox.isSelected() && n.running);
							btPlayButton.setText("Play");
							n.releaseHeldKey();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//n.releaseHeldKey();	
					}
				};
				playingThread.start();// We're using a new thread to be able to access Stop still.
				taText.requestFocusInWindow();
			}
		});

		/**
		 * Action Listener for the stop button
		 */
		btStopButton.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				//n.running = false;
				taText.requestFocusInWindow();
				if (playingThread != null){
					playingThread.stop();
				}
				if (countdownThread != null){
					countdownThread.stop();
				}

				btPlayButton.setText("Play");
				taText.requestFocusInWindow();
				//n.releaseHeldKey();
			}
		});

		try {
			frame.setIconImage(new ImageIcon(Main.class.getResource("/icon.png")).getImage());
		} catch (Exception e1) {
			System.out.println("Icon not found.");
			e1.printStackTrace();
		}
		frame.add(pnPanel0);
		frame.setSize(675, 500);
		frame.setTitle("TBbard");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);	
		frame.requestFocusInWindow();
	}

	public void setOpenFile(JFrame frame, String fileName) {
		System.out.println("Setting title to: " + fileName);
		if (fileName.equals(""))
			frame.setTitle("TBbard");
		else {
			fileLoaded = true;
			frame.setTitle("TBbard - " + fileName);
		}
	}

}
