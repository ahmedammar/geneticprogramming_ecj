/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.epl2012_basic;
import ec.*;
import ec.gp.*;
import ec.util.*;
import java.io.*;

public class Zero extends ERC
    {
    public double value;
    public String toStringForHumans() { return "" + value; }
    public String encode() { return Code.encode(value);}

    public boolean decode(DecodeReturn ret) {
        int pos = ret.pos;
        String data = ret.data;
        Code.decode(ret);
        if (ret.type != DecodeReturn.T_DOUBLE) // uh oh! Restore and signal error. 
            { ret.data = data; ret.pos = pos; return false; }
        value = (int) ret.d;
        return true; 
    }

    public boolean nodeEquals(GPNode node) 
    { 
        return (node.getClass() == this.getClass() && ((Zero)node).value == value); 
    }

    public void readNode(EvolutionState state, DataInput input) throws IOException 
    { 
        value = input.readDouble(); 
    }

    public void writeNode(EvolutionState state, DataOutput output) throws IOException 
    { 
        output.writeDouble(value); 
    }

    public void resetNode(EvolutionState state, int thread) 
    { 
        value = 0.0;//state.random[thread].nextDouble();// - 1.0; 
    }
/*
    public void mutateNode(EvolutionState state, int thread) {
        double v; 
        do v = value + state.random[thread].nextGaussian(); 
        while( v < 0 || v >= 1.0 ); 
        value = v;
        value += state.random[thread].nextDouble() * 10;
    }
*/
    public void checkConstraints(final EvolutionState state,
        final int tree,
        final GPIndividual typicalIndividual,
        final Parameter individualBase)
        {
        super.checkConstraints(state,tree,typicalIndividual,individualBase);
        if (children.length!=0)
            state.output.error("Incorrect number of children for node " + 
                toStringForError() + " at " +
                individualBase);
        }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
        ((DoubleData)input).x = value;
        }
    }

