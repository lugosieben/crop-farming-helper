package net.lugo.cropfarminghelper.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.lugo.cropfarminghelper.CropFarmingHelper;
import net.lugo.cropfarminghelper.OverlayHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.awt.*;

public class ModConfig {
    private static final class Defaults {
        static final int CHUNK_SCAN_RANGE = 4;
        static final int CHUNK_SCAN_RANGE_VERTICAL = 25;
        static final boolean HIDE_GREEN = false;
        static final int MAX_COMPUTATIONS_PER_TICK = 32;
        static final Color PERFECT_COLOR = new Color(0, 255, 0, 255);
        static final Color GOOD_COLOR = new Color(255, 255, 0, 255);
        static final Color BAD_COLOR = new Color(255, 0, 0, 255);
    }

    @SerialEntry
    public static int chunkScanRange = Defaults.CHUNK_SCAN_RANGE;

    @SerialEntry
    public static int chunkScanRangeVertical = Defaults.CHUNK_SCAN_RANGE_VERTICAL;
    public static boolean isChunkScanRangeVerticalInfinite(int value) {
        return value > 24;
    }

    @SerialEntry
    public static boolean hideGreen = Defaults.HIDE_GREEN;

    @SerialEntry
    public static int maxComputationsPerTick = Defaults.MAX_COMPUTATIONS_PER_TICK;

    @SerialEntry
    public static Color perfectColor = Defaults.PERFECT_COLOR;

    @SerialEntry
    public static Color goodColor = Defaults.GOOD_COLOR;

    @SerialEntry
    public static Color badColor = Defaults.BAD_COLOR;

    public static Screen makeScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("text.cropfarminghelper.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("text.cropfarminghelper.config.category.main"))
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("text.cropfarminghelper.config.group.general"))
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("text.cropfarminghelper.config.option.chunk_scan_range.name"))
                                        .description(OptionDescription.of(Component.translatable("text.cropfarminghelper.config.option.chunk_scan_range.description")))
                                        .binding(
                                                Defaults.CHUNK_SCAN_RANGE,
                                                () -> chunkScanRange,
                                                newVal -> {
                                                    chunkScanRange = newVal;
                                                    OverlayHandler.setChunkScanRadius(newVal);
                                                })
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(1, 24)
                                                .step(1))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("text.cropfarminghelper.config.option.scan_range_vertical.name"))
                                        .description(OptionDescription.of(Component.translatable("text.cropfarminghelper.config.option.scan_range_vertical.description")))
                                        .binding(
                                                Defaults.CHUNK_SCAN_RANGE_VERTICAL,
                                                () -> chunkScanRangeVertical,
                                                newVal -> {
                                                    chunkScanRangeVertical = newVal;
                                                    OverlayHandler.setChunkScanRadiusVertical(newVal);
                                                })
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(1, 25)
                                                .step(1)
                                                .formatValue(v -> isChunkScanRangeVerticalInfinite(v) ? Component.translatable("text.cropfarminghelper.config.option.scan_range_vertical.infinite") : Component.literal(v.toString())))
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("text.cropfarminghelper.config.group.overlay_hiding"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("text.cropfarminghelper.config.option.hide_green.name"))
                                        .description(OptionDescription.of(Component.translatable("text.cropfarminghelper.config.option.hide_green.description")))
                                        .binding(
                                                Defaults.HIDE_GREEN,
                                                () -> hideGreen,
                                                newVal -> {
                                                    hideGreen = newVal;
                                                    OverlayHandler.clearAll();
                                                })
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("text.cropfarminghelper.config.group.customize"))
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("text.cropfarminghelper.config.option.perfect_color.name"))
                                        .description(OptionDescription.of(Component.translatable("text.cropfarminghelper.config.option.perfect_color.description")))
                                        .binding(
                                                Defaults.PERFECT_COLOR,
                                                () -> perfectColor,
                                                newVal -> {
                                                    perfectColor = newVal;
                                                    OverlayHandler.clearAll();
                                                })
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("text.cropfarminghelper.config.option.good_color.name"))
                                        .description(OptionDescription.of(Component.translatable("text.cropfarminghelper.config.option.good_color.description")))
                                        .binding(
                                                Defaults.GOOD_COLOR,
                                                () -> goodColor,
                                                newVal -> {
                                                    goodColor = newVal;
                                                    OverlayHandler.clearAll();
                                                })
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("text.cropfarminghelper.config.option.bad_color.name"))
                                        .description(OptionDescription.of(Component.translatable("text.cropfarminghelper.config.option.bad_color.description")))
                                        .binding(
                                                Defaults.BAD_COLOR,
                                                () -> badColor,
                                                newVal -> {
                                                    badColor = newVal;
                                                    OverlayHandler.clearAll();
                                                })
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(false))
                                        .build())
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("text.cropfarminghelper.config.category.misc"))
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("text.cropfarminghelper.config.option.max_computations_per_tick.name"))
                                .description(OptionDescription.of(Component.translatable("text.cropfarminghelper.config.option.max_computations_per_tick.description")))
                                .binding(
                                        Defaults.MAX_COMPUTATIONS_PER_TICK,
                                        () -> maxComputationsPerTick,
                                        newVal -> {
                                            maxComputationsPerTick = newVal;
                                            OverlayHandler.setMaxComputationsPerTick(newVal);
                                        })
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(1, 128)
                                        .step(1))
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("text.cropfarminghelper.config.category.report"))
                        .option(ButtonOption.createBuilder()
                                .name(Component.translatable("text.cropfarminghelper.config.button.report"))
                                .description(OptionDescription.of(Component.translatable("text.cropfarminghelper.config.button.report.description")))
                                .text(Component.literal(""))
                                .action((_, _) -> {
                                    var modContainerOpt = FabricLoader.getInstance().getModContainer(CropFarmingHelper.MOD_ID);
                                    modContainerOpt.ifPresent(modContainer -> {
                                        var issuesUrlOpt = modContainer.getMetadata().getContact().get("issues");
                                        issuesUrlOpt.ifPresent(url -> Util.getPlatform().openUri(url));
                                    });
                                })
                                .build())
                        .build())
                .save(HANDLER::save)
                .build()
                .generateScreen(parent);
    }

    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(Identifier.fromNamespaceAndPath(CropFarmingHelper.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("cropfarminghelper.json5"))
                    .setJson5(true)
                    .build())
            .build();
}