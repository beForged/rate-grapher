package com.rateGrapher;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.xptracker.XpTrackerService;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.DragAndDropReorderPane;
import net.runelite.client.ui.components.PluginErrorPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RateGrapherPanel extends PluginPanel {

    //related to further down TODO
    private final Map<Skill, RateGrapherInfoBox> infoBoxes = new HashMap<>();

    //private final JLabel overallXpGrained = new JLabel
    private XpTrackerService xpTrackerService;

    private Client client;


    private final JPanel overallPanel = new JPanel();

    private final PluginErrorPanel errorPanel = new PluginErrorPanel();

    @Inject
    RateGrapherPanel(
            RateGrapherPlugin rateGrapherPlugin,
            RateGrapherConfig rateGrapherConfig,
            XpTrackerService xpTrackerService,
            Client client,
            SkillIconManager iconManager
            ){

       super();
       this.xpTrackerService = xpTrackerService;
       this.client = client;

       //should make some dark gray panels
       setBorder(new EmptyBorder(6,6,6,6));
       setBackground(ColorScheme.DARK_GRAY_COLOR);
       setLayout(new BorderLayout());

       //should let us place jpanels in a vertical line
       final JPanel layoutPanel = new JPanel();
       BoxLayout boxLayout = new BoxLayout(layoutPanel, BoxLayout.Y_AXIS);
       layoutPanel.setLayout(boxLayout);
       add(layoutPanel, BorderLayout.NORTH);

       overallPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
       overallPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
       overallPanel.setLayout(new BorderLayout());
       overallPanel.setVisible(true); //so it only becomes visible when player gets xp
       // ^ TODO should only be true when xp is gained

        //create reset xp grapher menu
        final JMenuItem reset = new JMenuItem("Reset All");
        reset.addActionListener(e -> rateGrapherPlugin.resetAndInitState());

        //create pause menu
        final JMenuItem pause = new JMenuItem("Pause");
        //pause.addActionListener(e -> rateGrapherPlugin.pause(true));
       //add more here and add it to the popup menu

        //add
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(new EmptyBorder(5,5,5,5));
        popupMenu.add(reset);
        popupMenu.add(pause);
        overallPanel.setComponentPopupMenu(popupMenu);

        //we can graph the overall rate too
        final JLabel overallIcon = new JLabel(new ImageIcon(iconManager.getSkillImage(Skill.OVERALL)));

        final JPanel overallInfo = new JPanel();
        overallInfo.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        overallInfo.setLayout(new GridLayout(2,1));
        overallInfo.setBorder(new EmptyBorder(0,10,0,0));

        overallPanel.add(overallIcon, BorderLayout.WEST);
        overallPanel.add(overallInfo, BorderLayout.CENTER);

        final JComponent infoboxPanel = new DragAndDropReorderPane();

        layoutPanel.add(overallPanel);
        layoutPanel.add(infoboxPanel);

        for(Skill skill : Skill.values()){
            if(skill == Skill.OVERALL){
                break;
            }
            infoBoxes.put(skill, new RateGrapherInfoBox(rateGrapherPlugin, rateGrapherConfig, client, infoboxPanel, skill, iconManager));
        }

        //for(RateGrapherInfoBox box : infoBoxes.values()){
        //    layoutPanel.add(box);
        //}

        add(layoutPanel);

        //do we need this? TODO
        errorPanel.setContent("Exp rate Grapher", "you have not gained exp yet");
        add(errorPanel);

    }

    void resetAllInfoBoxes(Skill skill){

    }

    public void update(ArrayList<Skill> upd){
        for(Skill skill : upd){
            System.err.println(skill.toString() + " updated");
            //if(skill > )
            //updateSkillExperience(, paused, skill);
        }
    }


    //TODO add the xp amount in; xptracker uses xpsnapshotsingle
    void updateSkillExperience(boolean updated, boolean paused, Skill skill){
        RateGrapherInfoBox rateGrapherInfoBox = infoBoxes.get(skill);

        //System.out.println("update " + skill.toString() + " experience: " + xpTrackerService.getXpHr(skill));
        if(rateGrapherInfoBox != null){
            rateGrapherInfoBox.update(updated, paused, xpTrackerService);
            // if(!rateGrapherInfoBox.isVisible()){
                // rateGrapherInfoBox.setVisible(true);
            // }
        }

    }

    //this is for total xp
    void updateTotal(){
        
        if(!overallPanel.isVisible()){
            overallPanel.setVisible(true);
            remove(errorPanel);
        } 
        SwingUtilities.invokeLater(() -> rebuildAsync());
    }

    private void rebuildAsync(){
        //TODO xp snapshot stuff

    }
}