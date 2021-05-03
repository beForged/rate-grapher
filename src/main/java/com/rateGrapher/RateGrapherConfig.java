package com.rateGrapher;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("rateGrapher")
public interface RateGrapherConfig extends Config
{
	@ConfigItem(
		position = 0,
		keyName = "idlebars",
		name = "idle bars",
		description = "turn on or off idle bars"
	)
	default boolean idlebars(){ return true; }
/*
	@ConfigItem(
			position = 1,
			keyName = "height",
			name = "height",
			description = ""
	)
	default int height(){ return 400;}
 */

	@ConfigItem(
			keyName = "activate total xp graph",
			name = "activate total xp graph",
			description = "activate the total xp graph. will update every tick"
	)
	default boolean activate(){return false;}
}
