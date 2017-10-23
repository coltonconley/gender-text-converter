import java.awt.*;			//needed for GUI elements
import java.awt.event.*;	//needed for processing events stuff (clicking, etc)

import javax.swing.*;		//JFrame, Timer...

/*********************************************************************************
 * 
 * Name:	Colton Conley
 * Block:	A Block
 * Date:	9/9/14
 * 	
 * Program A: TextConverter
 * Description:
 * 		This code creates a text pane for entering a "student comment" and 
 * buttons to select whether to convert the text from masculine to feminine 
 * ("he" -> "she" and other related changes) or feminine to masculine. When text
 * is entered, the program will convert all of the gender pronouns to the indicated
 * gender, displaying the fixed text.
 * 				
 ***********************************************************************************/
public class TextConverter extends JFrame
{
	public static final int WINDOW_WIDTH = 500;		// Initial window width
	public static final int WINDOW_HEIGHT = 300;	// Initial window height

	public static void main(String[] rags)
	{
		TextConverter window = new TextConverter();
	}

	/**
	 * Create the window contents and arrange for callbacks when the
	 *  buttons are clicked.
	 */
	public TextConverter()
	{
		// Set up the window basics
		setTitle("Text Converter");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Create and assemble the components for our window in one JPanel and
		//	add it into our JFrame. Send "this" so button actions can call
		//	methods in this class.
		JPanel windowContents = new ContentsOfWindow(this);		
		add(windowContents);

		setVisible(true);
	}

	/**
	 * Converts the given text from the female student version to the male
	 * student version.
	 * 
	 * @param text		code to convert
	 * @return			text converted from female to male
	 */
	public String convertFemaleToMale(String text)
	{
		//a 2d array is created with all the male pronouns and female counterparts
		String[][] pronounListFemale =
			{	{"her", "his"},
				{"man", "woman"},
				{"Man", "Woman"},
				{"herself", "himself"},
				{"Herself", "Himself"},
				{"Her", "His"},
				{"hers", "his"},
				{"Hers", "His"},
				{"she", "he"},
				{"She", "He"},
			};

		//calls a method to convert the pronouns in the original text
		text = conversion(pronounListFemale, text);

		return text;
	}

	/**
	 * Converts the given text from the male student version to the female
	 * student version.
	 * 
	 * @param text		code to convert
	 * @return			text converted from male to female
	 */
	public String convertMaleToFemale(String text)
	{
		
		//a 2d array is created with all the female pronouns and male counterparts
		String[][] pronounListMale = 
			{	{"his", "her"},
				{"woman", "man"},
				{"Woman", "Man"},
				{"himself", "herself"},
				{"Himself", "Herself"},
				{"he", "she"},
				{"him", "her"},
				{"His", "Her"},
				{"He", "She"},
				{"Him", "Her"}
			};
		
		//calls a method to convert the pronouns in the original text
		text = conversion(pronounListMale, text);



		return text;
	}

	/**
	 * Converts all the pronouns in the entered text to their counterparts depending on which 2d array 
	 * is provided for conversion.
	 * 
	 * @param strings		List of pronouns to be checked for and converted 
	 * @param text			Original text entered for conversion
	 * @return				Converted text
	 */
	public String conversion(String[][] strings, String text)
	{
		int pronounTestingFor = 0; //A counter for the while loop as well as the index of pronoun being tested for

		//Cycles through each pronoun in the provided 2D array and replaces them in the text as necessary
		while (pronounTestingFor < strings.length)
		{
			//call a method to replace one of the pronouns from the array
			text = checkForPronoun(text, strings, pronounTestingFor);
			pronounTestingFor++;
		}
		return text;
	}

	/**
	 * Checks for the presence of an indicated pronoun and replaces any occurences with the opposite gender pronoun
	 * 
	 * @param text					the text that will be examined for the specified pronoun
	 * @param strings				A 2D array of strings that has all the pronouns and their replacements
	 * @param pronounTestingFor		The index of the 2D array that holds the pronoun being tested for and a replacement 
	 * @return						Text with the specified pronoun replaced in all ocurrences
	 */
	public String checkForPronoun(String text, String[][] strings, int pronounTestingFor)
	{
		int pronounFoundAt = 0;  //integer value that will hold the index of a located gender pronoun
		char precedingCharacter;  //character located right before an identified pronoun
		char followingCharacter; //character located right after a located pronoun
		String fixedText = "";  //A string that will hold the corrected text with changed pronouns
		
		while (pronounFoundAt != -1)
		{
			//look for any ocurrences of the specified pronoun
			pronounFoundAt = text.indexOf(strings[pronounTestingFor][0]);
			if (pronounFoundAt != -1)
			{
				//checks to see if the pronoun is surrounded by other letters
				precedingCharacter = findPrecedingLetter(text, pronounFoundAt);
				followingCharacter = findFollowingLetter(strings[pronounTestingFor][0], text, pronounFoundAt);
				if (isLetter(precedingCharacter) || isLetter(followingCharacter))
				{	
					//doesn't replace the pronoun
					fixedText = fixedText + text.substring(0, pronounFoundAt) + strings[pronounTestingFor][0];
					text = text.substring(pronounFoundAt + strings[pronounTestingFor][0].length());
				}
				else
				{
					
					//replaces the pronoun
					fixedText = fixedText + text.substring(0, pronounFoundAt) + strings[pronounTestingFor][1];
					text = text.substring(pronounFoundAt + strings[pronounTestingFor][0].length());
				}
			}
		}	
		return fixedText+text;
	}
	
	
	/**
	 * Tests to see if the entered character is a letter or not
	 * 
	 * @param character		the character that will be tested to see if it is a letter or not
	 * @return				return boolean value determining whether character is a letter
	 */
	public boolean isLetter (char character)
	{
		boolean isLetter = false; //boolean value to be returned
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //all possible letters
		int letterFound = alphabet.indexOf(character);  //check to see if character is in the alphabet
		if(letterFound > -1)
		{
			isLetter = true;
		}
		return isLetter;

	}

