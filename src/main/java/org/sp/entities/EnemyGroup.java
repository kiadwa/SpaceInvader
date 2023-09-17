package org.sp.entities;

import java.util.ArrayList;
import java.util.List;

public class EnemyGroup {
    private boolean moveLeft = true;
    private boolean moveDown = false;
    private List<Enemy> enemyList = new ArrayList<>();

    public void addEnemy(Enemy enemy){
        enemyList.add(enemy);
    }
    public void setMoveDown(boolean bool){this.moveDown=bool;}
    public void setMoveLeft(boolean bool){this.moveLeft=bool;}
    public void moveEnemy(){
        for(Enemy enemy: enemyList){
            if(!moveLeft) {
                enemy.right();
            }
            else {
                enemy.left();
            }
        }
    }
    public void updateMoveScheme(){
        if(enemyList.get(0).getPosition().getX() <= 1) {
            this.moveLeft = false;
            for(Enemy enemy: enemyList){
                enemy.down();
            }
        }
        else if(enemyList.get(enemyList.size()-1).getPosition().getX() >= 574) {
            this.moveLeft= true;
            for(Enemy enemy: enemyList){
                enemy.down();
            }
        }
    }
}
