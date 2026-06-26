package net.lugo.cropfarminghelper.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.lugo.cropfarminghelper.OverlayHandler;
import net.lugo.cropfarminghelper.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;

public class CropFarmingHelperCommand {
    final static Minecraft MC = Minecraft.getInstance();

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext ignoredCommandRegistryAccess) {
        dispatcher.register(ClientCommands.literal("cropfarminghelper")
                .then(ClientCommands.literal("config")
                        .executes(_ -> {
                            MC.schedule(() -> MC.setScreenAndShow(ModConfig.makeScreen(MC.gui.screen())));
                            return 1;
                        })
                )
                .executes(_ -> {
                    OverlayHandler.toggle();
                    return 1;
                })
        );
    }
}
