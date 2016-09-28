package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final int PORT = 5000;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scan = null;
		try {
			socket = new Socket();

			socket.connect(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), PORT));

			scan = new Scanner(System.in);
			// InputStream is = socket.getInputStream();
			// OutputStream os = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			// BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(
			// socket.getOutputStream(), "UTF-8" ) );
			while (true) {
				System.out.print(">>");
				String data1 = scan.nextLine();
				if ("exit".equals(data1)) {
					break;
				}
				pw.println(data1);
				// bw.write(data1+"\n");
				// bw.newLine();

				String df = br.readLine();
				if (data1 == null) {
					System.out.println("closed");
					break;
				}

				System.out.println(df + "<<");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (scan != null) {
					scan.close();
				}
				if (socket != null && socket.isClosed() == false) {

					socket.close();

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}