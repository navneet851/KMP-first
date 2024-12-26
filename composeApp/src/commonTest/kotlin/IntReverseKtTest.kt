import assertk.assertThat
import assertk.assertions.isEqualTo
import org.kmpcompose.kmp.intReverse
import kotlin.test.Test

class IntReverseKtTest {
    @Test
    fun testIntReverse(){
        val num = 2011
        assertThat(intReverse(num)).isEqualTo(1102)
    }
}