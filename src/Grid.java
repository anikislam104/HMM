import java.util.ArrayList;

public class Grid {
    int row;
    int col;
    int obstacles;
    GridCell[][] box;

    public Grid(int m,int n) {
        row=m;
        col=n;
        box=new GridCell[row][col];
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                box[i][j]=new GridCell();
            }
        }
    }

    void initialSetup()
    {
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                //add edge
                if(i-1>-1){
                    box[i][j].addEdge(box[i-1][j]);
                }
                if(j-1>-1){
                    box[i][j].addEdge(box[i][j-1]);
                }
                if(i+1<row){
                    box[i][j].addEdge(box[i+1][j]);
                }
                if(j+1<col){
                    box[i][j].addEdge(box[i][j+1]);
                }


                //add corner
                if(i-1>-1 && j-1>-1){
                    box[i][j].addCorner(box[i-1][j-1]);
                }
                if(i-1>-1 && j+1<col){
                    box[i][j].addCorner(box[i-1][j+1]);
                }
                if(i+1<row && j-1>-1){
                    box[i][j].addCorner(box[i+1][j-1]);
                }
                if(j+1<col && i+1<row){
                    box[i][j].addCorner(box[i+1][j+1]);
                }
            }
        }
    }

    void setInitProb(){
        double total=row*col-obstacles;
        double p=1/total;
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if(box[i][j].hasObstacle==0){
                    box[i][j].p=p;
                }
            }
        }
    }

    public GridCell[][] getBox() {
        return box;
    }

    void showBox(){
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                System.out.print(String.format("%.3f",box[i][j].p)+"   ");
            }
            System.out.println();
        }
    }

    void setObstacleAtIJ(int i,int j){
        box[i][j].hasObstacle=1;
        obstacles++;
    }

    void updateProb(int u,int v,int b){

    }

    void getMaxProb(){
        int r=0,c=0;
        double max=-1;
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if(box[i][j].p>max){
                    max=box[i][j].p;
                    r=i;
                    c=j;
                }
            }
        }
        System.out.println("( "+r+" , "+c+" )");
    }
}
