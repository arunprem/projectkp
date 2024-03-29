package com.keralapolice.projectk.kpcore.unit.service;

import com.keralapolice.projectk.config.database.MySQLBaseDao;
import com.keralapolice.projectk.config.util.QueryUtilService;
import com.keralapolice.projectk.kpcore.unit.vo.UnitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UnitService {

    @Autowired
    MySQLBaseDao unitDoa;

    @Autowired
    QueryUtilService unitUtilService;

    public Long createRoot(HttpServletRequest request, UnitVo unit) {
        unit.setDepth("0");
        unit.setParantId("0");
        unit.setLft("1");
        unit.setRgt("2");
        SqlParameterSource unitSqlParameter = new MapSqlParameterSource()
                .addValue("ncrbId", unit.getNcrbId())
                .addValue("idunittype", unit.getIdUnitType())
                .addValue("headRank", unit.getHeadRank())
                .addValue("unitName", unit.getUnitName())
                .addValue("unitShortCode", unit.getUnitShortCode())
                .addValue("isSalaryUnit", unit.getIsSalaryDrawingUnit())
                .addValue("status", unit.getStatus())
                .addValue("unitDistrict", unit.getPoliceDistrictId())
                .addValue("isOldUnit", unit.getIsOldUnit())
                .addValue("parentUnitId", unit.getParantId())
                .addValue("lft", unit.getLft())
                .addValue("rgt", unit.getRgt())
                .addValue("depth", unit.getDepth());

        Long rootId = unitDoa.queryNameForUpdateReturnLong("unit.create.root", unitSqlParameter);
        return rootId;

    }


    public boolean createUnitNode(HttpServletRequest request, UnitVo unit) {
        boolean status = false;
        if (unit.getParantId() == null) {
            createRoot(request, unit);
            status = true;
        } else {
            UnitVo parentUnit = new UnitVo();
            parentUnit = (UnitVo) unitUtilService.getObject("unit.get.unit", new Object[]{unit.getParantId()}, UnitVo.class);
            unitDoa.queryNameForUpdate("unit.update.rgt",new Object[]{parentUnit.getRgt()});
            unitDoa.queryNameForUpdate("unit.update.lft",new Object[]{parentUnit.getRgt()});
            unitDoa.queryNameForUpdate("unit.create.unit", new Object[]{unit.getNcrbId(),
                    unit.getIdUnitType(),
                    unit.getHeadRank(),
                    unit.getUnitName(),
                    unit.getUnitShortCode(),
                    unit.getIsSalaryDrawingUnit(),
                    unit.getStatus(),
                    unit.getPoliceDistrictId(),
                    unit.getIsOldUnit(),
                    unit.getParantId(),
                    Long.valueOf(parentUnit.getRgt()),
                    Long.valueOf(parentUnit.getRgt()) + 1,
                    Long.valueOf(parentUnit.getDepth()) + 1});
            status = true;
        }
        return status;
    }

    public boolean moveUnit(HttpServletRequest request , UnitVo unit){
        boolean status = false;

        UnitVo previousUnit =  (UnitVo) unitUtilService.getObject("unit.get.unit", new Object[]{unit.getId()}, UnitVo.class);
        if(unit.getParantId()==previousUnit.getParantId()){
            status = false;
        }else{
            Long nodeSize = Long.valueOf(previousUnit.getRgt()) - Long.valueOf(previousUnit.getLft()) + 1;

            //negate current node and children
            unitDoa.queryNameForUpdate("unit.negate.current.node",new Object[]{previousUnit.getLft(),
                    previousUnit.getLft(),
                    previousUnit.getDepth(),
                    previousUnit.getLft(),
                    previousUnit.getRgt()});

            //update tree
            unitDoa.queryNameForUpdate("unit.moveunit.updatelft",new Object[]{nodeSize,previousUnit.getRgt()});
            unitDoa.queryNameForUpdate("unit.moveunit.updatergt",new Object[]{nodeSize,previousUnit.getRgt()});

            // Update parent id
            unitDoa.queryNameForUpdate("unit.moveunit.updateparentid",new Object[]{unit.getParantId(),unit.getId()});

            //Update tree with new parent id
            UnitVo currentUnit =  (UnitVo) unitUtilService.getObject("unit.get.unit", new Object[]{unit.getParantId()}, UnitVo.class);

            //update tree for isertion
            unitDoa.queryNameForUpdate("unit.moveunit.updatelftinsert",new Object[]{nodeSize,currentUnit.getLft()});
            unitDoa.queryNameForUpdate("unit.moveunit.updatergtinsert",new Object[]{nodeSize,currentUnit.getLft()});
            //update pluged nodes
            unitDoa.queryNameForUpdate("unit.moveunit.updateplugednode",new Object[]{currentUnit.getLft(),currentUnit.getLft(),currentUnit.getDepth()});

            status=true;
        }

        return  status;
    }

    public Boolean deleteNode(HttpServletRequest request,int unitId){
        boolean status = false;

        UnitVo currentUnit =  (UnitVo) unitUtilService.getObject("unit.get.unit", new Object[]{unitId}, UnitVo.class);
        if(currentUnit.getId()!=null) {
            Long nodeSize = Long.valueOf(currentUnit.getRgt()) - Long.valueOf(currentUnit.getLft()) + 1;
            unitDoa.queryNameForUpdate("unit.delete.node", new Object[]{currentUnit.getLft(),currentUnit.getRgt()});

            //update tree for isertion
            unitDoa.queryNameForUpdate("unit.delete.lft", new Object[]{nodeSize, currentUnit.getRgt()});
            unitDoa.queryNameForUpdate("unit.delete.rgt", new Object[]{nodeSize, currentUnit.getRgt()});
            status=true;
        }
        return status;
    }

}
