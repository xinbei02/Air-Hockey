package Socket;

import java.io.File;

public class Element {
	/** Scene **/
	public static double scene_W = 500;
	public static double scene_H = 650;
	
	/** loginPane **/
	//Label
	public static double label_W = 200;
	public static double label_H = 40;
	//title Label
	public static double title_X = scene_W/2 - label_W/2;
	public static double title_Y = 180;
	//username TextField
	public static double username_X = scene_W/2 - label_W/2;
	public static double username_Y = scene_H/2 - label_H - label_H/2 - 20;
	//password PasswordField
	public static double password_X = scene_W/2 - label_W/2;
	public static double password_Y = scene_H/2 - label_H/2;
	//hint Label
	public static double hint_X = scene_W/2 - label_W/2;
	public static double hint_Y = 210;
	//Button
	public static double button_W = label_W/2 - 10;
	public static double button_H = label_H;
	//login Button
	public static double login_X = scene_W/2 - label_W/2;
	public static double login_Y = scene_H/2 + label_H/2 + 20;
	//signup Button
	public static double signup_X = scene_W/2 + 10;
	public static double signup_Y = scene_H/2 + label_H/2 + 20;
	//userInfo.txt
	public static File file = new File(".\\src\\Socket\\userInfo.txt");
	
	/** topPane **/
	public static double topPane_H = 50;
	//user Label
	public static double user_lab_W = 100;
	public static double user_lab_H = 30;
	public static double user_lab_X = 10;
	public static double user_lab_Y = 10; 
	//userWinLose Label
	public static double userWinLose_lab_W = 100;
	public static double userWinLose_lab_H = 50;
	public static double userWinLose_lab_X = user_lab_X + user_lab_W;
	public static double userWinLose_lab_Y = 0; 
	//opponent Label
	public static double opponent_lab_W = 100;
	public static double opponent_lab_H = 30;
	public static double opponent_lab_X = scene_W - opponent_lab_W - 10;
	public static double opponent_lab_Y = 10; 
	//userWinLose Label
	public static double opponentWinLose_lab_W = 100;
	public static double opponentWinLose_lab_H = 50;
	public static double opponentWinLose_lab_X = opponent_lab_X - opponentWinLose_lab_W;
	public static double opponentWinLose_lab_Y = 0; 
	
	/** gamePane **/
	public static double gamePane_W = scene_W; //500
	public static double gamePane_H = scene_H - topPane_H; //600
	//VS Label
	public static double VS_W = 80;
	public static double VS_H = 40;
	public static double VS_X = 0;
	public static double VS_Y = gamePane_H/2 - VS_H/2;
	//score Label
	public static double score_W = 80;
	public static double score_H = 60;
	//scoreB Label
	public static double scoreB_X = 0;
	public static double scoreB_Y = gamePane_H/2 - score_H - VS_H;
	//scoreA Label
	public static double scoreA_X = 0;
	public static double scoreA_Y = gamePane_H/2 + VS_H;
	//play Button
	public static double playBtn_W = 150;
	public static double playBtn_H = 100;
	public static double playBtn_X = gamePane_W/2 - playBtn_W/2;
	public static double playBtn_Y = gamePane_H/2 + playBtn_H;
	//findOpponent Label
	public static double findOpponent_W = 200;
	public static double findOpponent_H = 80;
	public static double findOpponent_X = gamePane_W/2 - findOpponent_W/2;
	public static double findOpponent_Y = gamePane_H/2 + findOpponent_H;
	//ready(Pressed) Label
	public static double readyBtn_W = 200;
	public static double readyBtn_H = 100;
	public static double readyBtn_X = gamePane_W/2 - readyBtn_W/2;
	public static double readyBtn_Y = gamePane_H/2 + readyBtn_H;
	//ready Label
	public static double ready_W = 80;
	public static double ready_H = 40;
	//readyB Label
	public static double readyB_X = 0;
	public static double readyB_Y = scoreB_Y - ready_H;
	//readyA Label
	public static double readyA_X = 0;
	public static double readyA_Y = scoreA_Y + score_H;
	//table Rectangle
	public static double table_X = 85;
	public static double table_Y = 30;
	public static double table_W = 330;
	public static double table_H = 540;
	public static double table_strokeW = 10;
	
