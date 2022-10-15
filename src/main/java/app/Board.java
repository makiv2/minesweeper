package app;

import java.io.Serializable;
import java.util.*;

public class Board implements Serializable {

    private Cell[][] board;
    private final static int DEFAULT = 10;
    private int amountOfBombs;

    public Board() {
        this.board = new Cell[DEFAULT][DEFAULT];
        initializeBoard(DEFAULT);
    }

    public Board(int row, int col, int amountOfBombs) {
        this.board = new Cell[row][col];
        initializeBoard(amountOfBombs);

    }


    private void initializeBoard(int amountOfBombs) {
        initializeBombs(amountOfBombs);
        initializeNumbers();
        this.amountOfBombs = amountOfBombs;
    }



    private void initializeBombs(int amountOfBombs) {
        int temp = 0;
        Set<Integer> numbersWhereTheBombsOccure = generateRandom(amountOfBombs);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (numbersWhereTheBombsOccure.contains(temp)) {
                    board[i][j] = new Cell(Status.Bomb, i, j);
                } else {
                    board[i][j] = new Cell(Status.None, i, j);
                }
                temp++;
            }
        }
    }

    private boolean isInBounds(List<Integer> list, Cell[][] board) {
        return list.get(0) < board.length
                && list.get(0) >= 0
                && list.get(1) < board[0].length

                && list.get(1) >= 0;
    }

    private boolean isBomb(Cell cell) {
        return cell.getStatus() == Status.Bomb;
    }


    private List<List<Integer>> theList(int row, int col) {

        final List<List<Integer>> list = new ArrayList<>();

        List<Integer> temp = new ArrayList<>();
        temp.add(row - 1);
        temp.add(col);
        list.add(temp);

        List<Integer> temp2 = new ArrayList<>();
        temp2.add(row - 1);
        temp2.add(col - 1);
        list.add(temp2);

        List<Integer> temp3 = new ArrayList<>();
        temp3.add(row - 1);
        temp3.add(col + 1);
        list.add(temp3);

        List<Integer> temp4 = new ArrayList<>();
        temp4.add(row);
        temp4.add(col + 1);
        list.add(temp4);

        List<Integer> temp5 = new ArrayList<>();
        temp5.add(row);
        temp5.add(col - 1);
        list.add(temp5);

        List<Integer> temp6 = new ArrayList<>();
        temp6.add(row + 1);
        temp6.add(col + 1);
        list.add(temp6);

        List<Integer> temp7 = new ArrayList<>();
        temp7.add(row + 1);
        temp7.add(col);
        list.add(temp7);

        List<Integer> temp8 = new ArrayList<>();
        temp8.add(row + 1);
        temp8.add(col - 1);
        list.add(temp8);

        return list;


    }

    private void setValues(int row, int col, Cell[][] board) {
        for (List<Integer> list : theList(row, col)) {
            Cell cell = null;
            if (isInBounds(list, board)) {
                cell = board[list.get(0)]
                        [list.get(1)];
            }
            if (isInBounds(list, board) && !isBomb(cell)) {
                cell.addStatus();
            }
        }
    }

    private void initializeNumbers() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getStatus() == Status.Bomb) {
                    setValues(i, j, board);
                }
            }
        }
    }

    private Set<Integer> generateRandom(int amountOfNumbers) {
        Set<Integer> setOfRandomNumbers = new HashSet();
        Random random = new Random();
        while (setOfRandomNumbers.size() < amountOfNumbers) {
            setOfRandomNumbers.add(random.nextInt(board.length * board[0].length));
        }
        return setOfRandomNumbers;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public static void main(String[] args) {
        Board test = new Board(3, 3, 8);
        Arrays.stream(test.getBoard()).flatMap(Arrays::stream).map(Cell::getStatus).forEach(System.out::println);

    }

    public void revealCells(int row, int col, Cell[][] board) {
        for (List<Integer> list : theList(row, col)) {
            Cell cell = null;
            if (isInBounds(list, board)) {
                cell = board[list.get(0)][list.get(1)];
            }
            if (isInBounds(list, board) && cell.getStatus() == Status.None && !cell.isCellVisible()) {
                cell.setCellVisible(true);
                revealCells(list.get(0), list.get(1), board);
            }
            if (isInBounds(list, board) && (cell.getStatus() != Status.Bomb)) {
                cell.setCellVisible(true);
            }
        }
    }

    public boolean gameWon(Cell[][] board) {
        int won = board.length * board[0].length - this.amountOfBombs;
        int amountOfRevealed = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].isCellVisible()) {
                    amountOfRevealed++;
                }
            }
        }
        return won == amountOfRevealed;
    }

    public void leftClick(Cell cell, Cell[][] board) {
        if (cell.isFlagged())
            return;
        cell.setCellVisible(true);
        if (cell.getStatus() == Status.Bomb) {
            System.out.println("game lost");
            System.exit(0);
        } else {
            revealCells(cell.getRow(), cell.getCol(), board);
            if (gameWon(board))
                System.out.println("game won");
        }
    }

    public void rightClickflag(Cell cell) {
        cell.toggleFlag();
    }

}







