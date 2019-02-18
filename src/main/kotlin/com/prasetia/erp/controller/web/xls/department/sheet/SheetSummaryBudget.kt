package com.prasetia.erp.controller.web.xls.department.sheet

import com.prasetia.erp.pojo.department.DepartmentBudgetYearData
import com.prasetia.erp.utils.toSimpleDate
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import java.text.SimpleDateFormat

class SheetSummaryBudget(workbook: HSSFWorkbook, data:List<DepartmentBudgetYearData>){
    var sheet = workbook.createSheet("Summary")
    private var numRow = 3

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

    private fun setColWidth(sheet: HSSFSheet):HSSFSheet {
        //1:260
        sheet.setColumnWidth(0, 2340)
        sheet.setColumnWidth(1, 7800)
        sheet.setColumnWidth(2, 10400)
        sheet.setColumnWidth(3, 7800)
        sheet.setColumnWidth(4, 5200)
        sheet.setColumnWidth(5, 5200)
        return sheet
    }

    private fun createHeaderXls(workbook: HSSFWorkbook, sheet: HSSFSheet) {
        val styleTableHeader = styleTableHeader(workbook)

        var header = sheet.createRow(2)
        header.createCell(1).setCellValue("No Budget")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2).setCellValue("Notes")
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3).setCellValue("Periode")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("Nilai Budget")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("Realisasi Budget")
        header.getCell(5).setCellStyle(styleTableHeader)
    }

    private fun createDataXls(workbook: HSSFWorkbook, sheet: HSSFSheet, data: List<DepartmentBudgetYearData>) {
        val styleTableContent = styleTableContent(workbook)
        val styleTableContentNumber = styleTableContentNumber(workbook)
        val format = SimpleDateFormat("dd-MMM-yyy")

        var numRow = this.numRow
        var content:HSSFRow

        data.forEach { item->
            item.department_budget?.forEach {
                content = sheet.createRow(numRow++)
                val cell4 = content.createCell(4)
                val cell5 = content.createCell(5)

                content.createCell(1).setCellValue(it.name)
                content.getCell(1).setCellStyle(styleTableContent)
                content.createCell(2).setCellValue(it.notes)
                content.getCell(2).setCellStyle(styleTableContent)
                content.createCell(3).setCellValue("${format.format(it.periode_start)} s.d ${format.format(it.periode_end)}")
                content.getCell(3).setCellStyle(styleTableContent)
                it.nilai_budget?.let { it1 -> cell4.setCellValue(it1) }
                content.getCell(4).setCellStyle(styleTableContentNumber)
                it.realisasi_budget?.let { it1 -> cell5.setCellValue(it1) }
                content.getCell(5).setCellStyle(styleTableContentNumber)
            }
        }
    }

    init {
        setColWidth(sheet)
        createHeaderXls(workbook, sheet)
        createDataXls(workbook, sheet, data)
    }
}
