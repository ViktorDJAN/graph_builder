package ru.promelectronika.dto;

public class DataDto {
    private float coordinateA;
    private float coordinateB;

    public DataDto(float coordinateA, float coordinateB) {
        this.coordinateA = coordinateA;
        this.coordinateB = coordinateB;
    }

    public DataDto() {
    }

    public float getCoordinateA() {
        return coordinateA;
    }

    public void setCoordinateA(float coordinateA) {
        this.coordinateA = coordinateA;
    }

    public float getCoordinateB() {
        return coordinateB;
    }

    public void setCoordinateB(float coordinateB) {
        this.coordinateB = coordinateB;
    }

    @Override
    public String toString() {
        return "DataDto{" +
                "coordinateA=" + coordinateA +
                ", coordinateB=" + coordinateB +
                '}';
    }

}
