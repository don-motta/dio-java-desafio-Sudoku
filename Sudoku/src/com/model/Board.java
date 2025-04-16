package com.model;

import java.util.Collection;
import java.util.List;

import static com.model.GameStatusEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {
    private final List<List<Space>> board;

    public Board (final List<List<Space>> board) {
        this.board = board;
    }

    public List<List<Space>> getBoard() {
        return board;
    }

    public GameStatusEnum getStatus() {
        if (board.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))) {
            return NON_STARTED;
        }
        return board.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors() {
        if (getStatus() == NON_STARTED) {
            return false;
        }
        return board.stream().flatMap(Collection::stream).anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final int value){
        var space = board.get(col).get(row);
        if (space.isFixed()){
            return false;
        }
        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row) {
        var space = board.get(col).get(row);
        if (space.isFixed()){
            return false;
        }
        space.clearSpace();
        return true;
    }

    public void reset() {
        board.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus() == COMPLETE;
    }
}
