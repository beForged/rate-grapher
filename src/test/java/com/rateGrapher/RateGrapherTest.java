package com.rateGrapher;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RateGrapherTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(RateGrapherPlugin.class);
		RuneLite.main(args);
	}
}