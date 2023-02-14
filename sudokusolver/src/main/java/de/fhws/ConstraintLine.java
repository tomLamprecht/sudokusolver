package de.fhws;

import java.util.List;

public class ConstraintLine {
    private List<Integer> indexes;
    private int sum;


    public ConstraintLine(List<Integer> indexes, int sum) {
        this.indexes = indexes;
        this.sum = sum;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Integer> indexes) {
        this.indexes = indexes;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
