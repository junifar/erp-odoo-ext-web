package com.prasetia.erp.controller.web.xls.cme.sheet

import com.prasetia.erp.pojo.cme.CmeSummaryYearProjectTypeCustData
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment

class SheetSummary(workbook:HSSFWorkbook, data:List<CmeSummaryYearProjectTypeCustData>){
    var sheet: HSSFSheet = workbook.createSheet("Summary")
    var numRow:Int = 4

    private fun styleHeader(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleHeader = workbook.createCellStyle()
        val fontCalibri = workbook.createFont()
        fontCalibri.fontName = "Calibri"
        fontCalibri.bold = true
        fontCalibri.fontHeightInPoints = 11
        styleHeader.setFont(fontCalibri)
        return styleHeader
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

    private fun styleTableContent(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleTableContent = workbook.createCellStyle()
        val fontCalibriTableContent = fontCalibriTableContent(workbook)
        styleTableContent.setBorderBottom(BorderStyle.THIN)
        styleTableContent.setBorderTop(BorderStyle.THIN)
        styleTableContent.setBorderLeft(BorderStyle.THIN)
        styleTableContent.setBorderRight(BorderStyle.THIN)
        styleTableContent.setFont(fontCalibriTableContent)
        return styleTableContent
    }

    private fun styleTableContentNumber(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleTableContentNumber = workbook.createCellStyle()
        styleTableContentNumber.dataFormat = HSSFDataFormat.getBuiltinFormat("#,##0.00")
        styleTableContentNumber.setBorderBottom(BorderStyle.THIN)
        styleTableContentNumber.setBorderTop(BorderStyle.THIN)
        styleTableContentNumber.setBorderLeft(BorderStyle.THIN)
        styleTableContentNumber.setBorderRight(BorderStyle.THIN)
        styleTableContentNumber.setFont(fontCalibriTableContent(workbook))
        return styleTableContentNumber
    }

    private fun styleTableContentPercent(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleTableContentNumber = workbook.createCellStyle()
        styleTableContentNumber.dataFormat = HSSFDataFormat.getBuiltinFormat("0.00%")
        styleTableContentNumber.setBorderBottom(BorderStyle.THIN)
        styleTableContentNumber.setBorderTop(BorderStyle.THIN)
        styleTableContentNumber.setBorderLeft(BorderStyle.THIN)
        styleTableContentNumber.setBorderRight(BorderStyle.THIN)
        styleTableContentNumber.setFont(fontCalibriTableContent(workbook))
        return styleTableContentNumber
    }

    private fun styleTableHeaderNumber(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleTableHeader = workbook.createCellStyle()
        val fontCalibriTableHeader = workbook.createFont()
        fontCalibriTableHeader.fontName = "Calibri"
        fontCalibriTableHeader.fontHeightInPoints = 11
        fontCalibriTableHeader.bold = true
        styleTableHeader.dataFormat = HSSFDataFormat.getBuiltinFormat("#,##0.00")
        styleTableHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND)
        styleTableHeader.fillForegroundColor = HSSFColor.GREY_25_PERCENT.index
        styleTableHeader.setBorderBottom(BorderStyle.THIN)
        styleTableHeader.setBorderTop(BorderStyle.THIN)
        styleTableHeader.setBorderLeft(BorderStyle.THIN)
        styleTableHeader.setBorderRight(BorderStyle.THIN)
        styleTableHeader.setFont(fontCalibriTableHeader)
        return styleTableHeader
    }

