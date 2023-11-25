import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner iScanner = new Scanner(System.in);
        Game mGame = new Game();
        int size;
        System.out.print("Enter board size(even number and 4 or abover)?");
        while (true) {
            size = iScanner.nextInt();
            if (mGame.chackSize(size)==false) {
                System.out.print("\nRetry again?"+"\n"+"Enter board size(even number and 4 or abover)?");
            }else{break;}
        }
        Board mBoard = new Board(size);
        mBoard.print();
        int player;
        int countLoop = 0 ;
        while (mBoard.isEndgame()==false) {
            player = (countLoop %2) + 1;
            if (mBoard.hasMove(player)==false) {
                if (mBoard.isEndgame()==true) {break;}
                player = (countLoop %2) + 1;
                System.out.println("Player " + player +" cannot move, Player "+(player==1?2:1)+" turn");
            }
            int[] axis = mGame.stepChacker(iScanner, mBoard, player);
            mBoard.setChess(axis[0], axis[1], player);
            countLoop +=1;
            System.gc();
            mBoard.print();
        }
        System.out.println(mBoard.getWhowin()==0?"Draw":("Player "+ mBoard.getWhowin()+ " Win"));
    }
    public boolean chackSize(int num){
        return (num>3&&num%2==0?true:false);
    }
    public int[] stepChacker(Scanner iScanner,Board mBoard,int player){
        int [] axis = new int[2];
        do{
        System.out.print("Please enter the position of "+"'"+player+"' (row col) : ");
        axis[0]= iScanner.nextInt();
        axis[1]= iScanner.nextInt();
    
        if (axis[0]>=mBoard.getSizeN()||axis[1]>=mBoard.getSizeN()||axis[0]<0||axis[1]<0) {
            System.out.println("\nError - "+"input number should be 0 to "+(mBoard.getSizeN()-1)+"!");
        }else if (mBoard.hasChess(axis[0], axis[1])) {
            System.out.println("\nError - "+"input cell is not empty.");
        }else if (mBoard.isValid(axis[0], axis[1], player)==false) {
            System.out.println("\nError - invalid move.");
        }else{break;}
    }while (true);
        return axis;
    }
}    