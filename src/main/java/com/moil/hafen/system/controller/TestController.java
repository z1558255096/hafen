package com.moil.hafen.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.system.domain.Test;
import com.moil.hafen.system.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("test")
public class TestController extends BaseController {

    private String message;

    @Resource
    private TestService testService;

    @GetMapping
    public Map<String, Object> findTests(QueryRequest request) {
        Page<Test> page = new Page<>(request.getPageNum(), request.getLimit());
        return getDataTable(testService.page(page, null));
    }

    /**
     * 生成 Excel导入模板
     */
//    @PostMapping("template")
//    public void generateImportTemplate(HttpServletResponse response) {
//        // 构建数据
//        List<Test> list = new ArrayList<>();
//        IntStream.range(0, 20).forEach(i -> {
//            Test test = new Test();
//            test.setField1("字段1");
//            test.setField2(i + 1);
//            test.setField3("mrbird" + i + "@gmail.com");
//            list.add(test);
//        });
//        // 构建模板
//        ExcelKit.$Export(Test.class, response).downXlsx(list, true);
//    }

    /**
     * 导入Excel数据，并批量插入 T_TEST表
     */
//    @PostMapping("import")
//    public FebsResponse importExcels(@RequestParam("file") MultipartFile file) throws FebsException {
//        try {
//            if (file.isEmpty()) {
//                throw new FebsException("导入数据为空");
//            }
//            String filename = file.getOriginalFilename();
//            if (!StringUtils.endsWith(filename, ".xlsx")) {
//                throw new FebsException("只支持.xlsx类型文件导入");
//            }
//            // 开始导入操作
//            long beginTimeMillis = System.currentTimeMillis();
//            final List<Test> data = Lists.newArrayList();
//            final List<Map<String, Object>> error = Lists.newArrayList();
//            ExcelKit.$Import(Test.class).readXlsx(file.getInputStream(), new ExcelReadHandler<Test>() {
//                @Override
//                public void onSuccess(int sheet, int row, Test test) {
//                    // 数据校验成功时，加入集合
//                    test.setCreateTime(new Date());
//                    data.add(test);
//                }
//                @Override
//                public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
//                    // 数据校验失败时，记录到 error集合
//                    error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
//                }
//            });
//            if (!data.isEmpty()) {
//                // 将合法的记录批量入库
//                this.testService.batchInsert(data);
//            }
//            long time = ((System.currentTimeMillis() - beginTimeMillis));
//            ImmutableMap<String, Object> result = ImmutableMap.of(
//                    "time", time,
//                    "data", data,
//                    "error", error
//            );
//            return new FebsResponse().data(result);
//        } catch (Exception e) {
//            message = "导入Excel数据失败," + e.getMessage();
//            log.error(message);
//            throw new FebsException(message);
//        }
//    }

    /**
     * 导出 Excel
     */
//    @PostMapping("export")
//    public void export(HttpServletResponse response) throws FebsException {
//        try {
//            List<Test> list = this.testService.findTests();
//            ExcelKit.$Export(Test.class, response).downXlsx(list, false);
//        } catch (Exception e) {
//            message = "导出Excel失败";
//            log.error(message, e);
//            throw new FebsException(message);
//        }
//    }
}
