package net.lugo.cropfarminghelper;

import net.fabricmc.api.ModInitializer;

import net.lugo.cropfarminghelper.config.ModConfig;
import net.lugo.cropfarminghelper.registration.Commands;
import net.lugo.cropfarminghelper.registration.KeyMappings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CropFarmingHelper implements ModInitializer {
	public static final String MOD_ID = "cropfarminghelper";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Crop Farming Helper (" + MOD_ID + ") initializing.");

		KeyMappings.registerKeyMappings();
		Commands.registerCommands();

		ModConfig.HANDLER.load();

		OverlayHandler.init();

		LOGGER.info("Crop Farming Helper (" + MOD_ID + ") initialized.");
	}
}
