package org.practice.domo.herarchy;

import java.util.LinkedList;

public class TrainComposition {
    LinkedList<Integer> wagons = new LinkedList<>();

    public TrainComposition(){
    }

    public void attachWagonFromLeft(int wagonId) {
        wagons.addFirst(wagonId);
    }

    public void attachWagonFromRight(int wagonId) {
        wagons.addLast(wagonId);
    }

    public int detachWagonFromLeft() {
        return wagons.pollLast();
    }

    public int detachWagonFromRight() {
        return wagons.pollFirst();
    }

    public static void main(String[] args) {
        TrainComposition train = new TrainComposition();
        train.attachWagonFromLeft(7);
        train.attachWagonFromLeft(13);
        System.out.println(train.detachWagonFromRight()); // 7
        System.out.println(train.detachWagonFromLeft()); // 13
    }
}
