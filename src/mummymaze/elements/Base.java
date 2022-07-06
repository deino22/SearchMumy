package mummymaze.elements;

import mummymaze.Position;

public abstract class Base {
    protected Position position;

    public Base(Position position) {
        this.position = position;
    }

    public int getLine() {
        return position.getLine();
    }

    public int getColumn() {
        return position.getColumn();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isPosition(int line, int column) {
        return this.getLine() == line && this.getColumn() == column;
    }

    // This method only uses position for the lists method contains
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Base base = (Base) obj;
        if (base.getLine() != this.getLine())
            return false;
        return base.getColumn() == this.getColumn();
    }

}
