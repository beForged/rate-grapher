package com.rateGrapher;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AccessLevel;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.MouseDragEventForwarder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;

public class RateGrapherInfoBox extends JPanel {

    private final JComponent panel;

    private static final String REMOVE_STATE = "Remove from canvas";
    private static final String ADD_STATE = "Add to canvas";

    //@Getter(AccessLevel.PACKAGE)
    private final Skill skill;

    //trackers wrapping container
    private final JPanel container = new JPanel();

    //skill icon and stats panel
    private final JPanel headerPanel = new JPanel();

    //will hold graph
    private final JPanel statsPanel = new JPanel();

    private final LineGraph lineGraph = new LineGraph();

    private final JLabel totalXpGainedStat = new JLabel();
    private final JLabel currentXpRateStat = new JLabel();
    private final JMenuItem pauseSkill = new JMenuItem("Pause");
    private final JMenuItem canvasItem = new JMenuItem(ADD_STATE);

    private final RateGrapherConfig rateGrapherConfig;

    private boolean paused = false;

    RateGrapherInfoBox(RateGrapherPlugin rateGrapherPlugin, RateGrapherConfig rateGrapherConfig, Client client, JComponent panel, Skill skill, SkillIconManager iconManager)
    {
        this.rateGrapherConfig = rateGrapherConfig;
        this.panel = panel;
        this.skill = skill;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5,0,0,0));

        container.setLayout(new BorderLayout());
        container.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        final JMenuItem reset = new JMenuItem("Reset");
        //reset.addActionListener(e -> );

        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        popupMenu.add(reset);
        //not sure this is needed
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                canvasItem.setText(rateGrapherPlugin.hasOverlay(skill) ? REMOVE_STATE : ADD_STATE);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        JLabel skillIcon = new JLabel(new ImageIcon((iconManager.getSkillImage(skill))));
        skillIcon.setHorizontalAlignment(SwingConstants.CENTER);
        skillIcon.setVerticalAlignment(SwingConstants.CENTER);
        skillIcon.setPreferredSize(new Dimension(35,35));

        headerPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        headerPanel.setLayout(new BorderLayout());

        statsPanel.setLayout(new DynamicGridLayout(1, 2));
        statsPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        statsPanel.setBorder(new EmptyBorder(9,2,9,2));

        totalXpGainedStat.setFont(FontManager.getRunescapeSmallFont());
        currentXpRateStat.setFont(FontManager.getRunescapeSmallFont());

        statsPanel.add(totalXpGainedStat);
        statsPanel.add(currentXpRateStat);

        headerPanel.add(skillIcon, BorderLayout.WEST);
        headerPanel.add(statsPanel, BorderLayout.CENTER);

        //TODO add the graph panel here
        JPanel graphWrapper = new JPanel();
        graphWrapper.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        graphWrapper.setLayout(new BorderLayout());
        graphWrapper.setBorder(new EmptyBorder(0,7,7,7));

        //graphWrapper.add(lineGraph, BorderLayout.NORTH);

        container.add(headerPanel, BorderLayout.NORTH);
        container.add(graphWrapper, BorderLayout.SOUTH);
        MouseDragEventForwarder mouseDragEventForwarder = new MouseDragEventForwarder(panel);
        container.addMouseListener(mouseDragEventForwarder);
        container.addMouseMotionListener(mouseDragEventForwarder);
        lineGraph.addMouseListener(mouseDragEventForwarder);
        lineGraph.addMouseMotionListener(mouseDragEventForwarder);

        add(container, BorderLayout.NORTH);
    }
}
