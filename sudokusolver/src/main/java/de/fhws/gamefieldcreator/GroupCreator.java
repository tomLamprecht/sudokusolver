package de.fhws.gamefieldcreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GroupCreator {

    private final static int SUDOKU_MAX = 45;
    private final static int GROUPS_PER_ROW_COL = 3;

    public static List<Group> createGroups(Cell[][] cells) {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Group tmp = new Group(false);
                tmp.addCell(cells[i][j]);
                for (Cell.Openings opening : cells[i][j].getOpeningsList()) {
                    tmp.addCell(switch (opening) {
                        case TOP -> cells[i][j - 1];
                        case BOTTOM -> cells[i][j + 1];
                        case LEFT -> cells[i - 1][j];
                        case RIGHT -> cells[i + 1][j];
                    });
                }
                for (Group g : groups) {
                    if (shareCell(g, tmp)) {
                        g.addAll(tmp.cells);
                        g.cells = new ArrayList<>(new HashSet<>(g.cells));
                        tmp = null;
                        break;
                    }
                }
                if (tmp != null)
                    groups.add(tmp);
            }
        }
        groups.forEach(Group::processGroupSum);
        addVerticalGroups(cells, groups);
        addHorizontalGroups(cells, groups);
        addGroupBlocks(cells, groups);

        return groups;
    }

    private static void addGroupBlocks(Cell[][] cells, List<Group> groups) {
        for (int i = 1; i < cells.length; i+= GROUPS_PER_ROW_COL) {
            for (int j = 1; j < cells.length; j+= GROUPS_PER_ROW_COL) {
                groups.add(getGroupBlock(cells, i,j));
            }
        }
    }

    private static Group getGroupBlock(Cell[][] cells, int x, int y) {
        Group group = new Group(true);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                group.addCell(cells[x-i][y-j]);
            }
        }
        group.setSum(SUDOKU_MAX);
        return group;
    }

    private static void addHorizontalGroups(Cell[][] cells, List<Group> groups) {
        for (int y = 0; y < cells.length; y++) {
            Group horizontal = new Group(true);
            for (int x = 0; x < cells[y].length; x++) {
                horizontal.addCell(cells[x][y]);
            }
            horizontal.setSum(SUDOKU_MAX);
            groups.add(horizontal);
        }
    }

    private static void addVerticalGroups(Cell[][] cells, List<Group> groups) {
        for (int x = 0; x < cells.length; x++) {
            Group vert = new Group(true);
            for (int y = 0; y < cells[x].length; y++) {
                vert.addCell(cells[x][y]);
            }
            vert.setSum(SUDOKU_MAX);
            groups.add(vert);
        }
    }

    public static boolean shareCell(Group g1, Group g2) {
        return !Collections.disjoint(g1.cells, g2.cells);
    }

}
