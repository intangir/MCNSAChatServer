package com.mcnsa.chat.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;

public class ChatPlayer {
	public String name;
	public String server;
	public String channel;
	public String formatted;
	public String lastPM;
	public ArrayList<String> listening;
	public ArrayList<Mode> modes;

	public ChatPlayer(String name, String server) {
		this(name, server, "", new ArrayList<String>());
	}

	@SuppressWarnings("unchecked")
	public ChatPlayer(String name, String server, String channel, ArrayList<String> listening) {
		this.name = name;
		this.server = server;
		this.channel = channel;
		this.listening = (ArrayList<String>) listening.clone();
		this.formatted = name;
		this.modes = new ArrayList<Mode>();
		this.lastPM = "";
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeUTF(name);
		out.writeUTF(formatted);
		out.writeUTF(server);
		out.writeUTF(channel);
		out.writeUTF(lastPM);
		out.writeInt(listening.size());
		for (String listen : listening)
			out.writeUTF(listen);
		out.writeInt(modes.size());
		for (Mode mode : modes)
			out.writeUTF(mode.name());
	}

	public static ChatPlayer read(DataInputStream in) throws IOException {
		String name = in.readUTF();
		String formatted = in.readUTF();
		String server = in.readUTF();
		String channel = in.readUTF();
		String lastPM = in.readUTF();
		ArrayList<String> listening = new ArrayList<String>();
		int size = in.readInt();
		for (int i = 0; i < size; i++)
			listening.add(in.readUTF());
		ArrayList<Mode> modes = new ArrayList<Mode>();
		size = in.readInt();
		for (int i = 0; i < size; i++)
			try {
				modes.add(Mode.valueOf(in.readUTF()));
			}
			catch (Exception e) {
				Bukkit.getLogger().severe(e.getMessage());
			}

		ChatPlayer p = new ChatPlayer(name, server, channel, listening);
		p.formatted = formatted;
		p.modes = modes;
		p.lastPM = lastPM;
		return p;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof ChatPlayer))
			return false;
		ChatPlayer p = (ChatPlayer) o;
		return p.name.equals(name) && p.server.equals(server);
	}

	public int hashCode() {
		return (name + "|" + server).hashCode();
	}

	public enum Mode {
		SEEALL, MUTE, LOCKED, POOFED;
	}
}
