package de.tim.crosshairswitch;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CrosshairSwitchClient implements ClientModInitializer {

    private static final Identifier NORMAL = Identifier.of("crosshairswitch", "textures/gui/hud.png");
    private static final Identifier ATTACK = Identifier.of("crosshairswitch", "textures/gui/hud_attack.png");

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(this::onHudRender);
    }

    private void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();

        // Nur rendern wenn HUD sichtbar ist
        if (!mc.options.hudHidden) {
            // Check if we're looking at an entity
            boolean isTargetingEntity = false;
            HitResult crosshairTarget = mc.crosshairTarget;

            if (crosshairTarget != null && crosshairTarget.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) crosshairTarget;
                Entity targetedEntity = entityHit.getEntity();

                // Check if the entity is a mob or player
                if (targetedEntity instanceof MobEntity || targetedEntity instanceof PlayerEntity) {
                    isTargetingEntity = true;
                }
            }

            Identifier texture = isTargetingEntity ? ATTACK : NORMAL;

            // Render custom crosshair
            context.drawTexture(
                    RenderPipelines.GUI_TEXTURED,
                    texture,
                    mc.getWindow().getScaledWidth() / 2 - 8,
                    mc.getWindow().getScaledHeight() / 2 - 8,
                    0.0F, 0.0F,
                    16, 16,
                    16, 16
            );
        }
    }
}