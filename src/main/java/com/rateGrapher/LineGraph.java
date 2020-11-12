package com.rateGrapher;

import jdk.nashorn.internal.objects.annotations.Setter;
import net.runelite.client.ui.components.DimmableJPanel;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class LineGraph extends DimmableJPanel {
    //@Setter
    private int maximumValue;

    //@Setter
    private int Value;

    //@Setter
    private List<Integer> positions = Collections.emptyList();

    private int height;
    private int width;

    //just int values, ill just round to nearest int
    private int minScore;
    private int maxScore;

    private Color color;

    final private int yDivisions = 5;
    final private int xDivisions = 10;


    public LineGraph(){


    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        width = getSize().width;
        height = width;

        g2.fillRect(0, 0, height, width);

    }
}
