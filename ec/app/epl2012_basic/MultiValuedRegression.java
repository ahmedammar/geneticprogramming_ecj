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

        teamA = state.parameters.getInt(base.push("teama"),null,1);
        if (teamA<1) state.output.fatal("Team not chosen: set eval.problem.teama", base.push("teama")); 

        teamB = state.parameters.getInt(base.push("teamb"),null,1);
        if (teamB<1) state.output.fatal("Team not chosen: set eval.problem.teamb", base.push("teamb")); 

        db = new SqliteDB();
        db.init();

        state.output.message("Chosen: "+db.teams[teamA]+" vs "+db.teams[teamB]);

        db.sumdata[teamA][12].dump("a");
        db.sumdata[teamB][12].dump("b");

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

            for (gameWeek=1;gameWeek<12;gameWeek++)
            {
                expectedResult = db.twdata[gameWeek][teamA];
                if (expectedResult == Double.POSITIVE_INFINITY)
                    continue;

                if (db.sumdata[teamA][gameWeek-1] == null)
                    continue;

                teamB = db.gwdata[gameWeek][teamA];
                if (db.sumdata[teamB][gameWeek-1] == null)
                    continue;

                 ((GPIndividual)ind).trees[0].child.eval(
                    state,threadnum,input,stack,((GPIndividual)ind),this);

                //System.out.format("GW%d %s vs %s (%.2f) [%.2f]\n", gameWeek, db.teams[teamA], db.teams[teamB], expectedResult, input.x);
                result = Math.abs(expectedResult - input.x);

                if (result <= 0.001) hits++;
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

