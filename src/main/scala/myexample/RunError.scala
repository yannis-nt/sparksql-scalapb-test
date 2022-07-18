package myexample

import com.example.protos.demo._
import org.apache.spark.sql.SparkSession

object RunError {

  val spark = SparkSession
    .builder()
    .appName("ScalaPB Demo")
    .config("spark.master", "local[*]")
    .config("spark.driver.allowMultipleContexts", "true")
    .getOrCreate()

  val sc = spark.sparkContext

  def main(Args: Array[String]): Unit = {
    import spark.implicits._
    val inputPersonDS = spark.createDataset(testData)
    inputPersonDS.show(false)


    val outputPersonDS = inputPersonDS.map(convertSinglePerson(_))

    outputPersonDS.show(false)
  }

  def convertSinglePerson(inputPerson: InputPerson): ConvertedPerson = {
    val gender: Option[Gender] =    if(inputPerson.sex == "M") Some(Gender.MALE)
    else if (inputPerson.sex == "F") Some(Gender.FEMALE)
    else None

    if (gender.isEmpty)
      ConvertedPerson(None, Some(InvalidRecord(inputPerson, "Undefined sex")))
    else
      ConvertedPerson(Some(Person(Some(inputPerson.name), Some(inputPerson.age), gender)), None)
  }

  val testData: Seq[InputPerson] = Seq(
    InputPerson("Joe", 32, "M"),
    InputPerson("Boe", 33, "Bug")
  )

  case class ConvertedPerson (
                               person: Option[Person] = None,
                               invalidRecord: Option[InvalidRecord] = None
                             )

  case class InputPerson(
                          name: String,
                          age: Int,
                          sex: String
                        )

  case class InvalidRecord(
                            originalCompetitionMessage: InputPerson,
                            errorMessage: String
                          )

}
