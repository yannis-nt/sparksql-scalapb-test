import myexample.RunDemo
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSpec, FunSpecLike, Matchers}

class TestRunDemo  extends FunSpec
  with BeforeAndAfter
  with BeforeAndAfterAll
  with Matchers {
  val logger: Logger = Logger.getLogger(this.getClass)

  // TODO fix the warnings (if they are on our side), so we can go back to Level.WARN
  logger.setLevel(Level.ERROR)

  override def afterAll(): Unit = {
    returnSpecificSC.stop()
  }

  def returnSpecificSC: SparkContext = {
    RunDemo.sc
  }
  describe("IntegratedShoppingConvertor") {

    it("Basic tests") {
      RunDemo.main(Array())

    }
  }
}
