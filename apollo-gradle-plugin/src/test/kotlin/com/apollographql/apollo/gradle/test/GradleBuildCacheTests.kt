package com.apollographql.apollo.gradle.test

import com.apollographql.apollo.gradle.internal.child
import com.apollographql.apollo.gradle.util.TestUtils
import com.apollographql.apollo.gradle.util.replaceInText
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Assert
import org.junit.Test
import java.io.File

class GradleBuildCacheTests {

  @Test
  fun `generate apollo classes task is cached`() {
    TestUtils.withDirectory { dir ->
      val project1 = File(dir, "project1")
      val project2 = File(dir, "directory/project2")
      project2.mkdirs()

      File(System.getProperty("user.dir"), "testProjects/buildCache").copyRecursively(project1)
      File(System.getProperty("user.dir"), "testProjects/buildCache").copyRecursively(project2)

      File(project2, "build.gradle.kts").replaceInText("../../../../", "../../../../../")
      File(project2, "settings.gradle.kts").replaceInText("../buildCache", "../../buildCache")
      System.out.println("building project1")
      var result = TestUtils.executeTask("generateMainServiceApolloSources", project1, "--build-cache")
      Assert.assertEquals(TaskOutcome.SUCCESS, result.task(":module:generateMainServiceApolloSources")!!.outcome)

      System.out.println("building project2")
      result = TestUtils.executeTask("generateMainServiceApolloSources", project2, "--build-cache")
      Assert.assertEquals(TaskOutcome.FROM_CACHE, result.task(":module:generateMainServiceApolloSources")!!.outcome)
    }
  }
}
