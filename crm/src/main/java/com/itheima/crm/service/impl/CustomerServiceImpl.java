package com.itheima.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;


/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:52:51 <br/>       
 */
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerrepository;
    
    @Override
    public List<Customer> findAll() {
       
        return customerrepository.findAll();
    }

    @Override
    public List<Customer> findUnassociationCustomer() {
        
        return customerrepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findAssociationCustomer(String fixedAreaId) {
          
        return customerrepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void assignCustomers2FixedArea(String fixedAreaId, Long[] customerIds) {
          
        if(StringUtils.isNotEmpty(fixedAreaId)){
            //先根据定区id把所有定区的客户全部解绑
            customerrepository.unBindByFixedAreaId(fixedAreaId);
            //再把需要进行绑定的客户进行定区绑定
            if(customerIds != null && customerIds.length>0){
                for (Long id : customerIds) {
                    customerrepository.BindByFixedAreaById(fixedAreaId,id);
                }
                
            }
        }
    }

}
  
