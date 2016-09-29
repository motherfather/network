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
				ChatClient.consoleLog(data);
				
				if (data.equals(null)) {
					break;
				}
			}
		} catch (IOException e) {
			ChatClient.consoleLog("error:" + e);
		}

	}

}
