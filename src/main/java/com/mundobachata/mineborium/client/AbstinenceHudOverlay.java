package com.mundobachata.mineborium.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.abstinence.PlayerAbstinence;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class AbstinenceHudOverlay {
    private static final ResourceLocation EMPTY_ABSTINENCE = new ResourceLocation(Mineborium.MOD_ID,
            "textures/abstinence/cigarette_empty.png");

    private static final ResourceLocation[] ABSTINENCE_TEXTURES = {
            new ResourceLocation(Mineborium.MOD_ID, "textures/abstinence/cigarette.png"),
            new ResourceLocation(Mineborium.MOD_ID, "textures/abstinence/cigarette1.png"),
            new ResourceLocation(Mineborium.MOD_ID, "textures/abstinence/cigarette2.png"),
            new ResourceLocation(Mineborium.MOD_ID, "textures/abstinence/cigarette3.png")
    };

    private static final int NUMBER_OF_PHASES = ABSTINENCE_TEXTURES.length;

    public static final IGuiOverlay ABSTINENCE_HUD = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (!ClientAbstinenceData.playerHasSmoked()) {
            return;
        }

        int x = screenWidth / 2;
        int y = screenHeight;

        int horizontalAlign = 101;
        int verticalAlign = 46;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_ABSTINENCE);

        guiGraphics.blit(EMPTY_ABSTINENCE, x + horizontalAlign, y - verticalAlign, 0, 0, 16,
                16, 16, 16);

        int currentPhase = -1;

        for(int i = 0; i < NUMBER_OF_PHASES; i++) {
            if(
                ClientAbstinenceData.getPlayerAbstinence() >= PlayerAbstinence.APPLY_ABSTINENCE_EFFECT_TIME / NUMBER_OF_PHASES * i &&
                ClientAbstinenceData.getPlayerAbstinence() < PlayerAbstinence.APPLY_ABSTINENCE_EFFECT_TIME / NUMBER_OF_PHASES * (i + 1)
            ) {
                currentPhase = i;
                break;
            }
        }

        if(currentPhase >= 0) {
            RenderSystem.setShaderTexture(0, ABSTINENCE_TEXTURES[currentPhase]);
            guiGraphics.blit(ABSTINENCE_TEXTURES[currentPhase], x + horizontalAlign, y - verticalAlign, 0,
                    0, 16, 16, 16, 16);
        }
    });

}
