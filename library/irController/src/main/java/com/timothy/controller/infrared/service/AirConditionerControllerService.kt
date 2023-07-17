package com.timothy.controller.infrared.service


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.controller.infrared.service.AirConditionerControllerService
 * @Author: MoTao
 * @Date: 2023-07-17
 * <p>
 *     空调遥控接口服务
 * <p/>
 */
interface AirConditionerControllerService {

    enum class WindSpeed(speed: Int){   // 空调风等级
        Auto(0), Level1(1), Level2(2), Level3(3)
    }

    /**
     * 电源打开
     */
    fun on()

    /**
     * 电源关闭
     */
    fun off()

    /**
     * 空调风速
     * @param speed 自动，强风，中风和弱风
     */
    fun changeWindSpeed(speed: WindSpeed)

    /**
     * 摆风或者风向  自动摆风，上下摆风和左右摆风，或者自动风向和手动风向
     */
    fun airSwing()

    /**
     * 温度 +1
     */
    fun temperatureUp()

    /**
     * 温度 -1
     */
    fun temperatureDown()

    /**
     * 定时关闭
     * @param min 定时分钟数
     */
    fun timingOff(min: Int)
}