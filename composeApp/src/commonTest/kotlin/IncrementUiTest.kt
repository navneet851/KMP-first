import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.kmpcompose.kmp.IncrementUi
import kotlin.test.Test

class IncrementUiTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testIncrementUi() = runComposeUiTest {
        setContent {
            IncrementUi()
        }

        onNodeWithText("0").assertExists()
        onNodeWithText("1").assertDoesNotExist()
        onNodeWithText("Increment").performClick()
        onNodeWithText("1").assertExists()
        onNodeWithText("0").assertDoesNotExist()
    }
}