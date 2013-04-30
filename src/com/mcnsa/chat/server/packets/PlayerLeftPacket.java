package com.mcnsa.chat.server.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mcnsa.chat.chat.ChatPlayer;
import com.mcnsa.chat.server.packets.IPacket;


public class PlayerLeftPacket implements IPacket {
	public static final short id = 4;

	public ChatPlayer player = null;
	public String longname = "";

	public PlayerLeftPacket() {
	}

	public PlayerLeftPacket(ChatPlayer player, String longname) {
		this.longname = longname;
		this.player = player;
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeShort(id);
		out.writeUTF(longname);
		player.write(out);
		out.flush();
	}

	public void read(DataInputStream in) throws IOException {
		longname = in.readUTF();
		player = ChatPlayer.read(in);
	}
}