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
}
