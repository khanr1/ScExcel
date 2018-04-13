import java.io
import java.io.{File, FileInputStream}
import org.scexcel.tool.ReadExcel._

val filePath:String="/home/raphael/IdeaProjects/Resume4KTest/src/main/Data/test_data.xlsx"
val file =new File(filePath)
val wb=parseExcel(new FileInputStream(file))
val systemSheet=wb("System")
val indices=systemSheet.getIndicesOf("pt_type").flatten
indices

indices.map(t=> (t._2,0))