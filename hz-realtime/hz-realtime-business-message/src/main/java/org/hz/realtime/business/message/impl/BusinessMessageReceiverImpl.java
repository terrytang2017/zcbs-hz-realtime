package org.hz.realtime.business.message.impl;

import org.hz.realtime.business.message.bean.RealTimeCollRespBean;
import org.hz.realtime.business.message.dao.OrderCollectSingleDAO;
import org.hz.realtime.business.message.dao.TChnCollectSingleLogDAO;
import org.hz.realtime.business.message.enums.BusStat;
import org.hz.realtime.business.message.enums.ReturnInfo;
import org.hz.realtime.business.message.pojo.TChnCollectSingleLogDO;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.zcbspay.platform.hz.realtime.business.message.service.BusinessMessageReceiver;
import com.zcbspay.platform.hz.realtime.business.message.service.bean.MessageRespBean;
import com.zcbspay.platform.hz.realtime.business.message.service.bean.ResultBean;

public class BusinessMessageReceiverImpl implements BusinessMessageReceiver {

    @Autowired
    private TChnCollectSingleLogDAO tChnCollectSingleLogDAO;
    @Autowired
    private OrderCollectSingleDAO orderCollectSingleDAO;

    @Override
    public ResultBean realTimeCollectionChargesReceipt(MessageRespBean messageRespBean) {
        // 更新流水记录
        RealTimeCollRespBean realTimeCollRespBean = JSONObject.parseObject(messageRespBean.getMsgBody(), RealTimeCollRespBean.class);
        TChnCollectSingleLogDO chnCollectSingleLogDO = tChnCollectSingleLogDAO.updateRealCollectLog(realTimeCollRespBean);
        // 更新订单状态
        if (BusStat.SUCCESS.getValue().equals(realTimeCollRespBean.getRspnInf().getSts())) {
            orderCollectSingleDAO.updateOrderToSuccessByTn(chnCollectSingleLogDO.getTxnseqno());
        }
        else {
            orderCollectSingleDAO.updateOrderToFailByTn(chnCollectSingleLogDO.getTxnseqno());
        }
        return new ResultBean(ReturnInfo.SUCCESS.getValue());
    }

    @Override
    public ResultBean realTimePaymentReceipt(MessageRespBean messageRespBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean discardMessage(MessageRespBean messageRespBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean busStaQryResp(MessageRespBean messageRespBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean commProcAfrmResp(MessageRespBean messageRespBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean detectResponse(MessageRespBean messageRespBean) {
        // TODO Auto-generated method stub
        return null;
    }

}