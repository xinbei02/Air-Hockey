package Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Server {
	
	//Client information
	public static class clientInfo {
		boolean work = false;
		String username = "";
		int win, lose;
		boolean searchOpponet = false;
		int connect = -1;
		boolean ready = false;
		Socket socket = null;
		public clientInfo(Socket socket){
			this.socket = socket;
		}
	}
	//Save all the client
	public static ArrayList<clientInfo> clientList = new ArrayList<clientInfo>();
	public static int clientNum = 0;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try{
			String serverName = "127.0.0.1";
			int serverPort = 12000;
			Socket socket = new Socket();
			ServerSocket serverSocket = new ServerSocket();
			SocketAddress socketAddress = new InetSocketAddress(serverName, serverPort);
			serverSocket.bind(socketAddress);			
			//client connect, and new thread
			while(true) {
				socket = serverSocket.accept();
				clientList.add(new clientInfo(socket));
				Thread thread = new Thread(new clientThread(socket, clientNum));	
				thread.start();
				clientNum++;
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//Thread for Client
	private static class clientThread implements Runnable {
		
		private Socket socket = null;
		private int ID = -1;
		private int connect = -1;
		private String username = "";
		private int win, lose;
		
		private clientThread(Socket socket, int ID) {
			this.socket = socket;
			this.ID = ID;
		}
		
		@Override
		public void run() {
			//sent ID to client
			String sentence1 = String.valueOf(ID) + "$ID$";
			sentMessage(socket, sentence1);
			while(true) {
				try {
					byte[] buf=new byte[1024];
					InputStream input = socket.getInputStream();
					input.read(buf); //Read the message
					String sentence = new String(buf);
					//Login success, get username
					if(sentence.contains("Login")) {
						String[] user = sentence.split("\\$");
						username = user[1];
						win = Integer.valueOf(user[2]);
						lose = Integer.valueOf(user[3]);
						clientList.get(ID).username = username;
						clientList.get(ID).win = win;
						clientList.get(ID).lose = lose;
						clientList.get(ID).work = true;
						//Sent username to client
						sentMessage(socket, sentence);
					}
					//Search the opponent
					else if(sentence.contains("SearchOpponent")) {
						clientList.get(ID).searchOpponet = true;
						//If find the opponent
						if(searchOpponent()) {
							//Sent opponent's name and ID to client
							String sentence2 = "FindOpponent$" + String.valueOf(connect) + "$" + clientList.get(connect).username + "$"
									+ clientList.get(connect).win + "$" + clientList.get(connect).lose + "$";
							sentMessage(socket, sentence2);
							clientList.get(ID).searchOpponet = false;
						}
					}
					//Ready to play
					else if(sentence.contains("Ready")) {
						clientList.get(this.ID).ready = true;
						//Sent ready to opponent
						sentMessage(clientList.get(connect).socket, "AnotherReady");
						//If opponent is ready
						if(clientList.get(connect).ready) {
							//Sent start to client
							sentMessage(socket, "Start");
							//Sent start to opponent
							sentMessage(clientList.get(connect).socket, "Start");
						}
					}
					//Exit the game
					else if(sentence.contains("Exit")) {
						clientList.get(this.ID).work = false;
						//Sent "You Win" to opponent
						if(connect>-1 && clientList.get(connect).connect == ID) {
							sentMessage(clientList.get(connect).socket, "YouWin");
							lose++;
						}
						//Record user's information
						recordInfo();
						//Sent Exit to client
						sentMessage(socket, sentence);
						break;
					}
					//Get the score
					else if(sentence.contains("Score")) {
						sentMessage(clientList.get(connect).socket, sentence);
					}
					//The game is over
					else if(sentence.contains("GameOver")) {
						String[] gameOver = sentence.split("\\$");
						if(gameOver[1].equals("win")) {
							clientList.get(ID).win++;
							win++;
						}
						else {
							clientList.get(ID).lose++;
							lose++;
						}
						clientList.get(ID).connect = -1;
						clientList.get(ID).ready = false;
						connect = -1;
					}
					//Play again
					else if(sentence.contains("PlayAgain")) {
						sentMessage(socket, sentence);
					}
					//Get the playerA's position and ball's position
					else {
						sentMessage(clientList.get(connect).socket, sentence);
					}
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//Search the opponent
		private boolean searchOpponent() {
			if(clientList.get(ID).connect>-1) {
				connect = clientList.get(ID).connect;
				return true;
			}
			for(int i=0;i<clientNum;i++) {
				if(i!=ID && clientList.get(i).searchOpponet) {
					clientList.get(i).connect = ID;
					clientList.get(ID).connect = i;
					connect = i;
					return true;
				}
			}
			return false;
		}
		
		private void recordInfo() {
			try {
				String content = "";
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Element.file), "UTF-8"));
				String str = null;
				while ((str = reader.readLine()) != null) {
					String[] info = str.split(" ");
					if(username.equals(info[0])) {
						info[2] = String.valueOf(win);
						info[3] = String.valueOf(lose);
						content = content + info[0] + " " + info[1] + " " + info[2] + " "+ info[3] + "\n";
					}
					else content = content + str + "\n";
				}
				reader.close();
				BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Element.file, false), "UTF-8"));
				fw.write(content);
				fw.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		//Sent message
		private void sentMessage(Socket socket, String message) {
			try {
				OutputStream output = socket.getOutputStream();
				output.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
