import java.io.*;
import java.io.File;

public class compute
{
    public static String getInd(File in)
        throws IOException {
        String out = "", line = "";
        BufferedReader reader = new BufferedReader(new FileReader(in));
        boolean bI = false;
        int count = 0;
        while ((line = reader.readLine()) != null)
        {
            if (bI)
                count++;
            if (count == 5)
                out = line;
            if (line.matches("Best Individual of Run:"))
                bI = true;
        }
        return out;
    }

    public static void replace(String oldstring, String newstring, File in, File out)
        throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(in));
        PrintWriter writer = new PrintWriter(new FileWriter(out));
        String line = null;
        while ((line = reader.readLine()) != null)
            writer.println(line.replaceAll(oldstring,newstring));

        // I'm aware of the potential for resource leaks here. Proper resource
        // handling has been omitted in the interest of brevity
        reader.close();
        writer.close();
    }

    public static void main(String[] args) {
        if (args.length < 2)
        {
            System.out.println("call: java compute HomeTeam AwayTeam");
            return;
        }
        int th = Integer.parseInt(args[0]);
        int ta = Integer.parseInt(args[1]);
        if (th == ta)
        {
            System.out.println("home team and away team cannot be the same");
            return;
        }
        try
        {
            float a = 0.0f;
            int i = 0;
            int l=0, w=0, d=0;
            for(i=0; i<50; i++)
            {
                Process run = Runtime.getRuntime().exec("java -Xmn100M -Xms500M -Xmx1500M -d64 ec.Evolve -file ec/app/epl2012_basic/eplbasic.params -p gp.tree.print-style=c -p eval.problem.teama="+th+" -p eval.problem.teamb="+ta);
                BufferedReader input = new BufferedReader(new InputStreamReader(run.getInputStream()));
                String line = "";
                String output = "";
                while ((line = input.readLine()) != null)
                {
                    output = output + line + "\n";
                }

                File in = new File("next.java.tmpl");
                File out = new File("next.java.1");
                File stat = new File("out.stat");
                replace("##STATS##", output, in, out);

                in = new File("next.java.1");
                out = new File("next.java");
                replace("##PRED##", getInd(stat), in, out);

                Runtime.getRuntime().exec("javac next.java");
                run = Runtime.getRuntime().exec("java next");
                input = new BufferedReader(new InputStreamReader(run.getInputStream()));
                line = input.readLine();
                if (line.matches("NaN"))
                {
                    i--;
                    continue;
                }
                float r = Float.parseFloat(line);
                /*if (r > 1.0)
                {
                    r = 1.0f;
                }
                if (r < -1.0)
                {
                    r = -1.0f;
                }*/
                if (r > 1.0 || r < -1.0)
                {
                    i--;
                    continue;
                }
                System.out.format("%.1f %.0f\n",r,r);
                String x = String.format("%.0f",r);
                int t = Integer.parseInt(x);
                //int t  = Math.round((int)r*10)/10;
                if(t == -1)
                    l++;
                if(t == 0)
                    d++;
                if(t == 1)
                    w++;
                a += r;
                System.out.format("%d Running Avg: %.2f (w:%.2f d:%.2f l:%.2f)\n",i+1, a/(i+1),(float)w/(i+1),(float)d/(i+1),(float)l/(i+1));
            }
            a /= i;
            System.out.format("Average: %.2f\n",a);
        }
        catch (Exception e)
        {
            System.out.println("Exception: "+e.getMessage());
        }
    }
}
