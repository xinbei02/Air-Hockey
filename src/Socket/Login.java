package Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login {
	
	private Stage mainStage;
	private Socket socket;
	
	TextField username = new TextField();
	PasswordField password = new PasswordField();
	Label hint = new Label();
	
	public Login(Socket socket, Stage mainStage) {
		this.socket = socket;
		//Label for "User login"
		Label title = new Label("USER LOGIN");
		title.setMinSize(Element.label_W, Element.label_H);
		title.setLayoutX(Element.title_X);
		title.setLayoutY(Element.title_Y);
		title.setAlignment(Pos.CENTER);
		title.setTextFill(Color.WHITE);
		title.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 30));
		//TextField for username
		username.setPromptText("Username");
		username.setMinSize(Element.label_W, Element.label_H);
		username.setLayoutX(Element.username_X);
		username.setLayoutY(Element.username_Y);
		username.setAlignment(Pos.CENTER);
		username.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(255, 255, 255, .3)");
		username.setFont(Font.font("Timer New Roman", FontWeight.LIGHT, 15));
		username.setFocusTraversable(false);
		//PasswordField for password
		password.setPromptText("Password");
		password.setMinSize(Element.label_W, Element.label_H);
		password.setLayoutX(Element.password_X);
		password.setLayoutY(Element.password_Y);
		password.setAlignment(Pos.CENTER);
		password.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(255, 255, 255, .3)");
		password.setFont(Font.font("Timer New Roman", FontWeight.LIGHT, 15));
		password.setFocusTraversable(false);
		//Label for hint
		hint.setMinSize(Element.label_W, Element.label_H);
		hint.setLayoutX(Element.hint_X);
		hint.setLayoutY(Element.hint_Y);
		hint.setAlignment(Pos.CENTER);
		hint.setTextFill(Color.RED);
		hint.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 20));
		hint.setVisible(false);
		//Button for login
		Button login = new Button("LOGIN");
		login.setMinSize(Element.button_W, Element.button_H);
		login.setLayoutX(Element.login_X);
		login.setLayoutY(Element.login_Y);
		login.setAlignment(Pos.CENTER);
		login.setTextFill(Color.DARKGRAY);
		login.setStyle("-fx-background-radius: 30; -fx-background-color: white");
		login.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 12));
		login.setOnMouseClicked(login_clicked);
		//Button for sign up
		Button signup = new Button("SIGNUP");
		signup.setMinSize(Element.button_W, Element.button_H);
		signup.setLayoutX(Element.signup_X);
		signup.setLayoutY(Element.signup_Y);
		signup.setAlignment(Pos.CENTER);
		signup.setTextFill(Color.DARKGRAY);
		signup.setStyle("-fx-background-radius: 30; -fx-background-color: white");
		signup.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 12));
		signup.setOnMouseClicked(signup_clicked);
		//Pane for login
		Pane loginPane = new Pane();
		loginPane.setStyle("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
				+ "linear-gradient(#20262b, #191d22), radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0))");
		loginPane.getChildren().addAll(title, username, password, login, signup, hint);
		//Scene for login
		Scene LoginScene = new Scene(loginPane, Element.scene_W, Element.scene_H);
		//Stage
		this.mainStage = mainStage;
		this.mainStage.setAlwaysOnTop(true);
		this.mainStage.setResizable(false);
		this.mainStage.setTitle("Client");
		this.mainStage.setScene(LoginScene);
		this.mainStage.show();
	}
	
	//Event for login
	public EventHandler<MouseEvent> login_clicked = (e)->{
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Element.file), "UTF-8"));
			String str = null;
			while ((str = reader.readLine()) != null) {
				String[] info = str.split(" ");
				if(username.getText().equals(info[0]) && password.getText().equals(info[1])) {
					//Login success
					String sentence = "Login$" + username.getText() + "$" + String.valueOf(info[2]) + "$" + String.valueOf(info[3]) + "$";
					OutputStream output = socket.getOutputStream();
					output.write(sentence.getBytes());
					return;
				}
			}
			//Username or password is wrong
			hint.setText("Wrong !");
			hint.setVisible(true);
			reader.close();
		}
		catch(IOException e1) {
			e1.printStackTrace();
		}
	};
	
	//Event for sign up
	public EventHandler<MouseEvent> signup_clicked = (e)->{
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Element.file), "UTF-8"));
			String str = null;
			while ((str = reader.readLine()) != null) {
				String[] info = str.split(" ");
				//Username is exist
				if(username.getText().equals(info[0])) {
					hint.setText("Exist !");
					hint.setVisible(true);
					return;
				}
			}
			reader.close();
			//Sign up success
			hint.setText("Success !");
			hint.setVisible(true);
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Element.file, true), "UTF-8"));
			fw.append(username.getText()+" "+password.getText()+" 0 0\n");
			fw.flush();
			fw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	};
}
