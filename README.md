# ScExcel

This package is mainly an educative project. The purpose was to learn:
1. to create a Scala library  which extract information from a Excel xlsx file
2. to learn how to create vector like Scala collection: `Row[A]` and `Sheet[A]`

## Description of the Row and  Sheet collection

`Row[A]` and `Sheet[A]` are Vector like collection and are defined in org.scexcel.module.

`Row[A]` is a copy  of vector and `Sheet[A]` is a collection of `Row[A]`.

## Description  of the ParseExcel method.

`parseExcel` is a method which take a excel file as input and return a `Map[String,Sheet[String]]`. The key value of this returned `Map` is the name of
the sheet.

Example code

```Scala
import java.io
import java.io.{File, FileInputStream}
import org.scexcel.tool.ReadExcel._

val filePath:String="./your_filed.xlsx" /* path of the xlsx file*/
val file =new File(filePath)
val parsedfile=parseExcel(new FileInputStream(file))
`