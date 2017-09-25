package com.welcome;
import java.util.Scanner;
import java.util.Random;
import java.lang.*;
import java.lang.System;
import java.lang.String;
import java.util.Scanner;
import java.io .*;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

class MatrixDG {

    String[] mVexs;       // 
    int[][] mMatrix;    // 
    MatrixDG(int v)	//v is the number of vertexes
    {
    	mMatrix=new int[v+1][v+1];
    	for (int i=0;i<=v;i++)
    	{
    		for(int j=0;j<=v;j++)
    		{
    			mMatrix[i][j]=0;
    		}
    	}
    }
    int vlen=0;
    
    
}
public class welcome extends JFrame{
	
	private static final long serialVersionUID = -2707712944901661771L;

	public welcome(MatrixDG m)
	{
		super("GRAPH!");

		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try
		{

			Object v[]=new Object[100];
			//inserting vertexes:
			int f=-1;
			double k=1.3;
			for (int i=0;i<m.vlen;i++)
			{
				String ns=m.mVexs[i].substring(m.mVexs[i].length()-1);
				//System.out.print(ns+"\n");
				if (ns.equals("*")==false)
				{
					v[i]=graph.insertVertex(parent, null, m.mVexs[i], 500+Math.sqrt(i)*f, 20*i,80, 30,"shape=ellipse;perimeter=ellipsePerimeter");
				
				}
				else
				{
					v[i]=graph.insertVertex(parent, null, m.mVexs[i], 500+Math.sqrt(i)*f, 20*i,80, 30,"shape=ellipse;strokeColor=red;fillColor=green");
				}
				f=-f;
				k=k+0.05;
			}
			//inserting edges:
			for (int i=0;i<m.vlen;i++)
			{
				for (int j=0;j<m.vlen;j++)
				{
					if (m.mMatrix[i][j]!=999)
					{
						if (m.mVexs[i].substring(m.mVexs[i].length()-1).equals("*")==true &&
								m.mVexs[j].substring(m.mVexs[j].length()-1).equals("*")==true	&& 
								m.mVexs[i].substring(m.mVexs[i].length()-2).equals("**")==false)
						{
							graph.insertEdge(parent, null, m.mMatrix[i][j], v[i], v[j], "strokeColor=lightgreen");
						}
						else
						{
							graph.insertEdge(parent, null, m.mMatrix[i][j], v[i], v[j]);
						}
					}
				}
			}
			
			
			
			//graph.insertEdge(parent, null, "Edge", v1, v2);
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}

	public static void main(String[] args) 		//main
	{
		
		String filename=null;
		MatrixDG mtx;
		//mtx=createDirectedGraph( filename);		//creating graph
		//String s=" 2 \n";
		//s=calcShortestPath(mtx,"to","and");
		//showDirectedGraph(mtx);					//showing graph
		//s=randomWalk(mtx);
		//showDirectedGraph(mtx);
	//	System.out.print(s);
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Input the File:");
		filename=sc.nextLine();
		mtx=createDirectedGraph(filename);	
		
		//show g
		
		
		String choice="0";
		

		System.out.println("CHOICES: 1:show the Graph.  2:search bridge words.  3:build new text.  4:shortest path.  5:random walk\n");

		choice=sc.nextLine();
		if(choice.equals("1")){
			showDirectedGraph(mtx);
			System.out.println("Nodes:\n");
			for (int x=0;x<mtx.vlen;x++)
			{
				System.out.print(mtx.mVexs[x]+" ");
			}
			
		}
		else if(choice.equals("2")){
			String[] strl = new String[mtx.mVexs.length];
	        String word1 = null;
	        String word2 = null;
	        System.out.print("The first word:");
	        word1 = sc.nextLine();
	        System.out.print("The second word:");
	        word2 = sc.nextLine();
	        int flag1=0;
	        int flag2=0;
	        String multi="";
	        for(int i=0;i<mtx.mVexs.length;i++){
	        	if(word1.equals(mtx.mVexs[i]))
	        		flag1=1;
	        	if(word2.equals(mtx.mVexs[i]))
	        		flag2=1;
	        }
	        if(flag1==0&&flag2==0)
	        	System.out.println("No \"" + word1 + "\" and \"" + word2 + "\" in the Graph!");
	        else if(flag1==0&&flag2!=0)
	        	System.out.println("No \"" + word1 + "\" in the Graph!");
	        else if(flag2==0&&flag1!=0)
	        	System.out.println("No \"" +  word2 + "\" in the Graph!");
	        else {
	        	
	        	String reword = queryBridgeWords(mtx, word1, word2);
	            strl = reword.split("\\s+");
	            if (strl.length == 0) {
	                System.out.println("No bridge words from \"" + word1 + "\" to\"" + word2 + "\" !");
	            } 
	            else if(strl.length == 2){
	                System.out.println("The bridge words from \"" + word1 + "\" to\"" + word2 + "\" is:" + reword.replaceAll("\\s+",""));
	            }
	            else{
	            	for(int i=1;i<strl.length-1;i++){
	            		multi+=strl[i]+", ";
	            	}
	            	multi+="and "+strl[strl.length-1]+".";
	            	System.out.println("The bridge words from \"" + word1 + "\" to\"" + word2 + "\" are:" + multi);
	            	
	            }
	 
	        }
	        System.out.println("over!");
			
		}
		else if(choice.equals("3")){
			//bridge Return
	        String newstr = null;
	        System.out.print("Input the new String:");
	        newstr = sc.nextLine();
	        String bridgeStr = generateNewText(mtx, newstr);
	        System.out.println(bridgeStr);
	        System.out.println("over!");
		}
		else if(choice.equals("4")){
			System.out.print("input the starting point and the terminal");
	        System.out.print("The starting point:");
	        String word1="",word2="",s="";
	        word1 = sc.nextLine();
	        System.out.print("The terminal:");
	        word2 = sc.nextLine();
			s=calcShortestPath(mtx,word1,word2);
			showDirectedGraph(mtx);
			System.out.println("The shortest path is:"+s);
			
		}
		else if(choice.equals("5")){
			//System.out.println("over!");
			randomWalk(mtx);
		}
		
		
		
	}	//main ends

	
	//functions:
	
	private static String randomWalk(MatrixDG g)
	{
		String s="";
		Random random = new Random();
		int r = random.nextInt(g.vlen-1); //the starting vertex
		s=s+g.mVexs[r];
		System.out.print("random walk starts. input 's' to stop:\n"+g.mVexs[r]);
		int next=r,cch=0,ch[];
		ch=new int[g.vlen];
		int rpt[]=new int[g.vlen];
		for (int i=0;i<g.vlen;i++)
		{
			ch[i]=0;
			rpt[i]=-1;
		}
		Scanner sc= new Scanner(System.in);
		int cc=1;
		rpt[0]=r;
		while (next!=-1)
		{
			
			cch=0;
			//System.out.print(next);
			String st = sc.nextLine();
			//System.out.print("1"+st+"1");
			if (st.equals("s"))
			{
				System.out.print("STOP");
				return s;
			}
			for (int i=0;i<g.vlen;i++ )
			{
				if (g.mMatrix[next][i]!=999)
				{
					ch[cch]=i;
					cch++;
				}
			}
			if (cch==0)
			{
				System.out.print("over. no where to go\n");
				return s;
			}
			int c = random.nextInt(cch);
			for (int x=0;x<g.vlen;x++)
			{
				if (rpt[x]==ch[c])
				{
					System.out.print("edge repeated, quit\n");
					s=s+" -> "+g.mVexs[ch[c]];
					return s;
				}
			}
			next=ch[c];
			rpt[cc]=next;
			cc++;
			s=s+" -> "+g.mVexs[next];
			
			System.out.print(" -> "+g.mVexs[next]);
		}
		sc.close();
		return s;
	}
	
	private static String calcShortestPath(MatrixDG g, String word1, String word2)
	{
		// in the algorithm, the number 999 means "false"
		for (int i=0;i<g.vlen;i++)
		{
			for (int j=0;j<g.vlen;j++)
			{
				if (g.mMatrix[i][j]==0)
				{
					g.mMatrix[i][j]=999;
				}
			}
		}
		String s="";
		int w1,w2;
		w1=getposition(word1,g.mVexs);
		w2=getposition(word2,g.mVexs);
		//System.out.print(w1+" "+w2);
		int d[]=new int[g.vlen];
		int p[]=new int[g.vlen];
		int f[]=new int[g.vlen];
		
		for (int i=0;i<g.vlen;i++)
		{
			if (g.mMatrix[w1][i]!=999)
				{
					d[i]=g.mMatrix[w1][i];
					p[i]=w1;
				}
			else
				{
					d[i]=999; //infinite long, not reached yet
					p[i]=999; //no parent node
				}
			
			f[i]=0;
		}
		d[w1]=0;
		f[w1]=1;
		int min=999;
		int m=999;
		for (int i=0;i<g.vlen-1;i++)	//dijkstra
		{
			min=999;
			for (int j=0;j<g.vlen;j++)
			{
				if (f[j]==0)
				{
					if (d[j]<min)
					{
						m=j;
						min=d[j];
					}
				}
			}
			//System.out.print(min+"\n");
			if (min<999)
			{
				f[m]=1;
			}
			else if (m==999)
			{
				System.out.print("not reachable\n");
				return "NULL";
			}
			for (int j=0;j<g.vlen;j++)
			{
				if (f[j]==0 && g.mMatrix[m][j]+d[m]<d[j])
				{
					d[j]=g.mMatrix[m][j]+d[m];
					p[j]=m;
				}
			}
		}//end dijkstra
		int i=w2,c=0;
		String path[]=new String[100];
		for (int j=0;j<100;j++)
			path[j]="";
		while (i!=w1)
		{
			path[c]=g.mVexs[i];
			g.mVexs[i]=g.mVexs[i]+" *";
			c++;
			i=p[i];
		}
		g.mVexs[w2]=g.mVexs[w2]+"*";
		path[c+1]=g.mVexs[w1];
		g.mVexs[w1]=g.mVexs[w1]+" *";
		for (int j=99;j>=0;j--)
		{
			if (path[j]!="" && j!=0)
			{
				s=s+path[j]+" -> ";
				//System.out.print(path[j]+" -> ");
			}
			if (j==0)
			{
				s=s+path[j];
				//System.out.print(path[j]);
			}
		}
		
		return s;
	}
	
	
	private static void showDirectedGraph(MatrixDG G)
	{
		welcome frame = new welcome(G);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}
	
	
	private static int getposition(String s, String[] sw)		//get the position of s in array sw
	{
		for (int i=0;i<sw.length;i++){
			if(s.equals(sw[i])) return i;
		}
		return -1;
	}
				
	private static MatrixDG createDirectedGraph(String filename)	//createDirectedGraph	createDirectedGraph
	{
		String s;
		String s2="";
		try			//reading file
		{
			FileReader inFile = new FileReader(filename);
			BufferedReader buff = new BufferedReader(inFile);
			boolean endOfFile=false;
			while (!endOfFile)
			{
				s=buff.readLine();	//reading line by line to s
				if (s==null)
				{
					endOfFile=true;
				}
				else
				{
					s2=s2+s+" ";
				}
			}
			buff.close();
		}
		catch (IOException e){
			System.out.print("An Error Occurred : "+e.toString());
		}
		//reading done; converting begins (s2 to s3) 
		String s3="";
		int f=0;
		s2=s2.toLowerCase();// to lower case
		for(int i = 0; i<s2.length()-1;i++)
		{
			char c=s2.charAt(i);
			int b = (int)c;
			if(97<=b && b<=122)
			{
				s3=s3+c;
				f=0;
			}
			else if (b==32)
			{
				if (f==0)
					{
						s3=s3+' ';
						f=1;
					}
			}
			else if (b==33 || b==34 || b==39 || b==40 || b==41 || b==44 || b==45 || b==46 || b==58 || b==59 || b==63 )
			{
				if (f==0)
					{
						s3=s3+' ';
						f=1;
					}
			}
		}
		//converting to one space
		//String s4; //s4 has only one space between each words
		
		// extracting words
		String[] words = new String[100]; //all different
		String[] words1 = new String[100];	//all of the words; some are the same
		for(int i=0;i<100;i++){ //initializing 
			words1[i]="";
			words[i]="";
		}
		int i=0;
		int j=0;
		while(i<s3.length())
		{
			char c=s3.charAt(i);
			if (c!=' ')
			{
				words1[j]=words1[j]+c;
				i++;
			}
			else{
				i++;
				j++;
			}
		}

		i=0;
		j=0;
		while(i<s3.length())
		{
			char c=s3.charAt(i);
			finder:
			if (c!=' ')
			{
				words[j]=words[j]+c;
				i++;
			}
			else
			{
				if(j>0){
					
					for(int k=0;k<j;k++){
						if(words[k].equals(words[j])){
							i++;
							words[j]=""; 
							break finder;
						}
					}
					
				}
				i++;
				j++;
			
			}
		}
		for(int k=0;k<words.length;k++){
			if(words[k].equals(words[words.length-1])){
				i++;
				words[j]=""; 
				//break finder;
			}
		}
		
		
		for (i=0;i<20;i++){
			if (words[i]!="")
				{
					System.out.print(words[i]+"\n");
				}
		}

	//DAG
		int cw=0,cw2=0;  //cw is the number of differentwords	;	cw2 is number of all words
		for (int t=0;t<words.length;t++)
		{
			if (words[t]!="")
			{
				cw++;
			}
			else
				break;
		}
		for (int t=0;t<words.length;t++)
		{
			if (words1[t]!="")
			{
				cw2++;
			}
			else
				break;
		}
		int vlen = cw;
		int elen=0;
		for(i=0;i<s3.length();i++)
		{
			char c1=s3.charAt(i);
			if(c1==' ')
			{
				elen+=1;
			}
			
		}
		String[] vexs= new String[100];
		for(i=0;i<words.length;i++){
		vexs[i]=words[i];
		}
		
		MatrixDG aMATRIX;		//creating matrix 
		aMATRIX=new MatrixDG(vlen);
		aMATRIX.vlen=cw;
		aMATRIX.mVexs= new String[vlen];
        for (int m = 0; m < aMATRIX.mVexs.length; m++)		// initializing vertexes
        	{
        		aMATRIX.mVexs[m] = words[m];
        		//System.out.print("!:  "+aMATRIX.mVexs[m]+"\n");
        	}
        aMATRIX.mMatrix = new int[vlen][vlen];
        int p1,p2;
       
        for (i = 0; i < cw2; i++) {
            p1=getposition(words1[i],words);
            p2=getposition(words1[i+1],words);
           // System.out.print(p1+"  "+p2+"\n");
            if (words1[i]!="" && words1[i+1]!="")
            {
            		aMATRIX.mMatrix[p1][p2]++;
            }
        }
        for (int x=0;x<aMATRIX.vlen;x++)
        {
        	for (int xx=0;xx<aMATRIX.vlen;xx++)
            {
            	if (aMATRIX.mMatrix[x][xx]==0)
            	{
            		aMATRIX.mMatrix[x][xx]=999;
            	}
            }
        }
        
        
        return aMATRIX;
	}
				
	private static String queryBridgeWords(MatrixDG G, String word1, String word2)//鏌ヨ妗ユ帴璇�
    {
        String s = "";
        int i, i1;
        int j, k, f;
        
        for(int m=0;m<G.mVexs.length;m++){
        	for(int n=0;n<G.mVexs.length;n++){
        		if(G.mMatrix[m][n]!=999)
        		{
        			if (G.mMatrix[m][n]>1)
        			{
        				G.mMatrix[m][n]=1;
        			}
        		}
        			
        	}
        }
        String[] strlist = new String[G.mVexs.length];
        for (i = 0; i < G.mVexs.length; i++) {
            strlist[i] = "";
        }
        int[] list = new int[G.mVexs.length];
        for (i = 0; i < G.mVexs.length; i++) {
            if (G.mVexs[i].equals(word1)) {
                for (i1 = 0; i1 < G.mVexs.length; i1++) {
                    if (G.mMatrix[i][i1] == 1) {
                        list[i1] = 1;
                    } else list[i1] = 0;
                }

            }

        }
        for (j = 0; j < list.length; j++) {
            if (list[j] == 1) {
                for (k = 0; k < G.mVexs.length; k++) {
                    if (G.mMatrix[j][k] == 1 && G.mVexs[k].equals(word2)) {
                        strlist[j] = G.mVexs[j];
                    }
                }
            }
        }
        for (f = 0; f < G.mVexs.length; f++) {
            s += " " + strlist[f];
        }

        return s;
    }
	
	private static String generateNewText(MatrixDG G, String inputText) {//
        String s = "";
        String[] strlist = inputText.split(" ");
        String[] insert = new String[strlist.length - 1];
        String[] choicestr = new String[insert.length];
        
        for (int i = 0; i < strlist.length - 1; i++) {
            insert[i] = queryBridgeWords(G, strlist[i], strlist[i + 1]);
        }

        // choicestr 
        for (int j = 0; j < insert.length; j++) {
            insert[j] = insert[j].replaceAll("\\s+", " ");
            if (!insert[j].equals(" ")) {
                // 
                String[] splitTemp = insert[j].split("\\s+");
                // 
                if (splitTemp[0].equals("")) {
                	Random random = new Random();
                	int result=random.nextInt(splitTemp.length-1)+1;
                    insert[j] = splitTemp[result];
                } else {
                    insert[j] = splitTemp[0];
                }
            }
            choicestr[j] = insert[j];
        }

        for (int i = 0; i < choicestr.length; i++) {
            // 
            if (choicestr[i].equals(" ")) {
                s += strlist[i] + " ";
            }
            //
            else
            {
                s += strlist[i] + " " + choicestr[i] + " ";
            }
        }
        s += strlist[strlist.length - 1];
        s = s.replaceAll("\\s+", " ");
        return s;
    }

}
	
	



