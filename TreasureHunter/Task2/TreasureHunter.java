import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;
import java.io.*;

/**
 *Title: TreasureHunter.java
 *Description: This class is the class for the game TreasureHunter.
 *@author  Xiaoqian Huang
 *@version 1.0
 */
public class TreasureHunter {
	private int totalscore = 0;
	private ArrayList<GameResult> highscorelist = new ArrayList<GameResult>();//create an arraylist to store the result objects
	private static int q,w;
    static int m=3,n=3;
	private static JButton[][] bs;
	
	/**run the game*/
	public static void main(String[] args) throws ClassNotFoundException, ParseException{
		JOptionPane.showMessageDialog(null,"Welcome to Treasure Hunter game!\nThe objective is to dig up as much treasure as possible before being caught in a trap.\nSimply click on a square to uncover it.");
		TreasureHunter start = new TreasureHunter();//create a new object
	    bs = new JButton[m][n];
	    /*Create a random trap*/
	    q = (int)(Math.random()*m);
		w = (int)(Math.random()*n);
		start.go();
	}

	/**create a GUI and launch the game*/
	public void go() throws ClassNotFoundException, ParseException{
		
		try{
			File f =new File("highscorelist.txt");
			/*Check if the file exists. if not, create one*/
			if(!f.exists()){
				f.createNewFile();
			}
			/*Read the information of the score from the .txt file*/
			FileReader fileReader = new FileReader("highscorelist.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String content=null;
			while((content=bufferedReader.readLine())!=null){
				addcontent(content);
			}
			bufferedReader.close();
		}catch(IOException e){
			System.out.println("Errors occured!");
			System.exit(1);
		}
		
		/*Create the components on the GUI*/
		JFrame frame = new JFrame("Treasure Hunter!");
		JPanel mainPanel = new JPanel(new GridLayout(3,3));
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				bs[i][j] = new JButton("Unknown");
				bs[i][j].addActionListener(new TreasureListener());
				mainPanel.add(bs[i][j]);	
			}
		}
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		/*Display the frame*/
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		frame.setLocationRelativeTo(null);//set the frame at the middle of the screen
		frame.setVisible(true);
	}
	
	/**Distinguish the information from the contents*/
	public void addcontent(String Line)throws ParseException{
		String[] info = Line.split("/");
		int score = Integer.parseInt(info[1]);
		/*translate the string to date*/
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(info[2]);
		GameResult message = new GameResult(info[0],score,date);
		highscorelist.add(message);
	}
	
	/**Create a button listener*/
	class TreasureListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			/*To know which button is clicked*/
			JButton b = (JButton)e.getSource();
			if(e.getSource() != bs[q][w]){
				/*Generate the score(1-10) of the treasure*/
				int score = (int)(Math.random()*10+1);
				b.setText("Treasure:"+score);
				totalscore = totalscore + score;
				/*Make the clicked button unaccessible*/
				b.setEnabled(false);
			}
			else if(e.getSource() == bs[q][w]){
				b.setText("Trap!");
				/*Display a dialog*/
				JOptionPane.showMessageDialog(null,"A Trap Caught You! Score:" + totalscore);
				String name = JOptionPane.showInputDialog(null,"Please enter your name:\n","name",JOptionPane.PLAIN_MESSAGE);	
				Date time = new Date();
				/*Add the information to the arraylist*/
				highscorelist.add(new GameResult(name,totalscore,time));
				highscore();
				try {
					playagain();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**Display the high score*/
	public void highscore(){
		/*Sort the score*/
		Collections.sort(highscorelist);
		Collections.reverse(highscorelist);
		/*Display a dialog to show the score*/
		String showscore = "";
		for(int i=0;i<highscorelist.size();i++){
			showscore = showscore + "\nScore:#"+(i+1)+"."+highscorelist.get(i).getscore()+" "+highscorelist.get(i).getname()+" "+highscorelist.get(i).gettime();
		}
		JOptionPane.showMessageDialog(null,"High Score:"+ showscore);
	}
	
	/**Show a dialog to ask whether play again*/
	public void playagain() throws ClassNotFoundException, ParseException{
		int re=JOptionPane.showConfirmDialog(null,"Play Again?","Play",JOptionPane.YES_NO_OPTION);
		if(re==1){
			File f = new File("highscorelist.txt");
			if (f.exists()){
				f.delete();
			}
			System.exit(0);
		}else if(re==0){
			totalscore = 0;
			/*Store the information of the current player*/
			try{
				FileWriter fileWriter = new FileWriter("highscorelist.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				for(int i = 0;i<highscorelist.size();i++){
					bufferedWriter.write(highscorelist.get(i).getname()+"/");
					bufferedWriter.write(highscorelist.get(i).getscore()+"/");
					String datestr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(highscorelist.get(i).gettime());
					bufferedWriter.write(datestr+"/\n");
				}
				bufferedWriter.close();
				fileWriter.close();
			}catch(IOException e){
				System.out.println("Errors occured!");
				System.exit(1);
			}
			/*Create a new object to restart a game*/
		    q = (int)(Math.random()*m);
			w = (int)(Math.random()*n);
			TreasureHunter restart = new TreasureHunter();
			restart.go();
		}
	}
	
	
}