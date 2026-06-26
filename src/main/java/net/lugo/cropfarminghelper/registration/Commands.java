package net.lugo.cropfarminghelper.registration;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.lugo.cropfarminghelper.command.CropFarmingHelperCommand;

public class Commands {
    public static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(CropFarmingHelperCommand::register);
    }
}
