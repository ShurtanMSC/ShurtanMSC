package uz.neft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class UppgSimulatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String Name;

    private String FullName;

    private String Description;

    private BigDecimal Val;

    private int Quality;

    private Date Time_Stamp;

    private int ScaleMin;

    private int ScaleMax;

    public UppgSimulatorEntity() {

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public BigDecimal getVal() {
        return Val;
    }

    public void setVal(BigDecimal val) {
        Val = val;
    }

    public int getQuality() {
        return Quality;
    }

    public void setQuality(int quality) {
        Quality = quality;
    }

    public Date getTime_Stamp() {
        return Time_Stamp;
    }

    public void setTime_Stamp(Date time_Stamp) {
        Time_Stamp = time_Stamp;
    }

    public int getScaleMin() {
        return ScaleMin;
    }

    public void setScaleMin(int scaleMin) {
        ScaleMin = scaleMin;
    }

    public int getScaleMax() {
        return ScaleMax;
    }

    public void setScaleMax(int scaleMax) {
        ScaleMax = scaleMax;
    }

    public UppgSimulatorEntity(Long ID, String name, String fullName, String description, BigDecimal val, int quality, Date time_Stamp, int scaleMin, int scaleMax) {
        this.ID = ID;
        Name = name;
        FullName = fullName;
        Description = description;
        Val = val;
        Quality = quality;
        Time_Stamp = time_Stamp;
        ScaleMin = scaleMin;
        ScaleMax = scaleMax;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
