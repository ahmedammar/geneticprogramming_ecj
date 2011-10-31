package ec.app.epl2012_basic;

public class PLData
{
    public double home;    // return value
    public double gf;
    public double ga;
    public double w;
    public double l;
    public double d;
    public double morale;

    public void dump(String t)
    {
        if(t == "a")
            home = 1.0;
        if(t == "b")
            home = 0.0;
        System.out.format("h%s=%f; gf%s=%f; ga%s=%f; w%s=%f; l%s=%f; d%s=%f; m%s=%f;\n",
                t, home, t, gf, t, ga, t, w, t, l, t, d, t, morale);
    }
}
