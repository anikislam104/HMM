public class main {
    public static void main(String[] args) {
        Grid grid=new Grid(3,4);
        grid.initialSetup();
        grid.setInitProb();
        System.out.println(grid.getBox()[1][2].p);
    }
}
