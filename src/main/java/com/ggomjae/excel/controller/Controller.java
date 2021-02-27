package com.ggomjae.excel.controller;

@Controller
public class Controller {

    @Autowired
    private UserServices service;

    // 뷰에서는 location.href (button) 으로 onclick 하면 다운로드됨.
    /*
        참고 : https://www.codejava.net/frameworks/spring-boot/export-data-to-excel-example
              https://www.youtube.com/watch?v=BtUdl9pZwR0

        JPA 쪽은 알아서 findAll. Entity가 아닌 DTO도 가능
     */

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = service.listAll();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);
    }

}