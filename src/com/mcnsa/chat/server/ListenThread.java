package com.mcnsa.chat.server;

import java.io.IOException;
import java.net.Socket;

public class ListenThread extends Thread {
	public void run() {
		try {
			while (true) {
				Socket sock = Server.serverSock.accept();
				ServerThread t = new ServerThread(sock);
				t.start();
				Server.threads.add(t);
			}
		} catch (IOException e) {
			System.out.println("Server Socket closed.");
		}
	}
}
