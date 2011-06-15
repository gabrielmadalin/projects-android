/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josuvladimir.backgammonpc;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.josuvladimir.backgammonpc.Piece.colorType;

/**
 *
 * @author Vladimir
 */
public class Game {
    public static Game instance;
    static BackgammonPCView view;
    static Player  playerW;
    static Player  playerB;
    static long    threadSleep;
    gameState    currentState,nextState;
    currentPlay    turn;
    Table   table;
    boolean     gameHasBegin,playerAction,stateWasChange,gameOver;
    public enum gameState
    {
        idle,
        firtstRoll,
        waitRoll,
        wasRoll,
        waitMove,
        executeMove,
        moveOut,
        showMesage,
        showingMesage,
        hideMesage,
    }
    public enum currentPlay
    {
        playerW,
        playerB;
    }
    public enum gameType
    {
        vsComp,
        vsHuman,
        comPvsComp,
    }


    public Game(gameType gt)
    {
        instance = this;
        threadSleep = 1500;
        table = new Table();
        table.initTable();
        if(playerB != null)
        {
            playerB.interrupt();
            //playerB.sto
        }
        if(playerW != null)
        {
            playerW.interrupt();
            //playerW.stop();
        }
        switch (gt)
        {
            default:
            case vsComp:
                playerW = new Player(table.coordPlayerW,Piece.colorType.white);
                playerB = new iPlayer(table.coordPlayerB,Piece.colorType.black);
                break;
            case vsHuman:
                playerW = new Player(table.coordPlayerW,Piece.colorType.white);
                playerB = new Player(table.coordPlayerB,Piece.colorType.black);
                break;
            case comPvsComp:
                playerW = new iPlayer(table.coordPlayerW,Piece.colorType.white);
                playerB = new iPlayer(table.coordPlayerB,Piece.colorType.black);
        }

        gameHasBegin = false;
        playerAction = false;
        currentState = gameState.waitRoll;
        stateWasChange = true;
        gameOver = false;
        updatePipsValue();
    }

    public synchronized void start()
    {
        setCurrentPlayer(Piece.colorType.white);
        playerW.start();
        playerB.start();
        //test
        //gameHasBegin = true;
    }

    public synchronized boolean playerAction()
    {
        //currentPlayerFinishAction = true;
        return playerAction;
    }
    public synchronized void currentPlayerAction(boolean b)
    {
        playerAction = b;
    }

    public void setView(BackgammonPCView v)
    {
        view = v;
    }

    public void updatePipsValue()
    {
        playerW.updatePips();
        playerB.updatePips();
    }


    public void roll()
    {
        currentPlayer().roll();
    }

    public void hideDice()
    {
        playerW.hideDice();
        playerB.hideDice();
    }

    public synchronized boolean setCurrentState(gameState s)
    {
        if(!stateWasChange && currentState != s)
        {
            boolean canChange = false;
            switch(currentState)
            {
                case firtstRoll:
                    if(     s == gameState.waitRoll ||
                            s == gameState.showMesage)
                        canChange = true;
                    break;
                case waitRoll:
                    if(s == gameState.wasRoll ||
                            s == gameState.waitRoll ||
                            s == gameState.hideMesage)
                        canChange = true;
                    break;
                case wasRoll:
                    if(     s == gameState.waitMove ||
                            s == gameState.firtstRoll)
                        canChange = true;
                    break;
                case waitMove:
                    if(s == gameState.executeMove ||
                       s == gameState.moveOut ||
                       s == gameState.showMesage ||
                       s == gameState.waitRoll)
                        canChange = true;
                    break;
                case executeMove:
                case moveOut:
                    if(s == gameState.waitRoll ||
                       s == gameState.showMesage ||
                       s == gameState.waitMove)
                        canChange = true;
                    break;
                case showMesage:
                    if(s == gameState.showingMesage)
                        canChange = true;
                    break;
                case showingMesage:
                    if(s == gameState.hideMesage)
                        canChange = true;
                    break;
                case hideMesage:
                    canChange = true;
                    break;
            }
            if(canChange)
            {
                stateWasChange = true;
                currentState = s;
            }
            else
            {
            }
        }
        else
        {
            nextState(s);
        }
        return stateWasChange;
        /*
        if(nextState == gameState.idle)
        {
            currentState = newState;
        }
        else
        {
            currentState = nextState;
            nextState   = gameState.idle;
        }
         *
         */
    }

