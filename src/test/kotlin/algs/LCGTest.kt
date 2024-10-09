import org.example.algs.LCG
import org.example.utils.BaseUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class LCGTest {
    private val utils = BaseUtils()

    @Test
    fun testLCGcase1() {
        // Устанавливаем коэффициенты для LCG
        val lcgSeed1: Int = utils.block2num("ЛУЛУ")
        val lcgSet: List<Int> = listOf(723482, 8677, 983609)

        // Создаем объект LCG с начальным состоянием
        val lcg = LCG(lcgSeed1, lcgSet)

        // Создаем изменяемый список для хранения выходных данных
        val out: MutableList<String> = mutableListOf()

        // Вызываем LCG_next() 10 раз и сохраняем результаты в список out
        repeat(10) {
            lcg.LCG_next()
            out.add(utils.num2block(lcg.getState()))
        }

        assertEquals(mutableListOf<String>("КМЖТ", "ОГЫЕ", "ЛЭЮА", "ЦЫЧР", "Ж_ОР", "КОЭЖ", "ЩЛЧЖ", "ЙШДЛ", "МЧЮЬ", "ЧНЫК"),out)
    }
    @Test
    fun testLCGcase2() {
        // Устанавливаем коэффициенты для LCG
        val lcgSeed1: Int = utils.block2num("ЯВОР")
        val lcgSet: List<Int> = listOf(723482, 8677, 983609)

        // Создаем объект LCG с начальным состоянием
        val lcg = LCG(lcgSeed1, lcgSet)

        // Создаем изменяемый список для хранения выходных данных
        val out: MutableList<String> = mutableListOf()

        // Вызываем LCG_next() 10 раз и сохраняем результаты в список out
        repeat(10) {
            lcg.LCG_next()
            out.add(utils.num2block(lcg.getState()))
        }

        assertEquals(mutableListOf<String>("ХЕЖР", "ЭДАР", "КББЛ", "РАГЗ", "ЕГЛ_", "ЫОТЩ", "ЙГНЧ", "ВЕЦ_", "ШШУЯ", "ЕЮЫЦ"),out)
    }
    @Test
    fun testLCGcase3() {
        // Устанавливаем коэффициенты для LCG
        val lcgSeed1: Int = utils.block2num("ЛУЛУ")
        val lcgSet: List<Int> = listOf(357630, 8971, 948209)

        // Создаем объект LCG с начальным состоянием
        val lcg = LCG(lcgSeed1, lcgSet)

        // Создаем изменяемый список для хранения выходных данных
        val out: MutableList<String> = mutableListOf()

        // Вызываем LCG_next() 10 раз и сохраняем результаты в список out
        repeat(10) {
            lcg.LCG_next()
            out.add(utils.num2block(lcg.getState()))
        }

        assertEquals(mutableListOf<String>("НЕШЬ", "ДОЕМ", "АЦШП", "СЙЩН", "ЛХРН", "_ДДШ", "ЧЮЮИ", "ОУВУ", "ПЛЬУ", "МФРА"),out)
    }
}
