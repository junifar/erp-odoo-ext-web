package com.prasetia.erp.controller.web.xls.corrective.sheet

import com.prasetia.erp.pojo.corrective.CorrectiveYearData
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.util.CellRangeAddress

class SheetSummary(workbook:HSSFWorkbook, data: List<CorrectiveYearData>){
    var sheet: HSSFSheet = workbook.createSheet("Summary")
    var numRow:Int = 5

    fun styleHeader(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleHeader = workbook.createCellStyle()
        val fontCalibri = workbook.createFont()
        fontCalibri.fontName = "Calibri"
        fontCalibri.bold = true
        fontCalibri.fontHeightInPoints = 11
        styleHeader.setFont(fontCalibri)
        return styleHeader
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

    fun fontCalibriTableContent(workbook: HSSFWorkbook): HSSFFont {
        val fontCalibriTableContent = workbook.createFont()
        fontCalibriTableContent.fontName = "Calibri"
        fontCalibriTableContent.fontHeightInPoints = 11
        fontCalibriTableContent.bold = false
        return fontCalibriTableContent
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

    fun styleTableContentPercent(workbook: HSSFWorkbook):HSSFCellStyle{
        val styleTableContentNumber = workbook.createCellStyle()
        styleTableContentNumber.dataFormat = HSSFDataFormat.getBuiltinFormat("0.00%")
        styleTableContentNumber.setBorderBottom(BorderStyle.THIN)
        styleTableContentNumber.setBorderTop(BorderStyle.THIN)
        styleTableContentNumber.setBorderLeft(BorderStyle.THIN)
        styleTableContentNumber.setBorderRight(BorderStyle.THIN)
        styleTableContentNumber.setFont(fontCalibriTableContent(workbook))
        return styleTableContentNumber
    }

    fun styleTableHeaderNumber(workbook: HSSFWorkbook):HSSFCellStyle{
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

    fun styleTableHeaderPercent(workbook: HSSFWorkbook):HSSFCellStyle{
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

    fun createHeaderXls(workbook: HSSFWorkbook, sheet:HSSFSheet, year:String){
        val styleHeader = styleHeader(workbook)
        val styleTableHeader = styleTableHeader(workbook)

        var header = sheet.createRow(1)
        header.createCell(1).setCellValue("Last Update 20 September 2018")
        header.getCell(1).setCellStyle(styleHeader)

        header = sheet.createRow(3)
        header.createCell(1).setCellValue("Resume Corrective Januari - Desember $year")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2)
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3)
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4)
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5)
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6)
        header.getCell(6).setCellStyle(styleTableHeader)
        header.createCell(7)
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8)
        header.getCell(8).setCellStyle(styleTableHeader)

        header = sheet.createRow(4)
        header.createCell(1).setCellValue("Cust")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2).setCellValue("Jumlah Site")
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3).setCellValue("Nilai PO")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("Nilai INV")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("%")
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6).setCellValue("Realisasi Budget")
        header.getCell(6).setCellStyle(styleTableHeader)
        header.createCell(7).setCellValue("Laba / Rugi")
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8).setCellValue("% Laba / Rugi")
        header.getCell(8).setCellStyle(styleTableHeader)

        sheet.addMergedRegion(CellRangeAddress(3, 3,1, 8))
    }

    fun createDataXls(workbook: HSSFWorkbook, sheet: HSSFSheet, data: List<CorrectiveYearData>){
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
            val cell6 = content.createCell(6)
            val cell5 = content.createCell(5)
            cell5.setCellType(CellType.FORMULA)
            val cell7 = content.createCell(7)
            cell7.setCellType(CellType.FORMULA)
            val cell8 = content.createCell(8)
            cell7.setCellType(CellType.FORMULA)

            content.createCell(1).setCellValue(it.code)
            content.getCell(1).setCellStyle(styleTableContent)
            it.jumlah_site?.toDouble()?.let {  it1-> cell2.setCellValue(it1)}
            content.getCell(2).setCellStyle(styleTableContentNumber)
            it.nilai_po?.toDouble()?.let {  it1-> cell3.setCellValue(it1)}
            content.getCell(3).setCellStyle(styleTableContentNumber)
            it.nilai_inv?.toDouble()?.let {  it1-> cell4.setCellValue(it1)}
            content.getCell(4).setCellStyle(styleTableContentNumber)
            cell5.cellFormula = "E$numRow/D$numRow"
            content.getCell(5).setCellStyle(styleTableContentPercent)
            it.realisasi_budget?.toDouble()?.let {  it1-> cell6.setCellValue(it1)}
            content.getCell(6).setCellStyle(styleTableContentNumber)
            cell7.cellFormula = "E$numRow-G$numRow"
            content.getCell(7).setCellStyle(styleTableContentNumber)
            cell8.cellFormula = "H$numRow/G$numRow"
            content.getCell(8).setCellStyle(styleTableContentPercent)
        }
        content = sheet.createRow(numRow)
        val cell2 = content.createCell(2)
        cell2.setCellType(CellType.FORMULA)
        val cell3 = content.createCell(3)
        cell3.setCellType(CellType.FORMULA)
        val cell4 = content.createCell(4)
        cell4.setCellType(CellType.FORMULA)
        val cell6 = content.createCell(6)
        cell6.setCellType(CellType.FORMULA)
        val cell5 = content.createCell(5)
        cell5.setCellType(CellType.FORMULA)
        val cell7 = content.createCell(7)
        cell7.setCellType(CellType.FORMULA)
        val cell8 = content.createCell(8)
        cell7.setCellType(CellType.FORMULA)
        content.createCell(1).setCellValue("Total")
        content.getCell(1).setCellStyle(styleTableHeader)
        cell2.cellFormula = "SUM(C${this.numRow+1}:C$numRow)"
        content.getCell(2).setCellStyle(styleTableHeaderNumber)
        cell3.cellFormula = "SUM(D${this.numRow+1}:D$numRow)"
        content.getCell(3).setCellStyle(styleTableHeaderNumber)
        cell4.cellFormula = "SUM(E${this.numRow+1}:E$numRow)"
        content.getCell(4).setCellStyle(styleTableHeaderNumber)
        cell5.cellFormula = "E${numRow+1}/D${numRow+1}"
        content.getCell(5).setCellStyle(styleTableHeaderPercent)
        cell6.cellFormula = "SUM(G${this.numRow+1}:G$numRow)"
        content.getCell(6).setCellStyle(styleTableHeaderNumber)
        cell7.cellFormula = "SUM(H${this.numRow+1}:H$numRow)"
        content.getCell(7).setCellStyle(styleTableHeaderNumber)
        cell8.cellFormula = "H${numRow+1}/G${numRow+1}"
        content.getCell(8).setCellStyle(styleTableHeaderPercent)
        numRow += 1
    }

    fun setColWidth(sheet: HSSFSheet):HSSFSheet{
        sheet.setColumnWidth(0, 2340)
        sheet.setColumnWidth(1, 3380)
        sheet.setColumnWidth(2, 3120)
        sheet.setColumnWidth(3, 4420)
        sheet.setColumnWidth(4, 4420)
        sheet.setColumnWidth(5, 2080)
        sheet.setColumnWidth(6, 4420)
        sheet.setColumnWidth(7, 4420)
        sheet.setColumnWidth(8, 4420)
        return sheet
    }

    init {
        var tahun = ""
        data.forEach {
            tahun = it.year_project.toString()
        }
        setColWidth(sheet)
        createHeaderXls(workbook, sheet, tahun)
        createDataXls(workbook, sheet, data)
    }
}