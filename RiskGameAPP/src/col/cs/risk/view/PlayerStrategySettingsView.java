package col.cs.risk.view;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*; 
public class PlayerStrategySettingsView extends JFrame implements ActionListener {
            JPanel playerstretegyPanel;
		    JLabel currentplayerLabel;  
		    JCheckBox hcb,acb,bcb,rcb,ccb;  
		    JButton b;  
		    PlayerStrategySettingsView(){  
		        currentplayerLabel=new JLabel("Player1"); 
		        setTitle("PlayerStartegySettingsPanel");
		        currentplayerLabel.setBounds(50,50,300,20); 
		        currentplayerLabel.setForeground(new Color(254, 254, 254));
				currentplayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
				currentplayerLabel.setVerticalAlignment(SwingConstants.CENTER);
				currentplayerLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		        hcb=new JCheckBox("Human");  
		        hcb.setBounds(100,100,150,20);  
		        acb=new JCheckBox("aggressive");  
		        acb.setBounds(100,150,150,20);  
		        bcb=new JCheckBox("benevolent");  
		        bcb.setBounds(100,200,150,20); 
		        rcb=new JCheckBox("random");  
		        rcb.setBounds(100,250,150,20);
		        ccb=new JCheckBox("cheater");  
		        ccb.setBounds(100,300,150,20);
		        b=new JButton("Save");  
		        b.setBounds(100,350,80,30);  
		        b.addActionListener(this);  
		        getContentPane().setBackground(new java.awt.Color(1, 1, 1));
		        add(currentplayerLabel);add(hcb);add(acb);add(bcb);add(rcb);add(ccb);add(b);  
		        setSize(400,500);  
		        setLayout(null);  
		        setVisible(true);  
		        setDefaultCloseOperation(EXIT_ON_CLOSE);  
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
		        	playerType = "cheater\n"; 
		        } 
		        msg+="-----------------\n";  
		        JOptionPane.showMessageDialog(this,msg+playerType);  
		    }  
		    public static void main(String[] args) {  
		        new PlayerStrategySettingsView();  
		    }    

}
