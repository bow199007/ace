package com.huacainfo.ace.face.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.huacainfo.ace.face.model.Person;
import com.huacainfo.ace.face.vo.PersonQVo;
import com.huacainfo.ace.face.vo.PersonVo;

public interface PersonDao {
    int deleteByPrimaryKey(String PersonId);

    int insert(Person record);


    PersonVo selectByPrimaryKey(String PersonId);


    int updateByPrimaryKey(Person record);
    
    List<PersonVo> findList(@Param("condition") PersonQVo condition,
			@Param("start") int start, @Param("limit") int limit,
			@Param("orderBy") String orderBy);

	int findCount(@Param("condition") PersonQVo condition);

	int isExit(Person record);

	int updatePersonFaceToken(@Param("id") String id,@Param("faceFoken") String faceFoken);

    int updatePersonAttributes(@Param("id") String id,@Param("attributes") String attributes);

    int updatePersonFaceTokenAttributes(@Param("id") String id,@Param("faceFoken") String faceFoken,@Param("attributes") String attributes);

}