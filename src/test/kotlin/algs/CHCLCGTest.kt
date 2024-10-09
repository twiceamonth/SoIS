import org.junit.jupiter.api.Test
import org.example.algs.CHCLCG
import org.junit.jupiter.api.Assertions.assertEquals

class CHCLCGTest {


    @Test
    fun CHCLCGTest(){
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


    }


}