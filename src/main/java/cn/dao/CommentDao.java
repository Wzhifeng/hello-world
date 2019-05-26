package cn.dao;

import cn.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CommnetDao 接口
 */
public interface CommentDao {
    /**
     * 根据CommentCondition查询Comment
     * @param commentCondition
     * @return
     */
    List<Comment> queryCommentByCommentCondition(@Param("commentCondition") Comment commentCondition);

    /**
     * 插入评论
     * @param comment
     * @return
     */
    int insertComment(@Param("comment") Comment comment);

    /**
     * 根据商品id列表查询评论
     * @param productIdList
     * @return
     */
    List<Comment> queryCommentByProductIdList(@Param("productIdList") List<Integer> productIdList);
}
