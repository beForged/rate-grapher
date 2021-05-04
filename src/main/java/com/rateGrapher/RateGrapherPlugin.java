package com.rateGrapher;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.xptracker.XpTrackerPlugin;
import net.runelite.client.plugins.xptracker.XpTrackerService;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@PluginDescriptor(
	name = "Xp Rate Grapher"
)
@PluginDependency(XpTrackerPlugin.class)
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



	@Inject
	private XpTrackerService xpTrackerService;

	public Skill[] skillList;
	public Skill mostRecentSkillGained;

	private List<Skill> activeSkills;
	//private Map<Skill, Integer> time;
	private RateGrapherPanel panel;

	public int tickCount = 0;
	public int startTime = 0;
	public int currentTime = 0;

	private NavigationButton navButton;

	private boolean initializeTracker;
//	private OverlayManager overlayManager;

	private boolean panelEnabled;
	@Override
	protected void startUp() throws Exception
	{
		//log.info("Grapher started!");

		panel = new RateGrapherPanel(this, rateGrapherConfig, xpTrackerService, client, skillIconManager);
		//rateGrapherPanel = new RateGrapherPanel(this, rateGrapherConfig, client, skillIconManager);
		//panel = new RateGrapherPanel(this);

		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "/skill_icons/overall.png");

		navButton = NavigationButton.builder()
				.tooltip("xp rate grapher")
				.icon(icon)
				.priority(10)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);

		activeSkills = new ArrayList<>();

		//panel.update(activeSkills);

	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
		//log.info("Grapher stopped!");
	}


	//TODO later
	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		GameState state = gameStateChanged.getGameState();
		if (state == GameState.LOGGED_IN)
		{
//			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
			//should check if username or world type changed
			//if(!Objects.equals(client.getUsername(), lastUsername))
		}
		else if (state == GameState.LOGGING_IN || state == GameState.HOPPING)
		{
			initializeTracker = true;
		}
		else if (state == GameState.LOGIN_SCREEN)
		{

		}
	}

	@Provides
	RateGrapherConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RateGrapherConfig.class);
	}

	//TODO
	void resetAndInitState()
	{

		resetState();

		for(Skill skill : Skill.values()){
			long currentXp;
			//init skills
		}
	}

	//TODO
	private void resetState()
	{

	}

	//nothing to do here not making an overlay
	boolean hasOverlay(final Skill skill){
		return false; //no overlay for now
	}

	@Subscribe
	public void onStatChanged(StatChanged statChanged)
	{
	    final Skill skill = statChanged.getSkill();
	    final int currentXp = statChanged.getXp();
	    final int currentLevel = statChanged.getLevel();

		if(initializeTracker){
			//TODO figureout where to set this
			return;
		}

		//final XpStateSingle state = xpState.getSkill(skill);
		
		activeSkills.add(skill);
		panel.updateTotal();
		//panel.updateTotal(xpState.getTotalSnapshot());

	}


	//update total xp graph here
	@Subscribe
	public void onGameTick(GameTick event){
		//update all here?
		for(Skill s: activeSkills){
			panel.updateSkillExperience(true, false, s);
		}
	}


}
