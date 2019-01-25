package com.prasetia.erp.controller.web.xls.taxinfo

import com.prasetia.erp.controller.web.xls.taxinfo.sheet.SheetTax
import com.prasetia.erp.pojo.taxinfo.TaxInvoiceData
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import javax.servlet.http.HttpServletResponse

class XlsTaxInfo(response: HttpServletResponse, tahun:String, data:List<TaxInvoiceData>){
    private var workbook = HSSFWorkbook()
    init {
        SheetTax(workbook, tahun, data)
        val out = response.outputStream
        workbook.write(out)
        out.flush()
        out.close()
        workbook.close()
    }
}
