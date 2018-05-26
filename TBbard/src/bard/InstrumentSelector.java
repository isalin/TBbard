package bard;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class InstrumentSelector {
	
	int selectedIndex = 0;
	JDialog frame;
	
	JComboBox<String> cmbInstrumentSelect;


	public InstrumentSelector(String[] instruments) {
		
		frame = new JDialog();
		
		
		JPanel pnPanel0;
		JLabel lbMidiWarning;
		JButton btInstrumentSelectOk;
		JButton btInstrumentCancel;

		pnPanel0 = new JPanel();
		GridBagLayout gbPanel0 = new GridBagLayout();
		GridBagConstraints gbcPanel0 = new GridBagConstraints();
		pnPanel0.setLayout( gbPanel0 );

		lbMidiWarning = new JLabel( "Midi files contain channels for several instruments. Which instrument would you like to convert?"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 0;
		gbcPanel0.gridwidth = 2;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbMidiWarning, gbcPanel0 );
		pnPanel0.add( lbMidiWarning );

		String []dataInstrumentSelect = instruments;
		cmbInstrumentSelect = new JComboBox( dataInstrumentSelect );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 1;
		gbcPanel0.gridwidth = 2;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( cmbInstrumentSelect, gbcPanel0 );
		pnPanel0.add( cmbInstrumentSelect );

		btInstrumentSelectOk = new JButton( "Select"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 2;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btInstrumentSelectOk, gbcPanel0 );
		pnPanel0.add( btInstrumentSelectOk );

		btInstrumentCancel = new JButton( "Cancel"  );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 2;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btInstrumentCancel, gbcPanel0 );
		pnPanel0.add( btInstrumentCancel );
		
		
		
		
		btInstrumentSelectOk.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					if(cmbInstrumentSelect.getSelectedItem().toString() != null){
						frame.setVisible(false);
						frame.dispose();
					}
					
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		frame.setModal(true);
		frame.add(pnPanel0);
		frame.setSize(600, 200);
		frame.setTitle("Instrument Selector");
		frame.setResizable(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		//frame.setVisible(true);	
		frame.requestFocusInWindow();
		
	}
	
	
	
	public int showDialogue(){
		frame.setVisible(true);
		return cmbInstrumentSelect.getSelectedIndex();
	}
	

}
