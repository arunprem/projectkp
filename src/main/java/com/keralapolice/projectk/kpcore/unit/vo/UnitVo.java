package com.keralapolice.projectk.kpcore.unit.vo;

import javax.validation.constraints.NotNull;

public class UnitVo {

    private String id;
    private String ncrbId;
    @NotNull
    private String idUnitType;
    @NotNull
    private String headRank;
    @NotNull
    private String unitName;
    @NotNull
    private String unitShortCode;
    @NotNull
    private String isSalaryDrawingUnit;
    @NotNull
    private String policeDistrictId;
    @NotNull
    private String isOldUnit;

    private String lft;

    private String rgt;
    private String depth;

    @NotNull
    private String parantId;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNcrbId() {
        return ncrbId;
    }

    public void setNcrbId(String ncrbId) {
        this.ncrbId = ncrbId;
    }

    public String getIdUnitType() {
        return idUnitType;
    }

    public void setIdUnitType(String idUnitType) {
        this.idUnitType = idUnitType;
    }

    public String getHeadRank() {
        return headRank;
    }

    public void setHeadRank(String headRank) {
        this.headRank = headRank;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitShortCode() {
        return unitShortCode;
    }

    public void setUnitShortCode(String unitShortCode) {
        this.unitShortCode = unitShortCode;
    }

    public String getIsSalaryDrawingUnit() {
        return isSalaryDrawingUnit;
    }

    public void setIsSalaryDrawingUnit(String isSalaryDrawingUnit) {
        this.isSalaryDrawingUnit = isSalaryDrawingUnit;
    }

    public String getPoliceDistrictId() {
        return policeDistrictId;
    }

    public void setPoliceDistrictId(String policeDistrictId) {
        this.policeDistrictId = policeDistrictId;
    }

    public String getIsOldUnit() {
        return isOldUnit;
    }

    public void setIsOldUnit(String isOldUnit) {
        this.isOldUnit = isOldUnit;
    }

    public String getLft() {
        return lft;
    }

    public void setLft(String lft) {
        this.lft = lft;
    }

    public String getRgt() {
        return rgt;
    }

    public void setRgt(String rgt) {
        this.rgt = rgt;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getParantId() {
        return parantId;
    }

    public void setParantId(String parantId) {
        this.parantId = parantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
