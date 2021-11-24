package com.keralapolice.projectk.config.database;


import com.keralapolice.projectk.config.util.SQLSelectorWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class MySQLBaseDao {
	@Autowired
	@Qualifier("kpJdbcTemplate")
	private JdbcTemplate kpJdbcTemplate;
	

	@Autowired
	@Qualifier("kpNamedJdbcTemplate")
	private NamedParameterJdbcTemplate kpNamedJdbcTemplate;
	

	
	public long queryNameForLong(String queryName) {
        return kpJdbcTemplate.queryForObject(SQLSelectorWebService.getQuery(queryName), Long.class);
    }
	
	public long queryNameForLong(String queryName, String queryNameDynamicPart) {
        return kpJdbcTemplate.queryForObject(SQLSelectorWebService.getQuery(queryName)+queryNameDynamicPart, Long.class);
    }
	
	public long queryNameForLong(String queryName, Object[] paramValues) {
        return kpJdbcTemplate.queryForObject(SQLSelectorWebService.getQuery(queryName), paramValues, Long.class);
    }
	
	public long queryNameForLong(String queryName, String queryNameDynamicPart, Object[] paramValues) {
        return kpJdbcTemplate.queryForObject(SQLSelectorWebService.getQuery(queryName)+queryNameDynamicPart, paramValues, Long.class);
    }

	public int queryNameForInteger(String queryName) {
        return kpJdbcTemplate.queryForObject(SQLSelectorWebService.getQuery(queryName), Integer.class);
    }
	
	public int queryNameForInteger(String queryName, String queryNameDynamicPart) {
        return kpJdbcTemplate.queryForObject(SQLSelectorWebService.getQuery(queryName)+queryNameDynamicPart, Integer.class);
    }	
	
	public int queryNameForInteger(String queryName, Object[] paramValues) {
        return kpJdbcTemplate.queryForObject(SQLSelectorWebService.getQuery(queryName), paramValues, Integer.class);
    }
	
	public int queryNameForInteger(String queryName, String queryNameDynamicPart, Object[] paramValues) {
        return kpJdbcTemplate.queryForObject(SQLSelectorWebService.getQuery(queryName)+queryNameDynamicPart, paramValues, Integer.class);
    }
	

	
	public void queryNameForUpdate(String query) {
		kpJdbcTemplate.update(SQLSelectorWebService.getQuery(query));
    }
	
	public void queryNameForUpdate(String query, Object[] paramValues) {
		kpJdbcTemplate.update(SQLSelectorWebService.getQuery(query), paramValues);
    }
	
	public void queryNameForUpdate(String query, Object paramValues) {
		kpNamedJdbcTemplate.update(SQLSelectorWebService.getQuery(query), new BeanPropertySqlParameterSource(paramValues));
    }
	
	public void queryNameForUpdate(String query, Map<String, ?> paramValues) {
		kpNamedJdbcTemplate.update(SQLSelectorWebService.getQuery(query), (paramValues));
    }


	
	public long queryNameForUpdateReturnLong(String query, Object paramValues) {
		KeyHolder holder = new GeneratedKeyHolder();
		kpNamedJdbcTemplate.update(SQLSelectorWebService.getQuery(query), new BeanPropertySqlParameterSource(paramValues),holder);
		return holder.getKey().longValue();
	}
	
	public long queryNameForUpdateReturnLong(String query, SqlParameterSource parameters) {
		KeyHolder holder = new GeneratedKeyHolder();
		kpNamedJdbcTemplate.update(SQLSelectorWebService.getQuery(query), parameters,holder);
		return holder.getKey().longValue();
	}
	
	//search
	public Integer querySearchForInteger(String queryName, String countQueryName, String queryDynamicPart, Object paramValues) {
		ObjectType parameterType = ObjectType.findObjectType(paramValues);
		StringBuilder countqsb = null ;
		if(countQueryName == null) {
			StringBuilder qsb = new StringBuilder(SQLSelectorWebService.getQuery(queryName));
			qsb.append(queryDynamicPart);
			countqsb = new StringBuilder(qsb);
			if (countqsb.indexOf("UNION") > 0  || countqsb.indexOf("union") > 0
	                || countqsb.indexOf("GROUP BY") > 0  || countqsb.indexOf("group by") > 0
	                || countqsb.indexOf("ORDER BY") > 0  || countqsb.indexOf("order by") > 0
	                || countqsb.indexOf("DISTINCT") > 0) {
	            countqsb.insert(0, "SELECT COUNT(1) from (");
	            countqsb.append(") AS pagecounttable");
	        } else {
	            countqsb.replace(0, qsb.indexOf(" FROM "), "SELECT COUNT(1) ");
	        }
		}
		else {
			countqsb = new StringBuilder(SQLSelectorWebService.getQuery(countQueryName));
			countqsb.append(queryDynamicPart);
		}
		Integer totalCount = 0;
		switch(parameterType) {
	        case MAP:
	            totalCount = kpNamedJdbcTemplate.queryForObject(countqsb.toString(), (Map)paramValues, Integer.class);
	            break;
	        case OBJECT_ARRAY:
	            totalCount = kpJdbcTemplate.queryForObject(countqsb.toString(), (Object[])paramValues, Integer.class);
	            break;
	        case OBJECT_OTHER:
	            totalCount = kpNamedJdbcTemplate.queryForObject(countqsb.toString(), new BeanPropertySqlParameterSource(paramValues), Integer.class);
	            break;
	        case NULL:
	            totalCount = kpNamedJdbcTemplate.queryForObject(countqsb.toString(), new HashMap<String, String>(), Integer.class);
	            break;
	    }
		
        return totalCount;
    }
	

	
	public enum ObjectType {
	    NULL(0),
	    OBJECT_OTHER(1),
	    OBJECT_ARRAY(2),
	    MAP(3);
	    
	    private int objectTypeId;
	    
	    ObjectType(int objectTypeId) {
	        this.objectTypeId = objectTypeId;
	    }

	    public int getObjectTypeId() {
	        return objectTypeId;
	    }

	    
	    public static ObjectType findObjectType(Object object) {
	        if(object == null) {
	          return NULL;  
	        }
	        if(object.getClass().isArray()) {
	            return OBJECT_ARRAY;
	        }
	        if (object instanceof Map) {
	            return MAP;
	        }
	        return OBJECT_OTHER;
	    }
	}
	
	public void queryForUpdate(String query) {
		kpJdbcTemplate.update(query);
    }
	public List<String> queryForStringList(String queryName) {
        return kpJdbcTemplate.queryForList(queryName, String.class);
    }


	public SqlRowSet selctQuery(String query) {
		return kpJdbcTemplate.queryForRowSet(SQLSelectorWebService.getQuery(query));
	}

	public SqlRowSet selctQuery(String query, Object[] paramValues) {
		return kpJdbcTemplate.queryForRowSet(SQLSelectorWebService.getQuery(query),paramValues);
	}
}
