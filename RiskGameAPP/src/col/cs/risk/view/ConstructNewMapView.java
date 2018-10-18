package col.cs.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import col.cs.risk.controller.StartGameController;

public class ConstructNewMapView extends JFrame
{
	/** StringBuilder to add Contents to a File */
	//StringBuilder mapString;

	/** To show error status */
	private JTextArea errorStatus;

	/** StartGameController instance */
	private StartGameController startGameController;

	/**
	 * Constructor
	 */
	public ConstructNewMapView() {
		setTitle("Conquer Game");
	}

	/**
	 * Constructor
	 * @param startGameController
	 */
	public ConstructNewMapView(StartGameController startGameController) {
		this();
		this.startGameController = startGameController;
		startGameController.setConstructNewMapView(this);
		setVisible(true);
		createMap();
	}

	public void createMap()
	{
		/** Text Area to Store details of Map Text Area */
		final JTextArea map_txtArea;
		/** Text Area to Store details of Continents Text Area */
		final JTextArea continents_txtArea;
		/** Text Area to Store details of Territories Text Area */
		final JTextArea territories_txtArea;
		/** JComboBox for day,month,year */
		JComboBox day;
		JComboBox month;
		JComboBox year;
		JButton create_btn;
		JButton format_btn;

		errorStatus = new JTextArea();
		errorStatus.setName("errorStatus");
		errorStatus.setEditable(false);
		errorStatus.setVisible(false);
		errorStatus.setBounds(400,600,600,28);
		errorStatus.setBackground(Color.RED);
		errorStatus.setWrapStyleWord(true);
		errorStatus.setLineWrap(true);

		/** ---------------------------------- Creating JFrame -------------------------------------------------------- */
		/** Creating a frame using JFrame class	*/ 
		setVisible(true);
		setLayout(new BorderLayout());
		setBounds(200,100,1400,900 );    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(true);


		/** setting background color of Frame */	 
		Container c=getContentPane();

		c.setLayout(null);    
		//((JFrame) c).setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("game_logo.jpg")))));
		c.setBackground(Color.yellow); 
		/*---------------------------------- Creating JLabel for Heading Text ------------------------------------------- */	
		Font f=new Font("Arial",Font.BOLD,20);   // Creating font style and size for heading		

		/** creating JLabel for Heading */
		JLabel heading_lbl=new JLabel();
		heading_lbl.setBounds(250,5,200,40);
		heading_lbl.setText("<html><font><u><b>Create a New Map</b></u></html>");	

		/** applying font on  heading Label */
		heading_lbl.setFont(f);

		/** ----------------------------------- Creating Global Font style for all components ------------------------------ */

		Font f1=new Font("Arial",Font.BOLD,14);

		JLabel map_lbl=new JLabel("[Map]");
		map_lbl.setBounds(50,80,100,30); 

		/** Creating JTextArea for map */
		map_txtArea=new JTextArea();
		map_txtArea.setBounds(50,120,400,400);
		JScrollPane scroll = new JScrollPane(map_txtArea);


		JLabel continent_lbl=new JLabel("[Continents]");
		continent_lbl.setBounds(500,80,200,30); 

		/** Creating JTextArea for continents */
		continents_txtArea = new JTextArea();
		continents_txtArea.setBounds(500,120,400,400);
		JScrollPane scroll1 = new JScrollPane(continents_txtArea);

		JLabel territories_lbl=new JLabel("[Territories]");
		territories_lbl.setBounds(950,80,200,30);

		/** Creating JTextArea for continent Territories */
		territories_txtArea = new JTextArea();
		territories_txtArea.setBounds(950,120,400,400);
		JScrollPane scroll2 = new JScrollPane(territories_txtArea);

		Cursor cur=new Cursor(Cursor.HAND_CURSOR);

		/** Creating JLabel for Date of Creation */
		JLabel doc_lbl=new JLabel("Date of Creation : ");
		doc_lbl.setBounds(100,700,200,40);	

		/** Creating JComboBox for the day */
		String day_arr[]=new String[31];
		for(int i=1;i<=31;i++)
			day_arr[i-1]=Integer.toString(i);		
		day=new JComboBox(day_arr);
		day.setBounds(300,700,40,40);

		/** Creating JComboBox for the month */
		String month_arr[]={"Jan","Feb","March","April","May","June","July","Aug","Sept","Oct","Nov","Dec" };	
		month=new JComboBox(month_arr);
		month.setBounds(350,700,60,40);

		/** Creating JComboBox for the year	*/
		String year_arr[]=new String[70];
		for(int i=1951;i<=2020;i++)
			year_arr[i-1951]=Integer.toString(i);
		year=new JComboBox(year_arr);
		year.setBounds(420,700,60,40);

		/** Creating JButton for submit the details */
		create_btn=new JButton("Create");
		create_btn.setBounds(580,700,120,40);
		create_btn.setCursor(cur); 
		/** Applying hand cursor on the button */
		format_btn=new JButton("Format to create a Map");
		format_btn.setBounds(800,5,300,40);
		format_btn.setCursor(cur);

		/** Adding ActionListener on create button and write all the details to a new file*/
		create_btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				String line = map_txtArea.getText();
				String line1 = continents_txtArea.getText();
				String line2 = territories_txtArea.getText();
				startGameController.actionPerformedOnMapCreateButton(line, line1, line2);
			}
		});

		/**  Adding ActionListener on format button */
		format_btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				format_action(event);
			}	
		});

		/** Applying Global Font on all the Components */
		map_lbl.setFont(f);
		continent_lbl.setFont(f);
		territories_lbl.setFont(f);
		territories_txtArea.setFont(f1);
		map_txtArea.setFont(f1);
		continents_txtArea.setFont(f1);
		doc_lbl.setFont(f);
		create_btn.setFont(f1);
		format_btn.setFont(f1);
		errorStatus.setFont(f);

		/** Adding components to the container */ 

		c.add(heading_lbl);
		c.add(map_lbl);
		c.add(continent_lbl);
		c.add(territories_lbl);
		c.add(territories_txtArea);
		c.add(doc_lbl);
		c.add(map_txtArea);
		add(scroll);
		c.add(continents_txtArea);
		c.add(day);
		c.add(month);
		c.add(year);
		c.add(create_btn);
		c.add(format_btn);
		c.add(errorStatus);
	}

	/**
	 * Display error message
	 * @param errorMessage
	 */
	public void setErrorStatus(String errorMessage) {
		errorStatus.setText(errorMessage);
		errorStatus.setVisible(true);
		repaint();
	}

	/**
	 * Loads a new Frame that has Map Format on pressing Format button on Create Map Frame
	 * @param event
	 */
	private static void format_action(ActionEvent event) {
		// TODO Auto-generated method stub

		JFrame secondFrame = new JFrame("Format to create Map");
		secondFrame.setSize(900, 900);
		secondFrame.setVisible(true);
		Font f=new Font("Arial",Font.BOLD,20);   /** Creating font style and size for heading */
		JLabel heading_lbl=new JLabel();
		heading_lbl.setBounds(350,5,200,40);
		heading_lbl.setText("<html><font><u><b>Map Format</b></u></html>");
		String text = "<html>" + "[Map]"+"<br />" +"image=world.bmp" + "<br />" + 
				"wrap=yes" + "<br />" +"scroll=horizontal" + "<br />" + 
				"author=Your name"+"<br />" + 
				"warn=yes"+"<br />" +"<br />" +"The second section describes the Continent names and the scores for owning them completely" + 
				"<br/>"+"<br/>"+"[Continents]"+"<br />" +"North America=5" + "<br />" + 
				"South America=2" + "<br />" +"Africa=3" + "<br />" + 
				"Europe=5"+"<br />" + 
				"Asia=7"+"<br />" + 
				"Australia=2"+"<br />" +"<br />" +"The final section describes the territories. The first parameter is the territory's name, then the \"x\" and \"y\" coordinates of the center of the territory in pixels from the top left corner of the bitmap, then the continent that the territory is in and finally list all the territories that this territory connects to. The maximum number of continents that a map can have is 32"+"<br />"+"<br />"+"<br />"+"[Territories]"+"<br />" +"Siam,270,139,Asia,China,India,Indonesia" + "<br />" + 
				"Japan,322,104,Asia,Kamchatka,Mongolia" + "<br />" +"Ural,241,68,Asia,Siberia,China,Afghanistan,Ukraine" + "<br />" + 
				"etc..."+"<br />" + 
				""+"<br />" + 
				""+"</html>";;
				JLabel content_lbl=new JLabel(text);
				//content_lbl.setBounds(10,10,100,50);
				/** applying font on  heading Label */
				heading_lbl.setFont(f);
				secondFrame.add(heading_lbl);
				content_lbl.setFont(f);
				secondFrame.add(content_lbl);
	}

}
