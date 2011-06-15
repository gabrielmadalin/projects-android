/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josuvladimir.backgammonpc;

import java.awt.Point;
import java.util.Vector;

import com.josuvladimir.backgammonpc.Piece.colorType;
/**
 *
 * @author Vladimir
 */
@SuppressWarnings("serial")
public class Table extends Vector<TPoint>{
    static final int nrPoint   = 25;
    static final int tableH    = 673;
    static final int tableW    = 670;
    static final int middleGap = 65;
    static final int topGap    = -21;
    static final int maxPiece  = 5;
    Point coordPlayerW,coordPlayerB;
    //*
    final int vInitPoz[]= {0,0,0,0,0,0,5,0,3,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,2,0};
    /*/
    final int vInitPoz[]= {0,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    /**/
    static TPoint barW,barB,outW,outB;
    Move    currentMove;
    Turn    moves;
    colorType selectedOutPoint;
    boolean calculateMove;

    public Table() {
        setSize(nrPoint+1);
        selectedOutPoint = colorType.undefined;
        coordPlayerW = new Point(500, 330);
        coordPlayerB = new Point(150, 330);
        outW    = new TPoint(0);
        outB    = new TPoint(25);
        outW.empty();
        outB.empty();
        outW.setCoord(10, 620);
        outB.setCoord(10, 40);
        outW.color = colorType.white;
        outB.color = colorType.black;
        currentMove = new Move();
        moves = new Turn();
        calculateMove = false;
    }
    public void initTable()
    {
        for (int i = 0; i < this.size(); i++) {
            set(i, new TPoint(i));
        }
        setPointCoord();
        for (int i = 0; i < this.size(); i++) {
            if(vInitPoz[i] == 0 && vInitPoz[nrPoint - i] == 0)
            {
                //set(i, new Point(0, colorType.undefined,i));
                elementAt(i).initWithPiece(0, colorType.undefined);
            }
            else if(vInitPoz[i] != 0)
            {
                //set(i, new Point(vInitPoz[i], colorType.white,i));
                elementAt(i).initWithPiece(vInitPoz[i], colorType.white);
            }
            else if(vInitPoz[nrPoint - i] != 0)
            {
                //set(i, new Point(vInitPoz[nrPoint - i], colorType.black,i));
                elementAt(i).initWithPiece(vInitPoz[nrPoint - i], colorType.black);
            }
        }
        barW   = elementAt(nrPoint);//new Point(0, colorType.white);
        barB   = elementAt(0);//new Point(0, colorType.black);
        //calcPips();
        //setPointCoord();
        //System.out.println("Pips=" + blackPips);
    }

    public TPoint get(int i,colorType type)
    {
        return get(simetricIdx(i, type));
        /*
        if(type == colorType.white)
        {
            return get(i);
        }
        else
        {
            return get(nrPoint - i);
        }
         *
         */
    }
    public int simetricIdx(int i,colorType t)
    {
        switch(t)
        {
            case white:
                return i;
            case black:
                return nrPoint - i;
        }
        return i;
    }


    public void setSelectedPiece(int pointIdx)
    {
        if (Game.instance.currentState == Game.gameState.waitMove)
        {
            currentMove.begin(pointIdx);
            //elementAt(pointIdx).peek().setSelected(true);
        }
        else
        {
            //try to move
        }
    }
    public void setSelectedPoint(int pointIdx)
    {
        if(currentMove.begin())
        {
            currentMove.end(pointIdx);
            if(Game.instance.currentPlayer().validMove(currentMove))
            {
                Game.instance.setCurrentState(Game.gameState.executeMove);
            }
            else
            {
                currentMove.begin(pointIdx);
            }
        }
        else
        {
            currentMove.begin(pointIdx);
        }
    }
    public void selectOutPoint(colorType color)
    {
        if(currentMove.begin())
        {
            switch (color)
            {
                case white:
                    currentMove.end(outW.idx);
                    break;
                case black:
                    currentMove.end(outB.idx);
                    break;
            }
            
            if(validOut(currentMove,color))
            {
                Game.instance.setCurrentState(Game.gameState.moveOut);
            }
        }
    }
    public void setPointCoord()
    {
        Piece p = new Piece(colorType.white);
        int x,y;
        for (int i = 0; i < this.size(); i++)
        {
            TPoint point = this.elementAt(i);
            if(i<=12)
            {
                x = tableW - (i*p.getWidth());
                y = tableH;// - p.getHeight();
            }
            else
            {
                x = tableW - ((nrPoint - i)*p.getWidth());
                y = topGap;
            }

            if(i > 6 && i < 19)
                x-=middleGap;
            if(i == 0 || i == nrPoint)
                x = 330;
            point.setCoord(x, y);
        }
    }

    public boolean validPointIdx(int pointIdx)
    {
        if(pointIdx >= 0 && pointIdx <= nrPoint)
            return true;
        else
            return false;
    }

    public boolean validMove(Move m)
    {
        if(!existPieseOnTable(Game.instance.currentPlayer().color))
            return false;
        int nPozI = m.pozA;
        int nPozF = m.pozB;

        if(!m.isValid())
            return false;
        if(!validPointIdx(nPozI))
            return false;
        if(!validPointIdx(nPozF))
            return false;
        if(elementAt(nPozI).isEmpty())
            return false;
        if(elementAt(nPozF).color != elementAt(nPozI).color && elementAt(nPozF).size() > 1)
            return false;

        switch (get(nPozI).color)
        {
            case white:
                if(!barW.isEmpty() && nPozI != barW.idx && nPozF != barW.idx)
                    return false;
                break;
            case black:
                if(!barB.isEmpty() && nPozI != barB.idx && nPozF != barB.idx)
                    return false;
                break;
            case undefined:
                return false;
        }
        return true;
    }

