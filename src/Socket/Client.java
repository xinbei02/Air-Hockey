package Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Client extends Application {
	
	private static Thread thread;
	private static Stage mainStage;
	
	@Override
	public void start(Stage mainStage) throws Exception {
		Client.mainStage = mainStage;
		thread = new Thread(new socketThread());	
		thread.start();
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	//Thread for socket
	private static class socketThread implements Runnable {
		
		int ID, connect;
		String username;
		Socket socket = new Socket();
		Game game;
		
		@Override
		public void run() {
			//Connect to server
			connect();
			//Login
			Platform.runLater(()->{
				new Login(socket, mainStage);
			});
			//Close event
			Client.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					sentSocketMessage(socket, "Exit");
				}
			});
			//Get the socket message
			getSocketMessage();
		}
		
		//Connect to server
		private void connect() {
			try{
				String serverName = "127.0.0.1";
				int serverPort = 12000;
				SocketAddress socketAddress = new InetSocketAddress(serverName, serverPort);
				socket.connect(socketAddress);
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		
		//Get the socket message
		@SuppressWarnings("deprecation")
		private void getSocketMessage() {
			try {
				while(true) {
					byte[] buf = new byte[1024];
					InputStream input = socket.getInputStream();
					input.read(buf);
					String sentence = new String(buf);
					//Get ID
					if(sentence.contains("ID")) {
						String[] tmp = sentence.split("\\$");
						ID = Integer.valueOf(tmp[0]);
					}
					//Login success, and get username
					else if(sentence.contains("Login")) {
						String[] user = sentence.split("\\$");
						username = user[1];
						Platform.runLater(()->{
							new BackGround(mainStage, username, user[2], user[3]);
							BackGround.ready.setOnMouseClicked(ready_clicked);
							BackGround.ready.setOnMouseEntered(ready_entered);
							BackGround.ready.setOnMouseExited(ready_exited);
							BackGround.play.setOnMouseClicked(play_clicked);
							game = new Game(socket, ID);
						});
					}
					//Found the opponent, and get the opponent's name and ID
					else if(sentence.contains("FindOpponent")) {
						Platform.runLater(()->{
							String[] opponent = sentence.split("\\$");
							System.out.println(opponent.length);
							game.setConnect(Integer.valueOf(opponent[1])); //ID
							BackGround.findOpponent.setVisible(false);
							BackGround.opponent.setText(opponent[2]); //name
							BackGround.opponent.setVisible(true);
							BackGround.opponentAchievement[0] = Integer.valueOf(opponent[3]);
							BackGround.opponentAchievement[1] = Integer.valueOf(opponent[4]);
							BackGround.opponentWinLose.setText("Win :\t"+opponent[3]+"\nLoss :\t"+opponent[4]);
							BackGround.opponentWinLose.setVisible(true);
							BackGround.ready.setVisible(true);
							searchOpponent_TL.stop();
						});
					}
					//Start the game
					else if(sentence.contains("Start")) {
						Platform.runLater(()->{
							game.startGame();
						});
					}
					//Opponent is ready
					else if(sentence.contains("AnotherReady")) {
						BackGround.readyB_lab.setVisible(true);
					}
					//Exit the game
					else if(sentence.contains("Exit")) {
						Platform.runLater(()->{
							mainStage.close();
						});
						thread.stop();
						socket.close();
						break;
					}
					//Get the score
					else if(sentence.contains("Score")) {
						Platform.runLater(()->{
							String[] score = sentence.split("\\$");
							for(int i=0;i<score.length;i++) {
								if(score[i].equals("Score")) {
									game.score_get(Integer.valueOf(score[i+1]), Integer.valueOf(score[i+2]));
									game.stopAction();
									break;
								}
							}
						});
					}
					//Opponent exit the game
					else if(sentence.contains("YouWin")) {
						Platform.runLater(()->{
							game.opponentExist = false;
							game.stopAction();
						});
					}
					//Play again
					else if(sentence.contains("PlayAgain")) {
						Platform.runLater(()->{
							game.playAgain();
							searchOpponent_TL.setCycleCount(Timeline.INDEFINITE);
							searchOpponent_TL.play();
						});
					}
					//Get the playerB's position and ball's position
					else {
						String[] XY = sentence.split("\\$");
						game.playerBMouse(Double.valueOf(XY[0]), Double.valueOf(XY[1]));
						if(ID>connect) game.ball_get(Double.valueOf(XY[2]), Double.valueOf(XY[3]));
					}
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		//Timeline for searching the opponent
		private Timeline searchOpponent_TL = new Timeline((new KeyFrame(Duration.millis(10),(e)->{
			sentSocketMessage(socket, "SearchOpponent");
		})));
		
		//Play Button
		private EventHandler<MouseEvent> play_clicked = (e)->{
			BackGround.findOpponent.setVisible(true);
			BackGround.play.setVisible(false);
			searchOpponent_TL.setCycleCount(Timeline.INDEFINITE);
			searchOpponent_TL.play();
		};
		
		//Ready Label
		private EventHandler<MouseEvent> ready_clicked = (e)->{
			sentSocketMessage(socket, "Ready");
			BackGround.ready.setVisible(false);
			BackGround.readyA_lab.setVisible(true);
		};
		private EventHandler<MouseEvent> ready_entered = (e)->{
			BackGround.ready.setTextFill(Color.RED);
		};
		private EventHandler<MouseEvent> ready_exited = (e)->{
			BackGround.ready.setTextFill(Color.WHITE);
		};
		
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
}
