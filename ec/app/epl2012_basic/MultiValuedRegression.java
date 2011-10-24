/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.epl2012_basic;
import ec.util.*;
import ec.*;
import ec.gp.*;
import ec.gp.koza.*;
import ec.simple.*;
import java.lang.Math;

public class MultiValuedRegression extends GPProblem implements SimpleProblemForm
    {
    public int teamA;
    public int teamB;
    public int gameWeek;
   
    public DoubleData input;

    public SqliteDB db;

    public Object clone()
        {
        MultiValuedRegression newobj = (MultiValuedRegression) (super.clone());
        newobj.input = (DoubleData)(input.clone());
        return newobj;
        }

    public void setup(final EvolutionState state,
        final Parameter base)
        {
        // very important, remember this
        super.setup(state,base);

        db = new SqliteDB();
        db.init();

        // set up our input -- don't want to use the default base, it's unsafe here
        input = (DoubleData) state.parameters.getInstanceForParameterEq(
            base.push(P_DATA), null, DoubleData.class);
        input.setup(state,base.push(P_DATA));
        }

    public void evaluate(final EvolutionState state, 
        final Individual ind, 
        final int subpopulation,
        final int threadnum)
        {
        if (!ind.evaluated)  // don't bother reevaluating
            {
            int hits = 0;
            double sum = 0.0;
            double expectedResult;
            double result;
            for (int y=0;y<20;y++)
                {
                //gameWeek = y;
                while(true) {
                    teamA = Math.abs(state.random[threadnum].nextInt()) % 20 + 1;
                    teamB = Math.abs(state.random[threadnum].nextInt()) % 20 + 1;
                    //currentX = state.random[threadnum].nextDouble();
                    //currentY = state.random[threadnum].nextDouble();
                    expectedResult = db.vsdata[teamA][teamB];//currentX*currentX*currentY + currentX*currentY + currentY;
                    gameWeek = db.gwdata[teamA][teamB];
                    if (expectedResult != Double.POSITIVE_INFINITY)
                        break; 
                }
                
                //System.out.format("GW%d teamA: %s teamB: %s (%f)\n", gameWeek, db.teams[teamA], db.teams[teamB], expectedResult);
               // System.out.println("gw:"+y+" teamA: "+db.sumdata[teamA][y].name+" teamB: "+db.sumdata[teamB][y].name+" result:"+expectedResult);

                ((GPIndividual)ind).trees[0].child.eval(
                    state,threadnum,input,stack,((GPIndividual)ind),this);

                result = Math.abs(expectedResult - input.x);
                if (result <= 0.01) hits++;
                sum += result;
                }

            // the fitness better be KozaFitness!
            KozaFitness f = ((KozaFitness)ind.fitness);
            f.setStandardizedFitness(state,(float)sum);
            f.hits = hits;
            ind.evaluated = true;
            }
        }
    }

