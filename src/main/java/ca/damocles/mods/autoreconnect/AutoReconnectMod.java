package ca.damocles.mods.autoreconnect;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ServerInfo;
import org.apache.logging.log4j.LogManager;

public class AutoReconnectMod implements ClientModInitializer {
	@Environment(EnvType.CLIENT)
	public static ServerInfo lastestServerEntry;

	public static int disconnectTick = 0;
	public static final int TICK = 20;

	@Override
	public void onInitializeClient() {
		ClientTickCallback.EVENT.register(minecraftClient->clientTick());
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		LogManager.getLogger().info("Loading Auto Reconnect");
	}
	public static void clientTick(){
		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc.world != null && mc.getCurrentServerEntry() != null){
			lastestServerEntry = mc.getCurrentServerEntry();
		}
		if(mc.currentScreen instanceof DisconnectedScreen){
			disconnectTick++;
			if(disconnectTick >= (TICK*config.reconnect_timer) && lastestServerEntry!=null){
				mc.disconnect();
				mc.openScreen(new ConnectScreen(new TitleScreen(), mc, lastestServerEntry));
			}
		}else{
			disconnectTick = 0;
		}
	}
}
