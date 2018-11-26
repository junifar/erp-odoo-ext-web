package com.prasetia.erp.controller.web.xls.department.sheet

import com.prasetia.erp.pojo.department.*
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.util.CellRangeAddress

class SheetSummary(workbook: HSSFWorkbook, data: List<DepartmentBudgetYearData>, periode: String) {
    var sheet: HSSFSheet = workbook.createSheet("Summary")
    private var numRow: Int = 5

    fun setColWidth(sheet: HSSFSheet): HSSFSheet {
        //1:260
        sheet.setColumnWidth(0, 2340)
        sheet.setColumnWidth(1, 8320)
        sheet.setColumnWidth(2, 8320)
        sheet.setColumnWidth(3, 8320)
        sheet.setColumnWidth(4, 4420)
        sheet.setColumnWidth(5, 4420)
        sheet.setColumnWidth(6, 4420)
        return sheet
    }

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

    fun styleTableContent(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleTableContent = workbook.createCellStyle()
        val fontCalibriTableContent = fontCalibriTableContent(workbook)
        styleTableContent.setBorderBottom(BorderStyle.THIN)
        styleTableContent.setBorderTop(BorderStyle.THIN)
        styleTableContent.setBorderLeft(BorderStyle.THIN)
        styleTableContent.setBorderRight(BorderStyle.THIN)
        styleTableContent.setFont(fontCalibriTableContent)
        return styleTableContent
    }

    fun styleTableContentNumber(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleTableContentNumber = workbook.createCellStyle()
        styleTableContentNumber.dataFormat = HSSFDataFormat.getBuiltinFormat("#,##0.00")
        styleTableContentNumber.setBorderBottom(BorderStyle.THIN)
        styleTableContentNumber.setBorderTop(BorderStyle.THIN)
        styleTableContentNumber.setBorderLeft(BorderStyle.THIN)
        styleTableContentNumber.setBorderRight(BorderStyle.THIN)
        styleTableContentNumber.setFont(fontCalibriTableContent(workbook))
        return styleTableContentNumber
    }

    fun styleTableContentPercent(workbook: HSSFWorkbook): HSSFCellStyle {
        val styleTableContentNumber = workbook.createCellStyle()
        styleTableContentNumber.dataFormat = HSSFDataFormat.getBuiltinFormat("0.00%")
        styleTableContentNumber.setBorderBottom(BorderStyle.THIN)
        styleTableContentNumber.setBorderTop(BorderStyle.THIN)
        styleTableContentNumber.setBorderLeft(BorderStyle.THIN)
        styleTableContentNumber.setBorderRight(BorderStyle.THIN)
        styleTableContentNumber.setFont(fontCalibriTableContent(workbook))
        return styleTableContentNumber
    }

    fun styleTableHeaderNumber(workbook: HSSFWorkbook): HSSFCellStyle {
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

    fun styleTableHeaderPercent(workbook: HSSFWorkbook): HSSFCellStyle {
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

    fun createHeaderXls(workbook: HSSFWorkbook, sheet: HSSFSheet, year: String, periode: String) {
        val styleHeader = styleHeader(workbook)
        val styleTableHeader = styleTableHeader(workbook)

        var header = sheet.createRow(1)
        header.createCell(1).setCellValue("Department - Budget $periode")
        header.getCell(1).setCellStyle(styleHeader)

        header = sheet.createRow(3)
        header.createCell(1).setCellValue("Department Budget $year")
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

        header = sheet.createRow(4)
        header.createCell(1).setCellValue("No Budget")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2).setCellValue("No Budget Item")
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3).setCellValue("Item Description")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("Nilai Budget")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("Realisasi Budget")
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6).setCellValue("%")
        header.getCell(6).setCellStyle(styleTableHeader)

        sheet.addMergedRegion(CellRangeAddress(3, 3, 1, 6))
    }

    fun createDataXls(workbook: HSSFWorkbook, sheet: HSSFSheet, data: List<DepartmentBudgetYearData>) {
        val styleTableContent = styleTableContent(workbook)
        val styleTableContentNumber = styleTableContentNumber(workbook)
        val styleTableHeader = styleTableHeader(workbook)
        val styleTableHeaderPercent = styleTableHeaderPercent(workbook)
        val styleTableHeaderNumber = styleTableHeaderNumber(workbook)
        val styleTableContentPercent = styleTableContentPercent(workbook)
        var DetailDepartment: List<DepartmentBudgetData>? = mutableListOf()
        var DetailDepartment2: List<DepartmentBudgetDetailData>? = mutableListOf()
        var DetailDepartment3: List<DepartmentBudgetRealisasiData>? = mutableListOf()

        var content: HSSFRow
        var numRow = this.numRow

        data.forEach { data1 ->
            DetailDepartment = data1.department_budget
            DetailDepartment?.forEach { data2 ->
                DetailDepartment2 = data2.budget_detail


                DetailDepartment2?.forEach { data3 ->
                    DetailDepartment3 = data3.realisasi

                    content = sheet.createRow(numRow++)
                    val cell1 = content.createCell(1)
                    val cell2 = content.createCell(2)
                    val cell3 = content.createCell(3)
                    val cell4 = content.createCell(4)
                    val cell6 = content.createCell(6)
                    val cell5 = content.createCell(5)

                    content.createCell(1).setCellValue(data2.name)
                    content.getCell(1).setCellStyle(styleTableContent)
                    content.createCell(2).setCellValue(data3.code)
                    content.getCell(2).setCellStyle(styleTableContent)
                    content.createCell(3).setCellValue(data3.budget_item_view)
                    content.getCell(3).setCellStyle(styleTableContent)
                    data3.nilai_budget?.toDouble()?.let { it1 -> cell4.setCellValue(it1) }
                    content.getCell(4).setCellStyle(styleTableContentNumber)
                    data3.realisasi_budget?.toDouble()?.let { it1 -> cell5.setCellValue(it1) }
                    content.getCell(5).setCellStyle(styleTableContentNumber)
                    data3.persent_budget?.toDouble()?.let { it1 -> cell6.setCellValue(it1) }
                    content.getCell(6).setCellStyle(styleTableContentNumber)
                }
            }

        content = sheet.createRow(numRow)
        val cell4 = content.createCell(4)
        val cell5 = content.createCell(5)
        val cell6 = content.createCell(6)
        content.createCell(1).setCellValue("Total")
        content.getCell(1).setCellStyle(styleTableHeader)
        content.createCell(2).setCellValue("")
        content.getCell(2).setCellStyle(styleTableHeader)
        content.createCell(3).setCellValue("")
        content.getCell(3).setCellStyle(styleTableHeader)
        cell4.cellFormula = "SUM(E${this.numRow+1}:E$numRow)"
        content.getCell(4).setCellStyle(styleTableHeaderNumber)
        cell5.cellFormula = "SUM(F${this.numRow+1}:F$numRow)"
        content.getCell(5).setCellStyle(styleTableHeaderNumber)
        cell6.cellFormula = "SUM(G${this.numRow+1}:G$numRow)"
        content.getCell(6).setCellStyle(styleTableHeaderPercent)
        numRow += 1

    }
    }

    init {
        var tahun = ""
        data.forEach {
            tahun = it.department_name.toString()
        }
        setColWidth(sheet)
        createHeaderXls(workbook, sheet, tahun,periode)
        createDataXls(workbook, sheet, data)
    }
}