import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class EscapePlan {
	private List<FlowEdge>[] adj;
	private FlowEdge[] edgeTo;
	private boolean[] visiteded;
	private int value;
	private int vertexNum;
	private int poluted;
	@SuppressWarnings("unchecked")
	public EscapePlan(String fileName) {
		InputStream in;
		BufferedReader reader;
		String line;
		try {
			in = new FileInputStream(fileName);
			reader = new BufferedReader(new InputStreamReader(in));
			line = reader.readLine().trim();
			String[] tokens = line.split(" +");
			vertexNum = Integer.parseInt(tokens[0]) + 2;
			int edgeNum = Integer.parseInt(tokens[1]);
			poluted = Integer.parseInt(tokens[2]);
			adj = (List<FlowEdge>[]) new List[vertexNum];
			for(int i = 0; i<vertexNum; i++){
				adj[i] = new LinkedList<FlowEdge>();
			}
			line = reader.readLine().trim();
			tokens = line.split(" +");
			for(int i = 0; i < tokens.length; i++){
				int index = Integer.parseInt(tokens[i]) + 1;
				FlowEdge e = new FlowEdge(0, index);
				adj[0].add(e);
				adj[index].add(e);
			}
			line = reader.readLine().trim();
			tokens = line.split(" +");
			for(int i = 0; i < tokens.length; i++){
				int index = Integer.parseInt(tokens[i]) + 1;
				FlowEdge e = new FlowEdge(index, vertexNum - 1);
				adj[vertexNum - 1].add(e);
				adj[index].add(e);
			}
			int lineNum = 0;
			while ((lineNum < edgeNum && (line = reader.readLine()) != null)) {
					tokens = line.trim().split(" +");
					int v = Integer.parseInt(tokens[0]) +1;
					int w = Integer.parseInt(tokens[1]) +1;
					FlowEdge e = new FlowEdge(v, w);
					adj[v].add(e);
					adj[w].add(e);
					lineNum++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FordFulkerson(0, vertexNum - 1);
	}
	
	public void printResult(){
		String result = hasPlan()?"YES" : "NO";
		System.out.println(result);
	}
	
	public boolean hasPlan(){
		return value == poluted;
	}
	private boolean hasAugmentingPath(int s, int t){
		visiteded = new boolean[vertexNum];
		edgeTo = new FlowEdge[vertexNum];
		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(s);
		while(!q.isEmpty()){
			int v = q.poll();
			for(FlowEdge e : adj[v]){
				int w = e.other(v);
				if((w == vertexNum-1 || e.canConnectTo(w)) && !visiteded[w]){
					edgeTo[w] = e;
					visiteded[w] = true;
					q.offer(w);
				}
			}
		}
		return visiteded[t];
	}
	//FordFulkerson algorithms, hasAugmentingPath method and FlowEdge class is implemented in a concise way, 
	//so it only work for this escape problem only. 
	//it can not be used for general max flow problems;
	
	private void FordFulkerson(int s, int t){
		while(hasAugmentingPath(s, t)){
			for(int v = t; v!=s; v = edgeTo[v].other(v)){
				edgeTo[v].connectTo(v);
			}
			value++;
		}
	}
	
	
	public static void main(String[] args) {
		
		EscapePlan ep = new EscapePlan("input2.txt");
		ep.printResult();
	}

}
