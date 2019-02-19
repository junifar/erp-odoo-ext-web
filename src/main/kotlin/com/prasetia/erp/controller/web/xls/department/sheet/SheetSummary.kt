package com.prasetia.erp.controller.web.xls.department.sheet

import com.prasetia.erp.pojo.department.*
import com.prasetia.erp.utils.toSimpleString
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.util.CellRangeAddress

class SheetSummary(workbook: HSSFWorkbook, data: List<DepartmentBudgetYearData>, periode: String) {
    var sheet: HSSFSheet = workbook.createSheet("Summary Detail")
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
        sheet.setColumnWidth(7, 4160)
        sheet.setColumnWidth(8, 4160)
        sheet.setColumnWidth(9, 4160)
        sheet.setColumnWidth(10, 4160)
        sheet.setColumnWidth(11, 4160)
        sheet.setColumnWidth(12, 4160)
        sheet.setColumnWidth(13, 4160)
        sheet.setColumnWidth(14, 4160)
        sheet.setColumnWidth(15, 4160)
        sheet.setColumnWidth(16, 4160)
        sheet.setColumnWidth(17, 4160)
        sheet.setColumnWidth(18, 4160)
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
        header.createCell(7).setCellValue("Periode")
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8)
        header.getCell(8).setCellStyle(styleTableHeader)
        header.createCell(9)
        header.getCell(9).setCellStyle(styleTableHeader)
        header.createCell(10)
        header.getCell(10).setCellStyle(styleTableHeader)
        header.createCell(11)
        header.getCell(11).setCellStyle(styleTableHeader)
        header.createCell(12)
        header.getCell(12).setCellStyle(styleTableHeader)
        header.createCell(13)
        header.getCell(13).setCellStyle(styleTableHeader)
        header.createCell(14)
        header.getCell(14).setCellStyle(styleTableHeader)
        header.createCell(15)
        header.getCell(15).setCellStyle(styleTableHeader)
        header.createCell(16)
        header.getCell(16).setCellStyle(styleTableHeader)
        header.createCell(17)
        header.getCell(17).setCellStyle(styleTableHeader)
        header.createCell(18)
        header.getCell(18).setCellStyle(styleTableHeader)

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
        header.createCell(7).setCellValue("Jan")
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8).setCellValue("Feb")
        header.getCell(8).setCellStyle(styleTableHeader)
        header.createCell(9).setCellValue("Mar")
        header.getCell(9).setCellStyle(styleTableHeader)
        header.createCell(10).setCellValue("Apr")
        header.getCell(10).setCellStyle(styleTableHeader)
        header.createCell(11).setCellValue("Mei")
        header.getCell(11).setCellStyle(styleTableHeader)
        header.createCell(12).setCellValue("Jun")
        header.getCell(12).setCellStyle(styleTableHeader)
        header.createCell(13).setCellValue("Jul")
        header.getCell(13).setCellStyle(styleTableHeader)
        header.createCell(14).setCellValue("Agu")
        header.getCell(14).setCellStyle(styleTableHeader)
        header.createCell(15).setCellValue("Sep")
        header.getCell(15).setCellStyle(styleTableHeader)
        header.createCell(16).setCellValue("Okt")
        header.getCell(16).setCellStyle(styleTableHeader)
        header.createCell(17).setCellValue("Nov")
        header.getCell(17).setCellStyle(styleTableHeader)
        header.createCell(18).setCellValue("Des")
        header.getCell(18).setCellStyle(styleTableHeader)

        sheet.addMergedRegion(CellRangeAddress(3, 3, 1, 6))
        sheet.addMergedRegion(CellRangeAddress(3, 3, 7, 18))
    }

    fun getSumRealisasi(data:List<DepartmentBudgetRealisasiData>?, month:Int):Double{

        return data?.filter { toSimpleString(it.date) == month.toString()}?.sumByDouble { it.budget_realisasi?:0.00 }?:0.00
    }

    private fun createDataXls(workbook: HSSFWorkbook, sheet: HSSFSheet, data: List<DepartmentBudgetYearData>) {
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

                    val cell7 = content.createCell(7)
                    val cell8 = content.createCell(8)
                    val cell9 = content.createCell(9)
                    val cell10 = content.createCell(10)
                    val cell11 = content.createCell(11)
                    val cell12 = content.createCell(12)
                    val cell13 = content.createCell(13)
                    val cell14 = content.createCell(14)
                    val cell15 = content.createCell(15)
                    val cell16 = content.createCell(16)
                    val cell17 = content.createCell(17)
                    val cell18 = content.createCell(18)

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

                    cell7.setCellValue(getSumRealisasi(DetailDepartment3,1))
                    content.getCell(7).setCellStyle(styleTableContentNumber)
                    cell8.setCellValue(getSumRealisasi(DetailDepartment3,2))
                    content.getCell(8).setCellStyle(styleTableContentNumber)
                    cell9.setCellValue(getSumRealisasi(DetailDepartment3,3))
                    content.getCell(9).setCellStyle(styleTableContentNumber)
                    cell10.setCellValue(getSumRealisasi(DetailDepartment3,4))
                    content.getCell(10).setCellStyle(styleTableContentNumber)
                    cell11.setCellValue(getSumRealisasi(DetailDepartment3,5))
                    content.getCell(11).setCellStyle(styleTableContentNumber)
                    cell12.setCellValue(getSumRealisasi(DetailDepartment3,6))
                    content.getCell(12).setCellStyle(styleTableContentNumber)
                    cell13.setCellValue(getSumRealisasi(DetailDepartment3,7))
                    content.getCell(13).setCellStyle(styleTableContentNumber)
                    cell14.setCellValue(getSumRealisasi(DetailDepartment3,8))
                    content.getCell(14).setCellStyle(styleTableContentNumber)
                    cell15.setCellValue(getSumRealisasi(DetailDepartment3,9))
                    content.getCell(15).setCellStyle(styleTableContentNumber)
                    cell16.setCellValue(getSumRealisasi(DetailDepartment3,10))
                    content.getCell(16).setCellStyle(styleTableContentNumber)
                    cell17.setCellValue(getSumRealisasi(DetailDepartment3,11))
                    content.getCell(17).setCellStyle(styleTableContentNumber)
                    cell18.setCellValue(getSumRealisasi(DetailDepartment3,12))
                    content.getCell(18).setCellStyle(styleTableContentNumber)
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
