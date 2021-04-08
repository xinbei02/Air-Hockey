package Socket;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BackGround {
	
	private Stage mainStage;
	private static Pane gamePane = new Pane();
	public static Pane playPane = new Pane();
	
	public static int[] userAchievement = new int[2];
	public static Label userWinLose = new Label();
	public static Label opponent = new Label();
	public static int[] opponentAchievement = new int[2];
	public static Label opponentWinLose = new Label();
	
	public static Button play = new Button("Play");
	public static Label findOpponent = new Label("Searching Opponent...");
	
	public static Label ready = new Label("Ready");
	public static Label readyA_lab = new Label("Ready");
	public static Label readyB_lab = new Label("Ready");
	public static int scoreA = 0, scoreB = 0;
	public static Label scoreB_lab = new Label(String.valueOf(scoreB));
	public static Label scoreA_lab = new Label(String.valueOf(scoreA));
	
	public BackGround(Stage mainStage, String username, String win, String lose) {
		
		/** topPane **/
		//Label for user name
		Label user = new Label(username);
		user.setLayoutX(Element.user_lab_X);
		user.setLayoutY(Element.user_lab_Y);
		user.setMinSize(Element.user_lab_W, Element.user_lab_H);
		user.setAlignment(Pos.CENTER);
		user.setTextFill(Color.BLACK);
		user.setStyle("-fx-background-radius: 30; -fx-background-color: white");
		user.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 15));
		//Label for user name
		userAchievement[0] = Integer.valueOf(win);
		userAchievement[1] = Integer.valueOf(lose);
		userWinLose.setText("Win :\t"+win+"\nLoss :\t"+lose);
		userWinLose.setLayoutX(Element.userWinLose_lab_X);
		userWinLose.setLayoutY(Element.userWinLose_lab_Y);
		userWinLose.setMinSize(Element.userWinLose_lab_W, Element.userWinLose_lab_H);
		userWinLose.setAlignment(Pos.CENTER);
		userWinLose.setTextFill(Color.BLACK);
		userWinLose.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 10));
		//Label for opponent name
		opponent.setLayoutX(Element.opponent_lab_X);
		opponent.setLayoutY(Element.opponent_lab_Y);
		opponent.setMinSize(Element.opponent_lab_W, Element.opponent_lab_H);
		opponent.setAlignment(Pos.CENTER);
		opponent.setTextFill(Color.BLACK);
		opponent.setStyle("-fx-background-radius: 30; -fx-background-color: white");
		opponent.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 15));
		opponent.setVisible(false);
		//Label for user name
		opponentWinLose.setLayoutX(Element.opponentWinLose_lab_X);
		opponentWinLose.setLayoutY(Element.opponentWinLose_lab_Y);
		opponentWinLose.setMinSize(Element.opponentWinLose_lab_W, Element.opponentWinLose_lab_H);
		opponentWinLose.setAlignment(Pos.CENTER);
		opponentWinLose.setTextFill(Color.BLACK);
		opponentWinLose.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 10));
		opponentWinLose.setVisible(false);
		//Top pane
		Pane topPane = new Pane();
		topPane.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		topPane.setMinHeight(Element.topPane_H);
		topPane.getChildren().addAll(user, userWinLose, opponentWinLose, opponent);

		/** playPane **/
		//Shape for playing table
		Rectangle table = new Rectangle(Element.table_X, Element.table_Y, Element.table_W, Element.table_H); //6:10
		table.setArcHeight(10);
		table.setArcWidth(10);
		table.setFill(null);
		table.setStrokeWidth(Element.table_strokeW);
		table.setStroke(Color.RED);
		//Line for playing table
		Line middle_line = new Line(Element.middleLine_startX, Element.middleLine_startY, Element.middleLine_endX, Element.middleLine_endY); //6:10
		middle_line.setStrokeWidth(Element.playLine_strokeW);
		middle_line.setStroke(Color.RED);
		Circle middle_circle = new Circle(Element.middleCircle_X, Element.middleCircle_Y, Element.playCircle_R); //6:10
		middle_circle.setFill(null);
		middle_circle.setStrokeWidth(Element.playLine_strokeW);
		middle_circle.setStroke(Color.RED);
		Arc goal_arc_B = new Arc(Element.goalArcB_X, Element.goalArcB_Y, Element.goalArc_R, Element.goalArc_R, 180, 180);
		goal_arc_B.setFill(null);
		goal_arc_B.setStrokeWidth(Element.playLine_strokeW);
		goal_arc_B.setStroke(Color.RED);
		Arc goal_arc_A = new Arc(Element.goalArcA_X, Element.goalArcA_Y, Element.goalArc_R, Element.goalArc_R, 0, 180);
		goal_arc_A.setFill(null);
		goal_arc_A.setStrokeWidth(Element.playLine_strokeW);
		goal_arc_A.setStroke(Color.RED);
		Group table_group = new Group();
		table_group.getChildren().addAll(middle_line, middle_circle, goal_arc_A, goal_arc_B);
		//Playing pane
		playPane.setLayoutX(Element.playPane_X);
		playPane.setLayoutY(Element.playPane_Y);
		playPane.setMinSize(Element.playPane_W, Element.playPane_H);
		playPane.setMaxSize(Element.playPane_W, Element.playPane_H);
		playPane.getChildren().addAll(table_group);
		
		/** gmaePane **/
		//Label for playerB's score
		scoreB_lab.setLayoutX(Element.scoreB_X);
		scoreB_lab.setLayoutY(Element.scoreB_Y);
		scoreB_lab.setMinSize(Element.score_W, Element.score_H);
		scoreB_lab.setAlignment(Pos.CENTER);
		scoreB_lab.setTextFill(Color.WHITE);
		scoreB_lab.setFont(Font.font("Timer New Roman", FontWeight.NORMAL, 50));
		//Label for VS
		Label VS = new Label("V.S.");
		VS.setLayoutX(Element.VS_X);
		VS.setLayoutY(Element.VS_Y);
		VS.setMinSize(Element.VS_W, Element.VS_H);
		VS.setAlignment(Pos.CENTER);
		VS.setTextFill(Color.WHITE);
		VS.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 25));
		//Label fir playerA's score
		scoreA_lab.setLayoutX(Element.scoreA_X);
		scoreA_lab.setLayoutY(Element.scoreA_Y);
		scoreA_lab.setMinSize(Element.score_W, Element.score_H);
		scoreA_lab.setAlignment(Pos.CENTER);
		scoreA_lab.setTextFill(Color.WHITE);
		scoreA_lab.setFont(Font.font("Timer New Roman", FontWeight.NORMAL, 50));
		//Button for the play action
		play.setMinSize(Element.playBtn_W, Element.button_H);
		play.setLayoutX(Element.playBtn_X);
		play.setLayoutY(Element.playBtn_Y);
		play.setAlignment(Pos.CENTER);
		play.setTextFill(Color.BLACK);
		play.setStyle("-fx-background-radius: 30; -fx-background-color: white");
		play.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 30));
		//Label for text of finding opponent
		findOpponent.setLayoutX(Element.findOpponent_X);
		findOpponent.setLayoutY(Element.findOpponent_Y);
		findOpponent.setMinSize(Element.findOpponent_W, Element.findOpponent_H);
		findOpponent.setAlignment(Pos.CENTER);
		findOpponent.setTextFill(Color.WHITE);
		findOpponent.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 20));
		findOpponent.setVisible(false);
		//Label for ready action
		ready.setLayoutX(Element.readyBtn_X);
		ready.setLayoutY(Element.readyBtn_Y);
		ready.setMinSize(Element.readyBtn_W, Element.readyBtn_H);
		ready.setAlignment(Pos.CENTER);
		ready.setTextFill(Color.WHITE);
		ready.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 50));
		ready.setVisible(false);
		//Label for playerB's ready state
		readyB_lab.setLayoutX(Element.readyB_X);
		readyB_lab.setLayoutY(Element.readyB_Y);
		readyB_lab.setMinSize(Element.ready_W, Element.ready_H);
		readyB_lab.setAlignment(Pos.CENTER);
		readyB_lab.setTextFill(Color.YELLOW);
		readyB_lab.setFont(Font.font("Timer New Roman", FontWeight.NORMAL, 20));
		readyB_lab.setVisible(false);
		//Label for playerA's ready state
		readyA_lab.setLayoutX(Element.readyA_X);
		readyA_lab.setLayoutY(Element.readyA_Y);
		readyA_lab.setMinSize(Element.ready_W, Element.ready_H);
		readyA_lab.setAlignment(Pos.CENTER);
		readyA_lab.setTextFill(Color.YELLOW);
		readyA_lab.setFont(Font.font("Timer New Roman", FontWeight.NORMAL, 20));
		readyA_lab.setVisible(false);
		//Game pane
		gamePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		gamePane.getChildren().addAll(table, playPane, scoreA_lab, VS, scoreB_lab, play, findOpponent, ready, readyA_lab, readyB_lab);
		/** Border pane **/
		BorderPane backGroundPane = new BorderPane();
		backGroundPane.setTop(topPane);
		backGroundPane.setCenter(gamePane);
		
		/** Scene **/
		Scene backGroundScene = new Scene(backGroundPane, Element.scene_W, Element.scene_H);
		
		/** Stage **/
		this.mainStage = mainStage;
		this.mainStage.setScene(backGroundScene);
	}
}
