package Socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Game {
	
	private Socket socket;
	private int ID, connect;
	public boolean opponentExist = false;
	
	private Circle playerAMouse = new Circle(Element.mouseA_X, Element.mouseA_Y, Element.mouse_R);
	private Circle playerBMouse = new Circle(Element.mouseB_X, Element.mouseB_Y, Element.mouse_R);
	private Circle ball = new Circle(Element.ball_X, Element.ball_Y, Element.ball_R);
	
	private Label countDown = new Label("3");
	private Label scorePlusA = new Label("+1");
	private Label scorePlusB = new Label("+1");
	private Label win = new Label("Win");
	private Label lose = new Label("Lose");
	private Label playAgain = new Label("Play Again");
	
	public Game(Socket socket, int ID) {
		this.socket = socket;
		this.ID = ID;
		//Label for count down to play
		countDown.setLayoutX(Element.countDown_X);
		countDown.setLayoutY(Element.countDown_Y);
		countDown.setMinSize(Element.countDown_W, Element.countDown_H);
		countDown.setAlignment(Pos.CENTER);
		countDown.setTextFill(Color.YELLOW);
		countDown.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 100));
		countDown.setVisible(false);
		//Label for playerB get the score
		scorePlusB.setLayoutX(Element.scorePlusB_X);
		scorePlusB.setLayoutY(Element.scorePlusB_Y);
		scorePlusB.setMinSize(Element.scorePlus_W, Element.scorePlus_H);
		scorePlusB.setAlignment(Pos.CENTER);
		scorePlusB.setTextFill(Color.YELLOW);
		scorePlusB.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 70));
		scorePlusB.setVisible(false);
		//Label for playerA get the score
		scorePlusA.setLayoutX(Element.scorePlusA_X);
		scorePlusA.setLayoutY(Element.scorePlusA_Y);
		scorePlusA.setMinSize(Element.scorePlus_W, Element.scorePlus_H);
		scorePlusA.setAlignment(Pos.CENTER);
		scorePlusA.setTextFill(Color.YELLOW);
		scorePlusA.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 70));
		scorePlusA.setVisible(false);
		//Label for win the game
		win.setLayoutX(Element.winLose_X);
		win.setLayoutY(Element.winLose_Y);
		win.setMinSize(Element.winLose_W, Element.winLose_H);
		win.setAlignment(Pos.CENTER);
		win.setTextFill(Color.YELLOW);
		win.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 100));
		win.setVisible(false);
		//Label for lose the game
		lose.setLayoutX(Element.winLose_X);
		lose.setLayoutY(Element.winLose_Y);
		lose.setMinSize(Element.winLose_W, Element.winLose_H);
		lose.setAlignment(Pos.CENTER);
		lose.setTextFill(Color.YELLOW);
		lose.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 100));
		lose.setVisible(false);
		//Label for play again
		playAgain.setLayoutX(Element.playAgain_X);
		playAgain.setLayoutY(Element.playAgain_Y);
		playAgain.setMinSize(Element.playAgain_W, Element.playAgain_H);
		playAgain.setAlignment(Pos.CENTER);
		playAgain.setTextFill(Color.WHITE);
		playAgain.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 20));
		playAgain.setVisible(false);
		playAgain.setOnMouseClicked(playAgain_clicked);
		//Shape for playerA's mouse
		playerAMouse.setStrokeWidth(Element.mouse_strokeW);
		playerAMouse.setStroke(Color.BLUE);
		playerAMouse.setFill(null);
		playerAMouse.setVisible(false);
		//Shape for playerB's mouse
		playerBMouse.setStrokeWidth(Element.mouse_strokeW);
		playerBMouse.setStroke(Color.GREEN);
		playerBMouse.setFill(null);
		playerBMouse.setVisible(false);
		//Shape for ball
		ball.setFill(Color.YELLOW);
		ball.setVisible(false);
		//Add all thing to the pane
		BackGround.playPane.getChildren().addAll(playerAMouse, playerBMouse, ball, countDown, scorePlusB, scorePlusA, win, lose, playAgain);
		BackGround.playPane.setOnMouseMoved(mouseMove);		
	}
	
	//Start the game
	public void startGame() {
		playerAMouse.setVisible(true);
		playerBMouse.setVisible(true);
		countDown.setVisible(true);
		countDown_TL.setCycleCount(4);
		countDown_TL.play();
	}
	
	//Set the connect ID
	public void setConnect(int connect) {
		this.connect = connect;
		opponentExist = true;
	}
	
	/** MouseA control **/
	private boolean mouseMoveable = false; 
	//Event for playerA's mouse moved
	private EventHandler<MouseEvent> mouseMove = (e) -> {
		if(mouseMoveable) {
			//X
			if(e.getX()<Element.mouse_edgeR)
				playerAMouse.setCenterX(Element.mouse_edgeR);
			else if(e.getX()>Element.playPane_W-Element.mouse_edgeR)
				playerAMouse.setCenterX(Element.playPane_W-Element.mouse_edgeR);
			else
				playerAMouse.setCenterX(e.getX());
			//Y
			if(e.getY()<Element.playPane_H/2+Element.mouse_edgeR)
				playerAMouse.setCenterY(Element.playPane_H/2+Element.mouse_edgeR);
			else if(e.getY()>Element.playPane_H-Element.mouse_edgeR)
				playerAMouse.setCenterY(Element.playPane_H-Element.mouse_edgeR);
			else
				playerAMouse.setCenterY(e.getY());
		}
		else {
			playerAMouse.setCenterX(playerAMouse.getCenterX());
			playerAMouse.setCenterY(playerAMouse.getCenterY());
		}
	};
		
	/** MouseB control **/
	private double playerBMouse_X = Element.mouseB_X;
	private double playerBMouse_Y = Element.mouseB_Y;
	//Set the playerB's mouse's position
	public void playerBMouse(double X, double Y) {
		playerBMouse_X = Element.playPane_W-X;
		playerBMouse_Y = Element.playPane_H-Y;
	}
	//Timeline for playerB's mouse's position
	private Timeline playerBMouse_TL = new Timeline((new KeyFrame(Duration.millis(10),(e)->{
		playerBMouse.setCenterX(playerBMouse_X);
		playerBMouse.setCenterY(playerBMouse_Y);
	})));
	
	/** Ball control - Main client **/
	private double mouseA_x1 = Element.mouseA_X;
	private double mouseA_y1 = Element.mouseA_Y;
	private double mouseA_x2 = Element.mouseA_X;
	private double mouseA_y2 = Element.mouseA_Y;
	private double mouseB_x1 = Element.mouseB_X;
	private double mouseB_y1 = Element.mouseB_Y;
	private double mouseB_x2 = Element.mouseB_X;
	private double mouseB_y2 = Element.mouseB_Y;
	//Get the player's mouse's previous position for calculating the slope
	private void saveMouseXY() {
		//PlayerA's mouse
		mouseA_x1 = mouseA_x2;
		mouseA_y1 = mouseA_y2;
		mouseA_x2 = playerAMouse.getCenterX();
		mouseA_y2 = playerAMouse.getCenterY();
		//PlayerB's mouse
		mouseB_x1 = mouseB_x2;
		mouseB_y1 = mouseB_y2;
		mouseB_x2 = playerBMouse.getCenterX();
		mouseB_y2 = playerBMouse.getCenterY();
	}
	
	private double slopeX = 0;
	private double slopeY = 0;
	//Set the slope
	private void setSlope(double X, double Y) {
		slopeX = X;
		slopeY = Y;
	}
	//Calculate the slope
	private void calSlope(char AB) {
		if(AB=='A') setSlope(slopeX/2+(mouseA_x2-mouseA_x1)/2, slopeY/2+(mouseA_y2-mouseA_y1)/2);
		else setSlope(slopeX/2+(mouseB_x2-mouseB_x1)/2, slopeY/2+(mouseB_y2-mouseB_y1)/2);
		setSlope((slopeX>20)? 20: (slopeX<-20)? -20: slopeX, (slopeY>20)? 20: (slopeY<-20)? -20: slopeY);
	}
	//Slow down the ball speed
	private void slowBall() {
		setSlope((slopeX<0)? slopeX+0.005: (slopeX>0)? slopeX-0.005: 0, (slopeY<0)? slopeY+0.005: (slopeY>0)? slopeY-0.005: 0);
	}
	//Recalculate the slope
	private void reSlope_X(double X) {
		slopeX = 0 - slopeX;
		ball.setCenterX(X);
	}
	private void reSlope_Y(double Y) {
		slopeY = 0 - slopeY;
		ball.setCenterY(Y);
	}
	//Calculate the distance between the player's mouse and ball
	private boolean disWithBall(double x, double y, double r) {
		if(Math.sqrt(Math.pow(ball.getCenterX()-x, 2)+Math.pow(ball.getCenterY()-y, 2))<=r)
			return true;
		return false;
	}
	//Relocate the player's mouse when it touch the ball
	private void mouseTouchBall(char AB, Circle mouse, double x1, double x2, double y1, double y2) {
		double disX = (mouse.getCenterX() - ball.getCenterX())/1000;
		double disY = (mouse.getCenterY() - ball.getCenterY())/1000;
		while(disWithBall(mouse.getCenterX(), mouse.getCenterY(), Element.ball_R+Element.mouse_edgeR)) {
			ball.setCenterX(ball.getCenterX()-disX);
			ball.setCenterY(ball.getCenterY()-disY);
			if(x1==x2&&y1==y2) setSlope(0-slopeX, 0-slopeY);
			else calSlope(AB);
		}
	}
	//Timeline for ball's running
	private Timeline ball_sent_TL = new Timeline((new KeyFrame(Duration.millis(10),(e)->{
		saveMouseXY();
		slowBall();
		//PlayerA's mouse touch the ball
		mouseTouchBall('A', playerAMouse, mouseA_x1, mouseA_x2, mouseA_y1, mouseA_y2);
		//PlayerB's mouse touch the ball
		mouseTouchBall('B', playerBMouse, mouseB_x1, mouseB_x2, mouseB_y1, mouseB_y2);		
		//Set the ball's position
		double X = ball.getCenterX() + slopeX;
		double Y = ball.getCenterY() + slopeY;
		/* X */
		if(X<Element.ball_R) reSlope_X(Element.ball_R);
		else if(X>Element.playPane_W-Element.ball_R) reSlope_X(Element.playPane_W-Element.ball_R);
		else ball.setCenterX(X);
		/* Y */
		if(Y<Element.ball_R) reSlope_Y(Element.ball_R);
		else if(Y>Element.playPane_H-Element.ball_R) reSlope_Y(Element.playPane_H-Element.ball_R);
		else ball.setCenterY(Y);
		//Get the score
		if(ball.getCenterX()>=Element.winRangeL+Element.ball_R && ball.getCenterX()<=Element.winRangeR-Element.ball_R) {
			//PlayerA get the score
			if(ball.getCenterY()<=Element.ball_R) {
				BackGround.scoreA++;
				getScoreStop();
			}
			//PlayerB get the score
			else if(ball.getCenterY()>=Element.playPane_H-Element.ball_R) {
				BackGround.scoreB++;
				getScoreStop();
			}
		}
	})));
	
	
	/** Ball control - Second client **/
	private double ball_get_X = Element.ball_X;
	private double ball_get_Y = Element.ball_Y;
	//Set the ball's position
	public void ball_get(double X, double Y) {
		ball_get_X = Element.playPane_W-X;
		ball_get_Y = Element.playPane_H-Y;
	}
	//Timeline for ball's running
	private Timeline ball_get_TL = new Timeline((new KeyFrame(Duration.millis(10),(e)->{
		ball.setCenterX(ball_get_X);
		ball.setCenterY(ball_get_Y);
	})));
	
	/** Socket **/
	//Timeline for sent playerA's mouse's position and ball's position
	private Timeline socket_TL = new Timeline(new KeyFrame(Duration.millis(10), (e)->{
		String sentence = String.valueOf(playerAMouse.getCenterX()) + "$" 
						+ String.valueOf(playerAMouse.getCenterY()) + "$" 
						+ String.valueOf(ball.getCenterX()) + "$"
						+ String.valueOf(ball.getCenterY()) + "$";
		sentSocketMessage(socket, sentence);
	}));
	
	
	/** Count down**/
	private boolean socketFirst = true;
	private int countDownTime = 2;
	//Timeline for count down
	private Timeline countDown_TL = new Timeline(new KeyFrame(Duration.millis(1000),(e)->{
		countDown.setText(String.valueOf(countDownTime));
		if(countDownTime==0) {
			mouseMoveable = true;
			countDown.setVisible(false);
			BackGround.readyB_lab.setVisible(false);
			BackGround.readyA_lab.setVisible(false);
			ball.setVisible(true);
			//Run the ball
			if(ID<connect) {
				ball_sent_TL.setCycleCount(Timeline.INDEFINITE);
				ball_sent_TL.play();
			}
			else {
				ball_get_TL.setCycleCount(Timeline.INDEFINITE);
				ball_get_TL.play();
			}
			//Start to sent socket message
			if(socketFirst) {
				socket_TL.setCycleCount(Timeline.INDEFINITE);
				socket_TL.play();
				socketFirst = false;
			}
			//Run the playerB's mouse
			playerBMouse_TL.setCycleCount(Timeline.INDEFINITE);
			playerBMouse_TL.play();
		}
		countDownTime--;
	}));
	
	/** Score control **/
	//Set the score
	public void score_get(int scoreA, int scoreB) {
		BackGround.scoreA = scoreB;
		BackGround.scoreB = scoreA;
	}
	//Stop the action when get the score
	private void getScoreStop() {
		String sentence = "Score$" + String.valueOf(BackGround.scoreA) + "$" + String.valueOf(BackGround.scoreB) + "$";
		sentSocketMessage(socket, sentence);
		stopAction();
	}
	//Stop the action
	public void stopAction() {
		mouseMoveable = false;
		ball_sent_TL.stop();
		if(opponentExist) {
			getScore_TL.setCycleCount(105);
			getScore_TL.play();
		}
		//Opponent exit
		else gameOver();
	}
	
	private int getScoreTime = 0;
	private int preScoreA = 0, preScoreB = 0;
	//Timeline for animation of getting the score
	private Timeline getScore_TL = new Timeline(new KeyFrame(Duration.millis(10),(e)->{
		//PlayerA get the score
		if(BackGround.scoreA!=preScoreA) {
			scorePlusA.setVisible(true);
			scorePlusA.setLayoutX(scorePlusA.getLayoutX()-Element.scorePlusDisA_X);
			scorePlusA.setLayoutY(scorePlusA.getLayoutY()-Element.scorePlusDisA_Y);
		}
		//PlayerB get the score
		else if(BackGround.scoreB!=preScoreB){
			scorePlusB.setVisible(true);
			scorePlusB.setLayoutX(scorePlusB.getLayoutX()-Element.scorePlusDisB_X);
			scorePlusB.setLayoutY(scorePlusB.getLayoutY()-Element.scorePlusDisB_Y);
		}
		getScoreTime++;
		if(getScoreTime==100) {
			scorePlusA.setVisible(false);
			scorePlusB.setVisible(false);
			BackGround.scoreA_lab.setText(String.valueOf(BackGround.scoreA));
			BackGround.scoreB_lab.setText(String.valueOf(BackGround.scoreB));
			preScoreA = BackGround.scoreA;
			preScoreB = BackGround.scoreB;
		}
		if(getScoreTime==105) gameOver();
	}));
	//Game over
	private void gameOver() {
		ball_get_TL.stop();
		playerBMouse_TL.stop();
		countDown_TL.stop();
		init();
		//PlayerA win
		if(BackGround.scoreA==Element.gameScore || !opponentExist) {
			winOrLose("win");
		}
		//PlayerB win
		else if(BackGround.scoreB==Element.gameScore) {
			winOrLose("lose");
		}
		//Next game
		else {
			countDown.setVisible(true);
			countDown_TL.setCycleCount(4);
			countDown_TL.play();
		}
	}
	
	private void winOrLose(String winLose) {
		if(winLose.equals("win")) {
			win.setVisible(true);
			BackGround.userAchievement[0]++;
			BackGround.opponentAchievement[1]++;
		}
		else {
			lose.setVisible(true);
			BackGround.userAchievement[1]++;
			BackGround.opponentAchievement[0]++;
		}
		BackGround.userWinLose.setText("Win :\t"+String.valueOf(BackGround.userAchievement[0])+"\nLoss :\t"+String.valueOf(BackGround.userAchievement[1]));
		BackGround.opponentWinLose.setText("Win :\t"+String.valueOf(BackGround.opponentAchievement[0])+"\nLoss :\t"+String.valueOf(BackGround.opponentAchievement[1]));
		playAgain.setVisible(true);
		socket_TL.stop();
		sentSocketMessage(socket, "GameOver$"+winLose+"$");
		opponentExist = false;
	}
	
	/** Play again **/
	//Event for play again label
	private EventHandler<MouseEvent> playAgain_clicked = (e)->{
		sentSocketMessage(socket, "PlayAgain");
	};
	//Play again
	public void playAgain() {		
		playerAMouse.setVisible(false);
		playerBMouse.setVisible(false);
		win.setVisible(false);
		lose.setVisible(false);
		playAgain.setVisible(false);
		BackGround.opponent.setVisible(false);
		BackGround.opponentWinLose.setVisible(false);
		BackGround.findOpponent.setVisible(true);
		preScoreA = 0;
		preScoreB = 0;
		BackGround.scoreA = 0;
		BackGround.scoreB = 0;
		BackGround.scoreA_lab.setText("0");
		BackGround.scoreB_lab.setText("0");
	}
	
	/** Initial **/
	private void init() {
		mouseA_x1 = Element.mouseA_X;
		mouseA_y1 = Element.mouseA_Y;
		mouseA_x2 = Element.mouseA_X;
		mouseA_y2 = Element.mouseA_Y;
		
		mouseB_x1 = Element.mouseB_X;
		mouseB_y1 = Element.mouseB_Y;
		mouseB_x2 = Element.mouseB_X;
		mouseB_y2 = Element.mouseB_Y;
		
		slopeX = 0;
		slopeY = 0;
		
		ball_get_X = Element.ball_X;
		ball_get_Y = Element.ball_Y;
		
		socketFirst = true;
		countDownTime = 2;
		countDown.setText("3");
		countDown.setVisible(false);
		getScoreTime = 0;
		
		BackGround.ready.setVisible(false);
		BackGround.readyA_lab.setVisible(false);
		BackGround.readyB_lab.setVisible(false);
		
		ball.setVisible(false);
		ball.setCenterX(Element.ball_X);
		ball.setCenterY(Element.ball_Y);
		playerAMouse.setCenterX(Element.mouseA_X);
		playerAMouse.setCenterY(Element.mouseA_Y);
		playerBMouse.setCenterX(Element.mouseB_X);
		playerBMouse.setCenterY(Element.mouseB_Y);
		scorePlusA.setLayoutX(Element.scorePlusA_X);
		scorePlusA.setLayoutY(Element.scorePlusA_Y);
		scorePlusB.setLayoutX(Element.scorePlusB_X);
		scorePlusB.setLayoutY(Element.scorePlusB_Y);
	}
	
	//Sent socket message
	private void sentSocketMessage(Socket socket, String message) {
		try {
			OutputStream output = socket.getOutputStream();
			output.write(message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
