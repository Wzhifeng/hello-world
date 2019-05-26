package cn.service.serviceImpl;

import cn.dao.CommentDao;
import cn.dao.ProductDao;
import cn.dto.CommentExecution;
import cn.dto.CommentVO;
import cn.entity.Comment;
import cn.entity.Product;
import cn.entity.Shop;
import cn.enums.CommentStateEnum;
import cn.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ProductDao productDao;



    /**
     * 根据CommentCondition查询Comment
     * @param commentCondition
     * @return
     */
    @Override
    public CommentExecution getCommentByCondition(Comment commentCondition) {
        List<Comment> commentList = commentDao.queryCommentByCommentCondition(commentCondition);

        if (commentList.size() > 0) {
            return new CommentExecution(CommentStateEnum.SUCCESS, commentList);
        } else {
            return new CommentExecution(CommentStateEnum.COMMENT_INFO_NULL);
        }
    }

    /**
     * 添加客户评论
     * @param comment
     * @return
     */
    @Override
    public boolean addComment(Comment comment) {
        if (comment == null) {
            return false;
        }

        if (comment.getCommentContent() == null) {
            comment.setCommentContent("系统默认好评~");
        }

        int num = commentDao.insertComment(comment);
        if (num > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据商品查询id
     * @param shop
     * @return
     */
    @Override
    public List<CommentVO> getCustomerComments(Shop shop) {
        List<Product> productList = productDao.queryProductList(shop.getShopId());
        List<Integer> productIdList = new ArrayList<>();
        List<CommentVO> commentVOList = new ArrayList<>();

        //商铺下的产品id
        for (Product product:
                productList) {
            Integer productId = product.getProductId();
            productIdList.add(productId);
        }

        List<Comment> commentList = commentDao.queryCommentByProductIdList(productIdList);
        if (commentList != null) {
            for (Comment comment :
                    commentList) {
                Product product = productDao.queryProductById(comment.getProductId());

                //组装commentVo
                CommentVO commentVO = new CommentVO();
                commentVO.setProductName(product.getProductName());
                commentVO.setCommentContent(comment.getCommentContent());

                commentVOList.add(commentVO);
            }
        }

        if (commentVOList.size() > 0) {
            return commentVOList;
        }

        return null;
    }
}
