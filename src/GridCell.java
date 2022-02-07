import java.util.ArrayList;

public class GridCell {
    double p;
    GridCell[] edge=new GridCell[4];
    int numOfEdge,numOfCorner;
    GridCell[] corner=new GridCell[4];
    int hasObstacle;

    public GridCell() {
        hasObstacle=0;
        p=0;
        numOfEdge=0;
        numOfCorner=0;
    }

    void addEdge(GridCell g){
        edge[numOfEdge]=g;
        numOfEdge++;
    }

    void addCorner(GridCell g){
        corner[numOfCorner]=g;
        numOfCorner++;
    }
}
