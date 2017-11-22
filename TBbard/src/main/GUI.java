package main;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI {
	
	Notes n;
	
	public GUI() {
		JFrame frame = new JFrame();
		
		JPanel pnPanel0;
		JTextArea taText;
		JButton btPlayButton;
		JSpinner spnDelaySpinner;
		JLabel lbDelayLabel;
		JSpinner spnCd;
		JLabel lbCd;

		pnPanel0 = new JPanel();
		GridBagLayout gbPanel0 = new GridBagLayout();
		GridBagConstraints gbcPanel0 = new GridBagConstraints();
		pnPanel0.setLayout( gbPanel0 );

		taText = new JTextArea(2,10);
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 2;
		gbcPanel0.gridwidth = 20;
		gbcPanel0.gridheight = 16;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		JScrollPane sp = new JScrollPane(taText);  

		gbPanel0.setConstraints( sp, gbcPanel0 );
		pnPanel0.add( sp );


				

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

		spnDelaySpinner = new JSpinner( );
		gbcPanel0.gridx = 13;
		gbcPanel0.gridy = 0;
		gbcPanel0.gridwidth = 7;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( spnDelaySpinner, gbcPanel0 );
		pnPanel0.add( spnDelaySpinner );
		spnDelaySpinner.setValue(5000);

		lbDelayLabel = new JLabel( "Start delay:"  );
		gbcPanel0.gridx = 8;
		gbcPanel0.gridy = 0;
		gbcPanel0.gridwidth = 5;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0.1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbDelayLabel, gbcPanel0 );
		pnPanel0.add( lbDelayLabel );

		spnCd = new JSpinner( );
		gbcPanel0.gridx = 4;
		gbcPanel0.gridy = 0;
		gbcPanel0.gridwidth = 4;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( spnCd, gbcPanel0 );
		pnPanel0.add( spnCd );
		spnCd.setValue(200);

		lbCd = new JLabel( "ClickCooldown:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 0;
		gbcPanel0.gridwidth = 4;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0.1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbCd, gbcPanel0 );
		pnPanel0.add( lbCd );
		
		
		
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spnCd, "#"); spnCd.setEditor(editor);
		editor = new JSpinner.NumberEditor(spnDelaySpinner, "#"); spnDelaySpinner.setEditor(editor);

		btPlayButton.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Notes.cd = (int) spnCd.getValue();
					Thread.sleep((int)spnDelaySpinner.getValue());
					n = new Notes();
					
					String text = taText.getText().replace("[", "(").replace("]",")").replace(",","");
					
					
					
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
		frame.setSize(300, 300);
		frame.setTitle("TBbard");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);	
		frame.requestFocusInWindow();
	}
}
