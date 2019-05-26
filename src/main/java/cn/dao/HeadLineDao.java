package cn.dao;

import cn.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

/**
 * 头条图片接口
 */
public interface HeadLineDao {
    /**
     * 根据头条顺序删除头条
     * @param priority
     */
    void deleteHeadLine(int priority);


    /**
     * 插入头条
     * @param priority
     * @param shopId
     */
    void insertHeadLine(@Param("priority") int priority,@Param("shopId") int shopId);

    /**
     * 根据头条顺序查询头条
     * @param priority
     * @return
     */
    HeadLine queryHeadLineByPriority(int priority);
}
