package col.cs.risk.view;
import javax.swing.*;

import col.cs.risk.controller.PlayerSettingsController;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.HashMap; 
/**
 * It handles the display of player Strategies settings screen such as each
 * player Strategy and a button to save the changes made.
 * 
 * @author Team25
 * 
 *
 */
public class PlayerStrategySettingsView extends JFrame implements ActionListener {
	        /** Main Frame*/
	        JFrame PlayerStrategySettingsViewFrame;
	        
	        /** Label to display Players title */
		    JLabel currentplayerLabel; 
		    
		    /** CheckBoxes for each Strategies */
		    JCheckBox hcb,acb,bcb,rcb,ccb; 
		    
		    /** Button to Save Strategies for Corresponding Player */
		    JButton SaveButton; 
		    
		    /** Title Player */
		    String titlePlayer;
		    
		    /** No of Players Playing Game */
		    int noOfPlayers;
		    
		    /** HashKey and HashValue to Store details in the map */
		    String HashValue,HashKey;
		    
		    /** Map to store the Players and Corresponding Strategies */
		    HashMap<String,String> playersStrategiesMap = new HashMap<>();
		    
		    /** Instance for PlayerSettingsController Class */
		    private PlayerSettingsController playerSettingsController;
		    
		    /**
			 * Constructor to initialize and start the Frame
			 * 
			 * @param noOfPlayers No of Players Playing Game 
			 * @param TitlePlayer Title set as Players
			 */
		    public PlayerStrategySettingsView(int noOfPlayers, String TitlePlayer){ 
	            PlayerStrategySettingsViewFrame = new JFrame();
		    	this.titlePlayer = titlePlayer;
		    	this.noOfPlayers = noOfPlayers;
		    	
		        currentplayerLabel=new JLabel(titlePlayer); 
		        PlayerStrategySettingsViewFrame.setTitle("PlayerStartegySettingsPanel");
		        currentplayerLabel.setBounds(50,50,300,20); 
		        currentplayerLabel.setForeground(new Color(254, 254, 254));
				currentplayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
				currentplayerLabel.setVerticalAlignment(SwingConstants.CENTER);
				currentplayerLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
				JComboBox StrategyComboBox = new JComboBox();
				for(int i = 1;i<= noOfPlayers;i++)
				{
					StrategyComboBox.addItem("player"+i);
				}
				StrategyComboBox.setVisible(true);
				StrategyComboBox.setBounds(50,100,300,20);
				StrategyComboBox.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							
							HashKey = (String) StrategyComboBox.getSelectedItem();
    
						}

					});
		        hcb=new JCheckBox("Human");  
		        hcb.setBounds(100,150,150,20);  
		        acb=new JCheckBox("aggressive");  
		        acb.setBounds(100,200,150,20);  
		        bcb=new JCheckBox("benevolent");  
		        bcb.setBounds(100,250,150,20); 
		        rcb=new JCheckBox("random");  
		        rcb.setBounds(100,300,150,20);
		        ccb=new JCheckBox("cheater");  
		        ccb.setBounds(100,350,150,20);
		        SaveButton=new JButton("Save");  
		        SaveButton.setBounds(130,450,80,30);  
		        SaveButton.addActionListener(this); 
		        PlayerStrategySettingsViewFrame.getContentPane().setBackground(new java.awt.Color(1, 1, 1));
		        PlayerStrategySettingsViewFrame.add(currentplayerLabel);
		        PlayerStrategySettingsViewFrame.add(hcb);
		        PlayerStrategySettingsViewFrame.add(acb);
		        PlayerStrategySettingsViewFrame.add(bcb);
		        PlayerStrategySettingsViewFrame.add(rcb);
		        PlayerStrategySettingsViewFrame.add(ccb);
		        PlayerStrategySettingsViewFrame.add(SaveButton); 
		        PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox);
		        PlayerStrategySettingsViewFrame.setSize(600,600);  
		        PlayerStrategySettingsViewFrame.setLayout(null);  
		        PlayerStrategySettingsViewFrame.setVisible(true); 
 
		        addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent event) {
						exitForm(event);
					}
				});
		       
		    }  
		    public void actionPerformed(ActionEvent e){  
		        String playerType = null;  
		        String msg="";  
		        if(hcb.isSelected()){  
		        	playerType = "human\n"; 
		        }  
		        if(acb.isSelected()){  
		        	playerType = "aggressive\n";  
		        }  
		        if(bcb.isSelected()){  
		        	playerType = "benevolent\n"; 
		        }  
		        if(rcb.isSelected()){  
		        	playerType = "random\n"; 
		        } 
		        if(ccb.isSelected()){  
		        	playerType = "cheater\n"+noOfPlayers; 
		        } 
		        HashValue = playerType;
		        playersStrategiesMap.put(HashKey, HashValue);		        
		        msg+="-----------------\n";  
		        JOptionPane.showMessageDialog(this,msg+playerType);
		        
		    }  
		    
		    /**
			 * Exit from Player setting screen.
			 *
			 * @param evt
			 *            the event
			 * 
			 */
		    private void exitForm(WindowEvent evt) {
				setVisible(false);
				playerSettingsController.getStartGameController().setHomePageVisiblility(true);
			}
 

}
