package model;

import java.sql.Date;

public class LeaveRequest {
    private int leave_id;
    private int emp_id;
    private Date start_date;
    private Date end_date;
    private String reason;
    private String status;

    // Getters & Setters
    public int getLeave_id() {
        return leave_id;
    }
    public void setLeave_id(int leave_id) {
        this.leave_id = leave_id;
    }

    public int getEmp_id() {
        return emp_id;
    }
    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public Date getStart_date() {
        return start_date;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
