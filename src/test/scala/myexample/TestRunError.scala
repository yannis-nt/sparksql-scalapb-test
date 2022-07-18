package myexample

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSpec, Matchers}

class TestRunError  extends FunSpec
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
    RunError.sc
  }
  describe("IntegratedShoppingConvertor") {

    it("Basic tests") {
      RunError.main(Array())

    }
  }
}
