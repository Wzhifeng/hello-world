package cn.service;

import cn.entity.HeadLine;
import cn.enums.HeadLineStateEnum; /**
 * HeadLineService接口
 */
public interface HeadLineService {
    boolean modifyHeadLine(int headLine1Shop, int headLine2Shop, int headLine3Shop);

    HeadLine getHeadLine(int priority);
}
