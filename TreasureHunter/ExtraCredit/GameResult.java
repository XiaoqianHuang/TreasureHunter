import java.util.*;


/**
 *Title: GameResult.java
 *Description: This class is the class for the information of game result.
 *@author  XiaoqianHuang
 *@version 1.0
 */
public class GameResult implements Comparable<GameResult>{
	private String username;
	private int score;
	private Date time = new Date();
	
	/**Create a constructor*/
	public GameResult(String newname,int newscore,Date newtime){
		this.username = newname;
		this.score = newscore;
		this.time = newtime;
	}
	
	/**Overriding the compareTo method to sort the objects by score*/
	public int compareTo(GameResult g){
		return this.score - g.score;
	}
	
	/**Create the getters*/
	public String getname(){
		return username;
	}
	
	/**Create the getters*/
	public int getscore(){
		return score;
	}
	
	/**Create the getters*/
	public Date gettime(){
		return time;
	}
}
