/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.epl2012_basic;
import ec.*;
import ec.gp.*;
import ec.util.*;

public class Div extends GPNode
    {
    public String toString() { return "/"; }

    public void checkConstraints(final EvolutionState state,
        final int tree,
        final GPIndividual typicalIndividual,
        final Parameter individualBase)
        {
        super.checkConstraints(state,tree,typicalIndividual,individualBase);
        if (children.length!=2)
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
        double result;
        DoubleData rd = ((DoubleData)(input));

        children[1].eval(state,thread,input,stack,individual,problem);
        if (rd.x==0.0) 
            // the answer is 1.0 since the denominator was 0.0
            rd.x = rd.x / 1E-20;
        else
            {
            result = rd.x;

            children[0].eval(state,thread,input,stack,individual,problem);
            rd.x = rd.x / result;
            }
        }
    }

