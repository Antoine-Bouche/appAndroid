package com.example.gachagame.Models;

import java.util.ArrayList;
import java.util.List;

public class MonsterList {

    List<Monster> monsterList;
    int currentIndex;

    public MonsterList(List<Monster> monsterList) {
        this.monsterList = monsterList;
        this.currentIndex = 0;
    }

    public MonsterList() {
        this.monsterList = new ArrayList<>();
        this.currentIndex = 0;
    }

    public Monster getCurrentMonstre() {
        return monsterList.get(currentIndex);
    }

    public void nextMonster() {
        currentIndex++;
    }

    public int getNumberOfMonstres() {
        return monsterList.size();
    }

    public List<Monster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<Monster> monsterList) {
        this.monsterList = monsterList;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < monsterList.size() ;i++){
            result += monsterList.get(i).toString();
        }
        return result;
    }
}