	/** playPane **/
	/** background **/
	public static double playPane_X = table_X + table_strokeW/2; //90
	public static double playPane_Y = table_Y + table_strokeW/2; //30
	public static double playPane_W = table_W - table_strokeW; //320
	public static double playPane_H = table_H - table_strokeW; //530
	
	public static double playLine_strokeW = 5;
	
	public static double playLine_dif = 2.5;
	public static double middleLine_startX = playLine_dif;
	public static double middleLine_startY = playPane_H/2;
	public static double middleLine_endX = playPane_W - playLine_dif;
	public static double middleLine_endY = playPane_H/2;
	
	public static double playCircle_R = 50;
	public static double middleCircle_X = playPane_W/2;
	public static double middleCircle_Y = playPane_H/2;
	
	public static double playArc_dif = 2;
	public static double goalArc_R = 50;
	public static double goalArcB_X = playPane_W/2;
	public static double goalArcB_Y = playArc_dif;
	public static double goalArcA_X = playPane_W/2;
	public static double goalArcA_Y = playPane_H - playArc_dif;
	
	/** animation **/
	//countDown Label
	public static double countDown_W = 100;
	public static double countDown_H = 100;
	public static double countDown_X = playPane_W/2 - countDown_W/2;
	public static double countDown_Y = playPane_H/2 - countDown_H/2;
	//scorePlus Label
	public static double scorePlus_W = 100;
	public static double scorePlus_H = 100;
	//scorePlusB Label
	public static double scorePlusB_X = playPane_W/2 - scorePlus_W/2;
	public static double scorePlusB_Y = playPane_H/4 - scorePlus_H/2;
	//scorePlusA Label
	public static double scorePlusA_X = playPane_W/2 - scorePlus_W/2;
	public static double scorePlusA_Y = 3*playPane_H/4 - scorePlus_H/2;
	//Distance for scorePlusA to move
	public static double scorePlusDisA_X = ((scorePlusA_X + scorePlus_W/2 + table_X) - (scoreA_X + score_W/2))/100; 
	public static double scorePlusDisA_Y = ((scorePlusA_Y + scorePlus_H/2 + table_Y) - (scoreA_Y + score_H/2))/100;
	//Distance for scorePlusB to move
	public static double scorePlusDisB_X = ((scorePlusB_X + scorePlus_W/2 + table_X) - (scoreB_X + score_W/2))/100; 
	public static double scorePlusDisB_Y = ((scorePlusB_Y + scorePlus_H/2 + table_Y) - (scoreB_Y + score_H/2))/100;
	//win and lose Label
	public static double winLose_W = 300;
	public static double winLose_H = 100;
	public static double winLose_X = playPane_W/2 - winLose_W/2;
	public static double winLose_Y = playPane_H/2 - winLose_H/2;
	//playAgain Label
	public static double playAgain_W = 300;
	public static double playAgain_H = 50;
	public static double playAgain_X = playPane_W/2 - playAgain_W/2;
	public static double playAgain_Y = playPane_H/2 + winLose_Y/2 - playAgain_H/2;
	
	/** play **/
	//mouse
	public static double mouse_R = 20;
	public static double mouse_strokeW = 10;
	public static double mouse_edgeR = mouse_R + mouse_strokeW/2;
	//mouseA Circle
	public static double mouseA_X = playPane_W/2;
	public static double mouseA_Y = playPane_H - mouse_edgeR;
	//mouseB Circle
	public static double mouseB_X = playPane_W/2;
	public static double mouseB_Y = mouse_edgeR;
	//ball Circle
	public static double ball_R = 15;
	public static double ball_X = playPane_W/2;
	public static double ball_Y = playPane_H/2;
	//The range for getting the score
	public static double winRangeL = goalArcB_X - goalArc_R;
	public static double winRangeR = goalArcB_X + goalArc_R;
	//The score for win the game
	public static int gameScore = 3;
}
