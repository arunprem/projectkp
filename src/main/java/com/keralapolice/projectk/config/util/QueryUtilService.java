package com.keralapolice.projectk.config.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.keralapolice.projectk.config.database.MySQLBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Service
public class QueryUtilService {

    @Autowired
    private MySQLBaseDao mySQLBaseDao;

    private static final Logger logger = LoggerFactory.getLogger(QueryUtilService.class);

    private Object resultToObjectMapper(SqlRowSet sqlRowSet, Set<String> sqlRowSetColumnNames, Object o) {

        ObjectMapper objectMapper = new ObjectMapper(); // Jacakson Object Mapper
        CollectionType listType = null;
        Type genericType = null;

        for (Field field : o.getClass().getDeclaredFields()) {

            field.setAccessible(true);

            if (field.getType() == int.class || field.getType() == Integer.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getInt(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == float.class || field.getType() == Float.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getFloat(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == double.class || field.getType() == Double.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getDouble(field.getName()));
                    }
                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == long.class || field.getType() == Long.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getLong(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == char.class || field.getType() == Character.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getString(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == String.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getString(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getBoolean(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == short.class || field.getType() == Short.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getShort(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == byte.class || field.getType() == Byte.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getByte(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == java.sql.Date.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getDate(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == java.util.Date.class) {

                try {

                    if (sqlRowSetColumnNames.contains(field.getName())) {
                        field.set(o, sqlRowSet.getDate(field.getName()));
                    }

                } catch (InvalidResultSetAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.toString());
                }

            } else if (field.getType() == java.util.List.class) {

                if (sqlRowSetColumnNames.contains(field.getName())) {

                    genericType = field.getGenericType();

                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) genericType;
                        Type itemType = parameterizedType.getActualTypeArguments()[0];
                        String string = itemType.toString();

                        try {
                            listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class,
                                    Class.forName(string.split(" ")[1]));
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            logger.error(e.toString());
                        }

                        try {

                            field.set(o, objectMapper.readValue(sqlRowSet.getString(field.getName()), listType));

                        } catch (InvalidResultSetAccessException | JsonProcessingException | IllegalArgumentException
                                | IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            logger.error(e.toString());
                        }

                        continue;

                    }
                }

            } else {
                /*
                 * Sometimes an object have a list in it , Hoping it would map correctly, we use
                 * ObjectMapper to map it It would be better if we check for the Object contains
                 * another list or other objects
                 */
                if (sqlRowSetColumnNames.contains(field.getName())) {

                    try {

                        field.set(o, objectMapper.readValue(sqlRowSet.getString(field.getName()), field.getType()));

                    } catch (InvalidResultSetAccessException e) {
                        // TODO Auto-generated catch block
                        logger.error(e.toString());
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        logger.error(e.toString());
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        logger.error(e.toString());
                    } catch (JsonProcessingException e) {
                        // TODO: handle exception
                        logger.error(e.toString());
                    }

                }

            }

        }

        return o;
    }

    /**
     * Function to fetch the table data to ArrayList of Desired POJO
     *
     * @param querry      String ID of Query String in sql.property file
     * @param paramValues Object[] which holds parameter values to Querry / pass
     *                    null if there is none
     * @param klass       Class of the 'Desired POJO'
     * @return Populated ArrayList of Desired POJO
     */

    public Object getObjectList(String querry, Object[] paramValues, Class klass) {

        SqlRowSet sqlRowSet;
        ArrayList<Object> arrayList = new ArrayList<Object>();

        sqlRowSet = mySQLBaseDao.selctQuery(querry, paramValues);
        Set<String> sqlRowSetColumnNames = new HashSet<String>();
        try {
            if (sqlRowSet != null) {

                int numberOfColumns = sqlRowSet.getMetaData().getColumnCount();
                for (int i = 1; i <= numberOfColumns; i++) {
                    String tempString = sqlRowSet.getMetaData().getColumnLabel(i);
                    sqlRowSetColumnNames.add(tempString);
                }
                while (sqlRowSet.next()) {
                    arrayList.add(resultToObjectMapper(sqlRowSet, sqlRowSetColumnNames, klass.newInstance()));

                }
            }

        } catch (Exception e) {
            logger.error(e.toString());
        }
        return arrayList;
    }

    /**
     * Function to fetch one row of data from table to the Desired POJO
     *
     * @param querry      String ID of Query String in sql.property file
     * @param paramValues Object[] which holds parameter values to Querry / pass
     *                    null if there is none
     * @param klass       Class of the 'Desired POJO'
     * @return Populated object of Desired POJO
     */
    public Object getObject(String querry, Object[] paramValues, Class klass) {

        SqlRowSet sqlRowSet;
        Set<String> sqlRowSetColumnNames = new HashSet<String>();

        sqlRowSet = mySQLBaseDao.selctQuery(querry, paramValues);

        if (sqlRowSet != null) {

            int numberOfColumns = sqlRowSet.getMetaData().getColumnCount();

            for (int i = 1; i <= numberOfColumns; i++) {
                String tempString = sqlRowSet.getMetaData().getColumnLabel(i);
                sqlRowSetColumnNames.add(tempString);
            }
            if (sqlRowSet.next()) {
                try {
                    return resultToObjectMapper(sqlRowSet, sqlRowSetColumnNames, klass.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                  logger.error(e.toString());
                }
            }
        }



        return null;

    }


}
