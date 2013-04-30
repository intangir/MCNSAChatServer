package com.mcnsa.chat.server.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mcnsa.chat.chat.ChatPlayer;
import com.mcnsa.chat.server.packets.IPacket;

public class PlayerUpdatePacket implements IPacket {
	public static final short id = 6;

	public ChatPlayer player = null;

	public PlayerUpdatePacket() {
	}

	public PlayerUpdatePacket(ChatPlayer player) {
		this.player = player;
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeShort(id);
		player.write(out);
		out.flush();
	}

	public void read(DataInputStream in) throws IOException {
		player = ChatPlayer.read(in);
	}
}

