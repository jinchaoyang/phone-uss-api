package com.renhe.tenant.controller;

import com.alibaba.fastjson.JSON;
import com.renhe.auth.utils.TokenUtil;
import com.renhe.base.Result;
import com.renhe.tenant.entity.TenantProduct;
import com.renhe.tenant.service.TenantProductService;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value ="/tenant/product")
public class TenantProductController {

    private static final Logger logger = LoggerFactory.getLogger(TenantProductController.class);

    @Autowired
    TenantProductService tenantProductService;

    /**
     * 商品服务订购
     * @param tenantProduct
     * @return
     */
    @PostMapping(value="")
    public Result<TenantProduct> save(@RequestBody TenantProduct tenantProduct, HttpServletRequest request){
        Result<TenantProduct> result = new Result<>();
        int rcode = -1;
        try{
            String token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)) {
                tenantProduct.setCreatorId(TokenUtil.getUserId(token));
            }
            int count = tenantProductService.save(tenantProduct);
            if(count>0){
                rcode = 0;
                result.setData(tenantProduct);
            }else{
                result.setMessage("tenantProduct save failed!");
            }
        }catch(Exception e){
            logger.error("[saveException]: tenantProduct -> {}", JSON.toJSONString(tenantProduct),e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        logger.info("[save]: tenantProduct -> {}, result -> {}",JSON.toJSONString(tenantProduct),JSON.toJSONString(result));
        return result;
    }


    /**
     * 获取购买服务的列表
     * @param tenantId
     * @return
     */
    @GetMapping(value="/list")
    public Result<List<TenantProduct>> list(@RequestParam String tenantId){
        Result<List<TenantProduct>> result = new Result<>();
        int rcode = -1;
        try{
           List<TenantProduct> tenantProducts = tenantProductService.findByTenantId(tenantId);
           rcode = 0;
           result.setData(tenantProducts);
        }catch(Exception e){
            logger.error("[listException]: tenantId -> {}", tenantId,e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        logger.info("[list]: tenantId -> {}, result -> {}",tenantId,JSON.toJSONString(result));
        return result;
    }


    /**
     * 购买服务的详情
     * @param id
     * @return
     */
//    @GetMapping(value="/{id}")
//    public Result<TenantProduct> show(@PathVariable String id){
//        Result<TenantProduct> result = new Result<>();
//        int rcode = -1;
//        try{
//            TenantProduct tenantProduct = tenantProductService.findById(id);
//            rcode = 0;
//            result.setData(tenantProduct);
//        }catch(Exception e){
//            logger.error("[showException]: id -> {}", id,e);
//            result.setMessage(e.getMessage());
//        }
//        result.setCode(rcode);
//        logger.info("[show]: id -> {}, result -> {}",id,JSON.toJSONString(result));
//        return result;
//    }


    /**
     * 更新购买服务的详情
     * @param tenantProduct
     * @return
     */
    @PutMapping(value="/{id}")
    public Result<Integer> update(@RequestBody TenantProduct tenantProduct){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = tenantProductService.update(tenantProduct);
            if(count>0){
                rcode = 0;
                result.setData(count);
            }else{
                result.setMessage("tenantProduct update failed!");
            }

        }catch(Exception e){
            logger.error("updateException]: tenantProduct -> {}", JSON.toJSONString(tenantProduct),e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        logger.info("[update]: tenantProduct -> {}, result -> {}",JSON.toJSONString(tenantProduct),JSON.toJSONString(result));
        return result;
    }


    @GetMapping(value="/{tenantId}")
    public Result<TenantProduct> findByProductCode(@PathVariable String tenantId,@RequestParam String productCode){
        Result<TenantProduct> result = new Result<>();
        int rcode = -1;
        try{
            TenantProduct tenantProduct = tenantProductService.findByTenantIdAndType(tenantId,productCode);
            rcode = 0;
            result.setData(tenantProduct);
        }catch(Exception e){
            logger.error("[findByProductCodeException]: tenantId -> {}, productCode -> {}", tenantId,productCode,e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        logger.info("[findByProductCode]: tenantId -> {} , productCode -> {}, result -> {}",tenantId,productCode,JSON.toJSONString(result));
        return result;

    }

    @PostMapping(value= "/renew")
    public Result<Integer> renew(@RequestBody TenantProduct product){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = tenantProductService.renew(product);
            if(count>0){
                rcode = 0;
            }else{
                result.setMessage("续费失败，未找到订购记录");
            }

        }catch(Exception e){
            logger.error("[renewException]: product -> {} ", JSON.toJSONString(product),e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        logger.info("[renew]: product -> {}, result -> {}",JSON.toJSONString(product),JSON.toJSONString(result));
        return result;
    }

}