    private fun styleTableHeaderPercent(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleTableHeader = workbook.createCellStyle()
        val fontCalibriTableHeader = workbook.createFont()
        fontCalibriTableHeader.fontName = "Calibri"
        fontCalibriTableHeader.fontHeightInPoints = 11
        fontCalibriTableHeader.bold = true
        styleTableHeader.dataFormat = HSSFDataFormat.getBuiltinFormat("0.00%")
        styleTableHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND)
        styleTableHeader.fillForegroundColor = HSSFColor.GREY_25_PERCENT.index
        styleTableHeader.setBorderBottom(BorderStyle.THIN)
        styleTableHeader.setBorderTop(BorderStyle.THIN)
        styleTableHeader.setBorderLeft(BorderStyle.THIN)
        styleTableHeader.setBorderRight(BorderStyle.THIN)
        styleTableHeader.setFont(fontCalibriTableHeader)
        return styleTableHeader
    }

    init {
        setColWidth(sheet)
        createHeaderXls(workbook, sheet, 2018)
        createDataXls(workbook, sheet, data)
    }

    private fun createDataXls(workbook: HSSFWorkbook, sheet: HSSFSheet, data: List<CmeSummaryYearProjectTypeCustData>) {
        val styleTableContent = styleTableContent(workbook)
        val styleTableContentNumber = styleTableContentNumber(workbook)
        val styleTableHeader = styleTableHeader(workbook)
        val styleTableHeaderPercent = styleTableHeaderPercent(workbook)
        val styleTableHeaderNumber = styleTableHeaderNumber(workbook)
        val styleTableContentPercent = styleTableContentPercent(workbook)

        var content:HSSFRow
        var numRow = this.numRow

        data.forEach {
            content = sheet.createRow(numRow++)
            val cell2 = content.createCell(2)
            val cell3 = content.createCell(3)
            val cell4 = content.createCell(4)
            val cell5 = content.createCell(5)
            val cell6 = content.createCell(6)
            cell6.setCellType(CellType.FORMULA)
            val cell7 = content.createCell(7)
            val cell8 = content.createCell(8)
            val cell9 = content.createCell(9)
            cell9.setCellType(CellType.FORMULA)
            val cell10 = content.createCell(10)
            cell10.setCellType(CellType.FORMULA)
            val cell11 = content.createCell(11)
            cell11.setCellType(CellType.FORMULA)
            val cell12 = content.createCell(12)
            cell12.setCellType(CellType.FORMULA)
            val cell13 = content.createCell(13)
            cell13.setCellType(CellType.FORMULA)

            content.createCell(1).setCellValue(it.customer)
            content.getCell(1).setCellStyle(styleTableContent)
            it.jumlah_site.toDouble().let { it1-> cell2.setCellValue(it1)}
            content.getCell(2).setCellStyle(styleTableContentNumber)
            it.estimate_po.toDouble().let { it1 -> cell3.setCellValue(it1)}
            content.getCell(3).setCellStyle(styleTableContentNumber)
            it.nilai_po.toDouble().let { it1-> cell4.setCellValue(it1)}
            content.getCell(4).setCellStyle(styleTableContentNumber)
            it.nilai_invoice.toDouble().let { it1-> cell5.setCellValue(it1)}
            content.getCell(5).setCellStyle(styleTableContentNumber)
            cell6.cellFormula = "F$numRow/E$numRow"
            content.getCell(6).setCellStyle(styleTableContentPercent)
            it.nilai_budget.toDouble().let { it1-> cell7.setCellValue(it1)}
            content.getCell(7).setCellStyle(styleTableContentNumber)
            it.realisasi_budget.toDouble().let { it1-> cell8.setCellValue(it1)}
            content.getCell(8).setCellStyle(styleTableContentNumber)
            cell9.cellFormula = "I$numRow/H$numRow"
            content.getCell(9).setCellStyle(styleTableContentPercent)
            cell10.cellFormula = "E$numRow-F$numRow"
            content.getCell(10).setCellStyle(styleTableContentNumber)
            cell11.cellFormula = "I$numRow/F$numRow"
            content.getCell(11).setCellStyle(styleTableContentPercent)
            cell12.cellFormula = "H$numRow/E$numRow"
            content.getCell(12).setCellStyle(styleTableContentPercent)
            cell13.cellFormula = "1-M$numRow"
            content.getCell(13).setCellStyle(styleTableContentPercent)
        }
    }

    private fun createHeaderXls(workbook: HSSFWorkbook, sheet: HSSFSheet, tahun:Long) {
        val styleHeader = styleHeader(workbook)
        val styleTableHeader = styleTableHeader(workbook)

        var header = sheet.createRow(0)
        header.createCell(1).setCellValue("SACME - B2S 2018 $tahun")
        header.getCell(1).setCellStyle(styleHeader)

        header = sheet.createRow(2)
        header.createCell(1).setCellValue("Cust")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2).setCellValue("Jumlah Site")
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3).setCellValue("Estimasi Nilai PO")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("Nilai PO")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("Nilai INV")
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6).setCellValue("%")
        header.getCell(6).setCellStyle(styleTableHeader)
        header.createCell(7).setCellValue("Niai Budget")
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8).setCellValue("Realisasi Budget")
        header.getCell(8).setCellStyle(styleTableHeader)
        header.createCell(9).setCellValue("%")
        header.getCell(9).setCellStyle(styleTableHeader)
        header.createCell(10).setCellValue("Estimasi yang Belum Tetagih")
        header.getCell(10).setCellStyle(styleTableHeader)
        header.createCell(11).setCellValue("Realisasi Budget : Penagihan")
        header.getCell(11).setCellStyle(styleTableHeader)
        header.createCell(12).setCellValue("Nilai Budget : Nilai PO")
        header.getCell(12).setCellStyle(styleTableHeader)
        header.createCell(13).setCellValue("Potensi")
        header.getCell(13).setCellStyle(styleTableHeader)

        header = sheet.createRow(3)
        header.createCell(1).setCellValue("")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2).setCellValue("")
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3).setCellValue("")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("A")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("B")
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6).setCellValue("B:A")
        header.getCell(6).setCellStyle(styleTableHeader)
        header.createCell(7).setCellValue("C")
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8).setCellValue("D")
        header.getCell(8).setCellStyle(styleTableHeader)
        header.createCell(9).setCellValue("D:C")
        header.getCell(9).setCellStyle(styleTableHeader)
        header.createCell(10).setCellValue("A-B")
        header.getCell(10).setCellStyle(styleTableHeader)
        header.createCell(11).setCellValue("D:B")
        header.getCell(11).setCellStyle(styleTableHeader)
        header.createCell(12).setCellValue("C:A")
        header.getCell(12).setCellStyle(styleTableHeader)
        header.createCell(13).setCellValue("Profit /Loss")
        header.getCell(13).setCellStyle(styleTableHeader)
    }

    private fun setColWidth(sheet: HSSFSheet):HSSFSheet {
        //1:260
        sheet.setColumnWidth(0, 520)
        sheet.setColumnWidth(1, 3640)
        sheet.setColumnWidth(2, 2600)
        sheet.setColumnWidth(3, 4420)
        sheet.setColumnWidth(4, 4680)
        sheet.setColumnWidth(5, 4940)
        sheet.setColumnWidth(6, 2080)
        sheet.setColumnWidth(7, 4680)
        sheet.setColumnWidth(8, 4940)
        sheet.setColumnWidth(9, 1820)
        sheet.setColumnWidth(10, 6760)
        sheet.setColumnWidth(11, 7280)
        sheet.setColumnWidth(12, 5200)
        sheet.setColumnWidth(13, 3120)
        return sheet
    }
}