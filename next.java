public class next
{
    public static double IF(boolean tst, double left, double right)
    {
        if(tst)
            return left;
        else
            return right;
    }

    public static double IF(double tst, double left, double right)
    {
        if(tst != 0.0)
            return left;
        else
            return right;
    }

    public static void main(String[] args) {
        double ha, hb, gfa, gfb, gaa, gab, wa, wb, la, lb, da, db, ma, mb;
        // 1
        /*ha=1.000000; gfa=10.000000; gaa=20.000000; wa=1.000000; la=6.000000; da=2.000000; ma=-5.000000;
        hb=0.000000; gfb=15.000000; gab=13.000000; wb=5.000000; lb=2.000000; db=1.000000; mb=3.000000;*/
        // 17
        /*ha=0.000000; gfa=33.000000; gaa=7.000000; wa=8.000000; la=0.000000; da=1.000000; ma=8.000000;
        hb=1.000000; gfb=26.000000; gab=12.000000; wb=6.000000; lb=1.000000; db=2.000000; mb=5.000000;*/
        // 16
        /*ha=1.000000; gfa=26.000000; gaa=12.000000; wa=6.000000; la=1.000000; da=2.000000; ma=5.000000;
        hb=0.000000; gfb=33.000000; gab=7.000000; wb=8.000000; lb=0.000000; db=1.000000; mb=8.000000; */
        // 20
        /*ha=0.000000; gfa=10.000000; gaa=12.000000; wa=3.000000; la=4.000000; da=1.000000; ma=-1.000000;
        hb=1.000000; gfb=11.000000; gab=12.000000; wb=1.000000; lb=4.000000; db=4.000000; mb=-3.000000;*/
        // 3
        /*ha=1.000000; gfa=11.000000; gaa=12.000000; wa=1.000000; la=4.000000; da=4.000000; ma=-3.000000;
        hb=0.000000; gfb=10.000000; gab=12.000000; wb=3.000000; lb=4.000000; db=1.000000; mb=-1.000000;*/
        //Best generation
        // 14
        /*ha=0.000000; gfa=20.000000; gaa=10.000000; wa=6.000000; la=2.000000; da=1.000000; ma=4.000000;
        hb=1.000000; gfb=7.000000; gab=14.000000; wb=3.000000; lb=3.000000; db=3.000000; mb=0.000000;*/
        // 7
        /*ha=1.000000; gfa=7.000000; gaa=14.000000; wa=3.000000; la=3.000000; da=3.000000; ma=0.000000;
        hb=0.000000; gfb=20.000000; gab=10.000000; wb=6.000000; lb=2.000000; db=1.000000; mb=4.000000;*/
        // 6
        /*ha=0.000000; gfa=12.000000; gaa=10.000000; wa=2.000000; la=4.000000; da=3.000000; ma=-2.000000;
        hb=1.000000; gfb=12.000000; gab=24.000000; wb=2.000000; lb=7.000000; db=0.000000; mb=-5.000000;*/
        // 8 
        /*ha=1.000000; gfa=12.000000; gaa=24.000000; wa=2.000000; la=7.000000; da=0.000000; ma=-5.000000;
        hb=0.000000; gfb=12.000000; gab=10.000000; wb=2.000000; lb=4.000000; db=3.000000; mb=-2.000000;*/
        // 19
        /*ha=0.000000; gfa=15.000000; gaa=13.000000; wa=5.000000; la=2.000000; da=1.000000; ma=3.000000;
        hb=1.000000; gfb=10.000000; gab=20.000000; wb=1.000000; lb=6.000000; db=2.000000; mb=-5.000000;*/
        // 12
        /*ha=1.000000; gfa=15.000000; gaa=18.000000; wa=4.000000; la=4.000000; da=1.000000; ma=0.000000;
        hb=0.000000; gfb=7.000000; gab=11.000000; wb=3.000000; lb=3.000000; db=3.000000; mb=0.000000;*/
        // 13
        /*ha=0.000000; gfa=7.000000; gaa=11.000000; wa=3.000000; la=3.000000; da=3.000000; ma=0.000000;
        hb=1.000000; gfb=15.000000; gab=18.000000; wb=4.000000; lb=4.000000; db=1.000000; mb=0.000000;*/
        // 11
        /*ha=1.000000; gfa=12.000000; gaa=6.000000; wa=5.000000; la=0.000000; da=4.000000; ma=5.000000;
        hb=0.000000; gfb=6.000000; gab=15.000000; wb=1.000000; lb=6.000000; db=2.000000; mb=-5.000000;*/
        // 9
        /*ha=0.000000; gfa=6.000000; gaa=15.000000; wa=1.000000; la=6.000000; da=2.000000; ma=-5.000000;
        hb=1.000000; gfb=12.000000; gab=6.000000; wb=5.000000; lb=0.000000; db=4.000000; mb=5.000000;*/
        // 18
        /*ha=0.000000; gfa=9.000000; gaa=14.000000; wa=2.000000; la=4.000000; da=3.000000; ma=-2.000000;
        hb=1.000000; gfb=8.000000; gab=14.000000; wb=2.000000; lb=5.000000; db=2.000000; mb=-3.000000;*/
        // 2
        ha=1.000000; gfa=8.000000; gaa=14.000000; wa=2.000000; la=5.000000; da=2.000000; ma=-3.000000;
        hb=0.000000; gfb=9.000000; gab=14.000000; wb=2.000000; lb=4.000000; db=3.000000; mb=-2.000000;
        double result =
            // 8 (Bolton)
            /*IF((IF(hb, da, da) + (((IF(hb, da, da) + (0.0 - 1.0)) * (ma + db)) - ((la * gfa) - (lb / wa)))) > la, (wb + 1.0) - wb, 0.0 - 1.0)*/
            // 6 (Sunderland)
            //IF((gfa + lb) > (da + wb), IF((db * IF((0.0 * gfb) > IF(0.0 > gfb, IF(hb, db, db), gaa * mb), gfa * mb, (ma * wa) - (da + wb))) > (0.0 - mb), IF((0.0 * (ma / lb)) > gfb, wa + gfa, wb / da), IF((db + da) > (wb - ma), gfb - gaa, IF(da > 0.0, (0.0 - wa) / IF(hb, wa, db / 0.0), ((1.0 / mb) - (wa / la)) - IF(hb, wb, wa - ma)))), (ma * lb) - (gfa * 1.0))
            // 7 (QPR)
            //wa / IF(wb > 0.0, IF(hb, db, ((lb * ma) + 0.0) * ((1.0 + ma) / IF(hb, da, gfa))), ma - wa)
            // 14 (Chelsea)
            /*IF(la > gaa, lb, 0.0) - IF(gaa > mb, 0.0 - 1.0, IF(da > gfb, IF(hb, gaa, mb), 1.0))*/
            // 3 (Fulham)
            /*IF(((la / gfb) * (la - ((db + 0.0) + (0.0 / lb)))) > ((gab + gab) / (db / mb)), IF(hb, wa, wa), IF(IF(mb > ma, IF(hb, ma, IF(db > da, gfb * wa, ma + (lb / db)) / ((mb / db) * ((1.0 + ma) - IF(hb, 0.0, wb)))), db * gfa) > (gfa + ma), IF(hb, wa, db) - (wa - wa), IF(hb, ma, 1.0) - (mb * gab)))*/
            // 20 (Everton)
            /*IF(wa > (lb + ma), (((db - wa) - IF(gfa > lb, IF(gfa > lb, la + wb, 0.0 + db), IF(gfa > lb, la + wb, wb / wb))) + IF(IF(hb, gaa, lb) > (da - mb), IF(gfa > lb, la + wb, ma) * db, 1.0 + wa)) + ((lb / gfb) * (db / 0.0)), IF(gab > gfb, wa / (db - wb), 0.0 - 1.0))*/
            // 16 (Man Utd)
            /* IF((((wb / (1.0 + wb)) * 1.0) + lb) > mb, 1.0, IF(hb, 0.0, 1.0)) */
            // 17 (Man City)
            /* ((da - 0.0) - (0.0 - 0.0)) / IF(wb > 0.0, 1.0, gfb * db) */
            //1 (Blackburn)
            /*IF((gfb + wa) > (gfb + wa), (lb * db) / (da * gab), 0.0 * da) - ((IF(hb, gfb, 1.0) * (da - wa)) / (IF(da > ma, lb * db, 1.0 - wb) / (db + (lb - 1.0))))*/
            // 19 (Tottenham)
            //IF((db / wa) > (0.0 * gaa), lb / lb, IF(hb, gab, ma))
            // 12 (Arsenal)
            /* IF(hb, ma, IF(wa > ma, IF(hb, wa, 1.0), mb - gab)) */
            // 13 (Stoke)
            /*IF(IF(IF(gfa > db, 1.0, wa) > mb, 1.0 - db, 0.0 + gfa) > ((gfb - la) + (wb / gfa)), ((db / gaa) * (wa / db)) + ((mb + db) * (0.0 * da)), IF(IF(hb, ma, mb) > IF(hb, 0.0, gab), IF((gaa / lb) > (lb + wb), (db - la) / (wb - db), IF(la > mb, ma, gfa * 0.0)), IF(IF(ma > la, ma - 0.0, wb * wa) > lb, (db - la) / (wb - db), IF(la > mb, 1.0 - ma, gfa * 0.0))))*/
            // 11 (Newcastle)
            // IF(wb > lb, la / ma, db / db)
            // 9 (Wigan)
            // IF(IF(IF(hb, gab, da) > (wb + la), (wb - gfb) - (db - da), (gab * ma) - (mb / ma)) > ((da - 1.0) - 1.0), (((la / mb) / (lb + gab)) / (IF(hb, wa, lb) / (0.0 - 1.0))) + (((lb + mb) - (lb * 0.0)) - (db - da)), IF(((mb + gfb) + (gaa * gaa)) > ((wb / wb) / (wb / wa)), ((0.0 * mb) * (gab - ma)) - 1.0, ((wb - gab) + (ma / gab)) / ((ma + db) + (1.0 + ma))))
            // 18 (Swansea)
            // IF(gfb > 1.0, IF(hb, ma, 1.0), gab - gab)
            // 2 (Wolves)
((gab * 0.0) / ((mb - gfb) - (da + 1.0))) - IF((mb + gab) > (IF(0.0 > 1.0, (la + (gab * 0.0)) / ma, wa - gfb) / (gfb * 1.0)), (da / da) * 1.0, 0.0 - gaa)
            ;
        System.out.format("Result: %f\n", result);
    }
}
