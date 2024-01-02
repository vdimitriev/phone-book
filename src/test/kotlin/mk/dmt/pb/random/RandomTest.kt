package mk.dmt.pb.random

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class RandomTest {

    @Test
    fun testRandomUUID() {
        val random = UUID.randomUUID().toString()
        println(random)
        Assertions.assertEquals(36, random.length)
    }
}