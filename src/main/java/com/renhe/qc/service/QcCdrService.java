package com.renhe.qc.service;

import com.renhe.qc.entity.QcCdr;
import com.renhe.qc.vo.QcCdrVo;
import com.renhe.utils.DateUtil;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class QcCdrService {

    @Autowired
    MongoTemplate mongoTemplate;






    public Page<QcCdr> queryCallSheets(QcCdrVo vo){
        int pageNo  = vo.getPageNo() - 1;
        if(pageNo >= 0){
            Pageable pageable = PageRequest.of(pageNo,vo.getPageSize());
            Criteria criteria = Criteria.where("type").is("1");
            if(StringUtil.isPresent(vo.getCallee())){
                Pattern pattern = Pattern.compile("^.*"+vo.getCallee()+".*$",Pattern.CASE_INSENSITIVE);
                criteria = criteria.and("callee").regex(pattern);
            }
            if(StringUtil.isPresent(StringUtil.trim(vo.getHost()))){
                criteria = criteria.and("host").is(StringUtil.trim(vo.getHost()));
            }

            if(StringUtil.isPresent(StringUtil.trim(vo.getStatus()))){
                criteria = criteria.and("status").is(vo.getStatus());
            }

            String _beginAt = DateUtil.getNow("yyyyMMdd");
            if(StringUtil.isPresent(StringUtil.trim(vo.getBeginAt()))){
                _beginAt = vo.getBeginAt().replace("-","").replace(":","").replace(" ","");
            }else{
                _beginAt = _beginAt+"0000";
            }
            _beginAt = _beginAt+"00";
            String _endAt = DateUtil.getNow("yyyyMMdd");
            if(StringUtil.isPresent(StringUtil.trim(vo.getEndAt()))){
                _endAt = vo.getEndAt().replace("-","").replace(":","").replace(" ","")+"59";
            }else{
                _endAt = _endAt+"235959";
            }



            criteria.andOperator(Criteria.where("callAt").gte(_beginAt),Criteria.where("callAt").lte(_endAt));

            if(null!=vo.getIsHit()){
                criteria.and("isHit").is(vo.getIsHit());
            }

            if(StringUtil.isPresent(StringUtil.trim(vo.getWord()))){
                Pattern pattern = Pattern.compile("^.*"+vo.getWord()+".*$",Pattern.CASE_INSENSITIVE);
                criteria = criteria.and("tag").regex(pattern);
            }

            if(StringUtil.isPresent(vo.getCallerIp())){
                criteria = criteria.and("callerIp").is(StringUtil.trim(vo.getCallerIp()));
            }

            if(StringUtil.isPresent(vo.getCalleeIp())){
                criteria = criteria.and("calleeIp").is(StringUtil.trim(vo.getCalleeIp()));
            }

            if(StringUtil.isPresent(vo.getTenantId())){
                criteria = criteria.and("tenantId").is(vo.getTenantId());
            }


            Query query = Query.query(criteria).with(Sort.by(Sort.Order.desc("callAt")));
            String suffix = _beginAt.substring(0,8);

            long total = mongoTemplate.count(query,"qc_cdr_"+suffix);
            List<QcCdr> cdrList = mongoTemplate.find(query.with(pageable), QcCdr.class,"qc_cdr_"+suffix);
            Page<QcCdr> pager = new PageImpl<>(cdrList,pageable,total);
            return pager;
        }else{
            return null;
        }
    }


    public List<QcCdr> queryNotCheckCdr(){
        QcCdrVo vo = new QcCdrVo();
        vo.setPageNo(1);
        vo.setPageSize(300);
        int pageNo  = vo.getPageNo() - 1;
        if(pageNo >= 0){
            Pageable pageable = PageRequest.of(pageNo,vo.getPageSize());
            Criteria criteria = Criteria.where("status").is("0");

            Query query = Query.query(criteria).with(Sort.by(Sort.Order.asc("callAt")));
            String suffix = DateUtil.getNow("yyyyMMdd");

            List<QcCdr> cdrList = mongoTemplate.find(query.with(pageable), QcCdr.class,"qc_cdr_"+suffix);

            return cdrList;
        }else{
            return null;
        }
    }




}
