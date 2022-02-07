public class main {
    public static void main(String[] args) {
        Grid grid=new Grid(3,4);
        grid.initialSetup();
        grid.setObstacleAtIJ(2,1);
        grid.setInitProb();
        grid.showBox();
    }
}
