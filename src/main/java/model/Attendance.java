package model;

import java.sql.Date;
import java.sql.Time;

public class Attendance {
    private int attendanceId;
    private int empId;
    private Date workDate;
    private Time checkIn;
    private Time checkOut;
    private String status; // WORKING, LATE, ABSENT, LEAVE

    public Attendance() {}

    public Attendance(int attendanceId, int empId, Date workDate, Time checkIn, Time checkOut, String status) {
        this.attendanceId = attendanceId;
        this.empId = empId;
        this.workDate = workDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Time getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Time checkIn) {
        this.checkIn = checkIn;
    }

    public Time getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Time checkOut) {
        this.checkOut = checkOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Trả về màu chữ hiển thị theo trạng thái
     */
    public String getStatusColor() {
        if (status == null) return "black";
        switch (status) {
            case "WORKING":
                return "green";
            case "LATE":
                return "orange";
            case "ABSENT":
                return "red";
            case "LEAVE":
                return "blue";
            default:
                return "black";
        }
    }
}
