package edu.ucsb.cs.cs184.jaredbjensen.jjensendrawingmultitouch;


import android.graphics.Path;

public class Stroke {
    Path path;
    int color;
    float size;

    public Stroke(Path path, int color, float size) {
        this.path = path;
        this.color = color;
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public float getSize() {
        return size;
    }

    public Path getPath() {
        return path;
    }
}
