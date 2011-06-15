/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josuvladimir.backgammonpc;
/**
 *
 * @author Vladimir
 */
public class Move {
    int pozA;
    int pozB;
    Move combinate;
    boolean isOut = false;

    public Move()
    {
        pozA = pozB = -1;
        isOut = false;
    }

    public Move(int pozA, int pozB) {
        this.pozA = pozA;
        this.pozB = pozB;
        combinate = new Move();
    }

    public void set(int pozA, int pozB) {
        this.pozA = pozA;
        this.pozB = pozB;
        combinate = new Move();
    }

    public Move(int pozI)
    {
        pozA = pozI;
    }

    public void begin(int poz)
    {
        pozA = poz;
    }

    public void end(int poz)
    {
        pozB = poz;
    }



    public  boolean begin()
    {
        return pozA != -1;
    }

    public int getDice()
    {
        return Math.abs(pozA - pozB);
    }

    public boolean equal(Move m)
    {
        if(pozA != m.pozA)
            return false;
        if(pozB != m.pozB)
            return false;
        return true;
    }

    public boolean isValid()
    {
        if(pozA == pozB)
            return false;
        if(pozA == -1 || pozB == -1)
            return false;
        return true;
    }
    public boolean isAscend()
    {
        return pozA < pozB;
    }
    public boolean isDescend()
    {
        return pozA > pozB;
    }
    @Override
    public Move clone()
    {
        Move clone = new Move(pozA, pozB);
        if(combinate != null)  
            clone.combinate = combinate.clone();
        clone.isOut = isOut;
        return clone;
    }
}
