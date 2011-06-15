/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josuvladimir.backgammonpc;

import java.util.Stack;

/**
 *
 * @author Vladimir
 */
@SuppressWarnings("serial")
public class Turn extends Stack<Move>
{
    int cost;

    public Turn()
    {
        clear();
    }
    public void setCost(int i)
    {
        cost = i;
    }
    public boolean equal(Turn m)
    {
        if(size() != m.size())
            return false;
        for (int i = 0; i < m.size(); i++) {
            Move move = m.elementAt(i);
            if(!move.equal(this.elementAt(i)))
                return false;
        }
        return true;
    }

    @Override
    public synchronized Turn clone() {
        Turn clone  = new Turn();
        clone.clear();
        clone.cost  = cost;
        for (int i = 0; i < this.size(); i++)
        {
            Move move = this.elementAt(i);
            clone.add(move.clone());
        }
        return clone;
    }
}
