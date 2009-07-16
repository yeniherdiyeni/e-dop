package application;

import java.awt.GridLayout;
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
    private int returnValue2;
    private JFileChooser fileChooser = new JFileChooser();
    private File selectedFile;
    private File selectedFile2;
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

    private JButton Browse = new JButton("Input File");
    private JButton browseOntology = new JButton("Ontology");
    private JButton instructions = new JButton("Instructions");
    private JButton clear = new JButton("Clear Console");
    
    
    public  OntologyApp() {
        mainPanel.setBorder(BorderFactory.createTitledBorder("Ontolgy Application Interface"));
        mainPanel.setLayout(new GridLayout(3, 2, 0, 0));
        
        exit.addActionListener(this);
        submit.addActionListener(this);
        Browse.addActionListener(this);
        browseOntology.addActionListener(this);
        instructions.addActionListener(this);
        clear.addActionListener(this);
        
        initChoices();
        initOptions();
        
        buttonPanel.setBorder( BorderFactory.createTitledBorder(""));
        buttonPanel.add(exit);
        buttonPanel.add(submit);
        buttonPanel.add(Browse);
        
        centerPanel.add(choicePanel);
        centerPanel.add(optionPanel);
        centerPanel.add(browseOntology);
        centerPanel.add(instructions);
        centerPanel.add(clear);
        
        mainPanel.add(centerPanel);
        mainPanel.add( buttonPanel);
        
  
        display.setBorder(BorderFactory.createTitledBorder("Console"));
        display.setLineWrap(true);
        scrollText=new JScrollPane(display);
        scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollText);
        
        getContentPane().add(mainPanel);
        
        
        submit.setEnabled(false);
        
        selectedFile=null;
        selectedFile2=null;
        
        display.append("\n");
        display.append("1. Select a Word List\n");
        display.append("2. Select an existing ontology or import one by clicking <Ontology>\n");
        display.append("3. Click Submit to compute the result\n");
        display.append("\n");
        display.append("Default Ontology: Biocaster\n\n");
        display.setLineWrap(true);
    } // init()

    
    public void initChoices() {
        choicePanel.setBorder(BorderFactory.createTitledBorder("Word List "));
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.Y_AXIS));
    
        for (int k = 0; k < titles.length; k++) {
            titles[k] = new JCheckBox(titleLabels[k]);
            titles[k].addItemListener(this);
            choicePanel.add(titles[k]);
        }
        titles[0].setSelected(true);
        
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
        options[1].setEnabled(false);
        options[2].setEnabled(false);
    } // initOptions()    
       
    
    
    public void itemStateChanged(ItemEvent e) {
        
    }
    
    // itemStateChanged()
 
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getSource()==clear)
    	{
    		display.setText("");
    	}
    	else
    	if (e.getSource() == instructions)
    	{
    		display.append("\n");
            display.append("1. Select a Word List\n");
            display.append("2. Select an existing ontology or import one by clicking <Ontology>\n");
            display.append("3. Click Submit to compute the result\n");
            display.append("4. Please wait a few moments after clicking on Submit!");
            display.append("\n");
    	}
    	
    	else
        if(e.getSource() == Browse)
        {
            returnValue = fileChooser.showOpenDialog(null);
            selectedFile = fileChooser.getSelectedFile();
            try {
            	display.append("\n");
				display.append("File: "+selectedFile.getCanonicalPath()+"\n");
				if (selectedFile!=null)
					submit.setEnabled(true);
				
			} 
            catch (IOException e1) 
            {}
            
        }
                
        else if (e.getSource() == submit)
        {
          if ((titles[0].isSelected()) || (titles[1].isSelected()) || (titles[2].isSelected()) )
          {
          display.append("\n");
          submit.setText("Confirm\n");          
          submit.setText("Submit\n");
          
          display.append("Action submitted \n\n");
          display.append("Output:\n");
          display.append("--------------------------------\n");
         
          Analyzer a=new Analyzer();
          try {
          
          if (selectedFile2==null) 	  
        	  a = new Analyzer("http://www.cs.trincoll.edu/~ndragu/ontology/biocaster.owl", selectedFile.getCanonicalPath(), titles);
          else 
          	{
        	  String ontologyPath="file:"+selectedFile2.getCanonicalPath();
        	  a = new Analyzer(ontologyPath, selectedFile.getCanonicalPath(), titles);
          	}
          }				 
          catch (IOException e1) 
          {}
		
          display.append(a.getResult());
          }
          else //if (titles[0].isSelected() || (titles[1].isSelected()) || (titles[2].isSelected()) )
        	  display.append("No Word List is selected!");
        }
    
        else if (e.getSource() == exit)
        {  
            System.exit(0);
        }
        
        else if (e.getSource() == browseOntology)
        {
        	 returnValue2 = fileChooser.showOpenDialog(null);
             selectedFile2 = fileChooser.getSelectedFile();
             try {
 				display.append("Ontology: "+selectedFile2.getCanonicalPath()+"\n");
 				if (selectedFile2!=null && selectedFile!=null)
					submit.setEnabled(true);
 			} 
             catch (IOException e1) 
             {}
        }
       
        else
        {
          display.setText("");
        }
    }
    
public static void main(String[] args)
{
     OntologyApp f = new OntologyApp();
     f.setSize(500,500);
     f.setVisible(true);
}
}

//String filepath = System.getProperty("user.dir") + File.separatorChar + "biocaster.owl";
//if (filepath.indexOf(':')>=0 || filepath.indexOf(':')<=2)
//{
//filepath="file:///"+filepath;
//filepath=filepath.replace('\\','/');
//}
//else filepath="file:"+filepath;      
