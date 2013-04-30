package com.mcnsa.chat.server.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.mcnsa.chat.chat.ChatPlayer;
import com.mcnsa.chat.server.packets.IPacket;

public class ServerJoinedPacket implements IPacket {
	public static final short id = 1;

	public String shortName = null;
	public String longName = null;
	public ArrayList<ChatPlayer> players = null;
	public String passcode = null;

	public ServerJoinedPacket() {
	}
	
	public ServerJoinedPacket(String shortName, String longName, ArrayList<ChatPlayer> players, String password) {
		this.shortName = shortName;
		this.longName = longName;
		this.players = players;
		this.passcode = password;
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeShort(id);
		out.writeUTF(passcode);
		out.writeUTF(shortName);
		out.writeUTF(longName);
		if (players == null) {
			out.writeInt(0);
		} else {
			out.writeInt(players.size());
			for (ChatPlayer player : players) {
				player.write(out);
			}
		}
		out.flush();
	}

	public void read(DataInputStream in) throws IOException {
		passcode = in.readUTF();
		shortName = in.readUTF();
		longName = in.readUTF();
		players = new ArrayList<ChatPlayer>();
		int numPlayers = in.readInt();
		for (int i = 0; i < numPlayers; i++) {
			players.add(ChatPlayer.read(in));
		}
	}
}
