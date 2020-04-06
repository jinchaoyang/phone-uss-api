package com.renhe.cdr.service;

import com.renhe.cdr.vo.BlackPhone;
import com.renhe.cdr.vo.BlackRecordVo;
import com.renhe.cdr.vo.CdrVo;
import com.renhe.cdr.vo.VipRecordVo;
import com.renhe.utils.Constant;
import com.renhe.utils.DateUtil;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CdrService {

    @Autowired
    MongoTemplate mongoTemplate;


    @Autowired
    StringRedisTemplate redisTemplate;



    public Page<BlackRecordVo> queryBlackSheets(CdrVo vo){
        int pageNo  = vo.getPageNo() - 1;
        if(pageNo >= 0){
            Pageable pageable = PageRequest.of(pageNo,vo.getPageSize());
            Criteria criteria = Criteria.where("tenantId").is(vo.getTenantId());
            if(StringUtil.isPresent(vo.getCallee())){
                criteria = criteria.and("phone").is(vo.getCallee());
            }
            if(StringUtil.isPresent(StringUtil.trim(vo.getIsHit()))){
                criteria = criteria.and("result").is(vo.getIsHit());
            }
            Query query = Query.query(criteria).with(Sort.by(Sort.Order.desc("createTime")));
            long total = mongoTemplate.count(query,"cc_black_record_"+vo.getTenantId());
            List<BlackRecordVo> cdrList = mongoTemplate.find(query.with(pageable),BlackRecordVo.class,"cc_black_record_"+vo.getTenantId());
            Page<BlackRecordVo> pager = new PageImpl<>(cdrList,pageable,total);
            return pager;
        }else{
            return null;
        }
    }


    public Page<VipRecordVo> queryVipSheets(CdrVo vo){
        int pageNo  = vo.getPageNo() - 1;
        if(pageNo >= 0){
            Pageable pageable = PageRequest.of(pageNo,vo.getPageSize());
            Criteria criteria = Criteria.where("tenantId").is(vo.getTenantId());
            Query query = Query.query(criteria).with(Sort.by(Sort.Order.desc("createdAt")));
            long total = mongoTemplate.count(query,"cc_vip_record_"+vo.getTenantId());
            List<VipRecordVo> cdrList = mongoTemplate.find(query.with(pageable),VipRecordVo.class,"cc_vip_record_"+vo.getTenantId());
            Page<VipRecordVo> pager = new PageImpl<>(cdrList,pageable,total);
            return pager;
        }else{
            return null;
        }
    }








}