	/**
	 * Finds the letter before a pronoun found in a block of text.  If the pronoun comes at
	 * the beginning of the text, a non letter character is returned
	 * 
	 * @param remainingText			The text that contains the pronoun
	 * @param pronounFoundAt		The location of a pronoun for which the preceding letter/character 
	 * 								is being looked for
	 * @return						returns a character containing either the letter or non letter
	 * 								preceding the pronoun entered as a parameter
	 */
	public char findPrecedingLetter(String remainingText, int pronounFoundAt)
	{
		char precedingCharacter; //character value that will contain the character before the pronoun specified
		//if pronoun is found somewhere after the beginning assign the character at the index before the pronoun
		if(pronounFoundAt > 0)
		{
			precedingCharacter = remainingText.charAt(pronounFoundAt - 1);
		}

		else//if pronoun is at the beginning of the document assign a non letter value
		{
			precedingCharacter = ' ';

		}
		return precedingCharacter;

	}

	/**
	 * Finds the letter after a pronoun found in a block of text.  If the pronoun comes at
	 * the end of the text, a non letter character is returned
	 * 
	 * @param pronounTestingFor		a string containing the pronoun being looked for
	 * @param remainingText			the string containing the pronoun
	 * @param pronounFoundAt		the location of the pronoun within the string
	 * @return						returns a character containing either the letter or non letter
	 * 								following the pronoun entered as a parameter
	 */
	public char findFollowingLetter(String pronounTestingFor, String remainingText, int pronounFoundAt)
	{
		char followingCharacter; //character value that will contain the character following the pronoun specified
		//if pronoun is found before the end of the document, 
		if(pronounFoundAt+pronounTestingFor.length() < remainingText.length())
		{

			followingCharacter = remainingText.charAt(pronounFoundAt + pronounTestingFor.length());

		}
		else//if pronoun is at the end of the document assign a non letter value
		{
			followingCharacter = ' ';

		}

		return followingCharacter;
	}














	/**
	 * Paint the window, including all of its child components.
	 *  @param g		Graphics object
	 */
	public void paint(Graphics g)
	{
		// Paints all of the child components (recursively).
		paintComponents(g);
	}
}

/*********************************************************************************
 * 
 * WindowContents class
 * 
 * 		This class is a JPanel containing the scrollable text pane for the
 * text to convert and buttons for the user to initiate the conversions. It
 * detects the button clicks and decides which button was clicked but it does
 * not do the actual conversions. Instead it calls methods in the TextConverter
 * to do the real conversion work. It was written as its own class to
 * separate most of the GUI (graphical user interface) mess from the code
 * in the "main" class to do the non-graphical work.
 * 
 * 		The JPanel contains components which, in turn, can contain addition
 * components. The structure of components set up here is as follows:
 * 		JPanel
 * 			JPanel for label
 * 				Label
 * 				horizontal glue
 * 			JScrollPane for text
 * 			JPanel for buttons
 * 				JButton for "to MALE"
 * 				spacer
 * 				JButton for "to FEMALE"
 * Additional components could be added as needed.
 * 				
 ***********************************************************************************/
class ContentsOfWindow extends JPanel implements ActionListener
{
	private TextConverter tc;			// TextConverter object containing
	//	the conversion methods to call

	private JButton buttonToMale;		// Button for converting to masculine
	private JButton buttonToFemale;		// Button for converting to feminine
	private JTextArea textArea;			// Component containing text to convert

	public static final Color LIGHT_BLUE = new Color(180, 210, 255);

	/**
	 * Create the window and arrange for callbacks to actionPerformed
	 * 	when the buttons are clicked.
	 * 
	 * @param tcIn		Object that will contain this JPanel. It has the
	 * 					methods to call to do the actual text conversion
	 */

