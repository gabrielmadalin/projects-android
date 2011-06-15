/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josuvladimir.backgammonpc;

import java.awt.Point;
import java.util.Stack;

import com.josuvladimir.backgammonpc.Piece.colorType;

/**
 *
 * @author Vladimir
 */
@SuppressWarnings("serial")
public class TPoint extends Stack<Piece>{
    static  int maxPiece = 5;
    colorType   color;
    Point   location;
    int     idx;

    public TPoint(int i)
    {
        clear();
        location = new Point();
        idx = i;
        color = colorType.undefined;
    }

    @SuppressWarnings("static-access")
	public void initWithPiece(int nPiece,colorType color) {
        //this.clear();
        //this.pointIdx = pointIdx;
        this.color = color;
        //pointNr = poz;
        //pozX = poz;
        if(nPiece == 0)
            this.color = this.color.undefined;
        while(nPiece > 0)
        {
            add(new Piece(color));
            nPiece--;
        }

    }

    public boolean pushOut(Piece item)
    {
        /*
        int offset = 10;
        if(color == colorType.white)
            offset = -offset;
        item.setLocation(location.x,location.y + offset*size());
        item.setEnabled(false);
        item.setVisible(true);
         /*
         */
        //super.push(item);
        add(item);
        item.setEnabled(false);
        return true;
    }
    @Override
    public boolean add(Piece p)
    {
        if(!isEmpty() && color != p.color)
            return false;
        else
            color = p.color;
        if(size() >= maxPiece)
        {
            int offSet = p.getHeight()/size();
            if(idx >12)
                offSet = -offSet;
            for (int i = 0; i < this.size(); i++) {
                Piece piece = this.elementAt(i);
                piece.setLocation(location.x, piece.getLocation().y + offSet*i);
            }
        }
        else
        {
            if(idx <= 12)
            {
                location.y -= p.getHeight();
            }
            else
            {
                location.y += p.getHeight();
            }
        }
        p.setLocation(location);
        p.setPoint(idx);
        p.setVisible(true);
        if(!isEmpty())
            peek().setEnabled(false);
        return super.add(p);
    }
    @Override
    public Piece pop()
    {
        Piece piece = null;
        if(size() == 1)
            color = colorType.undefined;
        if(!isEmpty())
        {
            piece = super.pop();
            piece.setVisible(false);
            if(!isEmpty())
                peek().setEnabled(true);
            if(size() >= maxPiece)
            {
                int offset = piece.getHeight()/size();
                if(idx < 12)
                    offset = -offset;
                for (int i = 0; i < this.size(); i++) {
                    Piece p = this.elementAt(i);
                    p.setLocation(location.x, p.getLocation().y + offset*i);
                }
            }
            else
            {
                if(idx <= 12)
                    location.y += piece.getHeight();
                else
                    location.y -= piece.getHeight();
            }
        }
        return piece;
    }
    public void setCoord(int x, int y)
    {
        location.x = x;
        location.y = y;
    }
}
