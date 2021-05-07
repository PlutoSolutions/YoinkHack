package cat.yoink.yoinkhack.api.discord;

import cat.yoink.yoinkhack.Client;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Discord
{
	private final DiscordRichPresence presence = new DiscordRichPresence();
	private final DiscordRPC rpc = DiscordRPC.INSTANCE;
	private String details;
	private String state;

	@SuppressWarnings("all")
	public void start()
	{
		final DiscordEventHandlers handlers = new DiscordEventHandlers();
		String clientId = "715397195024695367";
		rpc.Discord_Initialize(clientId, handlers, true, "");
		presence.startTimestamp = System.currentTimeMillis() / 1000L;
		presence.details = "yoink";
		presence.state = "yoink";
		presence.largeImageKey = "yoink";
		presence.largeImageText = "yoink";

		rpc.Discord_UpdatePresence(presence);
		new Thread(() ->
		{
			while (!Thread.currentThread().isInterrupted())
			{
				try
				{
					rpc.Discord_RunCallbacks();
					details = Client.INSTANCE.rpc.getDetails();
					state = Client.INSTANCE.rpc.getState();
					if (!details.equals(presence.details) || !state.equals(presence.state))
					{
						presence.startTimestamp = System.currentTimeMillis() / 1000L;
					}
					presence.details = details;
					presence.state = state;
					rpc.Discord_UpdatePresence(presence);
				}
				catch (Exception ignored)
				{
				}
				try
				{
					Thread.sleep(5000L);
				}
				catch (InterruptedException ignored)
				{
				}
			}
		}, "Discord-RPC-Callback-Handler").start();
	}
}
