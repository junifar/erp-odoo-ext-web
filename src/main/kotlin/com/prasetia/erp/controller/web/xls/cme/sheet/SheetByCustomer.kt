package com.prasetia.erp.controller.web.xls.cme.sheet

import com.prasetia.erp.pojo.cme.CmeSummaryYearProjectTypeCustData
import com.prasetia.erp.pojo.cme.CmeYearProjectTypeCustProjectDetailData
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment

class SheetByCustomer(workbook: HSSFWorkbook, customer_id: Long, customer: String, data: List<CmeYearProjectTypeCustProjectDetailData>?, forEach: Unit?, it: CmeSummaryYearProjectTypeCustData){
    var sheet: HSSFSheet = workbook.createSheet("$customer_id-$customer")
    private var numRow:Int = 6

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
        createHeaderXls(workbook, sheet)
        createDataXls(workbook, sheet, data,it)
    }

    private fun createDataXls(workbook: HSSFWorkbook, sheet: HSSFSheet, data: List<CmeYearProjectTypeCustProjectDetailData>?, it: CmeSummaryYearProjectTypeCustData) {
        val styleTableContent = styleTableContent(workbook)
        val styleTableContentNumber = styleTableContentNumber(workbook)
        val styleTableHeader = styleTableHeader(workbook)
        val styleTableHeaderPercent = styleTableHeaderPercent(workbook)
        val styleTableHeaderNumber = styleTableHeaderNumber(workbook)
        val styleTableContentPercent = styleTableContentPercent(workbook)

        var content:HSSFRow
        var numRow = this.numRow
        var rowNumber = 1
        data?.forEach {
            d1->
            var isSame = false
            d1.invoice_list?.forEach {
              d2 ->
                content = sheet.createRow(numRow++)
                val cell8 = content.createCell(8)
                val cell9 = content.createCell(9)
                val cell10 = content.createCell(10)
                if(!isSame) cell10.setCellType(CellType.FORMULA)
                val cell11 = content.createCell(11)
                if(!isSame) cell11.setCellType(CellType.FORMULA)
                val cell12 = content.createCell(12)
                val cell14 = content.createCell(14)
                val cell16 = content.createCell(16)
                val cell17 = content.createCell(17)
                val cell18 = content.createCell(18)
                if(!isSame) cell18.setCellType(CellType.FORMULA)
                val cell19 = content.createCell(19)
                if(!isSame) cell19.setCellType(CellType.FORMULA)
                val cell21 = content.createCell(21)
                if(!isSame) cell21.setCellType(CellType.FORMULA)

                content.createCell(1).setCellValue(rowNumber++.toDouble())
                content.getCell(1).setCellStyle(styleTableContent)
                content.createCell(2).setCellValue(if(!isSame) d2.name else "")
                content.getCell(2).setCellStyle(styleTableContent)
                content.createCell(3).setCellValue("")
                content.getCell(3).setCellStyle(styleTableContent)
                content.createCell(4).setCellValue("")
                content.getCell(4).setCellStyle(styleTableContent)
                content.createCell(5).setCellValue(if(!isSame) d1.project_type else "")
                content.getCell(5).setCellStyle(styleTableContent)
                content.createCell(6).setCellValue(if(!isSame) d1.project_id else "")
                content.getCell(6).setCellStyle(styleTableContent)
                content.createCell(7).setCellValue(if(!isSame) d1.area else "")
                content.getCell(7).setCellStyle(styleTableContent)
                if (!isSame) d1.estimate_po.toDouble().let { it1->  cell8.setCellValue(it1)}
                content.getCell(8).setCellStyle(styleTableContentNumber)
                if(!isSame) d1.nilai_budget.toDouble().let { it1-> cell9.setCellValue(it1)}
                content.getCell(9).setCellStyle(styleTableContentNumber)
                if(!isSame) cell10.cellFormula = "I$numRow-J$numRow" else null
                content.getCell(10).setCellStyle(styleTableContentNumber)
                if(!isSame) cell11.cellFormula = "K$numRow/i$numRow"
                content.getCell(11).setCellStyle(styleTableContentPercent)
                if(!isSame) d1.realisasi_budget.toDouble().let { it1-> cell12.setCellValue(it1)}
                content.getCell(12).setCellStyle(styleTableContentNumber)
                content.createCell(13).setCellValue(if(!isSame) d1.no_po else "")
                content.getCell(13).setCellStyle(styleTableContent)
                if(!isSame) d1.nilai_po.toDouble().let { it1-> cell14.setCellValue(it1)}
                content.getCell(14).setCellStyle(styleTableContentNumber)
                content.createCell(15).setCellValue(d2.name)
                content.getCell(15).setCellStyle(styleTableContent)
                content.getCell(16).setCellStyle(styleTableContentNumber)
                d2.nilai_invoice?.toDouble().let { it1-> it1?.let { it2 -> cell16.setCellValue(it2) } }
                content.getCell(17).setCellStyle(styleTableContentNumber)
                if(!isSame) d1.nilai_invoice?.toDouble().let { it1-> cell17.setCellValue(it1)}
                content.getCell(18).setCellStyle(styleTableContentNumber)
                if(!isSame) cell18.cellFormula = "R$numRow-M$numRow"
                content.getCell(19).setCellStyle(styleTableContentNumber)
                if(!isSame) cell19.cellFormula = "S$numRow/R$numRow"
                content.createCell(20).setCellValue(d2.state)
                content.getCell(20).setCellStyle(styleTableContent)
                content.getCell(21).setCellStyle(styleTableContentNumber)
                if(!isSame) cell21.cellFormula="R$numRow/O$numRow"
                isSame = true
        }
        }
    }

    private fun createHeaderXls(workbook: HSSFWorkbook, sheet: HSSFSheet) {
        val styleHeader = styleHeader(workbook)
        val styleTableHeader = styleTableHeader(workbook)

        var header = sheet.createRow(0)
        header.createCell(1).setCellValue("SACME - B2S 2018")
        header.getCell(1).setCellStyle(styleHeader)

        header = sheet.createRow(4)
        header.createCell(1).setCellValue("No")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2).setCellValue("Nama Site")
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3).setCellValue("Tgl Start Pembayaran")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("Aging (Days)")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("Keterangan")
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6).setCellValue("Site ID")
        header.getCell(6).setCellStyle(styleTableHeader)
        header.createCell(7).setCellValue("Area")
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8).setCellValue("Estimasi Nilai PO")
        header.getCell(8).setCellStyle(styleTableHeader)
        header.createCell(9).setCellValue("Estimasi Budget")
        header.getCell(9).setCellStyle(styleTableHeader)
        header.createCell(10).setCellValue("Gross Margin")
        header.getCell(10).setCellStyle(styleTableHeader)
        header.createCell(11).setCellValue("%")
        header.getCell(11).setCellStyle(styleTableHeader)
        header.createCell(12).setCellValue("Realisasi Budget")
        header.getCell(12).setCellStyle(styleTableHeader)
        header.createCell(13).setCellValue("No PO")
        header.getCell(13).setCellStyle(styleTableHeader)
        header.createCell(14).setCellValue("Nilai PO")
        header.getCell(14).setCellStyle(styleTableHeader)
        header.createCell(15).setCellValue("No INV")
        header.getCell(15).setCellStyle(styleTableHeader)
        header.createCell(16).setCellValue("Nilai INV")
        header.getCell(16).setCellStyle(styleTableHeader)
        header.createCell(17).setCellValue("Total Inv")
        header.getCell(17).setCellStyle(styleTableHeader)
        header.createCell(18).setCellValue("Laba / Rugi")
        header.getCell(18).setCellStyle(styleTableHeader)
        header.createCell(19).setCellValue("%")
        header.getCell(19).setCellStyle(styleTableHeader)
        header.createCell(20).setCellValue("Status INV")
        header.getCell(20).setCellStyle(styleTableHeader)
        header.createCell(21).setCellValue("% Penagihan")
        header.getCell(21).setCellStyle(styleTableHeader)

        header = sheet.createRow(5)
        header.createCell(1).setCellValue("")
        header.getCell(1).setCellStyle(styleTableHeader)
        header.createCell(2).setCellValue("")
        header.getCell(2).setCellStyle(styleTableHeader)
        header.createCell(3).setCellValue("")
        header.getCell(3).setCellStyle(styleTableHeader)
        header.createCell(4).setCellValue("")
        header.getCell(4).setCellStyle(styleTableHeader)
        header.createCell(5).setCellValue("")
        header.getCell(5).setCellStyle(styleTableHeader)
        header.createCell(6).setCellValue("")
        header.getCell(6).setCellStyle(styleTableHeader)
        header.createCell(7).setCellValue("")
        header.getCell(7).setCellStyle(styleTableHeader)
        header.createCell(8).setCellValue("A")
        header.getCell(8).setCellStyle(styleTableHeader)
        header.createCell(9).setCellValue("B")
        header.getCell(9).setCellStyle(styleTableHeader)
        header.createCell(10).setCellValue("C")
        header.getCell(10).setCellStyle(styleTableHeader)
        header.createCell(11).setCellValue("C:A")
        header.getCell(11).setCellStyle(styleTableHeader)
        header.createCell(12).setCellValue("D")
        header.getCell(12).setCellStyle(styleTableHeader)
        header.createCell(13).setCellValue("")
        header.getCell(13).setCellStyle(styleTableHeader)
        header.createCell(14).setCellValue("")
        header.getCell(14).setCellStyle(styleTableHeader)
        header.createCell(15).setCellValue("")
        header.getCell(15).setCellStyle(styleTableHeader)
        header.createCell(16).setCellValue("")
        header.getCell(16).setCellStyle(styleTableHeader)
        header.createCell(17).setCellValue("")
        header.getCell(17).setCellStyle(styleTableHeader)
        header.createCell(18).setCellValue("")
        header.getCell(18).setCellStyle(styleTableHeader)
        header.createCell(19).setCellValue("")
        header.getCell(19).setCellStyle(styleTableHeader)
        header.createCell(20).setCellValue("")
        header.getCell(20).setCellStyle(styleTableHeader)
        header.createCell(21).setCellValue("")
        header.getCell(21).setCellStyle(styleTableHeader)
    }

    private fun setColWidth(sheet: HSSFSheet): HSSFSheet {
        sheet.setColumnWidth(0, 520)
        sheet.setColumnWidth(1, 1300)
        sheet.setColumnWidth(2, 6500)
        sheet.setColumnWidth(3, 6500)
        sheet.setColumnWidth(4, 4940)
        sheet.setColumnWidth(5, 4940)
        sheet.setColumnWidth(6, 2860)
        sheet.setColumnWidth(7, 3380)
        sheet.setColumnWidth(8, 4680)
        sheet.setColumnWidth(9, 4420)
        sheet.setColumnWidth(10, 3900)
        sheet.setColumnWidth(11, 2080)
        sheet.setColumnWidth(12, 3640)
        sheet.setColumnWidth(13, 8320)
        sheet.setColumnWidth(14, 3640)
        sheet.setColumnWidth(15, 5980)
        sheet.setColumnWidth(16, 3900)
        sheet.setColumnWidth(17, 3900)
        sheet.setColumnWidth(18, 3900)
        sheet.setColumnWidth(19, 2600)
        sheet.setColumnWidth(20, 3700)
        sheet.setColumnWidth(21, 3900)
        return sheet
    }
}