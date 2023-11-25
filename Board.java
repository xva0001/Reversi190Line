public class Board {
    private int[][] playerboard;
    private int sizeN;
    public int getSizeN() {
        return sizeN;
    }
    private boolean endgame=false;
    private int whowin;
    private int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };
    public Board(int number) {
        sizeN = number;
        endgame =false;
        int central = (sizeN / 2) - 1;
        playerboard = new int[number][number];
        for (int i = 0; i < playerboard.length; i++) {
            for (int j = 0; j < playerboard.length; j++) {
                playerboard[i][j] = 0;
            }
        }
        playerboard[central][central] = 1;
        playerboard[central][central + 1] = 2;
        playerboard[central + 1][central] = 2;
        playerboard[central + 1][central + 1] = 1;
    }
    public boolean isEndgame() {
        int [] count = countChess();
        endgame = (count[0] == 0 || count[1] == 0 || (count[0]+count[1]) == (int)(Math.pow(sizeN, 2)) ? true : false );
        if (endgame) {whowin=(count[0]>count[1]?1:count[1]>count[0]?2:0);}
        return endgame;
    }
    public int getWhowin(){
        return whowin;
    }
    public void print() {
        System.out.println("\n");
        for (int i = 0; i < playerboard.length; i++) {
            System.out.print((i - 10 < 0) ? ("    " + i + " | ") : "   " + i + " | ");
            for (int j = 0; j < playerboard.length; j++) {
                System.out.print(" " + playerboard[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("      " + "+" + "-");
        for (int i = 0; i < playerboard.length; i++) {
            System.out.print("---");
        }
        System.out.print("\n        ");
        for (int i = 0; i < playerboard.length; i++) {
            if (i-10<0) {
                System.out.print(" "+ i +" "); //3 unit    
                } else {
                    System.out.print(""+ i +" "); //3 unit(2 octect) i is 2 octect
                }
        }
        System.out.print("\n");
        int [] count = countChess();
        System.out.print("White : " + count[0]+"\nBlack : "+count[1]+"\n");
    }
    public void setChess(int x, int y, int player) {
        reverse(x, y, player, (player == 1 ? 2 : 1));
    }
    private boolean ext0= false;
    private boolean samecolor=false;
    private void flip(int x, int y, int dir, int color, int newColor) {
        if ((x >= 0 && x < sizeN && y >= 0 && y < sizeN) && (playerboard[x][y] == 0 || playerboard[x][y]== color)){
            if(playerboard[x][y] == 0){ext0 =true;}
            if(playerboard[x][y]== color){samecolor=true;}
        }
        if (x < 0 || x >= sizeN || y < 0 || y >= sizeN || playerboard[x][y] == 0 || playerboard[x][y] == color)
            return;
        if (playerboard[x][y] == newColor&& ext0==false) {
            flip(x + dx[dir], y + dy[dir], dir, color, newColor);
        }
        if (ext0 == false&& samecolor == true) {playerboard[x][y] = color;}
    }
    private void reverse(int row, int col, int color, int newColor) {
        playerboard[row][col] = color;
        for (int i = 0; i < 8; i++) {
            ext0 =false;
            samecolor=false;
            flip(row + dx[i], col + dy[i], i, color, newColor);
        }
    }
    public boolean isValid(int x, int y, int color) {
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0 || nx >= sizeN || ny < 0 || ny >= sizeN || playerboard[nx][ny] == 0
                    || playerboard[nx][ny] == color)
                continue;
            while (true) {
                nx += dx[i];
                ny += dy[i];
                if (nx < 0 || nx >= sizeN || ny < 0 || ny >= sizeN || playerboard[nx][ny] == 0)
                    break;
                if (playerboard[nx][ny] == color)
                    return true;
            }
        }
        return false;
    }
    public boolean hasMove(int color) {
        for (int i = 0; i < sizeN; i++) {
            for (int j = 0; j < sizeN; j++) {
                if (isValid(i, j, color))
                    return true;
            }
        }
        return false;
    }
    private int[] countChess(){ //dependent on arr_board , no static
        int[] counter = new int[3];
        for (int i = 0; i < playerboard.length; i++) {
            for (int j = 0; j < playerboard.length; j++) {
                if (playerboard[i][j]==1) {
                    counter[0] +=1;                 //count white chess //counter[0] is white//counter[1] is black
                }else if (playerboard[i][j]==2) {
                    counter[1]+=1;                  //count black chess//counter[1] is black
                }                
            }
        }
        return counter;// [0] is white(1) //[1] is black(2)
    }
    public boolean hasChess(int x, int y){return (playerboard[x][y]==0?false:true);}
}
