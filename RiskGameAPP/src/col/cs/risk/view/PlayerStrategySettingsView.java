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
		    JLabel playerTitle,currentplayerLabel; 
		    
		    /** CheckBoxes for each Strategies */
		    JCheckBox hcb,acb,bcb,rcb,ccb; 
		    
		    /** Button to Save Strategies for Corresponding Player */
		    JButton SaveButton; 
		    
		    /** Title Player */
		    String titlePlayer;
		    
		    /**
		     * Combo box for Players Strategies
		     */
		    JComboBox StrategyComboBox1;
		    
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
		    PlayerStrategySettingsView(int noOfPlayers, String TitlePlayer){ 
	            PlayerStrategySettingsViewFrame = new JFrame();
		    	this.titlePlayer = TitlePlayer;
		    	this.noOfPlayers = noOfPlayers;
		    	
		    	playerTitle = new JLabel(titlePlayer); 
		        PlayerStrategySettingsViewFrame.setTitle("PlayerStartegySettingsPanel");
		        playerTitle.setBounds(150,50,300,20); 
		        playerTitle.setForeground(new Color(254, 254, 254));
		        playerTitle.setHorizontalAlignment(SwingConstants.CENTER);
		        playerTitle.setVerticalAlignment(SwingConstants.CENTER);
		        playerTitle.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		        PlayerStrategySettingsViewFrame.add(playerTitle);
		    	
		        int y = 0;
				for(int i = 1;i<= noOfPlayers;i++)
				{
					
					currentplayerLabel =new JLabel("player"+i); 
			        PlayerStrategySettingsViewFrame.setTitle("PlayerStartegySettingsPanel");
			        currentplayerLabel.setBounds(50,150+y,300,20); 
			        currentplayerLabel.setForeground(new Color(254, 254, 254));
					currentplayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
					currentplayerLabel.setVerticalAlignment(SwingConstants.CENTER);
					currentplayerLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
					PlayerStrategySettingsViewFrame.add(currentplayerLabel);
					y = y+100;
				}
	
				int y1 = 0;
				for(int i = 1;i<= noOfPlayers;i++) {
				StrategyComboBox1 = new JComboBox();
			    StrategyComboBox1.addItem("Human");
			    StrategyComboBox1.addItem("aggressive");
			    StrategyComboBox1.addItem("benevolent");
			    StrategyComboBox1.addItem("random");
			    StrategyComboBox1.addItem("cheater");
				StrategyComboBox1.setVisible(true);
				StrategyComboBox1.setBounds(300,150+y1,200,20);
				y1 = y1+100;
				PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox1);
				StrategyComboBox1.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							
							HashKey = (String) StrategyComboBox1.getSelectedItem();
    
						}

					});
				}
				
		        SaveButton=new JButton("Save");  
		        SaveButton.setBounds(280,200+y,80,30);  
		        SaveButton.addActionListener(this); 
		        PlayerStrategySettingsViewFrame.getContentPane().setBackground(new java.awt.Color(1, 1, 1));
		        PlayerStrategySettingsViewFrame.add(SaveButton); 
		        PlayerStrategySettingsViewFrame.setSize(700,1000);  
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
