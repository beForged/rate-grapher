package com.rateGrapher;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "Xp Rate Grapher"
)
public class RateGrapherPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private RateGrapherConfig rateGrapherConfig;

	@Inject
	private SkillIconManager skillIconManager;

	public Skill[] skillList;
	public Skill mostRecentSkillGained;

	public int tickCount = 0;
	public int startTime = 0;
	public int currentTime = 0;

	private NavigationButton navButton;
	private RateGrapherPanel rateGrapherPanel;

//	private OverlayManager overlayManager;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Grapher started!");
		rateGrapherPanel = new RateGrapherPanel(this, rateGrapherConfig, client, skillIconManager);

		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "/skill_icons/overall.png");

		navButton = NavigationButton.builder()
				.tooltip("xp rate grapher")
				.icon(icon)
				.priority(10)
				.panel(rateGrapherPanel)
				.build();

		clientToolbar.addNavigation(navButton);

	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
		log.info("Grapher stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
//			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	RateGrapherConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RateGrapherConfig.class);
	}

	void resetAndInitState()
	{

		resetState();

		for(Skill skill : Skill.values()){
			long currentXp;
			//init skills
		}
	}

	private void resetState()
	{

	}

	boolean hasOverlay(final Skill skill){
		//TODO rateGrapherInfoBoxOverlay
		//return overlayManager.anyMatch(o -> o instanceof rateGrapher);
		return false; //no overlay for now
	}
}
