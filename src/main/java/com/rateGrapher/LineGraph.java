package com.rateGrapher;

//import jdk.nashorn.internal.objects.annotations.Setter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.DimmableJPanel;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
  time on the x axis, from start to current time.
  xp rates on the y axis, figure out how to fit it : order of magnitude for now?
 */
public class LineGraph extends JPanel{
    //@Setter
    private int maximumValue = 1;

    //@Setter
    private int Value;

    //@Setter
    private ArrayList<DataPoint> positions = new ArrayList<DataPoint>();
    private ArrayList<Integer> pos = new ArrayList<Integer>();

    private int height = 100;
    private int width;

    //just int values, ill just round to nearest int
    private int minScore;
    private int maxScore;

    private int padding;

    private Color color;

    final private int yDivisions = 5;
    final private int xDivisions = 10;

    //private Point origin = new Point(0,0);

    public LineGraph(){

        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setForeground(ColorScheme.DARK_GRAY_COLOR);

        setPreferredSize(new Dimension(width,100));
        
        padding = 10;

        /*
        leftLabel.setFont(FontManager.getRunescapeSmallFont());
        leftLabel.setForeground(Color.WHITE);
        leftLabel.setBorder(new EmptyBorder(2, 5, 0, 0));

        rightLabel.setFont(FontManager.getRunescapeSmallFont());
        rightLabel.setForeground(Color.WHITE);
        rightLabel.setBorder(new EmptyBorder(2, 0, 0, 5));

        centerLabel.setFont(FontManager.getRunescapeSmallFont());
        centerLabel.setForeground(Color.WHITE);
        centerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerLabel.setBorder(new EmptyBorder(2, 0, 0, 0));
        */
        // Adds components to be automatically redrawn when paintComponents is called
        // add(leftLabel, BorderLayout.WEST);
        // add(centerLabel, BorderLayout.CENTER);
        // add(rightLabel, BorderLayout.EAST);

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        width = (int) (getSize().width);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(ColorScheme.MEDIUM_GRAY_COLOR);
        g2.fillRect(0,0, width, height);

        //TODO change axis colors
        g2.setColor(ColorScheme.BRAND_ORANGE);
        Point origin = new Point(padding, height - padding);

        //set x/y axis
        //x axis
        g2.draw(new Line2D.Double(origin, new Point(width - padding, height - padding)));
        //y axis
        g2.draw(new Line2D.Double(origin, new Point(padding, padding)));

        //axis labels
        g2.setFont(FontManager.getRunescapeSmallFont());
        g2.drawString(String.valueOf(maximumValue), padding / 2, padding );

        //this is the width you can use i suppose you could write a helper but its not a n dimensional graph
        int graphWidth = width - (padding * 2);
        int graphHeight = height - (padding * 2);

        if (!pos.isEmpty()) {
            //positions = timing(graphWidth, 2);
            // draw the lines

            DataPoint prev = positions.get(0);
            g2.setColor(ColorScheme.PROGRESS_COMPLETE_COLOR);
            //spacing is space between ticks
            //positions = shorten(graphWidth, 2);
            for (DataPoint p : positions) {
                if (p.getValue() > maximumValue) {
                    maximumValue = p.getValue();
                }
                g2.draw(convert(prev, p));
                prev = p;
            }
        }

    }

    public ArrayList<DataPoint> shorten(int graphWidth, int spacing){
        return new ArrayList<>(
            positions.subList(
                positions.size() - Math.min(graphWidth / spacing, positions.size()), 
                positions.size()
                ));
    }

    private Line2D.Double convert(DataPoint start, DataPoint end) {
        //TODO divide height by max value not working atm
        int y = height - padding - start.getValue();
        y = y/maximumValue;
        int x = padding + start.getTime();

        int y2 = height - padding - end.getValue();
        y2 = y2/maximumValue;
        int x2 = padding + end.getTime();

        return new Line2D.Double(new Point(x, y), new Point(x2, y2));
    }

    // this figures out the scale based on number of ticks and range
    // round to nearest int (dont care about precision right now) but could be in
    // double
    private int scale(int numTicks, double range) {
        return (int) range / numTicks;
    }

    public void update(int xp) {
        pos.add(xp);
        positions.add(new DataPoint(pos.size(), xp));
    }

    public void reset() {
        pos.clear();
        positions.clear();
    }

    private ArrayList<DataPoint> timing(int graphWidth, int space) {
        //is this a good size?
        ArrayList<Integer> tempList = new ArrayList<>(graphWidth / space);
        if (true) {
            // positions = positions.subList(positions.size() - Math.min(graphWidth,
            // positions.size()), positions.size());
            //tempList =  pos.subList(pos.size() - Math.min(graphWidth / space, pos.size()), pos.size());
        } /*else {
            // take every n
            // better to take rolling average w convolution
            int n = positions.size() / graphWidth;
            if (positions.size() < graphWidth) {
                DataPoint[] temp = new DataPoint[graphWidth];
                // ArrayList<DataPoint> temp2 = new ArrayList<DataPoint>();
                for (int i = 0; i < graphWidth; i += n) {
                    temp[i / n] = positions.get(i);
                }
                //positions = Arrays.asList(temp);
            }
        }*/
        for (int i = 0; i < tempList.size(); i++) {
            if (i < positions.size()) {
                positions.set(i, new DataPoint(i*space, tempList.get(i)));
            } else {
                positions.add(new DataPoint(i*space, tempList.get(i)));
            }

        }
        return positions;
    }

}
