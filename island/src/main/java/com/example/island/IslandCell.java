package com.example.island;

import com.example.island.location.Location;

public class IslandCell {
    private final int x;
    private final int y;
    private Location location;
    private String terrainType;

    public IslandCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public String getTerrainType() { return terrainType; }
    public void setTerrainType(String terrainType) { this.terrainType = terrainType; }

    @Override
    public String toString() {
        return String.format("Cell[%d,%d] in %s", x, y,
                location != null ? location.getName() : "No Location");
    }
}