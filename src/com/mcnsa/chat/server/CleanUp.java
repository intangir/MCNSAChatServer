package com.mcnsa.chat.server;

import java.util.ArrayList;

import com.mcnsa.chat.chat.ChatChannel;
import com.mcnsa.chat.chat.ChatPlayer;
import com.mcnsa.chat.client.packets.ChannelListingPacket;
import com.mcnsa.chat.managers.ChannelManager;
import com.mcnsa.chat.managers.PlayerManager;

public class CleanUp {

	public static void clean() {
		//thread to clean up unused channels and update other servers
		System.out.println("Channel cleanup starting");
		//Setup new array for the new channels
		ArrayList<ChatChannel> newChannels = new ArrayList<ChatChannel>();
		//Loop through each channel
		for(int i=0; i < ChannelManager.channels.size(); i++) {
			Boolean remove = true;
			ChatChannel channel = ChannelManager.channels.get(i);
			//check if the channel is persistant
			if (channel.modes.contains(ChatChannel.Mode.PERSIST)) {
				//Channel is persistant
				newChannels.add(channel);
				remove = false;
			}
			else {
				//Channel is not persistant. Lets check if anyone is listening or in the channel
				for (int f = 0; f < PlayerManager.players.size(); f++) {
					//Get the player
					ChatPlayer player = PlayerManager.players.get(f);
					//Now see if the player is listening to the channel
					if (player.listening.contains(channel.name.toLowerCase())) {
						//Player is listening.
						newChannels.add(channel);
						remove = false;
					}
					else if (player.channel.equalsIgnoreCase(channel.name)) {
						//Player is in the channel
						newChannels.add(channel);
						remove = false;
					}
				}
			}
			
			if (remove) {
				System.out.println("Channel: "+channel.name+" Removed");
			}
		}
		//set the new channel list
		ChannelManager.channels = newChannels;
		//Send to servers
		Server.broadcast(new ChannelListingPacket(ChannelManager.channels));
	}

}
