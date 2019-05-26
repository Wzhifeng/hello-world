package cn.service;

import cn.dto.CommentExecution;
import cn.dto.CommentVO;
import cn.entity.Comment;
import cn.entity.Shop;

import java.util.List;

/**
 * CommentService 接口
 */
public interface CommentService {
    CommentExecution getCommentByCondition(Comment commentCondition);

    boolean addComment(Comment comment);

    List<CommentVO> getCustomerComments(Shop shop);
}
