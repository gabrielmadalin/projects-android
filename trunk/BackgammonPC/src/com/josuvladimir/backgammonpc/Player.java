/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josuvladimir.backgammonpc;

import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vladimir
 */
public class Player extends Thread {
    Dice diceA;
    Dice diceB;
    Piece.colorType color;
    boolean wasRoll;
    boolean endTurn;
    boolean isDouble;
    boolean pause;
    boolean haveMoves;
    int     pips;
    String  name;

    public Player()
    {

    }

    public void pause()
    {
        pause = true;
    }

    public void play()
    {
        pause = false;
    }

    public Player(Point diceCoord, Piece.colorType c)
    {
        diceA   = new Dice(diceCoord);
        diceB   = new Dice(new Point(diceCoord.x + 35, diceCoord.y));
        color   = c;
        wasRoll = false;
        isDouble = false;
        haveMoves = false;
        pause   = true;
        switch (c)
        {
            case white:
                name = "White";
                break;
            case black:
                name = "Black";
                break;
        }
    }
    public synchronized void roll()
    {
        diceA.roll();
        diceB.roll();
        //showDice();
        wasRoll = true;
        endTurn = false;
        if(diceA.value == diceB.value)
            isDouble = true;
        else
            isDouble = false;
    }
    public void playerAction()
    {
        Game.instance.stateWasChange = false;
    }

    @Override
    public synchronized void run()
    {
        int sleepVal = 100;
        while(!Game.instance.gameOver)
        {
            if(!pause && !Game.instance.playerAction())
            {
                Game.instance.currentPlayerAction(true);
                if(Game.instance.stateWasChange)
                {
                    Game.instance.run();
                    sleepVal = 100;
                }
                else
                {
                    sleepVal = 50;
                    switch (Game.instance.currentState)
                    {
                        case firtstRoll:
                            break;
                        case waitRoll:
                            break;
                        case wasRoll:
                            if(Game.instance.gameHasBegin)
                            {
                                Game.instance.setCurrentState(Game.gameState.waitMove);
                                showDice();
                            }
                            else
                            {
                                Game.instance.setCurrentState(Game.gameState.firtstRoll);
                                diceA.setVisible(true);
                                diceB.setVisible(false);
                            }
                            break;
                        case waitMove:
                            if(!haveMoves)
                            {
                                haveMoves = haveValidMoves();
                            }
                            if(!haveMoves)
                            {
                                Game.view.showMesage("No valid move");
                                Game.instance.changeCurrentPlayer();
                            }
                            sleepVal = 100;
                            break;
                        case executeMove:
                            haveMoves = false;
                            if(endTurn)
                            {
                                Game.instance.changeCurrentPlayer();
                                Game.instance.setCurrentState(Game.gameState.waitRoll);
                            }
                            else
                            {
                                Game.instance.setCurrentState(Game.gameState.waitMove);
                            }
                            break;
                        case moveOut:
                            if(pips == 0)
                            {
                                Game.view.showMesage( name + " win!");
                                Game.instance.gameOver = true;
                                hideDice();
                            }
                            if(endTurn)
                            {
                                Game.instance.changeCurrentPlayer();
                                Game.instance.setCurrentState(Game.gameState.waitRoll);
                            }
                            else if(pips != 0)
                            {
                                Game.instance.setCurrentState(Game.gameState.waitMove);
                            }
                            break;
                        case showingMesage:
                            Game.instance.setCurrentState(Game.gameState.hideMesage);
                            sleepVal = 1000;
                            break;
                        case showMesage:
                            break;
                    }
                }
                Game.instance.currentPlayerAction(false);
            }
            else
            {
                sleepVal = 1000;
            }
            try
            {
                sleep(sleepVal);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(iPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Game.instance.setCurrentState(Game.gameState.showMesage);
        Game.instance.run();
    }
    public void hideDice()
    {
        diceA.setVisible(false);
        diceB.setVisible(false);
    }
    public void showDice()
    {
        diceA.setVisible(true);
        diceB.setVisible(true);
    }

    public void notRoll()
    {
        wasRoll = false;
    }

    /*
    public boolean validOut(Move m)
    {
        if(!m.isValid())
            return false;
        switch (color)
        {
            case white:
                if(m.pozB != Table.outW.idx)
                    return false;
                break;
            case black:
                if(m.pozB != Table.outW.idx)
                    return false;
                break;
        }
        return true;
    }
     *
     */

    public boolean validMove(Move m)
    {
        if(!Game.instance.table.existPieseOnTable(color))
            return false;
        if(!m.isValid())
            return false;
        if(!validDice(m.getDice()))
            return false;
        if(Game.instance.table.get(m.pozA).color != color)
            return false;
        switch (color)
        {
            case white:
                if(!m.isDescend())
                    return false;
                if(m.pozA == Table.barB.idx )
                    return false;
                if(m.pozB == Table.barW.idx)
                    return false;
                if(m.pozB == Table.barB.idx)
                    return false;
              break;
            case black:
                if(!m.isAscend())
                    return false;
                if(m.pozA == Table.barW.idx)
                    return false;
                if(m.pozB == Table.barB.idx)
                    return false;
                if(m.pozB == Table.barW.idx)
                    return false;
                break;
            case undefined:
                break;
        }
        return true;
    }

    public boolean validDice(int poz)
    {
        if(diceA.valid && poz == diceA.value)
            return true;
        if(diceB.valid && poz == diceB.value)
            return true;
        return false;
    }
    public void useDice(int val)
    {
        if(!validDice(val))
            return;
        if(!endTurn)
        {
            if(Math.abs(val) == diceA.value && diceA.valid)
            {
                diceA.setVisible(false);
                if(!diceB.valid)
                    endTurn = true;
            }else if(Math.abs(val) == diceB.value && diceB.valid)
            {
                diceB.setVisible(false);
                if(!diceA.valid)
                    endTurn = true;
            }
        }
        if(endTurn == true && isDouble == true)
        {
            isDouble  = false;
            endTurn = false;
            showDice();
        }
    }
    public boolean undoDice(int val)
    {
        if(isDouble == false && diceA.value == diceB.value && diceA.valid && diceB.valid)
        {
            diceB.setVisible(false);
            isDouble = true;
            endTurn = false;
            return true;
        }else if(Math.abs(val) == diceA.value && !diceA.valid)
        {
            diceA.setVisible(true);
            endTurn = false;
            return true;
        }
        else if(Math.abs(val) == diceB.value && !diceB.valid)
        {
            diceB.setVisible(true);
            endTurn = false;
            return true;
        }
        
        return false;
    }
    public boolean haveValidMoves()
    {
        if(!endTurn)
        {
            if(diceA.valid && Game.instance.table.existValidMove(color, diceA.value))
                return true;
            if(diceB.valid && Game.instance.table.existValidMove(color, diceB.value))
                return true;
        }
        endTurn = true;
        return false;
    }

    public void updatePips()
    {
        //*
        pips = 0;
        for (int i = 1; i < Game.instance.table.size(); i++) {
            TPoint point = Game.instance.table.get(i, color);
            if(point.color == color)
            {
                pips += i*point.size();
            }
        }

         /*
         */
    }
}
