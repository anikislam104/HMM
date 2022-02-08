import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        int m,n,k,u,v;
        Scanner scanner=new Scanner(System.in);
        m=scanner.nextInt();
        n=scanner.nextInt();
        k=scanner.nextInt();
        Grid grid=new Grid(m,n);
        grid.initialSetup();
        for(int i=0;i<k;i++){
            u=scanner.nextInt();
            v=scanner.nextInt();
            grid.setObstacleAtIJ(u,v);
        }
        grid.setInitProb();
        grid.showBox();
//        System.out.println(grid.getBox()[2][3].numOfCorner);
        String command;
        while (true){
            command=scanner.next();
            if(command.equals("R")){
                int b;
                u= scanner.nextInt();
                v=scanner.nextInt();
                b=scanner.nextInt();
//                System.out.println(u+" "+v+" "+b);
                grid.updateProb(u,v,b);
            }
            else if(command.equals("C")){
                grid.getMaxProb();
            }
            else if(command.equals("Q")){
                System.out.println("Bye Casper!");
                break;
            }
        }
    }

}
