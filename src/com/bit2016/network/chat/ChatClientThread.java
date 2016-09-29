package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClientThread extends Thread {
	private Socket socket = null;
	// private List<PrintWriter> listPrintWriter;

	public ChatClientThread(
			Socket socket/* , List<PrintWriter> listPrintWriter */) {
		this.socket = socket;
		// this.listPrintWriter = listPrintWriter;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			while (true) {
				String data = br.readLine();
				if (data == null) {
					break;
				}
				ChatClient.consoleLog(data);
//				System.out.println("서버가준거 " + data);
			}
		} catch (IOException e) {
			ChatClient.consoleLog("erro3r:" + e);
		}// finally {
//			try {
//				if (socket != null && socket.isClosed() == false) {
//					socket.close();
//				}
//			} catch (IOException e) {
//				ChatClient.consoleLog("error4:" + e);
//			}
//		}
	}

}
