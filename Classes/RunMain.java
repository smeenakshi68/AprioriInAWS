package classes;

import java.io.IOException;
import java.util.StringTokenizer;

public class RunMain {
	
	public void run(String[] args)throws IOException
	{
		String[] values = new String[3];
		for(int i=0;i<args.length;i++)
		{
		StringTokenizer st = new StringTokenizer(args[i], " ");
        StringBuffer sb = new StringBuffer();
        while(st.hasMoreElements()){
            sb.append(st.nextElement()).append(" ");
        }
        values[i] = sb.toString().trim();
		}
		//System.out.println("uuid is :"+values[0]+":"+values[1]+":"+values[2]+":");
		new Apriori(values[0],values[1],values[2]);
	}
	public static void main(String args[])throws IOException
	{
		String[] arg = {"0.17","0.52","45ba16691cfb448ebf30a4f803a804c5.txt"}; 
		new RunMain().run(arg);
	}
}
