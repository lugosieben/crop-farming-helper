package net.lugo.cropfarminghelper.registration;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.lugo.cropfarminghelper.CropFarmingHelper;
import net.lugo.cropfarminghelper.OverlayHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyMappings {

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.parse(CropFarmingHelper.MOD_ID));
    private static final String BASE_KEY = "key." + CropFarmingHelper.MOD_ID;

    public static void registerKeyMappings() {
        registerCropFarmingHelperKeyMapping();
    }

    private static void registerCropFarmingHelperKeyMapping() {
        KeyMapping cropFarmingHelperKey = new KeyMapping(BASE_KEY + ".toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F8, CATEGORY);
        KeyMappingHelper.registerKeyMapping(cropFarmingHelperKey);

        ClientTickEvents.END_CLIENT_TICK.register(_ -> {
            if (cropFarmingHelperKey.consumeClick() && cropFarmingHelperKey.isDown()) {
                OverlayHandler.toggle();
            }
        });
    }
}
