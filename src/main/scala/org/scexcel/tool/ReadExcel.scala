package org.scexcel.tool

import java.io.{File, FileInputStream}
import java.io.File

import org.apache.poi.ss.usermodel.{Cell, DataFormatter, Row}
import org.apache.poi.xssf.usermodel.{XSSFFormulaEvaluator, XSSFSheet, XSSFWorkbook}
import org.scexcel.module.{Sheet}
import org.scexcel.converters.POIToScexcel.PoiSheet_To_ScexcelSheet
import scala.collection.JavaConverters._
object ReadExcel {

  /**
    * Check is the file is a excelfile
    * @param fileName name of thefile
    * @return True is Xls file else false
    */
  def CheckExcelFile(fileName:String): Boolean =(fileName.endsWith("xlsx") && (new File(fileName)).exists())

  /**
    * Look in a Sheet the number of column
    * @param sheet a POI XXSFSheet
    * @return an Int corresponding to the largest row
    */
  def getMaxNumberOfColumn(sheet:XSSFSheet): Int={

    val listNumberOfColumnInRow: List[Int] = (for(row <- sheet.rowIterator().asScala) yield row.getPhysicalNumberOfCells).toList
    listNumberOfColumnInRow.max
  }

  /**
    * Return the number of sheet in an Excel XSSFWorkbook
    * @param wb a XSSF workbook
    * @return an Int corresponding to the number of Sheet in the workbook
    */
  def getNumberofSheet(wb :XSSFWorkbook): Int={
    wb.getNumberOfSheets
  }

  /**
    * Convert an Excel file to a Map[name_of_the_Sheet, Sheet[String])
    * @param file file to be converted
    * @return  Map[String,Sheet[String]
    */
  def parseExcel(file:java.io.FileInputStream): Map[String,Sheet[String]]={

    val wb :XSSFWorkbook = new XSSFWorkbook(file);/* takes the file and makes a XSSF worksheet */
    val formatter: DataFormatter = new DataFormatter(); /* DataFormatter look API  of POI*/
    val formulaEval: XSSFFormulaEvaluator = wb.getCreationHelper.createFormulaEvaluator();
    val (sheetList1: Iterator[XSSFSheet],sheetList2:Iterator[XSSFSheet])= (wb.iterator().asScala).duplicate;
    val elements:Vector[Sheet[String]]=(for (sheet<-sheetList1) yield{
      PoiSheet_To_ScexcelSheet(sheet,formatter,formulaEval)
    }).toVector

   val key:List[String]=(for (sheet <- sheetList2) yield sheet.getSheetName).toList
    key.zip(elements).toMap
  }



}
