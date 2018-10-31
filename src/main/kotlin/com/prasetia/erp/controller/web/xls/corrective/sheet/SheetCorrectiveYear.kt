package com.prasetia.erp.controller.web.xls.corrective.sheet

import com.prasetia.erp.pojo.corrective.CorrectiveYearData
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment

class SheetCorrectiveYear(workbook: HSSFWorkbook, tahun: String, data:List<CorrectiveYearData>){
    var sheet: HSSFSheet = workbook.createSheet("Summary $tahun")
    private var numRow:Int = 5

    private fun setColWidth(sheet: HSSFSheet):HSSFSheet{
        sheet.setColumnWidth(0, 1560)
        sheet.setColumnWidth(1, 13520)
        sheet.setColumnWidth(2, 4940)
        sheet.setColumnWidth(3, 8580)
        sheet.setColumnWidth(4, 2860)
        sheet.setColumnWidth(5, 4420)
        sheet.setColumnWidth(6, 3120)
        sheet.setColumnWidth(7, 3640)
        sheet.setColumnWidth(8, 260)
        sheet.setColumnWidth(9, 260)
        sheet.setColumnWidth(10, 4420)
        sheet.setColumnWidth(11, 5720)
        sheet.setColumnWidth(12, 2080)
        sheet.setColumnWidth(13, 4420)
        sheet.setColumnWidth(14, 2860)
        sheet.setColumnWidth(15, 2340)
        sheet.setColumnWidth(16, 10140)
        sheet.setColumnWidth(17, 4160)
        sheet.setColumnWidth(18, 6240)
        sheet.setColumnWidth(19, 4420)
        sheet.setColumnWidth(20, 4680)
        sheet.setColumnWidth(21, 2600)
        sheet.setColumnWidth(22, 3120)
        return sheet
    }

