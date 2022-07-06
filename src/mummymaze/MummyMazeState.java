package mummymaze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import agent.Action;
import agent.State;
import mummymaze.elements.Door;
import mummymaze.elements.RedMummy;
import mummymaze.elements.Scorpion;
import mummymaze.elements.Trap;
import mummymaze.elements.WhiteMummy;
import mummymaze.helpers.EnemiesFight;

public class MummyMazeState extends State implements Cloneable {

    private final char[][] matrix;

    private Position heroPosition;
    private Position goalPosition;
    private Position keyPosition = null;

    // TODO on optimizations consider change to hashtable
    private List<Position> obstaclesPositions = new LinkedList<>();
    private List<Door> doors = new LinkedList<>();
    private List<Trap> traps = new LinkedList<>();
    private List<Scorpion> scorpions = new LinkedList<>();
    private List<WhiteMummy> whiteMummies = new LinkedList<>();
    private List<RedMummy> redMummies = new LinkedList<>();


    public MummyMazeState() {
        matrix = new char[Constants.SIZE][Constants.SIZE];
    }

    public MummyMazeState(char[][] matrix) {
        this.matrix = new char[Constants.SIZE][Constants.SIZE];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                switch (matrix[i][j]) {
                    case Constants.hero:
                        heroPosition = new Position(i, j);
                        break;

                    case Constants.scorpion:
                        scorpions.add(new Scorpion(new Position(i, j)));
                        break;

                    case Constants.whiteMummy:
                        whiteMummies.add(new WhiteMummy(new Position(i, j)));
                        break;

                    case Constants.redMummy:
                        redMummies.add(new RedMummy(new Position(i, j)));
                        break;

                    case Constants.wallHorizontal:
                    case Constants.wallVertical:
                        obstaclesPositions.add(new Position(i, j));
                        break;

                    case Constants.doorVerticalOpen:
                    case Constants.doorHorizontalOpen:
                        doors.add(new Door(true, new Position(i, j)));
                        break;

                    case Constants.doorVerticalClosed:
                    case Constants.doorHorizontalClosed:
                        doors.add(new Door(false, new Position(i, j)));
                        break;

                    case Constants.key:
                        keyPosition = new Position(i, j);
                        break;

                    case Constants.goal:
                        goalPosition = new Position(i, j);
                        break;

                    case Constants.trap:
                        traps.add(new Trap(new Position(i, j)));
                        break;
                }
            }
        }
        if (doors.size() > 0) {
            doors.forEach(door -> {
                if (!door.isOpen()) obstaclesPositions.add(door.getPosition());
            });
        }
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
    }

    public void moveElement(Position currentPosition, Position nextPosition) {
        char aux = matrix[nextPosition.getLine()][nextPosition.getColumn()];
        matrix[nextPosition.getLine()][nextPosition.getColumn()] = matrix[currentPosition.getLine()][currentPosition.getColumn()];

        if (traps.contains(new Trap(nextPosition)) ||
                nextPosition.equals(keyPosition))
            matrix[currentPosition.getLine()][currentPosition.getColumn()] = Constants.emptyCell;
        else
            matrix[currentPosition.getLine()][currentPosition.getColumn()] = aux;
        if (traps.contains(new Trap(currentPosition)))
            matrix[currentPosition.getLine()][currentPosition.getColumn()] = Constants.trap;
        if (currentPosition.equals(keyPosition))
            matrix[currentPosition.getLine()][currentPosition.getColumn()] = Constants.key;
        if (nextPosition.equals(keyPosition)) {
            updateDoors();
        }

        firePuzzleChanged(null);
    }

    public void moveEnemies() {
        for (WhiteMummy whiteMummy : new LinkedList<>(whiteMummies)) {
            for (int i = 0; i < Constants.MUMMIES_MOVES; i++) {
                Position nextPosition = whiteMummy.getNextPosition(
                        this,
                        heroPosition
                );
                if (!whiteMummy.getPosition().equals(nextPosition)) {
                    if (EnemiesFight.checkIfPositionHasEnemy(
                            this,
                            nextPosition
                    )) {
                        if (EnemiesFight.updateStateAndCheckIsKilled(
                                this,
                                whiteMummy.getPosition(),
                                nextPosition,
                                WhiteMummy.class
                        )) {
                            matrix[whiteMummy.getLine()][whiteMummy.getColumn()] = Constants.emptyCell;
                            firePuzzleChanged(null);
                            break;
                        } else {
                            matrix[nextPosition.getLine()][nextPosition.getColumn()] = Constants.emptyCell;
                        }
                    }
                    moveElement(whiteMummy.getPosition(), nextPosition);
                    whiteMummy.setPosition(nextPosition);
                }
            }
        }
        for (RedMummy redMummy : new LinkedList<>(redMummies)) {
            for (int i = 0; i < Constants.MUMMIES_MOVES; i++) {
                Position nextPosition = redMummy.getNextPosition(
                        this,
                        heroPosition
                );
                if (!redMummy.getPosition().equals(nextPosition)) {
                    if (EnemiesFight.checkIfPositionHasEnemy(
                            this,
                            nextPosition
                    )) {
                        if (EnemiesFight.updateStateAndCheckIsKilled(
                                this,
                                redMummy.getPosition(),
                                nextPosition,
                                RedMummy.class
                        )) {
                            matrix[redMummy.getLine()][redMummy.getColumn()] = Constants.emptyCell;
                            firePuzzleChanged(null);
                            break;
                        } else {
                            matrix[nextPosition.getLine()][nextPosition.getColumn()] = Constants.emptyCell;
                        }
                    }
                    moveElement(redMummy.getPosition(), nextPosition);
                    redMummy.setPosition(nextPosition);
                }
            }
        }
        for (Scorpion scorpion : new ArrayList<>(scorpions)) {
            Position nextPosition = scorpion.getNextPosition(
                    this,
                    heroPosition
            );
            if (!scorpion.getPosition().equals(nextPosition)) {
                if (EnemiesFight.checkIfPositionHasEnemy(this, nextPosition)) {
                    if (EnemiesFight.updateStateAndCheckIsKilled(
                            this,
                            scorpion.getPosition(),
                            nextPosition,
                            Scorpion.class
                    )) {
                        matrix[scorpion.getLine()][scorpion.getColumn()] = Constants.emptyCell;
                        firePuzzleChanged(null);
                        continue;
                    }
                }
                moveElement(scorpion.getPosition(), nextPosition);
                scorpion.setPosition(nextPosition);
            }
        }
    }

    public void updateDoors() {
        doors.forEach(door -> {
            door.setOpen(!door.isOpen());
            matrix[door.getLine()][door.getColumn()] = door.getCharacter();
            if (door.isOpen()) {
                obstaclesPositions.remove(door.getPosition());
            } else {
                obstaclesPositions.add(door.getPosition());
            }
        });
    }

    public void setHeroPosition(Position heroPosition) {
        this.heroPosition = heroPosition;
    }

    public void setKeyPosition(Position keyPosition) {
        this.keyPosition = keyPosition;
    }

    public void setTraps(List<Trap> traps) {
        this.traps = traps;
    }

    // GETTERS
    public char[][] getMatrix() {
        return matrix;
    }

    public Position getHeroPosition() {
        return heroPosition;
    }

    public List<Position> getObstaclesPositions() {
        return obstaclesPositions;
    }

    public List<Scorpion> getScorpions() {
        return scorpions;
    }

    public List<WhiteMummy> getWhiteMummies() {
        return whiteMummies;
    }

    public List<RedMummy> getRedMummies() {
        return redMummies;
    }

    public List<Trap> getTraps() {
        return traps;
    }

    public Character getChar(Position position) {
        return matrix[position.getLine()][position.getColumn()];
    }

    public double computeDistanceToGoal() {
        if (isGoal()) return 0;
        double h = 0;
        h += Math.abs(heroPosition.getLine() - goalPosition.getLine()) +
                Math.abs(heroPosition.getColumn() - goalPosition.getColumn());
        return h;
    }

    public double computeDistanceToEnemies() {
        if (isGoal()) return 0;
        double totalDistance = 0;
        for (WhiteMummy whiteMummy : whiteMummies) {
            totalDistance +=
                    Math.abs(heroPosition.getLine() - whiteMummy.getLine()) +
                            Math.abs(heroPosition.getColumn() -
                                    whiteMummy.getColumn());
        }
        for (RedMummy redMummy : redMummies) {
            totalDistance +=
                    Math.abs(heroPosition.getLine() - redMummy.getLine()) +
                            Math.abs(heroPosition.getColumn() -
                                    redMummy.getColumn());
        }
        for (Scorpion scorpion : scorpions) {
            totalDistance +=
                    Math.abs(heroPosition.getLine() - scorpion.getLine()) +
                            Math.abs(heroPosition.getColumn() -
                                    scorpion.getColumn());
        }
        return totalDistance != 0 ? Math.abs(1 / totalDistance) : 0;
    }

    public double computeNumberOfEnemies() {
        if (isGoal()) return 0;
        int numberOfEnemies =
                redMummies.size() + whiteMummies.size() + scorpions.size();

        return numberOfEnemies;
    }

    public boolean isGoal() {
        if (Utils.isElementOnLimit(heroPosition)) {
            int goalLine = goalPosition.getLine();
            int goalColumn = goalPosition.getColumn();
            if (goalLine == 0) {
                goalLine++;
            } else if (goalLine == Constants.SIZE - 1) {
                goalLine--;
            }
            if (goalColumn == 0) {
                goalColumn++;
            } else if (goalColumn == Constants.SIZE - 1) {
                goalColumn--;
            }
            Position positionNextToGoal = new Position(goalLine, goalColumn);
            return positionNextToGoal.equals(heroPosition);
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MummyMazeState)) {
            return false;
        }

        MummyMazeState o = (MummyMazeState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public MummyMazeState clone() {
        MummyMazeState clone = new MummyMazeState(matrix);
        if (this.traps.size() > 0 || this.keyPosition != null) {
            clone.setKeyPosition(this.keyPosition);
            clone.setTraps(this.traps);
        }
        return clone;
    }

    //Listeners
    private transient ArrayList<MummyMazeListener> listeners = new ArrayList<MummyMazeListener>(
            3);

    public synchronized void removeListener(MummyMazeListener l) {
        if (listeners != null && listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public synchronized void addListener(MummyMazeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void firePuzzleChanged(MummyMazeEvent pe) {
        for (MummyMazeListener listener : listeners) {
            listener.puzzleChanged(null);
        }
    }
}
