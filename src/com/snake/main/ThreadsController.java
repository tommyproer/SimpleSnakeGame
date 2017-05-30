package com.snake.main;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls game logic.
 * TODO: Bug with user pressing two directions in quick succession may lead to false collision
 */
public class ThreadsController extends Thread {
    Logger LOG = LoggerFactory.getLogger(ThreadsController.class);
    private Position headSnakePos;
    private int sizeSnake = Configuration.getInitialSnakeSize();
    public static int directionSnake;

    private ArrayList<Position> snakePositions = new ArrayList<>();
    private Position foodPosition;
	 
    // Constructor of Controller Thread
    ThreadsController(final Position positionDepart) {
        headSnakePos = new Position(positionDepart.getX(), positionDepart.getY());
        directionSnake = Configuration.getInitialSnakeDirection();

        //!!! Pointer !!!!
        snakePositions.add(new Position(headSnakePos.getX(), headSnakePos.getY()));

        spawnFoodRandomly();
    }

    public void run() {
        while(true){
            moveInterne(directionSnake);
            checkCollision();
            moveExterne();
            deleteTail();
            pause();
        }
    }

    /**
     * Pause between each snake move.
     */
    private void pause(){
         try {
                sleep(Configuration.getSpeed());
         } catch (InterruptedException e) {
                e.printStackTrace();
         }
     }

    /**
     * Check the position of the head of the snake and
     * if it is equal to the position of any other part of the snake
     * there is a collision.
     */
     private void checkCollision() {
         for(int i = 0; i<= snakePositions.size()-2; i++) {
             if (headSnakePos.equals(snakePositions.get(i))) {
                stopTheGame();
             }
         }

         if (headSnakePos.equals(foodPosition)) {
             sizeSnake = sizeSnake + 1;
             spawnFoodRandomly();
         }
     }

     //Stops The Game
     private void stopTheGame(){
         LOG.info("Collision, game over. Snake size: " + sizeSnake);
         while(true){
             pause();
         }
     }

     // Put food in a position and displays it
     private void spawnFood(Position foodPositionIn){
         Window.gameGrid.get(foodPositionIn.getX()).get(foodPositionIn.getY()).lightMeUp(DataOfSquare.GameColor.FOOD);
     }

    /**
     * TODO: This needs to be more efficient
     */
     private void spawnFoodRandomly() {
         Position p = new Position((int) (Math.random()*20), (int) (Math.random()*20));
         while (snakePositions.contains(p)) {
             p = new Position((int) (Math.random()*20), (int) (Math.random()*20));
         }
         LOG.info("New food spawn: {}, {}, snake size: {}", p.getX(), p.getY(), sizeSnake);
         spawnFood(p);
         foodPosition = p;
     }

    //Moves the head of the snake and refreshes the snakePositions in the arraylist
    //1:right 2:left 3:top 4:bottom 0:nothing
    private void moveInterne(int dir){
        switch(dir){
            case 4:
                headSnakePos.changeData(headSnakePos.getX(),(headSnakePos.getY()+1)%20);
                snakePositions.add(new Position(headSnakePos.getX(),headSnakePos.getY()));
                break;
            case 3:
                if(headSnakePos.getY()-1<0){
                    headSnakePos.changeData(headSnakePos.getX(),19);
                } else {
                    headSnakePos.changeData(headSnakePos.getX(),(headSnakePos.getY()-1)%20);
                }
                snakePositions.add(new Position(headSnakePos.getX(),headSnakePos.getY()));
                break;
            case 2:
                if(headSnakePos.getX()-1<0){
                    headSnakePos.changeData(19,headSnakePos.getY());
                } else {
                    headSnakePos.changeData((headSnakePos.getX()-1)%20,headSnakePos.getY());
                }
                snakePositions.add(new Position(headSnakePos.getX(),headSnakePos.getY()));
                break;
            case 1:
                headSnakePos.changeData((headSnakePos.getX()+1)%20,headSnakePos.getY());
                snakePositions.add(new Position(headSnakePos.getX(),headSnakePos.getY()));
                break;
        }
    }

     //Refresh the squares that needs to be
     private void moveExterne(){
         for (Position position : snakePositions) {
             Window.gameGrid.get(position.getX()).get(position.getY()).lightMeUp(DataOfSquare.GameColor.SNAKE);
         }
     }

    /**
     * Remove all squares that exceed the size of the snake
     */
    private void deleteTail() {
         while (snakePositions.size() > sizeSnake) {
             Position tail = snakePositions.get(0);
             Window.gameGrid.get(tail.getX()).get(tail.getY()).lightMeUp(DataOfSquare.GameColor.BACKGROUND);
             snakePositions.remove(0);
         }
     }
}
