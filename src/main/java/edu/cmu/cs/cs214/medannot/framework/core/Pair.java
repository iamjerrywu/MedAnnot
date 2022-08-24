package edu.cmu.cs.cs214.medannot.framework.core;

import java.util.Objects;

public class Pair {
    private final int x;
    private final int y;
    public Pair(int first, int second) {
        this.x = first;
        this.y = second;
    }
    public int getFirst() {
        return this.x;
    }
    public int getSecond() {
        return this.y;
    }
    @Override
    public boolean equals(Object op) {
        if (op == this) {
            return true;
        }


        if (op == null || getClass() != op.getClass()) {
            return false;
        }
        Pair that = (Pair) op;
        return this.x == that.x && this.y == that.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return "Pair[" + Integer.toString(x) + ", " + Integer.toString(y) + "]";
    }
}
