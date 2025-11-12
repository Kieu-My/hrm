package model;

public class Position {
    private int position_id;
    private String position_name;

    // Constructor mặc định
    public Position() {}

    // Constructor đầy đủ
    public Position(int position_id, String position_name) {
        this.position_id = position_id;
        this.position_name = position_name;
    }

    // Getter/Setter
    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    // Override toString để tiện debug/hiển thị
    @Override
    public String toString() {
        return position_id + " - " + position_name;
    }
}
