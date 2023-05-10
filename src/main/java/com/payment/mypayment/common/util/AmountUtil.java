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

        int transactionAmount = 0,
                totalCount = 0,
                totalProductAmount = 0,
                totalDiscountAmount = 0,
                totalTaxAmount = 0,
                totalTaxFreeAmount = 0,
                totalTaxSupplyAmount = 0,
                totalVatAmount = 0;

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

            transactionAmount += product.getAmount();
            totalCount += product.getCount();
            totalProductAmount += product.getAmount();
            totalDiscountAmount += product.getDiscountAmount();
            totalTaxAmount += taxAmount;
            totalTaxFreeAmount += taxFreeAmount;
            totalTaxSupplyAmount += taxSupplyAmount;
            totalVatAmount += vatAmount;
            productTaxInfoList.add(productTaxInfo);
        }
        amountInfo.setTransactionAmount(transactionAmount);
        amountInfo.setTotalCount(totalCount);
        amountInfo.setTotalProductAmount(totalProductAmount);
        amountInfo.setTotalDiscountAmount(totalDiscountAmount);
        amountInfo.setTotalTaxAmount(totalTaxAmount);
        amountInfo.setTotalTaxFreeAmount(totalTaxFreeAmount);
        amountInfo.setTotalTaxSupplyAmount(totalTaxSupplyAmount);
        amountInfo.setTotalVatAmount(totalVatAmount);
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
