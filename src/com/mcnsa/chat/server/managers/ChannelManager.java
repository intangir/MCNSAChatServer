package com.mcnsa.chat.server.managers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mcnsa.chat.chat.ChatChannel;
import com.mcnsa.chat.chat.ChatPlayer;
import com.mcnsa.chat.server.Server;

public class ChannelManager {
	public static ArrayList<ChatChannel> channels;
	
	public static void init() {
		channels = new ArrayList<ChatChannel>();
	}
	
	public static ChatChannel getChannel(String name) {
		for(ChatChannel chan : channels)
			if(chan.name.equalsIgnoreCase(name))
				return chan;
		return null;
	}
	
	public static void removeChannel(String name) {
		for (int i = 0; i < channels.size(); i++) {
			ChatChannel chan = channels.get(i);
			if (chan.name.equalsIgnoreCase(name)) {
				channels.remove(chan);
				break;
			}
		}
	}

	public static void removeChannel(ChatChannel chan) {
		removeChannel(chan.name);
	}
	
	public static void cleanChanList() {
		//Function takes the current list. and cleans it
		//Get a current version of the channel list
		ArrayList<ChatChannel> currentChannels = new ArrayList<ChatChannel>();
		for (int i = 0; i < channels.size(); i++) {
			currentChannels.add(channels.get(i));
		}
		
		//loop through the new array list
		for (ChatChannel channel: currentChannels) {
			boolean remove = true;
			if (!channel.modes.contains(ChatChannel.Mode.PERSIST)) {
				//Not persist. Check the players
				for (int f = 0; f < PlayerManager.players.size(); f++){
					ChatPlayer player = PlayerManager.players.get(f);
					
					//check if in channel or listening
					if (player.listening.contains(channel.name.toLowerCase()) || player.listening.contains(channel.name.toLowerCase())){
						remove = false;
					}
				}
			}
			else {
				remove = false;
			}
			
			if (remove) {
				removeChannel(channel.name);
				System.out.println("Remove channel: "+channel.name);
			}
		}
	}
}
