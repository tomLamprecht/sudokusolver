package de.fhws;

import de.fhws.gamefieldcreator.Cell;
import de.fhws.gamefieldcreator.Group;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MathSolver {

    Cell[][] cells;
    List<Group> groups;

    public MathSolver(Cell[][] cells, List<Group> groups) {
        this.cells = cells;
        this.groups = groups;
    }

    public int[][] solve() {
        List<ConstraintLine> constraints = GroupParser.parseGroupsToConstraints(groups);
        Model model = new Model("Sudoku");
        List<IntVar> vars = createVariables(model);

        for (ConstraintLine line : constraints) {
            model.sum(line.getIndexes()
                            .stream()
                            .map(vars::get)
                            .toArray(IntVar[]::new),
                    "=", line.getSum()).post();
        }

        oneToNineRule(model, vars);

        boolean found = model.getSolver().solve();
        System.out.println("Found solution: " + found);

        int[][] result = new int[9][9];
        for (int i = 0; i < vars.size(); i++) {
            result[i % 9][i / 9] = vars.get(i).getValue();
        }

        return result;
    }

    private void oneToNineRule(Model model, List<IntVar> vars) {
        groups.stream()
                .filter(Group::isDistinct)
                .map(Group::getCells)
                .map(c -> c.stream()
                        .map(Cell::getIndex)
                        .map(vars::get)
                        .toArray(IntVar[]::new))
                .map(model::allDifferent)
                .forEach(Constraint::post);
    }

    @NotNull
    private List<IntVar> createVariables(Model model) {
        return Stream.iterate(0, (i) -> i + 1)
                .map(i -> model.intVar("" + i, 1, 9))
                .limit(81)
                .collect(Collectors.toList());
    }


}
