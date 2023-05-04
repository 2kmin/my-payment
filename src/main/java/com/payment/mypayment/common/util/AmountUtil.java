package com.payment.mypayment.common.util;

import com.payment.mypayment.controller.common.dto.Product;
import com.payment.mypayment.controller.common.dto.AmountInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmountUtil {

    public static AmountInfo getAmountInfo(List<Product> productList) {

        AmountInfo amountInfo = new AmountInfo();
        List<AmountInfo.ProductTaxInfo> productTaxInfoList = new ArrayList<>();

        for(Product product : productList) {
            Map<String, Integer> taxInfo = getTaxInfo(product.getAmount(), product.getTaxType());

            int taxAmount = taxInfo.get("taxAmount");
            int taxFreeAmount = taxInfo.get("taxFreeAmount");
            int taxSupplyAmount = taxInfo.get("taxSupplyAmount");
            int vatAmount = taxInfo.get("vatAmount");

            AmountInfo.ProductTaxInfo productTaxInfo = AmountInfo.ProductTaxInfo.builder()
                    .product(product)
                    .taxAmount(taxAmount)
                    .taxFreeAmount(taxFreeAmount)
                    .taxSupplyAmount(taxSupplyAmount)
                    .vatAmount(vatAmount)
                    .build();

            amountInfo.setTransactionAmount(amountInfo.getTransactionAmount() + product.getAmount());
            amountInfo.setTotalTaxAmount(amountInfo.getTotalTaxAmount() + taxAmount);
            amountInfo.setTotalTaxFreeAmount(amountInfo.getTotalTaxFreeAmount() + taxFreeAmount);
            amountInfo.setTotalTaxSupplyAmount(amountInfo.getTotalTaxSupplyAmount() + taxSupplyAmount);
            amountInfo.setTotalVatAmount(amountInfo.getTotalVatAmount() + vatAmount);
            productTaxInfoList.add(productTaxInfo);
        }
        amountInfo.setProductTaxInfoList(productTaxInfoList);

        return amountInfo;
    }

    public static Map<String, Integer> getTaxInfo(int amount, String taxType){
        Map<String, Integer> map = new HashMap<>();

        if(taxType.equalsIgnoreCase("tax")){
            int vatAmount = (int) Math.round(amount * 0.1);
            map.put("taxAmount", amount);
            map.put("taxFreeAmount", 0);
            map.put("taxSupplyAmount",amount - vatAmount);
            map.put("vatAmount", vatAmount);
        }else{
            map.put("taxAmount", 0);
            map.put("taxFreeAmount", amount);
            map.put("taxSupplyAmount",0);
            map.put("vatAmount", 0);
        }
        return map;
    }

}
