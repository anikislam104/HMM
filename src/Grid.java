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
                if(i-1>-1 && box[i-1][j].hasObstacle==0){
                    box[i][j].addEdge(box[i-1][j]);
                }
                if(j-1>-1 && box[i][j-1].hasObstacle==0){
                    box[i][j].addEdge(box[i][j-1]);
                }
                if(i+1<row && box[i+1][j].hasObstacle==0){
                    box[i][j].addEdge(box[i+1][j]);
                }
                if(j+1<col && box[i][j+1].hasObstacle==0){
                    box[i][j].addEdge(box[i][j+1]);
                }


                //add corner
                if(i-1>-1 && j-1>-1 && box[i-1][j-1].hasObstacle==0){
                    box[i][j].addCorner(box[i-1][j-1]);
                }
                if(i-1>-1 && j+1<col && box[i-1][j+1].hasObstacle==0){
                    box[i][j].addCorner(box[i-1][j+1]);
                }
                if(i+1<row && j-1>-1 && box[i+1][j-1].hasObstacle==0){
                    box[i][j].addCorner(box[i+1][j-1]);
                }
                if(j+1<col && i+1<row && box[i+1][j+1].hasObstacle==0){
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
                System.out.print(String.format("%.3f",box[i][j].p*100.0)+"   ");
            }
            System.out.println();
        }
    }

    void setObstacleAtIJ(int i,int j){
        box[i][j].hasObstacle=1;
        obstacles++;
    }

    void normaliseProbability(){
        double total=0;
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                total+=box[i][j].p;
            }
        }
        //System.out.println(total);
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                box[i][j].p=box[i][j].p/total;
            }
        }
    }

    void updateAfterSensorReading(int u,int v,int b){
        double adj,nonAdj;
        if(b==1){
            adj=0.85;
            nonAdj=0.15;
        }
        else{
            adj=0.15;
            nonAdj=0.85;
        }

        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                //for adjacent
                if(i==u-1 && j==v-1){
                    box[i][j].p*=adj;
                }
                else if(i==u-1 && j==v){
                    box[i][j].p*=adj;
                }
                else if(i==u-1 && j==v+1){
                    box[i][j].p*=adj;
                }
                else if(i==u && j==v-1){
                    box[i][j].p*=adj;
                }
                else if(i==u && j==v){
                    box[i][j].p*=adj;
                }
                else if(i==u && j==v+1){
                    box[i][j].p*=adj;
                }
                else if(i==u+1 && j==v-1){
                    box[i][j].p*=adj;
                }
                else if(i==u+1 && j==v){
                    box[i][j].p*=adj;
                }
                else if(i==u+1 && j==v+1){
                    box[i][j].p*=adj;
                }
                //for non-adjacent
                else{
                    box[i][j].p*=nonAdj;
                }
            }
        }
        //showBox();
        normaliseProbability();
    }

    void updateGhostMove()
    {
        double[][] global=new double[row][col];

        for (int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                double[][] temp=new double[row][col];


                for (int k=0;k<row;k++) {
                    for (int l = 0; l < col; l++) {
                        temp[k][l]=box[k][l].p;
                    }
                }

                int edgeCount;
                int cornerCount;
                double edgeProb;
                double cornerProb;
                //System.out.println(i+" "+j+" "+edgeCount+" "+edgeProb+" "+cornerCount+" "+cornerProb);
                double newProb=0.0;


                double edgeSum = 0.0, cornerSum=0.0;
                if(i-1>-1){
                    edgeCount=box[i-1][j].numOfEdge;
                    edgeProb=0.9/edgeCount;
                    edgeSum+=temp[i-1][j]*edgeProb;
                }
                if(j-1>-1){
                    edgeCount=box[i][j-1].numOfEdge;
                    edgeProb=0.9/edgeCount;
                    edgeSum+=temp[i][j-1]*edgeProb;
                }
                if(i+1<row){
                    edgeCount=box[i+1][j].numOfEdge;
                    edgeProb=0.9/edgeCount;
                    edgeSum+=temp[i+1][j]*edgeProb;
                }
                if(j+1<col){
                    edgeCount=box[i][j+1].numOfEdge;
                    edgeProb=0.9/edgeCount;
                    edgeSum+=temp[i][j+1]*edgeProb;
                }
                //System.out.println(edgeSum);


                if(i-1>-1 && j-1>-1){
                    cornerCount=box[i-1][j-1].numOfCorner+1;
                    cornerProb=0.1/cornerCount;
                    cornerSum+=temp[i-1][j-1]*cornerProb;
                }
                if(i-1>-1 && j+1<col){
                    cornerCount=box[i-1][j+1].numOfCorner+1;
                    cornerProb=0.1/cornerCount;
                    cornerSum+=temp[i-1][j+1]*cornerProb;
                }
                if(i+1<row && j-1>-1){
                    cornerCount=box[i+1][j-1].numOfCorner+1;
                    cornerProb=0.1/cornerCount;
                    cornerSum+=temp[i+1][j-1]*cornerProb;
                }
                if(j+1<col && i+1<row){
                    cornerCount=box[i+1][j+1].numOfCorner+1;
                    cornerProb=0.1/cornerCount;
                    cornerSum+=temp[i+1][j+1]*cornerProb;
                }
                cornerCount=box[i][j].numOfCorner+1;
                cornerProb=0.1/cornerCount;
                cornerSum+=temp[i][j]*cornerProb;
                //System.out.println(cornerSum);

                newProb=edgeSum+cornerSum;
                if(box[i][j].hasObstacle==1){
                    global[i][j]=0;
                }
                else{
                    global[i][j]=newProb;
                }
            }
        }

        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                box[i][j].p=global[i][j];
            }
        }
        //showBox();
        //normaliseProbability();
    }

    void updateProb(int u,int v,int b){
        updateGhostMove();
        updateAfterSensorReading(u,v,b);
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
