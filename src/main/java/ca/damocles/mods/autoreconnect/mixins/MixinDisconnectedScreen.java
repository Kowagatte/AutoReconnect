package ca.damocles.mods.autoreconnect.mixins;

import ca.damocles.mods.autoreconnect.AutoReconnectMod;
import ca.damocles.mods.autoreconnect.ModConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public class MixinDisconnectedScreen {
    private static final int FONT_HEIGHT = 9;
    @Shadow
    private int reasonHeight;
    @Inject(method = "render", at = @At("RETURN"))
    private void onRender (MatrixStack stack, int p_render_1_, int p_render_2_, float p_render_3_, CallbackInfo ci){
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if(AutoReconnectMod.lastestServerEntry == null)return;
        MinecraftClient mc = MinecraftClient.getInstance();
        TextRenderer textRenderer = mc.textRenderer;
        if( mc.currentScreen == null) return;
        int width = mc.currentScreen.width;
        int height = mc.currentScreen.height;
        mc.currentScreen.drawCenteredString(
                stack,
                textRenderer,
                I18n.translate("autoReconnect.waitingTime", ((AutoReconnectMod.TICK* config.reconnect_timer )- AutoReconnectMod.disconnectTick)/20),
                width/2,
                height - this.reasonHeight / 2 - FONT_HEIGHT * 2,
                0xffffff);
    }
}
