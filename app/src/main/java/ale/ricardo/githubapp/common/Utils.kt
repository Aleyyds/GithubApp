package ale.ricardo.githubapp.common

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object Utils {

    fun getFormatNumber(value: Double, scale: String?): String? {
        return getFormatObjValue(BigDecimal(value), scale)
    }

    /**
     * 字符串数字转千位单位值
     * @param value 字符串数字值
     * @param scale 精度值
     * @return
     */
    fun getFormatNumber(value: String?, scale: String?): String? {
        return getFormatObjValue(BigDecimal(value), scale)
    }


    fun getFormatObjValue(bigDecimal: BigDecimal, scale: String?): String? {
        //转换为万元（除以10000）
        val decimal: BigDecimal = bigDecimal.divide(BigDecimal(scale))
        //保留两位小数 ss="###.##";
        //保留定位分组 ss="###,###.##";
        //保留定位分组 ss="###.#";
        val format = "###.#"
        val formater = DecimalFormat(format)
        //四舍五入
        formater.setRoundingMode(RoundingMode.HALF_UP)
        //格式化完成之后得出结果
        return formater.format(decimal)
    }

}