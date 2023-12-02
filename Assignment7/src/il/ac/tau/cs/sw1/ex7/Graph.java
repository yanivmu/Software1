package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge{
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    @Override
    public Iterator<Edge> selection() {

        int find = 0;
        Edge tmp, toReplace;
        int index = 0;
        int size = this.lst.size();

        for (int k = 0; k < size - 1; k++) {
            toReplace = this.lst.get(k);
            find = 0;

            for (int j = k + 1; j < size; j++) {
                if (this.lst.get(j).weight < toReplace.weight) {
                    toReplace = this.lst.get(j);
                    index = j;
                    find = 1;
                }
            }
            if (find == 1) {
                tmp = this.lst.get(k);
                this.lst.set(k, this.lst.get(index));
                this.lst.set(index, tmp);
            }
        }
        int wRange = 1;
        index = 0;
        find = 0;

        for (int k = 0; k < size - 1; k++) {
            if (this.lst.get(k).weight == this.lst.get(k + 1).weight) {
                wRange++;
            }
            if ((this.lst.get(k).weight != this.lst.get(k + 1).weight) || ((wRange > 1) && (k == size - 2))) {
                if (k == size -2){
                    k++;
                }

            for (int j = k - wRange + 1; j < k ; j++) {
                toReplace = this.lst.get(j);
                find = 0;
                for (int h = j + 1; h < k + 1; h++) {
                    if (this.lst.get(h).node1 < toReplace.node1) {
                        toReplace = this.lst.get(h);
                        index = h;
                        find = 1;
                    }
                }

                if (find == 1) {
                    tmp = this.lst.get(j);
                    this.lst.set(j, this.lst.get(index));
                    this.lst.set(index, tmp);
                }
            }
            wRange = 1;
        }
    }
        find = 0;
        int nod = 1;
        index = 0;
        for (int k = 0; k < size - 1; k++) {
            if (this.lst.get(k).node1 == this.lst.get(k + 1).node1) {
                nod++;
            }


            if ((this.lst.get(k).node1 != this.lst.get(k + 1).node1)
                    || ((nod > 1) && (k == size - 2))) {
                if (k == size - 2) {
                    k++;
                }

            for (int j = k - nod + 1; j < k; j++) {
                toReplace = this.lst.get(j);
                find = 0;
                for (int h = j + 1; h < k + 1; h++) {
                    if (this.lst.get(j).node2 < toReplace.node2) {
                        toReplace = this.lst.get(j);
                        index = j;
                        find = 1;
                    }
                }
                if (find == 1) {
                    tmp = this.lst.get(j);
                    this.lst.set(j, this.lst.get(index));
                    this.lst.set(index, tmp);
                }
            }
            nod++;
            }

        }

        return lst.iterator();
    }

    public ArrayList<Integer>[] EdgesMapping (List<Edge> candidates_lst){
        int nodesNum = this.n;
        ArrayList<Integer>[] EdgesMap = new ArrayList[this.n +1];

        for (int i = 0 ; i <= nodesNum; i++){
            EdgesMap[i] = new ArrayList<>();
        }
        for (int l = 0; l < candidates_lst.size(); l++){
            EdgesMap[candidates_lst.get(l).node2].add((candidates_lst.get(l).node1));
            EdgesMap[candidates_lst.get(l).node1].add((candidates_lst.get(l).node2));
        }
        return EdgesMap;

    }

    public boolean[] findPathRec (int node, ArrayList<Integer>[] EdgesMap , boolean[] visitMap){
        visitMap[node] = true;
        int neighbours = EdgesMap[node].size();

        for (int k = 0 ; k < neighbours; k++ ){
            int connectedNode = EdgesMap[node].get(k);

            if (visitMap[connectedNode] == false){
                findPathRec(connectedNode, EdgesMap, visitMap);
            }

        }
        return visitMap;
    }

    public  Boolean findPath (ArrayList<Integer>[] EdgesMap, Edge element){
        int nodesNum = this.n;
        boolean [] visitMap = new boolean[nodesNum +1];
        boolean [] n1Connections = findPathRec(element.node1, EdgesMap, visitMap);
        return n1Connections[element.node2];
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
        ArrayList<Integer>[] EdgesMap = EdgesMapping(candidates_lst);
        return (!findPath(EdgesMap,element));

    }


    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        int nodesNum = this.n +1 ;
        boolean[] visitMap = new boolean[nodesNum];
        ArrayList<Integer>[] EdgesMap = EdgesMapping(candidates_lst);
        findPathRec(0, EdgesMap, visitMap);

        for (int l = 0; l< nodesNum; l++ ){
            if (EdgesMap[l].size() == 0){
                return false;
            }
        }
        for (int k = 0 ; k < nodesNum ; k++ ){
            if (!visitMap[k]){
                return false;
            }
        }
        return true;

    }
}
