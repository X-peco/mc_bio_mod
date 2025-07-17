package good.modid;

import good.modid.block.ModBlocks;
import good.modid.item.ModItemGroups;
import good.modid.item.ModItems;
import good.modid.item.SyringeItem;

import good.modid.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Good implements ModInitializer {
	public static final String MOD_ID = "good";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	// public static final String MOD_NAME = "Good";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		        ModItems.registerModItems();
        
		        ModItemGroups.registerItemGroups();
        ModBlocks.registerModBlocks();
        ModScreenHandlers.registerScreenHandlers();

		

		LOGGER.info("Hello Fabric world!");
	}
}