    private fun styleTableHeader(workbook: HSSFWorkbook): HSSFCellStyle {
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

    private fun fontCalibriTableContent(workbook: HSSFWorkbook): HSSFFont {
        val fontCalibriTableContent = workbook.createFont()
        fontCalibriTableContent.fontName = "Calibri"
        fontCalibriTableContent.fontHeightInPoints = 11
        fontCalibriTableContent.bold = false
        return fontCalibriTableContent
    }

    private fun styleTableContent(workbook: HSSFWorkbook):HSSFCellStyle{
        val styleTableContent = workbook.createCellStyle()
        val fontCalibriTableContent = fontCalibriTableContent(workbook)
        styleTableContent.setBorderBottom(BorderStyle.THIN)
        styleTableContent.setBorderTop(BorderStyle.THIN)
        styleTableContent.setBorderLeft(BorderStyle.THIN)
        styleTableContent.setBorderRight(BorderStyle.THIN)
        styleTableContent.setFont(fontCalibriTableContent)
        return styleTableContent
    }

    private fun styleTableContentNumber(workbook: HSSFWorkbook):HSSFCellStyle{
        val styleTableContentNumber = workbook.createCellStyle()
        styleTableContentNumber.dataFormat = HSSFDataFormat.getBuiltinFormat("#,##0.00")
        styleTableContentNumber.setBorderBottom(BorderStyle.THIN)
        styleTableContentNumber.setBorderTop(BorderStyle.THIN)
        styleTableContentNumber.setBorderLeft(BorderStyle.THIN)
        styleTableContentNumber.setBorderRight(BorderStyle.THIN)
        styleTableContentNumber.setFont(fontCalibriTableContent(workbook))
        return styleTableContentNumber
    }

    private fun styleTableContentDate(workbook: HSSFWorkbook):HSSFCellStyle{
        val styleTableContentNumber = workbook.createCellStyle()
        styleTableContentNumber.dataFormat = HSSFDataFormat.getBuiltinFormat("d-mmm-yy")
        styleTableContentNumber.setBorderBottom(BorderStyle.THIN)
        styleTableContentNumber.setBorderTop(BorderStyle.THIN)
        styleTableContentNumber.setBorderLeft(BorderStyle.THIN)
        styleTableContentNumber.setBorderRight(BorderStyle.THIN)
        styleTableContentNumber.setFont(fontCalibriTableContent(workbook))
        return styleTableContentNumber
    }

    private fun styleTableContentPercent(workbook: HSSFWorkbook):HSSFCellStyle{
        val styleTableContentNumber = workbook.createCellStyle()
        styleTableContentNumber.dataFormat = HSSFDataFormat.getBuiltinFormat("0.00%")
        styleTableContentNumber.setBorderBottom(BorderStyle.THIN)
        styleTableContentNumber.setBorderTop(BorderStyle.THIN)
        styleTableContentNumber.setBorderLeft(BorderStyle.THIN)
        styleTableContentNumber.setBorderRight(BorderStyle.THIN)
        styleTableContentNumber.setFont(fontCalibriTableContent(workbook))
        return styleTableContentNumber
    }

    private fun createHeaderXls(workbook: HSSFWorkbook, sheet:HSSFSheet){
        val styleTableHeader = styleTableHeader(workbook)

        var header = sheet.createRow(4)
        header.createCell(0).setCellValue("No")
        header.getCell(0).setCellStyle(styleTableHeader)
        header.createCell(1).setCellValue("Pembayaran")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2).setCellValue("PIC")
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3).setCellValue("Penerima Dana")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("TGL")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("Start No CA/GPR/RMB")
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6).setCellValue("Status")
        header.getCell(6).setCellStyle(styleTableHeader)
        header.createCell(7).setCellValue("Qty")
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8).setCellValue("Unit")
        header.getCell(8).setCellStyle(styleTableHeader)
        header.createCell(9).setCellValue("Price")
        header.getCell(9).setCellStyle(styleTableHeader)
        header.createCell(10).setCellValue("Start Nominal")
        header.getCell(10).setCellStyle(styleTableHeader)
        header.createCell(11).setCellValue("Site / Dept")
        header.getCell(11).setCellStyle(styleTableHeader)
        header.createCell(12).setCellValue("No Memo")
        header.getCell(12).setCellStyle(styleTableHeader)
        header.createCell(13).setCellValue("Realisasi Payment")
        header.getCell(13).setCellStyle(styleTableHeader)
        header.createCell(14).setCellValue("Start Payment")
        header.getCell(14).setCellStyle(styleTableHeader)
        header.createCell(15).setCellValue("Cust")
        header.getCell(15).setCellStyle(styleTableHeader)
        header.createCell(16).setCellValue("Nomor PO/SPK/WO/BAST")
        header.getCell(16).setCellStyle(styleTableHeader)
        header.createCell(17).setCellValue("Nilai PO")
        header.getCell(17).setCellStyle(styleTableHeader)
        header.createCell(18).setCellValue("No INV")
        header.getCell(18).setCellStyle(styleTableHeader)
        header.createCell(19).setCellValue("Nilai Penagihan INV")
        header.getCell(19).setCellStyle(styleTableHeader)
        header.createCell(20).setCellValue("Laba / Rugi")
        header.getCell(20).setCellStyle(styleTableHeader)
        header.createCell(21).setCellValue("% Laba / Rugi")
        header.getCell(21).setCellStyle(styleTableHeader)
        header.createCell(22).setCellValue("Status INV")
        header.getCell(22).setCellStyle(styleTableHeader)
    }

    private fun createDataXls(workbook: HSSFWorkbook, sheet: HSSFSheet, data: List<CorrectiveYearData>){
        val styleTableContent = styleTableContent(workbook)
        val styleTableContentNumber = styleTableContentNumber(workbook)
        val styleTableContentDate = styleTableContentDate(workbook)
        val styleTableContentPercent = styleTableContentPercent(workbook)

        var content:HSSFRow
        var numRow = this.numRow
        var numRec = 1.00

        data.forEach {
            items ->
            items.projects?.forEach {
                projects->
                projects.cash_advance?.forEach {
                    cash_advance->
                    content = sheet.createRow(numRow++)
                    val cell110 = content.createCell(10)
                    val cell13 = content.createCell(13)
                    val cell17 = content.createCell(17)
                    val cell19 = content.createCell(19)
                    val cell20 = content.createCell(20)
                    cell20.setCellType(CellType.FORMULA)
                    val cell21 = content.createCell(21)
                    cell20.setCellType(CellType.FORMULA)
                    content.createCell(0).setCellValue(numRec++)
                    content.getCell(0).setCellStyle(styleTableContent)
                    content.createCell(1).setCellValue(cash_advance.narration)
                    content.getCell(1).setCellStyle(styleTableContent)
                    content.createCell(2).setCellValue(cash_advance.pic)
                    content.getCell(2).setCellStyle(styleTableContent)
                    content.createCell(3).setCellValue(cash_advance.penerima_dana)
                    content.getCell(3).setCellStyle(styleTableContent)
                    content.createCell(4).setCellValue(cash_advance.tanggal)
                    content.getCell(4).setCellStyle(styleTableContentDate)
                    content.createCell(5).setCellValue(cash_advance.ref)
                    content.getCell(5).setCellStyle(styleTableContent)
                    content.createCell(6).setCellValue("-")
                    content.getCell(6).setCellStyle(styleTableContent)
                    content.createCell(7).setCellValue(0.00)
                    content.getCell(7).setCellStyle(styleTableContentNumber)
                    content.createCell(8).setCellValue(0.00)
                    content.getCell(8).setCellStyle(styleTableContentNumber)
                    content.createCell(9).setCellValue(0.00)
                    content.getCell(9).setCellStyle(styleTableContentNumber)
//                    content.createCell(10).setCellValue(0.00)
//                    content.getCell(10).setCellStyle(styleTableContentNumber)
                    cash_advance.amount.toDouble().let {cell110.setCellValue(it)}
                    content.getCell(10).setCellStyle(styleTableContentNumber)
                    content.createCell(11).setCellValue(projects.site_name)
                    content.getCell(11).setCellStyle(styleTableContent)
                    content.createCell(12).setCellValue(cash_advance.no_mi)
                    content.getCell(12).setCellStyle(styleTableContent)
                    cash_advance.amount.toDouble().let {cell13.setCellValue(it)}
                    content.getCell(13).setCellStyle(styleTableContentNumber)
                    content.createCell(14).setCellValue("-")
                    content.getCell(14).setCellStyle(styleTableContent)
                    content.createCell(15).setCellValue(projects.customer)
                    content.getCell(15).setCellStyle(styleTableContent)
                    content.createCell(16).setCellValue(cash_advance.no_po)
                    content.getCell(16).setCellStyle(styleTableContent)
                    cash_advance.nilai_po?.toDouble()?.let {cell17.setCellValue(it)}
                    content.getCell(17).setCellStyle(styleTableContentNumber)
                    var noInvoice = ""
                    var nilaiInvoice:Long = 0
                    var invoiceState = ""
                    cash_advance.advance_invoice!!.forEach {
                        noInvoice = it.no_inv
                        nilaiInvoice = it.nilai_invoice
                        invoiceState = it.invoice_state
                    }
                    content.createCell(18).setCellValue(noInvoice)
                    content.getCell(18).setCellStyle(styleTableContent)
                    nilaiInvoice.toDouble().let {cell19.setCellValue(it)}
                    content.getCell(19).setCellStyle(styleTableContentNumber)
                    cell20.cellFormula = "T$numRow-N$numRow"
                    content.getCell(20).setCellStyle(styleTableContentNumber)
                    cell21.cellFormula = "U$numRow/N$numRow"
                    content.getCell(21).setCellStyle(styleTableContentPercent)
                    content.createCell(22).setCellValue(invoiceState)
                    content.getCell(22).setCellStyle(styleTableContent)
                }
            }
        }
    }

    init {
        setColWidth(sheet)
        createHeaderXls(workbook, sheet)
        createDataXls(workbook, sheet, data)
    }
}