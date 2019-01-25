package com.prasetia.erp.controller.web.xls.taxinfo.sheet

import com.prasetia.erp.pojo.taxinfo.TaxInvoiceData
import com.prasetia.erp.utils.toSimpleDate
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment

class SheetTax(workbook: HSSFWorkbook, tahun:String, data: List<TaxInvoiceData>){
    var sheet = workbook.createSheet("Tax Invoice")

    private var numRow:Int = 4

    fun styleHeader(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleHeader = workbook.createCellStyle()
        val fontCalibri = workbook.createFont()
        fontCalibri.fontName = "Calibri"
        fontCalibri.bold = true
        fontCalibri.fontHeightInPoints = 11
        styleHeader.setFont(fontCalibri)
        return styleHeader
    }

    fun fontCalibriTableContent(workbook: HSSFWorkbook): HSSFFont {
        val fontCalibriTableContent = workbook.createFont()
        fontCalibriTableContent.fontName = "Calibri"
        fontCalibriTableContent.fontHeightInPoints = 11
        fontCalibriTableContent.bold = false
        return fontCalibriTableContent
    }

    fun styleTableHeader(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleTableHeader = workbook.createCellStyle()
        val fontCalibriTableHeader = workbook.createFont()
        fontCalibriTableHeader.fontName = "Calibri"
        fontCalibriTableHeader.fontHeightInPoints = 11
        fontCalibriTableHeader.bold = true
        styleTableHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND)
        styleTableHeader.fillForegroundColor = HSSFColor.GREY_25_PERCENT.index
        styleTableHeader.setAlignment(HorizontalAlignment.CENTER)
        styleTableHeader.setBorderBottom(BorderStyle.THIN)
        styleTableHeader.setBorderTop(BorderStyle.THIN)
        styleTableHeader.setBorderLeft(BorderStyle.THIN)
        styleTableHeader.setBorderRight(BorderStyle.THIN)
        styleTableHeader.setFont(fontCalibriTableHeader)
        return styleTableHeader
    }

    fun styleTableContent(workbook: HSSFWorkbook):HSSFCellStyle{
        val styleTableContent = workbook.createCellStyle()
        val fontCalibriTableContent = fontCalibriTableContent(workbook)
        styleTableContent.setBorderBottom(BorderStyle.THIN)
        styleTableContent.setBorderTop(BorderStyle.THIN)
        styleTableContent.setBorderLeft(BorderStyle.THIN)
        styleTableContent.setBorderRight(BorderStyle.THIN)
        styleTableContent.setFont(fontCalibriTableContent)
        return styleTableContent
    }

    fun styleTableContentNumber(workbook: HSSFWorkbook):HSSFCellStyle{
        val styleTableContentNumber = workbook.createCellStyle()
        styleTableContentNumber.dataFormat = HSSFDataFormat.getBuiltinFormat("#,##0.00")
        styleTableContentNumber.setBorderBottom(BorderStyle.THIN)
        styleTableContentNumber.setBorderTop(BorderStyle.THIN)
        styleTableContentNumber.setBorderLeft(BorderStyle.THIN)
        styleTableContentNumber.setBorderRight(BorderStyle.THIN)
        styleTableContentNumber.setFont(fontCalibriTableContent(workbook))
        return styleTableContentNumber
    }

    fun setColWidth(sheet: HSSFSheet):HSSFSheet{
        sheet.setColumnWidth(0, 6500)
        sheet.setColumnWidth(1, 7800)
        sheet.setColumnWidth(2, 7800)
        sheet.setColumnWidth(3, 10400)
        sheet.setColumnWidth(4, 2600)
        sheet.setColumnWidth(5, 5200)
        sheet.setColumnWidth(6, 5200)
        sheet.setColumnWidth(7, 5200)
        sheet.setColumnWidth(8, 5980)
        sheet.setColumnWidth(9, 5200)
        return sheet
    }

    fun createHeaderXls(workbook: HSSFWorkbook, sheet: HSSFSheet, year:String){
        val styleHeader = styleHeader(workbook)
        val styleTableHeader = styleTableHeader(workbook)

        var header = sheet.createRow(1)
        header.createCell(0).setCellValue("Report Pajak Periode $year")
        header.getCell(0).setCellStyle(styleHeader)

        header = sheet.createRow(3)
        header.createCell(0).setCellValue("Tanggal Pembayaran")
        header.getCell(0).setCellStyle(styleTableHeader)
        header.createCell(1).setCellValue("Nomor Faktur")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2).setCellValue("Nomor Invoice")
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3).setCellValue("Customer")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("Status")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("Nilai Pembayaran")
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6).setCellValue("% Tax")
        header.getCell(6).setCellStyle(styleTableHeader)
        header.createCell(7).setCellValue("Nilai Pajak")
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8).setCellValue("Pajak")
        header.getCell(8).setCellStyle(styleTableHeader)
        header.createCell(9).setCellValue("Ref")
        header.getCell(9).setCellStyle(styleTableHeader)
    }

    fun createDataXls(workbook: HSSFWorkbook, sheet: HSSFSheet, data:List<TaxInvoiceData>){
        val styleTableContent = styleTableContent(workbook)
        val styleTableContentNumber = styleTableContentNumber(workbook)

        var content: HSSFRow
        var numRow = this.numRow
        data.forEach {
            content = sheet.createRow(numRow++)

            val cell5 = content.createCell(5)
            val cell6 = content.createCell(6)
            val cell7 = content.createCell(7)

            content.createCell(0).setCellValue(toSimpleDate(it.tanggal_pembayaran))
            content.getCell(0).setCellStyle(styleTableContent)
            content.createCell(1).setCellValue(it.nomor_faktur)
            content.getCell(1).setCellStyle(styleTableContent)
            content.createCell(2).setCellValue(it.invoice_no)
            content.getCell(2).setCellStyle(styleTableContent)
            content.createCell(3).setCellValue(it.customer_name)
            content.getCell(3).setCellStyle(styleTableContent)
            content.createCell(4).setCellValue(it.state)
            content.getCell(4).setCellStyle(styleTableContent)
            content.getCell(5).setCellStyle(styleTableContentNumber)
            it.subtotal_original?.toDouble()?.let {  it1-> cell5.setCellValue(it1)}
            content.getCell(6).setCellStyle(styleTableContentNumber)
            it.tax_percentage?.toDouble()?.let {  it1-> cell6.setCellValue(it1)}
            content.getCell(7).setCellStyle(styleTableContentNumber)
            it.tax_amount?.toDouble()?.let {  it1-> cell7.setCellValue(it1)}
            content.createCell(8).setCellValue(it.name)
            content.getCell(8).setCellStyle(styleTableContent)
            content.createCell(9).setCellValue(it.ref)
            content.getCell(9).setCellStyle(styleTableContent)
        }
    }

    init{
        setColWidth(sheet)
        createHeaderXls(workbook, sheet, tahun)
        createDataXls(workbook, sheet, data)
    }
}
