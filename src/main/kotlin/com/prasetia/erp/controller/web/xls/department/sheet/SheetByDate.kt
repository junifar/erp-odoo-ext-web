package com.prasetia.erp.controller.web.xls.department.sheet

import com.prasetia.erp.pojo.department.*
import com.prasetia.erp.utils.toSimpleDate
import com.prasetia.erp.utils.toSimpleString
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import java.text.SimpleDateFormat
import java.util.*

class SheetByDate (workbook: HSSFWorkbook, bulan: Int, data: List<DepartmentBudgetYearData>) {
    var bulanString = when(bulan){
        1->"jan"
        2->"feb"
        3->"mar"
        4->"apr"
        5->"mei"
        6->"jun"
        7->"jul"
        8->"ags"
        9->"sep"
        10->"okt"
        11->"nov"
        else->"des"
    }

    var sheet: HSSFSheet = workbook.createSheet(bulanString)
    private var numRow: Int = 5


    fun setColWidth(sheet: HSSFSheet): HSSFSheet {
        //1:260
        sheet.setColumnWidth(0, 2340)
        sheet.setColumnWidth(1, 8320)
        sheet.setColumnWidth(2, 8320)
        sheet.setColumnWidth(3, 5980)
        sheet.setColumnWidth(4, 8320)
        sheet.setColumnWidth(5, 25740)
        sheet.setColumnWidth(6,4420)
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

    fun createHeaderXls(workbook: HSSFWorkbook, sheet: HSSFSheet) {
        val styleHeader = styleHeader(workbook)
        val styleTableHeader = styleTableHeader(workbook)

        var header = sheet.createRow(1)
        header.createCell(1).setCellValue("Detail - Realisasi ")
        header.getCell(1).setCellStyle(styleHeader)

        header = sheet.createRow(3)
        header.createCell(1).setCellValue("Detail Realisasi Department")
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
        header.createCell(3).setCellValue("Date")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("Ref")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("Description")
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6).setCellValue("Total")
        header.getCell(6).setCellStyle(styleTableHeader)


        sheet.addMergedRegion(CellRangeAddress(3, 3, 1, 6))
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


    fun createDataXls(workbook: HSSFWorkbook, bulan: Int, sheet: HSSFSheet, data: List<DepartmentBudgetYearData>) {
        val styleTableContent = styleTableContent(workbook)
        val styleTableContentNumber = styleTableContentNumber(workbook)
        val styleTableHeader = styleTableHeader(workbook)
        val styleTableHeaderPercent = styleTableHeaderPercent(workbook)
        val styleTableHeaderNumber = styleTableHeaderNumber(workbook)
        val styleTableContentPercent = styleTableContentPercent(workbook)
        var Detail1: List<DepartmentBudgetData>? = mutableListOf()
        var Detail2: List<DepartmentBudgetDetailData>? = mutableListOf()
        var Detail3: List<DepartmentBudgetRealisasiData>? = mutableListOf()
        var datal: List<DepartmentBudgetYearData>? = mutableListOf()

        var content: HSSFRow
        var numRow = this.numRow


        data.forEach { d1 ->
            d1.department_budget?.forEach { d2 ->
                d2.budget_detail?.forEach { d3 ->
                    d3.realisasi?.forEach {
                        if (toSimpleString(it.date) == bulan.toString()) {

                            content = sheet.createRow(numRow++)
                            val cell6 = content.createCell(6)

                            content.createCell(1).setCellValue(d2.name)
                            content.getCell(1).setCellStyle(styleTableContent)
                            content.createCell(2).setCellValue(d3.code)
                            content.getCell(2).setCellStyle(styleTableContent)
                            content.createCell(3).setCellValue(toSimpleDate(it.date))
                            content.getCell(3).setCellStyle(styleTableContent)
                            content.createCell(4).setCellValue(it.ref)
                            content.getCell(4).setCellStyle(styleTableContent)
                            content.createCell(5).setCellValue(it.narration)
                            content.getCell(5).setCellStyle(styleTableContent)
                            it.budget_realisasi?.toDouble()?.let { it1 -> cell6.setCellValue(it1) }
                            content.getCell(6).setCellStyle(styleTableContentNumber)

                        }
                    }
                }
            }
        }
                    content = sheet.createRow(numRow)
                    val cell6 = content.createCell(6)
                    content.createCell(1).setCellValue("Total")
                    content.getCell(1).setCellStyle(styleTableHeader)
                    content.createCell(2).setCellValue("")
                    content.getCell(2).setCellStyle(styleTableHeader)
                    content.createCell(3).setCellValue("")
                    content.getCell(3).setCellStyle(styleTableHeader)
                    content.createCell(4).setCellValue("")
                    content.getCell(4).setCellStyle(styleTableHeader)
                    content.createCell(5).setCellValue("")
                    content.getCell(5).setCellStyle(styleTableHeader)
                    cell6.cellFormula = "SUM(F${this.numRow+1}:F$numRow)"
                    content.getCell(6).setCellStyle(styleTableHeaderNumber)
                    numRow += 1

    }

    init {
        setColWidth(sheet)
        createHeaderXls(workbook, sheet)
        createDataXls(workbook, bulan,sheet,data)
    }
}
