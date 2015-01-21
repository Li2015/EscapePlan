
public class FlowEdge {
	int v;
	int w;
	boolean vToW;
	public FlowEdge(int v, int w) {
		this.v = v;
		this.w = w;
		this.vToW = true;
	}
	int other(int vertex){
		if(vertex == v) return w;
		else if(vertex == w) return v;
		else return -1;
	}
	public boolean canConnectTo(int vertex){
		if(vertex == w) return vToW;
		else return !vToW;
	}
	public void connectTo(int vertex){
		if(vertex == v){
			vToW = true;
		}
		if(vertex == w){
			vToW = false;
		}
	}
}