    public void nextState(gameState newS)
    {
        if(nextState != currentState)
        {
            nextState = newS;
        }
    }
    /*
    public boolean updateState()
    {
        if(!stateWasChange && nextState != currentState && nextState!= null)
        {
            currentState = nextState;
            stateWasChange = true;
        }
        return stateWasChange;
    }
     *
     */

    public void tryToMove()
    {
        switch(currentState)
        {
            case waitRoll:
                //view.showMesage("You need to roll dice first", currentState);
                view.showMesage("You need to roll dice first");
                break;
            case firtstRoll:
                //view.showMesage("You need to roll dice first", currentState);
                view.showMesage("You need to roll dice first");
                break;

        }
    }

    public synchronized void run()
    {
        stateWasChange = false;
        switch (currentState)
        {
            case firtstRoll:
                if(playerW.wasRoll == playerB.wasRoll)
                {
                    if(playerW.diceA.value == playerB.diceA.value)
                    {
                        view.showMesage("Roll again");
                        changeCurrentPlayer();
                    }
                    else
                    {
                        if(playerW.diceA.value > playerB.diceA.value)
                        {
                            setCurrentPlayer(Piece.colorType.white);
                            view.showMesage("First turn White");
                        }
                        else
                        {
                            setCurrentPlayer(Piece.colorType.black);
                            view.showMesage("First turn Black");
                        }
                        gameHasBegin = true;
                    }
                    playerW.wasRoll = playerB.wasRoll = false;
                }
                else
                {
                    changeCurrentPlayer();
                    setCurrentState(gameState.waitRoll);
                }
                break;
            case idle:
                break;
            case waitRoll:
                view.RollButton.setEnabled(true);
                if(nextState == gameState.waitMove)
                    hideDice();
                break;
            case wasRoll:
                roll();
                view.RollButton.setEnabled(false);
                break;
            case waitMove:
                table.currentMove = new Move(table.currentMove.pozB);
                break;
            case executeMove:
                table.move(table.currentMove);
                currentPlayer().useDice(table.currentMove.getDice());
                updatePipsValue();
                break;
            case moveOut:
                table.moveOut(table.currentMove);
                updatePipsValue();
                break;
            case showMesage:
                view.MesageLabel.setVisible(true);
                if(gameOver)
                {
                    //view.OkButton.setVisible(false);
                    view.RollButton.setEnabled(false);
                }
                else
                {
                    //view.OkButton.setVisible(true);
                    view.RollButton.setEnabled(false);
                }
                setCurrentState(gameState.showingMesage);
                break;
            case showingMesage:
                if(gameOver)
                {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    view.getApplication().exit(null);
                }
                break;
            case hideMesage:
                view.MesageLabel.setVisible(false);
                //view.OkButton.setVisible(false);
                //view.OkButton.setEnabled(false);
                setCurrentState(gameState.waitRoll);
                hideDice();
                break;
        }
        view.updateView();
    }
    public Player getPlayer(colorType type)
    {
        switch(type)
        {
            case white:
                return playerW;
            case black:
                return playerB;
        }
        return currentPlayer();
    }
    public synchronized Player currentPlayer()
    {
        if(turn == currentPlay.playerW)
            return playerW;
        else
            return playerB;
    }
    public synchronized void setCurrentPlayer(Piece.colorType t)
    {
        if(playerW.color == t)
        {
            playerW.play();
            playerB.pause();
            turn = currentPlay.playerW;
        }
        else
        {
            playerW.pause();
            playerB.play();
            turn = currentPlay.playerB;
        }
    }
    public synchronized void changeCurrentPlayer()
    {
        currentPlayer().pause();
        if(turn == currentPlay.playerW)
        {
            turn = currentPlay.playerB;
        }
        else
        {
            turn = currentPlay.playerW;
        }
        currentPlayer().play();
        view.updateView();
    }
}
