package com.prasetia.erp.controller.web.xls.department

import com.prasetia.erp.controller.web.xls.department.sheet.SheetByDate
import com.prasetia.erp.controller.web.xls.department.sheet.SheetSummary
import com.prasetia.erp.pojo.department.*
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import javax.servlet.http.HttpServletResponse

class XlsDepartment(response: HttpServletResponse, periode: String, department_id: String, data: List<DepartmentBudgetYearData>) {
    private var workbook: HSSFWorkbook = HSSFWorkbook()
    var Detail1: List<DepartmentBudgetData>? = mutableListOf()
    var Detail2: List<DepartmentBudgetDetailData>? = mutableListOf()
    var Detail3: List<DepartmentBudgetRealisasiData>? = mutableListOf()

    init {
        SheetSummary(workbook, data, periode)

        SheetByDate(workbook,1,data)
        SheetByDate(workbook,2,data)
        SheetByDate(workbook,3,data)
        SheetByDate(workbook,4,data)
        SheetByDate(workbook,5,data)
        SheetByDate(workbook,6,data)
        SheetByDate(workbook,7,data)
        SheetByDate(workbook, 8,data)
        SheetByDate(workbook, 9,data)
        SheetByDate(workbook,10,data)
        SheetByDate(workbook,11,data)
        SheetByDate(workbook,12,data)



        val out = response.outputStream
        workbook.write(out)
        out.flush()
        out.close()
        workbook.close()
    }
    }