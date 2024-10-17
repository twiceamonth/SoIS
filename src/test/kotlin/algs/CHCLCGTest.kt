import org.junit.jupiter.api.Test
import org.example.algs.CHCLCG
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertNotEquals

class CHCLCGTest {

    // тест для не модифицированного генератора
    /*@Test
    fun CHCLCGTestCase1(){
        val seed = "АБВГДЕЖЗИЙКЛМНОП"
        var set = mutableListOf<MutableList<Int>>(mutableListOf<Int>(723482,8677,983609),mutableListOf<Int>(252564,9109,961193),mutableListOf<Int>(357630,8971,948209))

        val chclcg = CHCLCG(seed,set)

        val out: MutableList<String> = mutableListOf()

        repeat(9) {
            out.add(chclcg.CHCLCG_next())
        }
        assertEquals(mutableListOf<String>(
            "РЭЦБЯШПСДЛАЕАГФ_",
            "ЕЯМОТЙУЖЧЬЗГФВЫП",
            "ПКАЗЛ_ИОМШУИЭМЙК",
            "ХЯИЛУШЬСФПХХСКЗР",
            "ЬЬФСЦУЖШВЗТУЙЭЮФ",
            "БНКЖ_ЛСЫЭЩЛЩЕЭЯЕ",
            "ЛЬГШЧЭЬДФПСИРЕАВ",
            "СХГЫЬАМДХУЗЯЕАЦЧ",
            "ДФТВЩПВЕСЗМЧЦХЯЧ"),out)


    }*/

    @Test
    fun CHCLCGTestCase2(){
        val seed1 = "ААААББББВВВВГГГГ"
        val seed2 = "ВВВВГГГГААААББББ"
        var set = mutableListOf<MutableList<Int>>(mutableListOf<Int>(723482,8677,983609),mutableListOf<Int>(252564,9109,961193),mutableListOf<Int>(357630,8971,948209))

        val chclcg1 = CHCLCG(seed1,set)
        val chclcg2 = CHCLCG(seed2,set)

        val out1: MutableList<String> = mutableListOf()
        val out2: MutableList<String> = mutableListOf()

        repeat(9) {
            out1.add(chclcg1.CHCLCG_next())
        }
        repeat(9) {
            out2.add(chclcg2.CHCLCG_next())
        }
        assertNotEquals(out1,out2)
    }
    @Test
    fun CHCLCGTestCase3(){
        val seed1 = "ААААААААББББББББ"
        val seed2 = "ВВВВГГГГАААААААА"
        val seed3 = "АААААААААААААААА"

        var set = mutableListOf<MutableList<Int>>(mutableListOf<Int>(723482,8677,983609),mutableListOf<Int>(252564,9109,961193),mutableListOf<Int>(357630,8971,948209))

        val chclcg1 = CHCLCG(seed1,set)
        val chclcg2 = CHCLCG(seed2,set)
        val chclcg3 = CHCLCG(seed3,set)

        val out1: MutableList<String> = mutableListOf()
        val out2: MutableList<String> = mutableListOf()
        val out3: MutableList<String> = mutableListOf()

        repeat(9) {
            out1.add(chclcg1.CHCLCG_next())
        }
        repeat(9) {
            out2.add(chclcg2.CHCLCG_next())
        }
        repeat(9) {
            out3.add(chclcg3.CHCLCG_next())
        }


        val list1 = listOf("________________", "________________", "________________", "________________", "________________", "________________", "________________", "________________", "________________")

        val list2 = listOf("ИМФГКВЩЗБЛЭЗНФГС", "ПЛЛХТЩЭЯС_ЕЙСТЙМ", "ВТЮПЛРИПЬХСШВЬПЭ", "ГГЯСУЮШЖЗНШЯКЕБМ", "Х_НДРРОЮХНГЩЮЭЙЬ", "КЕКУГХЬДЯЙНЬАЩЦИ", "ИЩПЩПФЯСДЫТДЬЧОР", "АТМЮЫЮЩЩАШХЮЖЖЮФ", "ЫФЕЯЬЮБДФФСМОКЕЛ")

        val list3 = listOf("________________", "________________", "________________", "________________", "________________", "________________", "________________", "________________", "________________")


        assertNotEquals(list1,out1)
        assertNotEquals(list2,out2)
        assertNotEquals(list3,out3)
    }


}