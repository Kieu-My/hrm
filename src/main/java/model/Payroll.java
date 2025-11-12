package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Payroll {
    private int payroll_id;
    private int emp_id;
    private Date pay_date;
    private BigDecimal base_salary;
    private BigDecimal bonus;
    private BigDecimal deductions;
    private BigDecimal net_salary;

    // Getters & Setters
    public int getPayroll_id() { return payroll_id; }
    public void setPayroll_id(int payroll_id) { this.payroll_id = payroll_id; }

    public int getEmp_id() { return emp_id; }
    public void setEmp_id(int emp_id) { this.emp_id = emp_id; }

    public Date getPay_date() { return pay_date; }
    public void setPay_date(Date pay_date) { this.pay_date = pay_date; }

    public BigDecimal getBase_salary() { return base_salary; }
    public void setBase_salary(BigDecimal base_salary) { this.base_salary = base_salary; }

    public BigDecimal getBonus() { return bonus; }
    public void setBonus(BigDecimal bonus) { this.bonus = bonus; }

    public BigDecimal getDeductions() { return deductions; }
    public void setDeductions(BigDecimal deductions) { this.deductions = deductions; }

    public BigDecimal getNet_salary() { return net_salary; }
    public void setNet_salary(BigDecimal net_salary) { this.net_salary = net_salary; }
}
