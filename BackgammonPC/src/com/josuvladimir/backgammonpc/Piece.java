/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josuvladimir.backgammonpc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Vladimir
 */

@SuppressWarnings("serial")
public class Piece extends JButton{
    static ImageIcon iconW = new ImageIcon(Piece.class.getResource("resources/pieceWhite.png"));
    static ImageIcon iconB = new ImageIcon(Piece.class.getResource("resources/pieceBlack.png"));
    static ImageIcon iconWs = new ImageIcon(Piece.class.getResource("resources/pieceWhiteSelect.png"));
    static ImageIcon iconBs = new ImageIcon(Piece.class.getResource("resources/pieceBlackSelect.png"));
    colorType   color;
    ImageIcon   icon,iconS;
    int         point;
    MouseListener  action;

    public enum colorType
    {
        undefined,white,black;
        public colorType reverse()
        {
            if(this == white)
                return black;
            else
                return white;
        }
    }

    public Piece()
    {
        color  = colorType.undefined;
        icon    = iconS    = null;
    }

    public void setPoint(int newPoint)
    {
        point = newPoint;
    }

    @Override
    public void setLocation(int x, int y) {
        if(!Game.instance.table.calculateMove)
            super.setLocation(x, y);
    }

    @Override
    public void setVisible(boolean aFlag) {
        if(!Game.instance.table.calculateMove)
            super.setVisible(aFlag);
    }

    public Piece(colorType color)
    {
        this.color = color;
        switch (color)
        {
            case white:
                icon = iconW;
                iconS = iconWs;
                break;
            case black:
                icon = iconB;
                iconS = iconBs;
                break;
            case undefined:
                icon = null;
                break;
        }
        setIcon(icon);
        setDisabledIcon(icon);
        setSelectedIcon(iconS);
        setPressedIcon(iconS);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setSize(icon.getIconWidth() - 1,icon.getIconHeight() - 5);
        action = new MouseListener() {
            public void mouseReleased(MouseEvent e)
            {
                Game.instance.table.setSelectedPiece(point);
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
        };
        addMouseListener(action);
    }
}
