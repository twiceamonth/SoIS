import org.example.algs.Cesar
import org.example.algs.HCLCG
import org.example.algs.SBlockImpl
import org.example.utils.BaseUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class HCLCGTest {
    private val utils = BaseUtils()
    private val sBlock = SBlockImpl(utils, Cesar(utils))

    @Test
    fun HCLCGTestCase1(){
        var seed1 = mutableListOf<String>("АПЧХ", "Ч_ОК","ШУРА")

        var s1 = utils.seed2nums(seed1)

        var set = mutableListOf<MutableList<Int>>(mutableListOf<Int>(723482,8677,983609),mutableListOf<Int>(252564,9109,961193),mutableListOf<Int>(357630,8971,948209))

        val hclcg = HCLCG(s1, set)

        val out: MutableList<String> = mutableListOf()

        // Вызываем HCLCG_next() 10 раз и сохраняем результаты в список out
        repeat(10) {
            out.add(utils.num2block(hclcg.HCLCG_next()))
        }
        assertEquals(mutableListOf<String>("ТЖЧТ", "КЯВБ", "НЯПП", "КХЖР", "НЧ_С", "ЖХПН", "ЦЦ_М", "ФБТБ", "УРГР", "ОЩЖД"),out)


    }
    @Test
    fun HCLCGTestCase2(){
        var seed1 = sBlock.make_seed("КОЛА")

        var s1 = utils.seed2nums(seed1)

        var set = mutableListOf<MutableList<Int>>(mutableListOf<Int>(723482,8677,983609),mutableListOf<Int>(252564,9109,961193),mutableListOf<Int>(357630,8971,948209))

        val hclcg = HCLCG(s1, set)

        val out: MutableList<String> = mutableListOf()

        // Вызываем HCLCG_next() 10 раз и сохраняем результаты в список out
        repeat(10) {
            out.add(utils.num2block(hclcg.HCLCG_next()))
        }
        assertEquals(mutableListOf<String>("ЬИГЙ", "ФНВЧ", "ИВИМ", "ЭТПЛ", "ШЩЧЕ", "УЭЭ_", "ГРУШ", "МТЧА", "АНЦП", "ЧИТЩ"),out)


    }
}