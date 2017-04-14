/*Mohammad Abdul Mubeen Ahmed Tachi*/
package battle;

import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;

public class Battle extends Application 
 {
 public int numrows = 10;
 public int numcols = 10;
 public int count=20;
 public int numLines = 0;
 public int hits = 0;
 public char[][] world=new char[numcols+2][numrows+2];		
 public char empty=' ';
 public char full ='O';
 public char miss= 'M';
 public char hit = 'H';
 public String emptyString = " " + empty + " ";
 public String  fullString = " " + full + " ";
 public String missString = " " + miss + " ";
 public String  hitString = " " + hit + " ";
 private String buttonName;
 private Stage primaryStage;

 GridPane gridPane = new GridPane();
 
 private Button loadButton = new Button("LOAD");
 private Button saveButton = new Button("SAVE");
 private Button[][] btButtons = new Button[numcols+1][numrows+1];
 private String inFile,outFile;

 private TextField tfLoad    = new TextField();
 private TextField tfSave    = new TextField();
 
 public FileReader myfilereader;
 public BufferedReader mybuffer;
 public FileWriter myfilewriter;
 public PrintWriter myprintwriter;

 public Battle() throws Exception // constructor
  {
  NumOfMoves();
  System.out.println(numLines);
  }
   @Override
 
 public void start(Stage primaryStage) throws Exception 
  {
  gridPane.setAlignment(Pos.CENTER);
  gridPane.setHgap(2);
  gridPane.setVgap(2);
  loadButton.setOnAction(e -> readLife());
  saveButton.setOnAction(e -> saveLife());

  gridPane.add(new Label("input file"),15,0);
  gridPane.add(tfLoad,20,0);
  gridPane.add(loadButton,10,0);

  gridPane.add(new Label("output file"),15,2);
  gridPane.add(tfSave,20,2);
  gridPane.add(saveButton,10,2);

 //set up grid of buttons
  initButtons();

  //add the buttons to the grid
  for (int row=1;row<=numrows;row++)
   {
   for (int col=1;col<=numcols;col++)
    {
    gridPane.add(btButtons[col][row],col,row+numrows + 10);
    }
   }

  Scene scene = new Scene(gridPane,250+numcols*100,numrows*100);
  primaryStage.setTitle("BATTLE");
  primaryStage.setScene(scene);
  primaryStage.show();//let the show begin!
  }
  
  public int GetFirstNum(String line)
   {
   int position;
   position = line.indexOf(" ");//finds position of first blank space
   return Integer.valueOf(line.substring(0,position).trim());
  //find string up to first space, drop extra spaces, convert to a number
   }
 
  public int GetSecondNum(String line)
   {
   int position;
   position = line.indexOf(" ");//finds position of first blank space
   return Integer.valueOf(line.substring(position).trim());
   //find string up to first space, drop extra spaces, convert to a number
   //you can find spaces using indexOf in a substring
   }
   
   void NumOfMoves() // To count the moves of the player
    {
    String strLine="";
    try
     {
     FileInputStream fstream = new FileInputStream("life6.txt");
     BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

     while ((strLine = br.readLine()) != null)  //Reading file to the end.
      {
      numLines++;
      }
     }
     catch(Exception e)
      {
      }
    }
    
   public void initButton(int col, int row, String label) //place a single button on grid
    {
    buttonName = label;
    btButtons[col][row] = new Button(buttonName);
    btButtons[col][row].setOnAction(e -> pushMyButtons(col,row));
    btButtons[col][row].setFont(Font.font("Courier New", FontWeight.BOLD, 20)); 
    }

   public void initButtons() throws Exception//do all battle grid buttons 
    {
    char letter;
    String label;
    label = emptyString; 
    //initialize to empty
    //emptyWorld();

    for (int row=0; row<=numrows+1; row++)
     {
     for (int col=0;col<=numcols+1; col++)
      {
      world[col][row]=empty;
      if((row>0) && (row<=numrows) && (col>0) && (col<=numcols)) 
      initButton(col,row,label);
      }
     }
    }

  private void pushMyButtons(int col,int row) // click the button to play
   {
   String buttonName;
   if(world[col][row]==empty)
    {
    world[col][row]=miss;
    }
    else
    {
    world[col][row]=hit;
    hits++;
    }
    if(world[col][row]==hit)
	buttonName = hitString;
    else
	buttonName = missString;

    updateButton(col,row,buttonName);
    count--;
  
    if(count == 0)
    {
    Alert alert = new Alert(AlertType.INFORMATION); // pop up for game lost
    alert.setTitle("Information Dialog");
    alert.setHeaderText(null);
    alert.setContentText("Game is Lost! Try Again");
    alert.showAndWait();
    System.exit(0);
    }
    else
    {
    if(hits==numLines)
     {
     Alert alert = new Alert(AlertType.INFORMATION); // pop up for winning the game
     alert.setTitle("Information Dialog");
     alert.setHeaderText(null);
     alert.setContentText("Congratulations!! You Won Game..");
     alert.showAndWait();
     System.exit(0);
     }
    	
    }
  }    
   
  public void updateButton(int col, int row, String label)//update a single button on a grid
  {
  //remove from grid to replace
  gridPane.getChildren().removeAll(btButtons[col][row]);

  //replace
  initButton(col,row,label);
  
  gridPane.add(btButtons[col][row],col,row+numrows + 10);//must match original placement
  }

  public void ReadWorld()//read initial live spots from file
  {
  String label,line;//line holds coordinates  )in string form
  int X,Y,numlines=0;

  label = fullString;
  //initialize to empty
  for (int row=0; row<=numrows+1; row++)
   {
   for (int col=0;col<=numcols+1; col++)
    {
    world[col][row]=empty;
    }
   }
 
    try{
    //process stuff here
    while( (line = mybuffer.readLine()) != null)
	{
	//System.out.println(line + " coordinates read");
	X=GetFirstNum(line);
        Y=GetSecondNum(line);
	numlines++;
        world[X][Y]=full;
	//System.out.println("about to update:" + X + Y + '*' + label + '*');
	
        
	}
    } catch (IOException exception)
	{
	System.out.println("exception: " + exception);
	}//IO error
  }

 public void DrawWorld(PrintWriter myprintwriter)
  {
  //System.out.print("   ");
  myprintwriter.print("   ");
  for (int col=1;col<numcols; col++)
	{
	//System.out.print(col/10);
        myprintwriter.print(col/10);
	};
  //System.out.println("  ");
  myprintwriter.println("  ");
  //System.out.print("   ");
  myprintwriter.print("   ");
  for (int col=1;col<numcols; col++)
	{
	//System.out.print(col%10);
        myprintwriter.print(col%10);
	};
//System.out.println();
  myprintwriter.println();
  for (int row=1; row<numrows; row++)
	{
	//System.out.print(row/10);
        myprintwriter.print(row/10);
	//System.out.print(row%10+":");
        myprintwriter.print(row%10+":");
	for (int col=1;col<numcols; col++)
		{
		//System.out.print(world[col][row]);
                myprintwriter.print(world[col][row]);
		}
	//System.out.println();
        myprintwriter.println();
	}
  }

 public void readLife()
 {
 try{
  //get a file name for input
  inFile = tfLoad.getText();
  if (inFile == "") inFile="life0.txt";
  myfilereader = new FileReader(inFile);//where to read from
  mybuffer = new BufferedReader(myfilereader);//for reading to and editing in
  ReadWorld();
  mybuffer.close();
 } catch (IOException exception)
	{
	System.out.println("exception: " + exception);
	}//IO error
 }

public void saveLife()
  { 
  outFile = tfSave.getText();
  if (outFile == "") outFile="junk.txt";
  try{
   FileWriter myfilewriter = new FileWriter(outFile);//where to write to
  
   PrintWriter myprintwriter = new PrintWriter(myfilewriter);//for writing from
   DrawWorld(myprintwriter);
   myprintwriter.close();
   //System.out.println("LIFE has closed up shop.");
  
  } catch (IOException exception)
	{
	System.out.println("exception: " + exception);
	}//IO error
   }

   public static void main(String[] args) {
        launch(args);
    }
    
}