//package com.chunhe.sojourn.web;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.annotation.ExcelIgnore;
//import com.alibaba.excel.annotation.ExcelProperty;
//import com.alibaba.excel.util.ListUtils;
//import com.alibaba.excel.write.merge.LoopMergeStrategy;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * @program: chunhe
// * @description:
// * @author: Moil
// * @create: 2022-07-14 20:17
// **/
//public class Test {
//
//    public static void main(String[] args) {
//        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
//        EasyExcel.write("D:\\test\\mergeWrite" + System.currentTimeMillis() + ".xlsx",DemoData.class).registerWriteHandler(loopMergeStrategy).sheet("模板").doWrite(new Test().data());
//    }
//    private List<DemoData> data() {
//        List<DemoData> list = ListUtils.newArrayList();
//        for (int i = 0; i < 10; i++) {
//            DemoData data = new DemoData();
//            data.setString("字符串" + i);
//            data.setDate(new Date());
//            data.setDoubleData(0.56);
//            list.add(data);
//        }
//        return list;
//    }
//
//    @Data
//    @EqualsAndHashCode
//    class DemoData {
//        @ExcelProperty("字符串标题")
//        private String string;
//        @ExcelProperty("日期标题")
//        private Date date;
//        @ExcelProperty("数字标题")
//        private Double doubleData;
//        /**
//         * 忽略这个字段
//         */
//        @ExcelIgnore
//        private String ignore;
//    }
//
//}
