package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.Activity;
import com.example.novel.pojo.Feedback;
import com.example.novel.pojo.Participation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ParticipationMapper extends BaseMapper<Participation> {
    @Select("select * from participation where reader_id = #{readerId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "activityId", column = "activity_id"),
            @Result(property = "participationTime", column = "participation_time"),
            @Result(property = "participationStatus", column = "participation_status"),
            @Result(column = "activity_id", property = "activity", javaType = Activity.class,
                    one = @One(select = "com.example.novel.mapper.ActivityMapper.selectById"))
    })
    List<Participation> selectParticipationByReaderId(@Param("readerId") int readerId);

    @Insert("INSERT INTO participation (reader_id, activity_id, participation_status) " +
            "VALUES (#{readerId}, #{activityId}, #{participationStatus})")
    int insertParticipation(Participation participation);
}
