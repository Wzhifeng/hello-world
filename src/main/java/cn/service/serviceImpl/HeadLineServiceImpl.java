package cn.service.serviceImpl;

import cn.dao.HeadLineDao;
import cn.entity.HeadLine;
import cn.enums.HeadLineStateEnum;
import cn.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * HeadLineService 实现类
 */
@Service
@Transactional
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;

    /**
     * 更改头条图
     * @param headLine1Shop
     * @param headLine2Shop
     * @param headLine3Shop
     * @return
     */
    @Override
    public boolean modifyHeadLine(int headLine1Shop, int headLine2Shop, int headLine3Shop) {

        headLineDao.deleteHeadLine(HeadLineStateEnum.HEAD_LINE_1.getState());
        headLineDao.insertHeadLine(HeadLineStateEnum.HEAD_LINE_1.getState(), headLine1Shop);

        headLineDao.deleteHeadLine(HeadLineStateEnum.HEAD_LINE_2.getState());
        headLineDao.insertHeadLine(HeadLineStateEnum.HEAD_LINE_2.getState(), headLine2Shop);

        headLineDao.deleteHeadLine(HeadLineStateEnum.HEAD_LINE_3.getState());
        headLineDao.insertHeadLine(HeadLineStateEnum.HEAD_LINE_3.getState(), headLine3Shop);

        return true;
    }

    /**
     * 根据头条顺序获取头条信息
     * @param priority
     * @return
     */
    @Override
    public HeadLine getHeadLine(int priority) {
        return headLineDao.queryHeadLineByPriority(priority);
    }
}
