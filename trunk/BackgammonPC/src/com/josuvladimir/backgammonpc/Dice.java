/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josuvladimir.backgammonpc;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Vladimir
 */
@SuppressWarnings("serial")
public class Dice extends JLabel{
        static int maxValue = 6;
        static Vector<ImageIcon> side;
        static Random  rand;
        int     value;
        boolean valid;

        public Dice(Point p) {
            rand = new Random();
            rand.setSeed(rand.nextLong());
            valid = false;
            value = 1;
            side = new Vector<ImageIcon>(maxValue + 1);
            side.setSize(maxValue+1);
            for (int i = 1; i < side.size(); i++)
            {
                ImageIcon img = new ImageIcon(Dice.class.getResource("resources/dice/dice"+i+".png"));
                side.set(i, img);
            }
            setSize(side.get(1).getIconHeight(), side.get(1).getIconWidth());
            setIcon(side.get(value));
            setLocation(p);
            setVisible(false);
        }
        public int roll()
        {
            //test
            //value = 1;
            value = rand.nextInt(maxValue) + 1;
            setIcon(side.get(value));
            valid = true;
            return value;
        }
    @Override
        public void setVisible(boolean b)
        {
            valid = b;
            if(!Game.instance.table.calculateMove)
                super.setVisible(b);
        }
}
