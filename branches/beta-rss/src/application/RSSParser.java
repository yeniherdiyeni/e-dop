
/**
 * The RSSParser class provides the basic functinality of parsing an RSS file given by the path.
 * For convenience, the constructor for the class enables the user to input the xml tag that he 
 * would like to extract information from. The default xml tags are <description> and </description>
 * 
 * @author Nicolae Dragu, Fouad Elkhoury
 * Date: July 13th, 2009
 */


package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

public class RSSParser {
	
	//descriptionList will contain all the words found between startTag and endTag
	private LinkedList<String> descriptionList;
	private String startTag;
	private String endTag;
	
	
	// Default constructor which sets startTag and endTag to the default values
	public RSSParser()
	{
	startTag="<description>";
	endTag="</description>";
	}
	
	/*
	 * Constructor which enables the user to specify the begin and end tags that he wants to 
	 * search for. 
	 */
	public RSSParser(String startTag, String endTag)
	{
	this.startTag=startTag;
	this.endTag=endTag;
	}
	
	/*
	 * A method which returns the linked list of all articles found in the RSS file.
	 * Each node in the list will contain such an article of type String.
	 * @return This method returns the linked list containing all the articles found in the RSS file
	 */
	public LinkedList<String> getList()
	{
	return descriptionList;
	}
	
	/*
	 * This method parses through the RSS document and extracts articles contained between startTag and endTag
	 * @param This method receives the path to the RSS file
	 */
	public void parse(String documentPath) throws IOException
	{
	Scanner in = new Scanner(new FileInputStream(documentPath));
	StringBuffer sb=new StringBuffer();
	
	//this will hold all the articles
	descriptionList=new LinkedList<String>();
	
	String document="";
	String description="";
		
		//reading the RSS file and creating a single line in a StringBuffer
		while (in.hasNext())
		{
		sb.append(in.nextLine()+" ");
		}
		//String representation of the RSS file
		document=sb.toString();
		
		boolean done=false;
		//while there still are articles in the RSS file
		while (!done)
		{
			if ((document.indexOf(startTag))>=0 && (document.indexOf(endTag)>=0))
			{
				//description will contain the article content between startTag and endTag
				description=document.substring(document.indexOf(startTag)+startTag.length(), document.indexOf(endTag));
				document=document.substring(document.indexOf(endTag)+endTag.length(),document.length());
				descriptionList.add(description);
			}
			else done=true; //no tags found=no articles left in the RSS file
		}
		
	}
}
