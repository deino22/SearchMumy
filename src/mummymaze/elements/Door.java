package mummymaze.elements;

import mummymaze.Constants;
import mummymaze.Position;

public class Door extends Base {
    private boolean isOpen;
    private final boolean isHorizontal;
    private Character character;

    public Door(boolean isOpen, Position position) {
        super(position);
        this.isOpen = isOpen;
        this.isHorizontal = position.getColumn() % 2 != 0;
        if (isHorizontal) {
            this.character = isOpen ? Constants.doorHorizontalOpen : Constants.doorHorizontalClosed;
        } else {
            this.character = isOpen ? Constants.doorVerticalOpen : Constants.doorVerticalClosed;
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
        if (isHorizontal) {
            this.character = open ? Constants.doorHorizontalOpen : Constants.doorHorizontalClosed;
        } else {
            this.character = open ? Constants.doorVerticalOpen : Constants.doorVerticalClosed;
        }
    }

    public Character getCharacter() {
        return character;
    }
}
