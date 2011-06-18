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
public class iPlayer extends Player {
    //Vector<Turn> turnes;
    Turn moves;
    Turn bestTurn;

    public iPlayer(Point diceCoord,Piece.colorType t)
    {
        diceA   = new Dice(diceCoord);
        diceB   = new Dice(new Point(diceCoord.x + 35, diceCoord.y));
        //turnes  = new Vector<Turn>();
        moves   = new Turn();
        bestTurn = new Turn();
        //turnes.clear();
        color   = t;
        wasRoll = false;
        isDouble = false;
        switch (t)
        {
            case white:
                name = "White";
                break;
            case black:
                name = "Black";
                break;
        }
    }

    @Override
    public void run() 
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
                    sleepVal = 500;
                    switch (Game.instance.currentState)
                    {
                        case firtstRoll:
                            break;
                        case waitRoll:
                            sleepVal = 0;
                            Game.instance.setCurrentState(Game.gameState.wasRoll);
                            //Game.instance.nextState(Game.gameState.wasRoll);
                            break;
                        case wasRoll:
                            if(Game.instance.gameHasBegin)
                            {
                                showDice();
                                Game.instance.setCurrentState(Game.gameState.waitMove);
                                //turnes.clear();
                                moves.clear();
                                bestTurn.clear();
                                playerAction();
                            }
                            else
                            {
                                Game.instance.setCurrentState(Game.gameState.firtstRoll);
                                diceA.setVisible(true);
                                diceB.setVisible(false);
                                sleepVal = 0;
                            }
                            break;
                        case waitMove:
                            if(!haveMoves)
                            {
                                haveMoves = haveValidMoves();
                            }
                            if(haveMoves)
                            {
                                if(bestTurn.isEmpty())
                                {
                                    Game.instance.table.calculateMove = true;
                                    //turnes.clear();
                                    moves.clear();
                                    searchTurn();
                                    getBestTurn();
                                    Game.instance.table.calculateMove = false;
                                }
                                if(!bestTurn.isEmpty())
                                    Game.instance.table.currentMove = bestTurn.pop();
                                if(Game.instance.table.currentMove.isOut)
                                    Game.instance.setCurrentState(Game.gameState.moveOut);
                                else
                                    Game.instance.setCurrentState(Game.gameState.executeMove);
                            }
                            else
                            {
                                Game.view.showMesage("No valid move");
                                hideDice();
                                Game.instance.changeCurrentPlayer();
                                //Game.instance.setCurrentState(Game.gameState.waitRoll);
                            }
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
                            //Game.instance.nextState(Game.gameState.hideMesage);
                            sleepVal = 1000;
                            break;
                        case hideMesage:
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
    public void searchTurn()
    {
        Move m = new Move();

        for(int i = 0; i <= 25; i++)
        {
            for(int j = 0; j <= 25; j++)
            {
                m.set(i, j);
                if(validMove(m) && Game.instance.table.validMove(m))
                {
                    moves.add(m.clone());
                    Game.instance.table.move(m);
                    useDice(m.getDice());
                    if(haveValidMoves())
                        searchTurn();
                    else
                    {
                        moves.cost = evaluateTable();
                        if(bestTurn.isEmpty())
                            bestTurn = moves.clone();
                        else if(bestTurn.cost > moves.cost)
                            bestTurn = moves.clone();
                        //turnes.add(moves.clone());
                    }
                    Game.instance.table.undoMove(m);
                    undoDice(m.getDice());
                    moves.pop();
                }else if(Game.instance.table.validOut(m, color)){
                    m.isOut = true;
                    moves.add(m.clone());
                    Game.instance.table.moveOut(m);
                    if(haveValidMoves())
                    {
                        searchTurn();
                    }
                    else
                    {
                        moves.cost = evaluateTable();
                        //turnes.add(moves.clone());
                        if(bestTurn.isEmpty())
                            bestTurn = moves.clone();
                        else if(bestTurn.cost > moves.cost)
                            bestTurn = moves.clone();
                    }
                    Game.instance.table.undoMoveOut(m, color);
                    int diceValue = m.getDice();
                    while(!undoDice(diceValue) && diceValue < 7)
                        diceValue++;
                }
            }
        }
    }
    public int evaluateTable()
    {
        int cost = 0;
        for (int i = 1; i < Game.instance.table.size(); i++)
        {
            TPoint point = Game.instance.table.get(i, color);
            if(!point.isEmpty())
            {
                if(point.color == color)
                    cost += getPointCost(i, point.size(), point.color);
                else
                    cost -= getPointCost(25 - i, point.size(), point.color.reverse());
            }
        }
        return cost;
    }
    /*

    public void undoTurn(Turn t)
    {
        Turn tmp = (Turn) t.clone();
        while(!tmp.isEmpty())
        {
            Game.instance.table.undoMove(t.pop());
        }
    }
     *
     */


    public void showTurn(Turn turn)
    {
        for (int i = 0; i < turn.size(); i++) {
            Move move = turn.get(i);
            System.out.println(move.pozA + "->"+ move.pozB);
        }
        System.out.println("Cost:" + turn.cost);
    }

    /*
    public boolean move()
    {

        for(int i = 0; i <= 25; i++)
        {
            for(int j = 0; j <= 25; j++)
            {
                if(Game.instance.table.validMove(i, j))
                {
                    //Game.instance.table.move(i, j);
                    Game.instance.table.setSelectedPiece(i);
                    Game.instance.table.setSelectedPoint(j);
                    return true;
                }
                if(Game.instance.table.validOut(j, color))
                {
                    Game.instance.table.setSelectedPiece(j);
                    //Game.instance.table.move(new Move(i, j));
                    return true;
                }
            }
        }
        return false;
    }
    /*
     */
    /*

    public boolean  existTurn(Turn t)
    {
        for (int i = 0; i < turnes.size(); i++)
        {
            Turn turn = turnes.elementAt(i);
            if(turn.equal(t))
                return true;
        }
        return false;
    }
     * 
     */
    /*
    public boolean existMove(int level,Move m)
    {
        Vector<Move> tmp;
        if(level == 0)
            tmp = movesLevel0;
        else
            tmp = movesLevel1;
        for (int i = 0; i < tmp.size(); i++) {
            Move move = tmp.elementAt(i);
            if(move.equal(m))
                return true;
        }
        return false;
    }
     *
     */

    public int getPointCost(int pointIdx, int pointSize, Piece.colorType c)
    {
        @SuppressWarnings("unused")
		int hight = 30, medium = 10, low = 1,pointCost, currentRisc;
        currentRisc = low;
        //*
        if(pointSize == 1 && pointIdx != 25)
        {
            //currentRisc = hight;
            /*
            for(int i = pointIdx - 1; (i >= pointIdx - 12) && (i >=0); i--)
            {
                //TPoint point = Game.instance.table.get(i, c.reverse());
                TPoint point = Game.instance.table.get(i, c);
                if(!point.isEmpty() && point.color == c.reverse())
                {
                    currentRisc += (25 - pointIdx);
                    //currentRisc++;
                }
            }
            //*/
            currentRisc+= (25 - pointIdx);
            //pointCost = currentRisc*(25 - pointIdx);
        }
        if(pointIdx < 7)
        {
            pointIdx = 1;
            //currentRisc++;
        }

        pointCost = pointIdx * pointSize;
        //*
        if(pointIdx < 7)
        {
            if(pointSize == 1)
                pointCost = currentRisc*(25 - pointIdx);
            //else if(point.size() == 2)                        pointCost = low * i;
            else
                pointCost = low * pointIdx;
        }
        else if(pointIdx < 18)
        {
            if(pointSize == 1)
                pointCost = currentRisc*(25 - pointIdx);
            //else if(point.size() == 2)pointCost = low * i;
            else
                pointCost = medium*pointIdx;
        }
        else if(pointIdx <= 24)
        {
            if(pointSize == 1)
                pointCost = currentRisc*(25 - pointIdx);
            //else if(point.size() == 2)  pointCost = low * i;
            else
                pointCost = medium * pointIdx;
        }
        else
            pointCost = medium * pointIdx;
         
         //*/
        return pointCost;
    }

    public Turn getBestTurn()
    {
        Turn t = new Turn();
        t.clear();
        while (!bestTurn.isEmpty()) {            
            t.push(bestTurn.pop());
        }
        bestTurn = t;
        return bestTurn;
        /*
        Turn tmpTurn = turnes.firstElement();
        for (int i = 1; i < turnes.size(); i++) {
            Turn turn = turnes.get(i);
            if(turn.cost < tmpTurn.cost)
                tmpTurn = turn;
        }
        bestTurn = new Turn();
        bestTurn.cost = tmpTurn.cost;
        while(!tmpTurn.isEmpty())
            bestTurn.push(tmpTurn.pop());
        return bestTurn;
         *
         */
    }
}
