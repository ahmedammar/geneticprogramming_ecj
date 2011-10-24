package ec.app.epl2012_basic;
import java.sql.*;

public class SqliteDB
{
    public PLData[][] sumdata = new PLData[21][10];
    public double[][] vsdata = new double[21][21];
    public int[][] gwdata = new int[21][21];

    public void init()
    {
       try 
       {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:epldata2012.db");
            Statement s = c.createStatement();

            // fill sumdata
            ResultSet r = s.executeQuery("select * from teamweeksum");
            while (r.next()) {
                PLData current = new PLData();
                int tid = r.getInt("teamid");
                int gw = r.getInt("gameweek");
                current.home = r.getInt("home");
                current.gf = r.getInt("goalsfor");
                current.ga = r.getInt("goalsagainst");
                current.w = r.getInt("w");
                current.l = r.getInt("l");
                current.d = r.getInt("d");
                current.morale = r.getInt("morale");
                sumdata[tid][gw] = current;
            }

            // fill vsdata
            for (int i=0; i<21; i++)
                for (int j=0; j<21; j++)
                    vsdata[i][j] = Double.POSITIVE_INFINITY;
            r = s.executeQuery("select * from games");
            while (r.next()) {
                double result = Double.POSITIVE_INFINITY;
                int hid = r.getInt("homeid");
                int aid = r.getInt("awayid");
                int gf = r.getInt("goalsfor");
                int ga = r.getInt("goalsagainst");
                if(gf > ga)
                    result = 1.0d;
                if(ga > gf)
                    result = 0.0d;
                if(ga == gf)
                    result = 0.5d;
                vsdata[hid][aid] = result;
            }

            // fill gwdata
            for (int i=0; i<21; i++)
                for (int j=0; j<21; j++)
                    gwdata[i][j] = 0;
            r = s.executeQuery("select * from games");
            while (r.next()) {
                int hid = r.getInt("homeid");
                int aid = r.getInt("awayid");
                int gw = r.getInt("gameweek");
                gwdata[hid][aid] = gw;
            }
       }
       catch (Exception e)
       {
           System.out.println(e.getMessage());
       }
    }
}
