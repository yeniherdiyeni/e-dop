package application;

/*
 *  File: OrderApplet.java
 *  Author: Java, Java, Java
 *  Description: This applet introduces some additional Swing
 *   components, including the JCheckBox and JRadioButton classes.
 *   An ItemListener interface is implemented to support check box
 *   and radio button actions. Also, the BorderFactory class is used
 *   to create borders around various areas of the applet window.
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OntologyApp extends JApplet implements ItemListener, ActionListener {

    private final int NTITLES = 3, NOPTIONS = 3;
 
    private JPanel mainPanel = new JPanel(),
                   centerPanel = new JPanel(),
                   choicePanel = new JPanel(),
                   optionPanel = new JPanel(),
                   buttonPanel = new JPanel();
    
    private int returnValue;
    private JFileChooser fileChooser = new JFileChooser();
    private File selectedFile;
    private ButtonGroup optGroup = new ButtonGroup();
    private JCheckBox titles[] = new JCheckBox[NTITLES];
    private JRadioButton options[] = new JRadioButton[NOPTIONS];
    private String titleLabels[] =
        {"Unigrams		", "Bigrams		","Trigrams		"};
    private String optionLabels[] = {"Biocaster      ", "Disease Ontology      ", "Other Disease Ontology"};
 
    private JTextArea display = new JTextArea(7, 25);
    private JScrollPane scrollText; 
    private JButton submit = new JButton("Submit"),
                    exit = new JButton("Exit");

    private JButton Browse = new JButton("Browse");
   //JFrame frame = new JFrame("JComboBox Test");
   // frame.setLayout(new FlowLayout());
   // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    /**
     * init() sets up the interface. Note that it calls the initChoices()
     *  and initOptions() methods to set up the check boxes and radio buttons.
     *  Also, note that it uses several panels to organize the interface into
     *  distinct areas, making it easier for the user to navigate.
     */
    public void init() {
        mainPanel.setBorder(BorderFactory.createTitledBorder("Ontolgy Application Interface"));
        mainPanel.setLayout(new GridLayout(3, 1, 0, 0));
        exit.addActionListener(this);
        submit.addActionListener(this);
        Browse.addActionListener(this);
        
        initChoices();
        initOptions();
        buttonPanel.setBorder( BorderFactory.createTitledBorder(""));
        buttonPanel.add(exit);
        buttonPanel.add(submit);
        centerPanel.add(choicePanel);
        centerPanel.add(optionPanel);
        
        
        buttonPanel.add(Browse);
 
        mainPanel.add(centerPanel);
        mainPanel.add( buttonPanel);
        
        
        display.setLineWrap(true);
        scrollText=new JScrollPane(display);
        scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollText);
        
        getContentPane().add(mainPanel);
        setSize(400,400);
        submit.setEnabled(false);
    } // init()
 
    /**
     * initChoices() initializes the JCheckBoxes which are organized
     *   into a separate panel, layed out in BoxLayout format with a
     *   border around it.
     */
    private void initChoices() {
        choicePanel.setBorder(BorderFactory.createTitledBorder("Word List "));
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.Y_AXIS));
    
        for (int k = 0; k < titles.length; k++) {
            titles[k] = new JCheckBox(titleLabels[k]);
            titles[k].addItemListener(this);
            choicePanel.add(titles[k]);
        }
    } // initChoices()

    /**
     * initOptions() initializes the JRadioButtons which are organized
     *   into a separate panel, layed out in BoxLayout format with a
     *   border around it.
     */
    private void initOptions() {
        optionPanel.setBorder(BorderFactory.createTitledBorder("Ontology "));
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
     
        for (int k = 0; k < options.length; k++) {
            options[k] = new JRadioButton(optionLabels[k]);
            options[k].addItemListener(this);
            optionPanel.add(options[k]);
            optGroup.add(options[k]);
        }
        options[0].setSelected(true);
    } // initOptions()

    /**
     * itemStateChanged() handles the user's check box and radio button
     *   selections. In each case, it appends some text to the main
     *   display area.
     * @param e -- the ItemEvent that led to this method call
     */
    public void itemStateChanged(ItemEvent e) {
        display.setText("Your options so far: ");
        for (int k = 0; k < options.length; k++ )
            if (options[k].isSelected())
                display.append(options[k].getText() + "\n");
        for (int k = 0; k < titles.length; k++ )
            if (titles[k].isSelected())
                display.append("\t" + titles[k].getText() + "\n");
    }
    
    // itemStateChanged()
 
    /**
     * actionPerformed() handles the applet's action events
     *  Note that the submit button changes labels depending on the state
     *  of the user's order. In effect, the button's label keeps track of
     *  the applet's state -- either submitting or confirming an order.
     * @param e -- the ActionEvent that led to this method call
     */
    public void actionPerformed(ActionEvent e)
    {
        
        if(e.getSource() == Browse)
        {
            returnValue = fileChooser.showOpenDialog(null);
            selectedFile = fileChooser.getSelectedFile();
            try {
				display.append("Click 'Browse' to import a text file \n "+"You selected the following file: "+selectedFile.getCanonicalPath()+"\n");
				submit.setEnabled(true);
			} 
            catch (IOException e1) 
            {}
            
        }
                
        else if (e.getSource() == submit)
        {
          display.append("Press Submit !\n");
          submit.setText("Confirm\n");          
          submit.setText("Submit\n");
          
          display.append("Action submitted \n\n");
          display.append("Output:\n");
          display.append("--------------------------------\n");
         
          Analyzer a=new Analyzer();
          try {
        	 // a = new Analyzer("file:/home/ndragu/Desktop/biocaster.owl", selectedFile.getCanonicalPath(), titles);\
        	  
        	  String filepath = System.getProperty("user.dir") + File.separatorChar + "biocaster.owl";
        	  filepath="file:"+filepath;
        	  System.out.println(filepath);
        	  a = new Analyzer(filepath, selectedFile.getCanonicalPath(), titles);
          	}				 
          catch (IOException e1) 
          {}
		
          display.append(a.getResult());
        }
    
        else if (e.getSource() == exit)
        {  
            System.exit(0);
        }
       
        else
        {
          display.append("");
        }
    }

   }