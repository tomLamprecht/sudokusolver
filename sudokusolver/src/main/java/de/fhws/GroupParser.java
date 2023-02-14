package de.fhws;

import de.fhws.gamefieldcreator.Cell;
import de.fhws.gamefieldcreator.Group;
import de.fhws.gamefieldcreator.Splitter;

import java.util.List;
import java.util.stream.Collectors;

public class GroupParser {

    public final static int AMOUNT_VARIABLES = Splitter.SUDOKU_SIZE * Splitter.SUDOKU_SIZE;


    public static List<ConstraintLine> parseGroupsToConstraints(List<Group> groups){
        return groups.stream().map(GroupParser::parseGroupToConstraint).collect(Collectors.toList());
    }


    public static ConstraintLine parseGroupToConstraint(Group group) {
        return new ConstraintLine(
                group.getCells()
                        .stream()
                        .map(Cell::getIndex)
                        .collect(Collectors.toList()),
                group.getSum());
    }


}
