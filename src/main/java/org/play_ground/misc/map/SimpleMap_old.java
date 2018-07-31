package org.play_ground.misc.map;


import java.util.function.Consumer;

public class SimpleMap_old {
    private Field[][] fields;

    public void init(int numX, int numY) {
        fields = new SimpleMap_old.Field[numX][numY];
        for (int i = 0; i < numX; i++) {
            for (int j = 0; j < numY; j++) {
                fields[i][j] = new SimpleMap_old.Field(i, j);
            }
        }
    }

    public Field getBlock(int x, int y) {
        if (x < 0 || y < 0 || x >= fields.length || y >= fields[0].length) {
            return null;
        }
        return fields[x][y];
    }

    public void reset() {
        forEach(a -> a.selected = false);
    }

    public void forEach(Consumer<Field> action) {
        for (SimpleMap_old.Field[] field : fields) {
            for (SimpleMap_old.Field aField : field) {
                action.accept(aField);
            }
        }
    }

    public static class Field {
        public int     x;
        public int     y;
        public boolean selected = false;

        public Field(int x, int y) {this.x = x; this.y = y;}
    }
}