    public void move(Move m)
    {
        int nPozI = m.pozA;
        int nPozF = m.pozB;
        if(validMove(m))
        {
            if(elementAt(nPozF).size() == 1 && elementAt(nPozF).color != elementAt(nPozI).color)
            {
                switch(elementAt(nPozF).color)
                {
                    case white:
                        m.combinate = new Move(nPozF, barW.idx);
                        move(m.combinate);
                        //barW.add(elementAt(nPozF).pop());
                        break;
                    case black:
                        //barB.add(elementAt(nPozF).pop());
                        m.combinate = new Move(nPozF, barB.idx);
                        move(m.combinate);
                        break;
                }
            }
            Piece p = get(nPozI).pop();
            get(nPozF).add(p);
            moves.push(m);
            //currentMove = new Move();
        }
    }

    public void undoLastMove()
    {
        undoMove(moves.pop());
    }

    public void undoMove(Move m)
    {
        if(m.isValid())
        {
            Piece p = get(m.pozB).pop();
            if(p == null)
                return;
            get(m.pozA).add(p);
            if(m.combinate != null && m.combinate.isValid())
            {
                undoMove(m.combinate);
            }
        }
    }
    public void undoMoveOut(Move m,Piece.colorType color)
    {
        Piece p = null;
        switch (color)
        {
            case white:
                p = outW.pop();
                if(p == null)
                    return;
                get(m.pozA).add(p);
                p.setEnabled(true);
                //p.setVisible(true);
                break;
            case black:
                p = outB.pop();
                if(p == null)
                    return;
                get(m.pozA).add(p);
                p.setEnabled(true);
                break;
        }
    }

    public boolean existValidMove(colorType color,int nPoz)
    {
        Move m = new Move();
        if(!existPieseOnTable(color))
            return false;
        switch(color)
        {
            case white:
                if(barW.isEmpty())
                {
                    for (int i = nPoz; i < this.size(); i++)
                    {
                        m.set(i, i - nPoz);
                        if(Game.instance.getPlayer(color).validMove(m))
                        {
                            if(validMove(m))
                                return true;
                            m.end(outW.idx);
                            if(validOut(m, color))
                                return true;

                        }
                    }
                }
                else
                {
                    m.set(barW.idx, barW.idx - nPoz);
                    return validMove(m);
                }
                break;
            case black:
                if(barB.isEmpty())
                {
                    for (int i = 1; i < this.size(); i++)
                    {
                        m.set(i, i + nPoz);
                        if(Game.instance.getPlayer(color).validMove(m))
                        {
                            if(validMove(m))
                                return true;
                            m.end(outB.idx);
                            if(validOut(m, color))
                                return true;

                        }
                    }
                }
                else
                {
                    m.set(barB.idx,barB.idx + nPoz);
                    return validMove(m);
                }
                break;
        }
        return false;
    }

    boolean allinHouse(colorType color)
    {
        switch (color)
        {
            case white:
                if(!barW.isEmpty())
                    return false;
                break;
            case black:
                if(!barB.isEmpty())
                    return false;
                break;
        }
        for (int i = 7; i < nrPoint; i++)
        {
            TPoint tPoint = this.get(i, color);
            if(!tPoint.isEmpty() && tPoint.color == color)
                return false;
        }
        return true;
    }

    public boolean existPieseOnTable(colorType color)
    {
        for (int i = 0; i < this.size(); i++) {
            TPoint point = this.elementAt(i);
            if(!point.isEmpty() && point.color == color)
                return true;
        }
        return false;
    }

    public void moveOut(Move m)
    {
        TPoint point = get(m.pozA);
        if(validOut(m,point.color))
        {
            switch (point.color)
            {
                case white:
                    outW.pushOut(point.pop());
                    if(!calculateMove)
                        Game.view.outToInfoPanel(outW.peek());
                    break;
                case black:
                    outB.pushOut(point.pop());
                    if(!calculateMove)
                        Game.view.outToInfoPanel(outB.peek());
                    break;
            }
            int idx = m.getDice();
            while(!Game.instance.currentPlayer().validDice(idx) && idx < 7)
            {
                idx++;
            }
            Game.instance.currentPlayer().useDice(idx);
        }
    }
    public boolean validOut(Move m, colorType type)
    {
        colorType color = Game.instance.currentPlayer().color;
        TPoint point = get(m.pozA);
        if(!m.isValid())
            return false;
        if(color != type)
            return false;
        switch (color)
        {
            case white:
                if(m.pozB != Table.outW.idx)
                    return false;
                break;
            case black:
                if(m.pozB != Table.outB.idx)
                    return false;
                break;
        }
        if(!allinHouse(color))
            return false;
        if(point.isEmpty() || point.color != color)
            return false;

        if(Game.instance.currentPlayer().validDice(m.getDice()))
            return true;
        else
        {
            boolean validDice = false;
            for (int i = 6; i > 0;i--)
            {
                TPoint tPoint = get(i,color);
                if(Game.instance.currentPlayer().validDice(i))
                    validDice = true;
                if(!tPoint.isEmpty() && tPoint.color == color)
                {
                    if(m.getDice() == i && validDice)
                        return true;
                    else
                        return false;
                }
            }
            //return true;
        }
        return false;
    }
}
