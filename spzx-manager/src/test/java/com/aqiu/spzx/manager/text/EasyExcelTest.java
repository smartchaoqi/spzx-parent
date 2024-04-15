package com.aqiu.spzx.manager.text;

import com.alibaba.excel.EasyExcel;
import com.aqiu.spzx.model.vo.product.CategoryExcelVo;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelTest {
    public static void main(String[] args) {
        write();
    }

    public static void read(){
        String fileName="D:\\mm\\01.xlsx";
        ExcelListener<CategoryExcelVo> listener = new ExcelListener<>();
        EasyExcel.read(fileName, CategoryExcelVo.class, listener).sheet().doRead();
        listener.getData().forEach(System.out::println);
    }

    public static void write(){
        List<CategoryExcelVo> data=new ArrayList<>();
        data.add(new CategoryExcelVo(1L,"手机","http://www.baidu.com",0L,1,1));
        data.add(new CategoryExcelVo(2L,"电脑","http://www.baidu.com",0L,1,1));
        data.add(new CategoryExcelVo(3L,"家电","http://www.baidu.com",0L,1,1));


        String fileName="D:\\mm\\02.xlsx";
        EasyExcel.write(fileName, CategoryExcelVo.class).
                sheet("模板").doWrite(data);
    }
}
