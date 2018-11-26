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
		    JComboBox StrategyComboBox1,StrategyComboBox2,StrategyComboBox3,StrategyComboBox4,StrategyComboBox5,StrategyComboBox6;
		    
		    /** No of Players Playing Game */
		    int noOfPlayers;
		    
		    /** HashKey and HashValue to Store details in the map */
		    String HashValue,HashKey;
		    
		    /** Map to store the Players and Corresponding Strategies */
		    HashMap<String,String> playersStrategiesMap = new HashMap<>();
		    
		    String currentPlayer = null;
		    
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
			   /* if(noOfPlayers == 1)
			    {
				StrategyComboBox1 = new JComboBox();
			    StrategyComboBox1.addItem("Human");
			    StrategyComboBox1.addItem("aggressive");
			    StrategyComboBox1.addItem("benevolent");
			    StrategyComboBox1.addItem("random");
			    StrategyComboBox1.addItem("cheater");
			    currentPlayer = "player1";
				StrategyComboBox1.setVisible(true);
				StrategyComboBox1.setBounds(300,150+y1,200,20);
				y1 = y1+100;
				PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox1);
				StrategyComboBox1.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							HashKey = currentPlayer;
							HashValue = (String) StrategyComboBox1.getSelectedItem();
							playersStrategiesMap.put(HashKey, HashValue);
    
						}

					});
			    }*/
			    if(noOfPlayers == 2)
			    {
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
								HashKey = "player1";
								HashValue = (String) StrategyComboBox1.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					StrategyComboBox2 = new JComboBox();
				    StrategyComboBox2.addItem("Human");
				    StrategyComboBox2.addItem("aggressive");
				    StrategyComboBox2.addItem("benevolent");
				    StrategyComboBox2.addItem("random");
				    StrategyComboBox2.addItem("cheater");
					StrategyComboBox2.setVisible(true);
					StrategyComboBox2.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox2);
					StrategyComboBox2.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player2";
								HashValue = (String) StrategyComboBox2.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
				
				}
			    if(noOfPlayers == 3)
			    {
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
								HashKey = "player1";
								HashValue = (String) StrategyComboBox1.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					StrategyComboBox2 = new JComboBox();
				    StrategyComboBox2.addItem("Human");
				    StrategyComboBox2.addItem("aggressive");
				    StrategyComboBox2.addItem("benevolent");
				    StrategyComboBox2.addItem("random");
				    StrategyComboBox2.addItem("cheater");
					StrategyComboBox2.setVisible(true);
					StrategyComboBox2.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox2);
					StrategyComboBox2.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player2";
								HashValue = (String) StrategyComboBox2.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox3 = new JComboBox();
				    StrategyComboBox3.addItem("Human");
				    StrategyComboBox3.addItem("aggressive");
				    StrategyComboBox3.addItem("benevolent");
				    StrategyComboBox3.addItem("random");
				    StrategyComboBox3.addItem("cheater");
					StrategyComboBox3.setVisible(true);
					StrategyComboBox3.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox3);
					StrategyComboBox3.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player3";
								HashValue = (String) StrategyComboBox3.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
				
				}
			    
			    if(noOfPlayers == 4)
			    {
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
								HashKey = "player1";
								HashValue = (String) StrategyComboBox1.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					StrategyComboBox2 = new JComboBox();
				    StrategyComboBox2.addItem("Human");
				    StrategyComboBox2.addItem("aggressive");
				    StrategyComboBox2.addItem("benevolent");
				    StrategyComboBox2.addItem("random");
				    StrategyComboBox2.addItem("cheater");
					StrategyComboBox2.setVisible(true);
					StrategyComboBox2.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox2);
					StrategyComboBox2.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player2";
								HashValue = (String) StrategyComboBox2.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox3 = new JComboBox();
				    StrategyComboBox3.addItem("Human");
				    StrategyComboBox3.addItem("aggressive");
				    StrategyComboBox3.addItem("benevolent");
				    StrategyComboBox3.addItem("random");
				    StrategyComboBox3.addItem("cheater");
					StrategyComboBox3.setVisible(true);
					StrategyComboBox3.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox3);
					StrategyComboBox3.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player3";
								HashValue = (String) StrategyComboBox3.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox4 = new JComboBox();
				    StrategyComboBox4.addItem("Human");
				    StrategyComboBox4.addItem("aggressive");
				    StrategyComboBox4.addItem("benevolent");
				    StrategyComboBox4.addItem("random");
				    StrategyComboBox4.addItem("cheater");
					StrategyComboBox4.setVisible(true);
					StrategyComboBox4.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox4);
					StrategyComboBox4.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player4";
								HashValue = (String) StrategyComboBox4.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
				
				}
			    
			    if(noOfPlayers == 5)
			    {
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
								HashKey = "player1";
								HashValue = (String) StrategyComboBox1.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					StrategyComboBox2 = new JComboBox();
				    StrategyComboBox2.addItem("Human");
				    StrategyComboBox2.addItem("aggressive");
				    StrategyComboBox2.addItem("benevolent");
				    StrategyComboBox2.addItem("random");
				    StrategyComboBox2.addItem("cheater");
					StrategyComboBox2.setVisible(true);
					StrategyComboBox2.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox2);
					StrategyComboBox2.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player2";
								HashValue = (String) StrategyComboBox2.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox3 = new JComboBox();
				    StrategyComboBox3.addItem("Human");
				    StrategyComboBox3.addItem("aggressive");
				    StrategyComboBox3.addItem("benevolent");
				    StrategyComboBox3.addItem("random");
				    StrategyComboBox3.addItem("cheater");
					StrategyComboBox3.setVisible(true);
					StrategyComboBox3.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox3);
					StrategyComboBox3.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player3";
								HashValue = (String) StrategyComboBox3.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox4 = new JComboBox();
				    StrategyComboBox4.addItem("Human");
				    StrategyComboBox4.addItem("aggressive");
				    StrategyComboBox4.addItem("benevolent");
				    StrategyComboBox4.addItem("random");
				    StrategyComboBox4.addItem("cheater");
					StrategyComboBox4.setVisible(true);
					StrategyComboBox4.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox4);
					StrategyComboBox4.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player4";
								HashValue = (String) StrategyComboBox4.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox5 = new JComboBox();
				    StrategyComboBox5.addItem("Human");
				    StrategyComboBox5.addItem("aggressive");
				    StrategyComboBox5.addItem("benevolent");
				    StrategyComboBox5.addItem("random");
				    StrategyComboBox5.addItem("cheater");
					StrategyComboBox5.setVisible(true);
					StrategyComboBox5.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox5);
					StrategyComboBox5.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player5";
								HashValue = (String) StrategyComboBox5.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
				
				}
				if(noOfPlayers == 6)
			    {
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
								HashKey = "player1";
								HashValue = (String) StrategyComboBox1.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					StrategyComboBox2 = new JComboBox();
				    StrategyComboBox2.addItem("Human");
				    StrategyComboBox2.addItem("aggressive");
				    StrategyComboBox2.addItem("benevolent");
				    StrategyComboBox2.addItem("random");
				    StrategyComboBox2.addItem("cheater");
					StrategyComboBox2.setVisible(true);
					StrategyComboBox2.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox2);
					StrategyComboBox2.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player2";
								HashValue = (String) StrategyComboBox2.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox3 = new JComboBox();
				    StrategyComboBox3.addItem("Human");
				    StrategyComboBox3.addItem("aggressive");
				    StrategyComboBox3.addItem("benevolent");
				    StrategyComboBox3.addItem("random");
				    StrategyComboBox3.addItem("cheater");
					StrategyComboBox3.setVisible(true);
					StrategyComboBox3.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox3);
					StrategyComboBox3.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player3";
								HashValue = (String) StrategyComboBox3.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox4 = new JComboBox();
				    StrategyComboBox4.addItem("Human");
				    StrategyComboBox4.addItem("aggressive");
				    StrategyComboBox4.addItem("benevolent");
				    StrategyComboBox4.addItem("random");
				    StrategyComboBox4.addItem("cheater");
					StrategyComboBox4.setVisible(true);
					StrategyComboBox4.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox4);
					StrategyComboBox4.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player4";
								HashValue = (String) StrategyComboBox4.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox5 = new JComboBox();
				    StrategyComboBox5.addItem("Human");
				    StrategyComboBox5.addItem("aggressive");
				    StrategyComboBox5.addItem("benevolent");
				    StrategyComboBox5.addItem("random");
				    StrategyComboBox5.addItem("cheater");
					StrategyComboBox5.setVisible(true);
					StrategyComboBox5.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox5);
					StrategyComboBox5.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player5";
								HashValue = (String) StrategyComboBox5.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
							}

						});
					y1 = y1+100;
					StrategyComboBox6 = new JComboBox();
				    StrategyComboBox6.addItem("Human");
				    StrategyComboBox6.addItem("aggressive");
				    StrategyComboBox6.addItem("benevolent");
				    StrategyComboBox6.addItem("random");
				    StrategyComboBox6.addItem("cheater");
					StrategyComboBox6.setVisible(true);
					StrategyComboBox6.setBounds(300,150+y1,200,20);
					PlayerStrategySettingsViewFrame.getContentPane().add(StrategyComboBox6);
					StrategyComboBox6.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								HashKey = "player6";
								HashValue = (String) StrategyComboBox6.getSelectedItem();
								playersStrategiesMap.put(HashKey, HashValue);
								System.out.println(playersStrategiesMap.toString());
	    
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

			@Override
			public void actionPerformed(ActionEvent e) {
            System.out.println(playersStrategiesMap.toString());
			}
 

}
