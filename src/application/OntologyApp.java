package application;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OntologyApp extends JFrame implements ItemListener, ActionListener {

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
  
    
    
    
    
    public  OntologyApp() {
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

    
    public void initChoices() {
        choicePanel.setBorder(BorderFactory.createTitledBorder("Word List "));
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.Y_AXIS));
    
        for (int k = 0; k < titles.length; k++) {
            titles[k] = new JCheckBox(titleLabels[k]);
            titles[k].addItemListener(this);
            choicePanel.add(titles[k]);
        }
    } // initChoices()

   
    public void initOptions() {
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
 
    public void actionPerformed(ActionEvent e)
    {
        
        if(e.getSource() == Browse)
        {
            returnValue = fileChooser.showOpenDialog(null);
            selectedFile = fileChooser.getSelectedFile();
            try {
				display.setText("Click 'Browse' to import a text file \n "+"You selected the following file: "+selectedFile.getCanonicalPath()+"\n");
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

//        	  String filepath = System.getProperty("user.dir") + File.separatorChar + "biocaster.owl";
//        	  if (filepath.indexOf(':')>=0 || filepath.indexOf(':')<=2)
//        	  {
//        	  filepath="file:///"+filepath;
//        	  filepath=filepath.replace('\\','/');
//        	  }
//        	  else filepath="file:"+filepath;        	  
       	  a = new Analyzer("http://www.cs.trincoll.edu/~ndragu/ontology/biocaster.owl", selectedFile.getCanonicalPath(), titles);

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
          display.setText("");
        }
    }
    
public static void main(String[] args)
{
     OntologyApp f = new OntologyApp();
     f.setSize(500,400);
     f.setVisible(true);
}
}



//     f.setLayout(new FlowLayout());
//      f.addWindowListener(new windowListener());
//     f.setLayout(new GridBagLayout());
//	 GridBagConstraints constraints = new GridBagConstraints();
//	 constraints.gridx = 0;
//	 constraints.gridy = 0;
//	 constraints.weightx = 50;
//	 constraints.weighty = 50;
//	 constraints.anchor = GridBagConstraints.CENTER;
//	 constraints.fill = GridBagConstraints.BOTH;
//	
//	 f.add(f, constraints);
//	 f.pack();
//	 f.setSize(850, 575);

//	 // Center the window
//	 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//	 Dimension frameSize = f.getSize();
//	 if (frameSize.height > screenSize.height)
//	 {
//	 frameSize.height = screenSize.height;
//	 }
//	 if (frameSize.width > screenSize.width)
//	 {
//	 frameSize.width = screenSize.width;
//	 }
//	 f.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
//	 f.setVisible(true);
//	 }


    		
//    		 public static void main(String args[])
//    		 {
//    			 
//    			OntologyApp applet = new OntologyApp();
//    		 Frame frame = new Frame("Ontology");
    		 
    		
    		 // set the layout and add the applet
    		
