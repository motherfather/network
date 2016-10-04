package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class ChatServerThread extends Thread {
	private Socket socket;
	private String name;
	private List<PrintWriter> listPrintWriter;

	public ChatServerThread(Socket socket, List<PrintWriter> listPrintWriter) {		// 4-1. 생성자(초기값 설정)
		this.socket = socket;
		this.listPrintWriter = listPrintWriter;
	}

	@Override
	public void run() {		// 4-2. run작성
		try {
			// 1. print remote socket address
			InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			ChatServer.consoleLog("connected by client[" + remoteSocketAddress.getAddress().getHostAddress() + ":"
					+ remoteSocketAddress.getPort() + "]");

			// 2. create Stream(From Basic Stream)
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			// 3. processing....
			String line = null;
			while (true) {
				line = bufferedReader.readLine();
				ChatServer.consoleLog(line);
				if (line == null) {
					doQuit(printWriter);
					break;
				}
				
				String[] tokens = line.split(":");
				if ("JOIN".equals(tokens[0])) { // "...".equalsIgnoreCase(......) <= 대소문자 무시!!
					doJoin(tokens[1], printWriter);
				} else if ("MESSAGE".equals(tokens[0])) {
					doMessage(name, printWriter, bufferedReader);
					doQuit(name, printWriter);
					break;
				} else if ("QUIT".equals(tokens[0])) {
//					printWriter.println("\r\n");
					doQuit(name, printWriter);
					break;
				}
			}
			ChatServer.consoleLog("disconnected by client[" + remoteSocketAddress.getAddress().getHostAddress() + ":"
					+ remoteSocketAddress.getPort() + "]");

		} catch (UnsupportedEncodingException e) {
			ChatServer.consoleLog("erro5r:" + e);
		} catch (IOException e) {
			ChatServer.consoleLog("error6:" + e);
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				ChatServer.consoleLog("error7:" + e);
			}
		}

	}

	private void doJoin(String name, PrintWriter printWriter) {
		// 1. save nickname
		this.name = name;

		// 2. broadcasting...
		String message = name + "님이 입장했습니다";
		broadcastMessage(message);

		// 3. add PrintWriter
		addPrintWriter(printWriter);

		// 4. ack
		printWriter.println("JOIN:OK");

	}

	private void doMessage(String name, PrintWriter printWriter, BufferedReader bufferedReader) throws IOException {
//		deletePrintWriter(printWriter); // doJoin에서의 printWriter를 지우고 채팅시작후 다시 생성!!
		printWriter.println("채팅시작");
		
		// add PrintWriter
//		addPrintWriter(printWriter);
		

		while (true) {
			String message = bufferedReader.readLine();
			if ("QUIT".equals(message)) {
				// printWriter.println("\r\n");
				break;
			}
			broadcastMessage(name + ":" + message);
			ChatServer.consoleLog(message);

		}
//		doQuit(name, printWriter);
	}

	private void doQuit(PrintWriter printWriter) {
		printWriter.println("종료");
	}
	
	private void doQuit(String name, PrintWriter printWriter) {
		deletePrintWriter(printWriter);

		broadcastMessage(name + "님이 퇴장하셨습니다.");

		printWriter.println("종료");

	}

	private void addPrintWriter(PrintWriter printWriter) {
		synchronized (listPrintWriter) {
			listPrintWriter.add(printWriter);
		}
	}

	private void deletePrintWriter(PrintWriter printWriter) {
		synchronized (listPrintWriter) {
			listPrintWriter.remove(printWriter);
		}
	}

	private void broadcastMessage(String message) {
		synchronized (listPrintWriter) {
			for (PrintWriter printWriter : listPrintWriter) {
				printWriter.println(message);
			}
		}
	}

}