	public ContentsOfWindow(TextConverter tcIn)
	{
		// Save the pointer to the TextConverter object. We need it since the
		//	methods defining the actions to take when the buttons are clicked
		//	are all in the TextConverter object.
		tc = tcIn;

		// Create a panel containing a label on the left.
		JPanel labelPanel = createLabelPanel();

		// Create a scrollable text area to enter text. The text should
		//	wrap and break at word boundaries when possible.
		JScrollPane scrollPane = createTextPanel();

		// Make a panel containing buttons lined up horizontally.
		JPanel buttonPanel = createButtonPanel();

		// Insert the label, the scrollable text area and the buttons with 
		//	appropriate dividers, border and background color.
		assembleTheWindow(labelPanel, scrollPane, buttonPanel);

		// Make sure actionPerformed is called when the buttons are clicked.
		buttonToMale.addActionListener(this);
		buttonToFemale.addActionListener(this);
	}

	/**
	 * Create a panel containing a label on the left.
	 * 
	 * @return			the newly created panel with the label
	 */
	private JPanel createLabelPanel()
	{
		// Create the panel with a horizontal layout.
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.LINE_AXIS));

		// Create the label
		JLabel label = new JLabel(" Enter Text To Be Converted:");

		// Fill the panel, adding "glue" at the end to force the label
		//	to be all the way to the left.
		labelPanel.add(label);
		labelPanel.add(Box.createHorizontalGlue());

		labelPanel.setBackground(LIGHT_BLUE);

		return labelPanel;
	}

	/**
	 * Create a scrollable text area in which the user can enter text. The 
	 * text should wrap and break at word boundaries when possible.
	 * 
	 * @return			the newly created JScrollPane for entering text
	 */
	private JScrollPane createTextPanel()
	{
		// Create the (empty) text area with wrapping a word boundaries.
		textArea = new JTextArea("", 1, 1);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		// Place it in a scroll pane and return the result.
		return new JScrollPane(textArea);
	}

	/**
	 * Create a panel containing a row of buttons.
	 * 
	 * @return			the newly created panel of clickable buttons
	 */
	private JPanel createButtonPanel()
	{
		// Create the panel with horizontal layout
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

		// Create buttons
		buttonToMale = new JButton("To MALE");
		buttonToFemale = new JButton("To FEMALE");
		/**
		 * You may create more buttons here as needed. Declare them class
		 * scope since the actionPerformed thread must refer to them.
		 */

		// Add newly created buttons to the panel with spacers (rigid areas)
		//	for spacing.
		buttonPanel.add(buttonToMale);
		buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
		buttonPanel.add(buttonToFemale);
		/**
		 * If you create more buttons then add them to the JPanel here. It 
		 * looks nice if you put additional spacers between them.
		 */

		buttonPanel.setBackground(LIGHT_BLUE);

		return buttonPanel;
	}

	/**
	 * Assemble all of the child components for this panel.
	 * 
	 * @param labelPanel	first component in this window
	 * @param scrollPane	second component in this window
	 * @param buttonPanel	third component in this window
	 */
	private void assembleTheWindow(	JComponent labelPanel, 
			JComponent scrollPane, 
			JComponent buttonPanel)
	{
		// This panel is laid out vertically, with appropriate border and 
		//	background color.
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(15,15,15,15));		
		setBackground(LIGHT_BLUE);

		// Put the components into the panel with spacers.
		add(labelPanel);
		add(Box.createRigidArea(new Dimension(0,5)));
		add(scrollPane);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(buttonPanel);
	}

	/**
	 * Receive callbacks whenever a button is clicked. See which was clicked
	 * 	and perform the appropriate action.
	 * 
	 * @param e			ActionEvent that contains the source of this action		
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object sourceOfAction = e.getSource();

		if(sourceOfAction == buttonToMale)
		{
			// Convert feminine terms to masculine
			String text = textArea.getText();
			text = tc.convertFemaleToMale(text);
			textArea.setText(text);
		}
		else if(sourceOfAction == buttonToFemale)
		{
			// Convert masculine terms to feminine
			String text = textArea.getText();			
			text = tc.convertMaleToFemale(text);
			textArea.setText(text);
		}
		/**
		 * If you create more buttons then add corresponding actions here.
		 */
	}

}






/*
 * 
 * {" her ", " his "},
				{" her. ", " his. "},
				{" her! ", " his! "},
				{" her? ", " his? "},
				{" her)", " his)"},
				{"(her ", "(his "},
				{" her\" ", " his\" "},
				{" \"her ", " \"his "},
				{"Her ", "His "},
				{" hers ", " his "},
				{" hers. ", " his. "},
				{" hers! ", " his! "},
				{" hers? ", " his? "},
				{" hers)", " his)"},
				{"(hers ", "(his "},
				{" hers\" ", " his\" "},
				{" \"hers ", " \"his "},
				{"Hers ", "His "},
				{" she ", " he "},
				{" she. ", " he. "},
				{" she! ", " he! "},
				{" she? ", " he? "},
				{" she)", " he)"},
				{"(she ", "(he "},
				{" she\" ", " he\" "},
				{" \"she ", " \"he "},
				{"She ", "He "},
 */









