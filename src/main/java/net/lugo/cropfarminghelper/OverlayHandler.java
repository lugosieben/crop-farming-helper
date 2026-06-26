package net.lugo.cropfarminghelper;

import net.lugo.cropfarminghelper.config.ModConfig;
import net.lugo.cropfarminghelper.renderers.MarkerOverlayRenderer;
import net.lugo.cropfarminghelper.util.HudMessage;
import net.lugo.cropfarminghelper.util.ReusableBlockData;
import net.lugo.overlaylib.Overlay;
import net.lugo.overlaylib.OverlayLib;
import net.lugo.overlaylib.managers.CachedOverlayManager;
import net.lugo.overlaylib.util.OverlayRendererBlockData;
import net.lugo.overlaylib.util.TextureSection;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.TorchflowerCropBlock;

import java.awt.*;

public class OverlayHandler {
    private static final Minecraft MC = Minecraft.getInstance();

    private static boolean isActive = false;
    private static Overlay overlay;

    private static final CachedOverlayManager overlayManager = new CachedOverlayManager((blockPos -> {
        ReusableBlockData blockData = new ReusableBlockData(blockPos);
        if (blockData.block() instanceof CropBlock) {
            int age = ((CropBlock) blockData.block()).getAge(blockData.blockState());
            float height = switch (blockData.block()) {
                case TorchflowerCropBlock _ -> age * .35f;
                case CarrotBlock _ -> age * .065f;
                default -> age * .1f;
            };
            //noinspection DataFlowIssue
            float speed = CropBlock.getGrowthSpeed(blockData.block(), MC.level, blockPos) / 10;
            Color color;
            CropFarmingHelper.LOGGER.info("Growth speed: {}", speed);
            if (speed > 25/30f) color = ModConfig.perfectColor;
            else if (speed > 25/40f) color = ModConfig.goodColor;
            else color = ModConfig.badColor;
            return new OverlayRendererBlockData(blockPos, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, height- 1f, TextureSection.SINGULAR);
        }

        return OverlayRendererBlockData.NO_RENDER;
    }));

    public static void init() {
        if (overlay == null) {
            overlay = new Overlay(new MarkerOverlayRenderer(), ModConfig.chunkScanRange, ModConfig.chunkScanRangeVertical, overlayManager);
            overlay.register();
        }
        overlay.setActive(isActive);
    }

    public static void toggle() {
        isActive = !isActive;

        if (overlay != null) {
            overlay.setActive(isActive);
        }

        if (isActive) {
            HudMessage.show(Component.translatable("text.cropfarminghelper.message.toggle.on"), ChatFormatting.GREEN);
        } else {
            HudMessage.show(Component.translatable("text.cropfarminghelper.message.toggle.off"), ChatFormatting.RED);
        }
    }

    public static void setChunkScanRadius(int radius) {
        if (overlay != null) overlay.setChunkScanRadius(radius);
    }

    public static void setChunkScanRadiusVertical(int radius) {
        if (overlay != null) overlay.setChunkScanRadiusVertical(radius);
    }

    public static void setMaxComputationsPerTick(int maxComputationsPerTick) {
        overlayManager.setMaxComputationsPerTick(maxComputationsPerTick);
    }

    public static void refresh(BlockPos pos) {
        overlayManager.refresh(pos);
    }

    public static void refresh(SectionPos section) {
        overlayManager.refresh(section);
    }

    public static void clearAll() {
        OverlayLib.LOGGER.info("Clearing all");
        overlayManager.clearAll();
    }
}