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

        teamA = state.parameters.getInt(base.push("team"),null,1);
        if (teamA<1) state.output.fatal("Team not chosen: set eval.problem.team", base.push("team")); 

        db = new SqliteDB();
        db.init();

        state.output.message("Chosen team: "+db.teams[teamA]);

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
        //if (!ind.evaluated)  // don't bother reevaluating
            {
            int hits = 0;
            double sum = 0.0;
            double expectedResult;
            double result;
            /*teamB=teamA;
            for (teamA=1;teamA<21;teamA++)
            {
                expectedResult = db.vsdata[teamA][teamB];//currentX*currentX*currentY + currentX*currentY + currentY;
                gameWeek = db.gwdata[teamA][teamB];
                if (expectedResult == Double.POSITIVE_INFINITY)
                    continue; 

                ((GPIndividual)ind).trees[0].child.eval(
                    state,threadnum,input,stack,((GPIndividual)ind),this);

                result = Math.abs(expectedResult - input.x);

                //System.out.format("GW%d teamA: %s teamB: %s (%.2f) %.4f\n", gameWeek, db.teams[teamA], db.teams[teamB], expectedResult, input.x);
                if (result <= 0.000001) hits++;
                //if (result < 1E-10) result = 0.0;
                sum += result;
            }
            teamA=teamB;
            //for (teamA=1;teamA<21;teamA++)
            for (teamB=1;teamB<21;teamB++)
            {
                expectedResult = db.vsdata[teamA][teamB];//currentX*currentX*currentY + currentX*currentY + currentY;
                gameWeek = db.gwdata[teamA][teamB];
                if (expectedResult == Double.POSITIVE_INFINITY)
                    continue;

                ((GPIndividual)ind).trees[0].child.eval(
                    state,threadnum,input,stack,((GPIndividual)ind),this);

                result = Math.abs(expectedResult - input.x);

                //System.out.format("GW%d teamA: %s teamB: %s (%.2f) %.4f\n", gameWeek, db.teams[teamA], db.teams[teamB], expectedResult, input.x);
                if (result <= 0.000001) hits++;
                //if (result < 1E-10) result = 0.0;
                sum += result;
            }*/
            for (gameWeek=1;gameWeek<10;gameWeek++)
            {
                expectedResult = db.twdata[gameWeek][teamA];//currentX*currentX*currentY + currentX*currentY + currentY;
                teamB = db.gwdata[gameWeek][teamA];
                 ((GPIndividual)ind).trees[0].child.eval(
                    state,threadnum,input,stack,((GPIndividual)ind),this);

                //System.out.format("GW%d teamA: %s teamB: %s (%.2f) %.4f\n", gameWeek, db.teams[teamA], db.teams[teamB], expectedResult, input.x);
                result = Math.abs(expectedResult - input.x);

                if (result <= 0.001) hits++;
                //if (result < 1E-10) result = 0.0;
                sum += result;
            }

            //sum /= 40;
            // the fitness better be KozaFitness!
            KozaFitness f = ((KozaFitness)ind.fitness);
            f.setStandardizedFitness(state,(float)sum);
            //f.hits = hits;
            //System.out.println("fitness: "+f.fitness());
            //ind.evaluated = true;
            }
        }
    }

