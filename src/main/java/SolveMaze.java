import edu.illinois.cs.cs125.lib.mazemaker.Maze;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Solve a randomly-generated maze.
 *
 * @see <a href="https://github.com/cs125-illinois/mazemaker">Mazemaker on GitHub</a>
 * @see <a href="https://cs125-illinois.github.io/mazemaker/">Mazemaker Documentation</a>
 * @see <a href="https://cs125.cs.illinois.edu/lab/2/#maze">Lab 2 Writeup</a>
 */
@SuppressWarnings("checkstyle:emptyblock")
public class SolveMaze {

    /**
     * Implement your maze solving algorithm in the main method below.
     *
     * @param unused unused input arguments
     */
    private static final int initChance = 20;
    private static int chance = initChance/2;
    private static int chanceIncreaseRate = chance/2;
    private static final int mazeXSize = 5;
    private static final int mazeYSize = 5;
    public static void main(final String[] unused) {
        /*
         * Create a new 10 x 10 maze. Feel free to change these values.
         */
        //Maze maze = new Maze(10, 10);

        /*
         * Pick (0, 0), the bottom left corner, as the starting point.
         * Put the end in the top right corner.
         */


        /*
         * You should be able to solve a 10 x 10 maze in (far fewer than) 1000 steps.
         * Feel free to adjust this number if you experiment with other mazes.
         */
        //System.out.println(maze);
        int passed = 0;
        int failed = 0;
        int testRepeatTimes = 100;
        int maximumSteps = 40;

        for(int i = 0; i < testRepeatTimes; i++) {
            Maze maze = generateNewMaze();
            A: for (int step = 0; step < maximumSteps; step++) {
                //System.out.println(step);
                maze = isSolution(maze);
                if (maze.isFinished()) {
                    break A;
                }
            }
            if (maze.isFinished()) {
                System.out.println("You solved the maze!");
                passed += 1;
            } else {
                System.out.println("Try again!");
                failed += 1;
            }
            System.out.println();
            drawLine();
        }
        printResult(passed, failed, testRepeatTimes);
    }

    public static Maze isSolution(final Maze maze){
        int i = ThreadLocalRandom.current().nextInt(0, initChance + 1);
        Maze m = maze;
        if(i  >= chance) {
            if (tryMoveLeft(m)) {
                m.move();
                //printStep(1);
            } else if (tryMoveRight(m)) {
                m.move();
                //printStep(2);
            } else if (m.canMove()) {
                m.move();
                //printStep(0);
            } else {
                m.turnLeft();
                m.turnLeft();
                m.move();
                chance += chanceIncreaseRate;
                //printStep(3);
            }
        } else {
            if (tryMoveRight(m)) {
                m.move();
                //printStep(2);
            } else if (tryMoveLeft(m)){
                m.move();
                //printStep(1);
            } else if (m.canMove()) {
                m.move();
                //printStep(0);
            } else {
                m.turnLeft();
                m.turnLeft();
                m.move();
                //printStep(3);
                chance -= chanceIncreaseRate;
            }
        }
        //System.out.println(i);
        return m;
    }
    public static boolean tryMoveLeft(Maze maze){
        boolean result = false;
        maze.turnLeft();
        if (maze.canMove()) {
            result = true;
        } else {
            maze.turnRight();
        }
        //System.out.println(result);
        return result;
    }
    public static boolean tryMoveRight(Maze maze){
        boolean result = false;
        maze.turnRight();
        if (maze.canMove()) {
            result = true;
        } else {
            maze.turnLeft();
        }
        //System.out.println(result);
        return result;
    }
    public static void printStep(int s){
        String dir = "";
        switch(s){
            case 0:
                dir = "foward";
                break;
            case 1:
                dir = "left";
                break;
            case 2:
                dir = "right";
                break;
            case 3:
                dir = "turn backward";
                break;
        }
    }
    public static void drawLine(){
        System.out.println("=====================");
    }
    public static void printResult (int passed, int failed, int testRepeatTimes){
        drawLine();
        System.out.println("passed: " + passed);
        System.out.println("failed: " + failed);
        System.out.println("passed% = " + (double) passed/testRepeatTimes * 100 + "%");
    }
    public static Maze generateNewMaze(){
        Maze maze = new Maze(mazeXSize, mazeYSize);
        maze.startAtZero();
        maze.endAtTopRight();
        System.out.println(maze);
        return maze;
    }
}
