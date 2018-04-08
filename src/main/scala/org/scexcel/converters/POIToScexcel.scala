package org.scexcel.converters
import scala.collection.JavaConverters._

import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.xssf.usermodel.{XSSFCell, XSSFFormulaEvaluator, XSSFRow, XSSFSheet}
import org.apache.poi.ss.usermodel.{Cell, Row}
import org.scexcel.tool.ReadExcel.getMaxNumberOfColumn
import scala.collection.immutable

object POIToScexcel {

  /**
    * Convert a POI row to the Scexcel Row collection
    *
    * @param poi   of type org.apache.poi.ss.usermodel.Row
    * @param nbColumn of type Int
    * @param formatter of type DataFormatter
    * @param formulaEval of type XSSFFormulaEvaluator
    * @return
    */
  def PoiRow_To_ScexcelRow(poi: org.apache.poi.ss.usermodel.Row,nbColumn: Int,formatter: DataFormatter,formulaEval:XSSFFormulaEvaluator): org.scexcel.module.Row[String]={

    val elements: Vector[String] =(for (i <-0 until nbColumn) yield{
      val cell: org.apache.poi.ss.usermodel.Cell=poi.getCell(i,Row.CREATE_NULL_AS_BLANK)
      formatter.formatCellValue(cell,formulaEval)
    }).toVector

    org.scexcel.module.Row(elements)

  }

  def PoiSheet_To_ScexcelSheet(sheet:XSSFSheet,formatter: DataFormatter,formulaEval:XSSFFormulaEvaluator):org.scexcel.module.Sheet[String]={
    val nbOfColumn: Int= getMaxNumberOfColumn(sheet)
    val rowList: Iterator[Row]=sheet.rowIterator().asScala
    val elements: Vector[org.scexcel.module.Row[String]]=(for (row<-rowList) yield{
      PoiRow_To_ScexcelRow(row,nbOfColumn,formatter,formulaEval)
    }).toVector
    org.scexcel.module.Sheet(elements)
  }
}
