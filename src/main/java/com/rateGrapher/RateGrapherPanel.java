package com.rateGrapher;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.DragAndDropReorderPane;
import net.runelite.client.ui.components.PluginErrorPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RateGrapherPanel extends PluginPanel {

    //related to further down TODO
    private final Map<Skill, RateGrapherInfoBox> infoBoxes = new HashMap<>();

    //private final JLabel overallXpGrained = new JLabel

    private final JPanel overallPanel = new JPanel();

    private final PluginErrorPanel errorPanel = new PluginErrorPanel();

    RateGrapherPanel(RateGrapherPlugin rateGrapherPlugin, RateGrapherConfig rateGrapherConfig, Client client, SkillIconManager iconManager){
       super();

       //should make some dark gray panels
       setBorder(new EmptyBorder(6,6,6,6));
       setBackground(ColorScheme.DARK_GRAY_COLOR);
       setLayout(new BorderLayout());

       final JPanel layoutPanel = new JPanel();
       BoxLayout boxLayout = new BoxLayout(layoutPanel, BoxLayout.Y_AXIS);
       layoutPanel.setLayout(boxLayout);
       add(layoutPanel, BorderLayout.NORTH);

       overallPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
       overallPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
       overallPanel.setLayout(new BorderLayout());
       overallPanel.setVisible(false); //so it only becomes visible when player gets xp

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
            //this makes the info panels TODO
            //infoBoxes.put(skill, new rateGrapherInfoBox())
        }

        errorPanel.setContent("Exp rate Grapher", "you have not gained exp yet");
        add(errorPanel);

    }

    void resetAllInfoBoxes(Skill skill){

    }
}
