package com.mundobachata.mineborium.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mundobachata.mineborium.Mineborium;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RollingMachineScreen extends AbstractContainerScreen<RollingMachineMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Mineborium.MOD_ID, "textures/gui/rolling_machine_gui.png");

    public RollingMachineScreen(RollingMachineMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        gui.blit(TEXTURE,x, y, 0, 0, imageWidth, imageHeight);

        renderProgressCigarette(gui, x, y);

    }

    private void renderProgressCigarette(GuiGraphics gui, int x, int y) {
        if(menu.isCrafting()) {
            gui.blit(TEXTURE, x + 83, y + 41, 176, 0, menu.getScaledProgress(), 4);
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        renderBackground(gui);
        super.render(gui, mouseX, mouseY, delta);
        renderTooltip(gui, mouseX, mouseY);
    }
}
