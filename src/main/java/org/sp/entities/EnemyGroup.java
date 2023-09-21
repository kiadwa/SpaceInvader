package org.sp.entities;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyGroup {
    private boolean moveLeft = true;
    private List<List<Enemy>> enemyList = new CopyOnWriteArrayList<>();
    private int enemyCount = 0;

    public void setEnemyCount(){this.enemyCount = getRealTimeCount();}

    public void addEnemy(List<Enemy> enemylist){
        enemyList.add(enemylist);
    }
    public void removeEnemy(Enemy enemy){
        for(List<Enemy> enemies: enemyList)
            enemies.remove(enemy);
        }
    public int getRealTimeCount(){return this.enemyList.get(0).size() + this.enemyList.get(1).size();}
    public List<List<Enemy>> getEnemyList(){return this.enemyList;}
    public void moveEnemy(){
        for(List<Enemy> enemylist: enemyList){
            for(Enemy enemy: enemylist) {
                if (!moveLeft) {
                    enemy.right();
                } else {
                    enemy.left();
                }
            }
        }
    }
    public void updateMoveScheme(){
        //todo make enemy move faster after each take down.
        if(enemyList.get(0).isEmpty()){
            if (enemyList.get(1).get(0).getPosition().getX() <= 1) {
                this.moveLeft = false;
                for (List<Enemy> enemylist : enemyList) {
                    for (Enemy enemy : enemylist)
                        enemy.down();
                }
            } else if (enemyList.get(1).get(enemyList.get(1).size() - 1).getPosition().getX() >= 574) {
                this.moveLeft = true;
                for (List<Enemy> enemylist : enemyList) {
                    for (Enemy enemy : enemylist)
                        enemy.down();
                }
            }
        }else if(enemyList.get(1).isEmpty()){
            if (enemyList.get(0).get(0).getPosition().getX() <= 1) {
                this.moveLeft = false;
                for (List<Enemy> enemylist : enemyList) {
                    for (Enemy enemy : enemylist)
                        enemy.down();
                }
            } else if (enemyList.get(0).get(enemyList.get(0).size() - 1).getPosition().getX() >= 574) {
                this.moveLeft = true;
                for (List<Enemy> enemylist : enemyList) {
                    for (Enemy enemy : enemylist)
                        enemy.down();
                }
            }
        }else {
            if (enemyList.get(0).get(0).getPosition().getX() <= 1
                    || enemyList.get(1).get(0).getPosition().getX() <= 1) {
                this.moveLeft = false;
                for (List<Enemy> enemylist : enemyList) {
                    for (Enemy enemy : enemylist)
                        enemy.down();
                }
            } else if (enemyList.get(0).get(enemyList.get(0).size() - 1).getPosition().getX() >= 574
                    || enemyList.get(1).get(enemyList.get(1).size() - 1).getPosition().getX() >= 574) {
                this.moveLeft = true;
                for (List<Enemy> enemylist : enemyList) {
                    for (Enemy enemy : enemylist)
                        enemy.down();
                }
            }
        }
        if(getRealTimeCount() != this.enemyCount){
            for(List<Enemy> enemylist: enemyList){
                for(Enemy enemy: enemylist)
                    enemy.increaseMovementSPD();
            }
            this.enemyCount = getRealTimeCount();
        }
    }


}
