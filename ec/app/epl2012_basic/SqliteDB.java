package ec.app.epl2012_basic;
import java.sql.*;

public class SqliteDB
{
    public int T = 20 + 1;
    public int W = 18 + 1;
    public PLData[][] sumdata = new PLData[T][W];
    public double[][] twdata = new double[W][T];
    public double[][] vsdata = new double[T][T];
    public int[][] gwdata = new int[W][T];
    public String[] teams = new String[T];
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
            for (int i=0; i<T; i++)
                for (int j=0; j<T; j++)
                    vsdata[i][j] = Double.POSITIVE_INFINITY;
            r = s.executeQuery("select * from games");
            while (r.next()) {
                double result = Double.POSITIVE_INFINITY;
                int hid = r.getInt("homeid");
                int aid = r.getInt("awayid");
                int gf = r.getInt("goalsfor");
                int ga = r.getInt("goalsagainst");
                if(gf > ga)
                    result = 3.0d;
                if(ga > gf)
                    result = -3.0d;
                if(ga == gf)
                    result = 1.0d;
                vsdata[hid][aid] = result;
            }

            // fill gwdata
            for (int i=0; i<W; i++)
                for (int j=0; j<T; j++)
                    gwdata[i][j] = 0;
            r = s.executeQuery("select * from games");
            while (r.next()) {
                int hid = r.getInt("homeid");
                int aid = r.getInt("awayid");
                int gw = r.getInt("gameweek");
                gwdata[gw][hid] = aid;
                gwdata[gw][aid] = hid;
            }

            // fill names
            r = s.executeQuery("select * from teams");
            while (r.next()) {
                int tid = r.getInt("id");
                teams[tid] = r.getString("name");
            }

            // fill twdata
            for (int i=0; i<W; i++)
                for (int j=0; j<T; j++)
                    twdata[i][j] = Double.POSITIVE_INFINITY;

            r = s.executeQuery("select * from teamweek");
            while (r.next()) {
                double result = Double.POSITIVE_INFINITY;
                int gw = r.getInt("gameweek");
                int tid = r.getInt("teamid");
                int gf = r.getInt("goalsfor");
                int ga = r.getInt("goalsagainst");
                if(gf > ga)
                    result = 1.0d;
                if(ga > gf)
                    result = -1.0d;
                if(ga == gf)
                    result = 0.0d;
                twdata[gw][tid] = result;
            }


       }
       catch (Exception e)
       {
           System.out.println(e.getMessage());
       }
    }
}
