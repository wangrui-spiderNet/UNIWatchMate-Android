package com.base.sdk.entity.data

import androidx.annotation.IntDef

@IntDef(
    WmSyncDataType.STEP,
    WmSyncDataType.SLEEP,
    WmSyncDataType.HEART_RATE,
    WmSyncDataType.OXYGEN,
    WmSyncDataType.BLOOD_PRESSURE,
    WmSyncDataType.RESPIRATORY_RATE,
    WmSyncDataType.SPORT,
    WmSyncDataType.ECG,
    WmSyncDataType.TEMPERATURE,
    WmSyncDataType.PRESSURE,
    WmSyncDataType.GAME,
    WmSyncDataType.GPS,
    WmSyncDataType.HEART_RATE_MEASURE,
    WmSyncDataType.OXYGEN_MEASURE,
    WmSyncDataType.BLOOD_PRESSURE_MEASURE,
    WmSyncDataType.RESPIRATORY_RATE_MEASURE,
    WmSyncDataType.TEMPERATURE_MEASURE,
    WmSyncDataType.PRESSURE_MEASURE,
    WmSyncDataType.TODAY_TOTAL_DATA,
)
@Retention(AnnotationRetention.BINARY)
annotation class WmSyncDataType {
    companion object {
        /**
         * Data type of step(步数数据)
         */
        const val STEP = 0x01

        /**
         * Data type of sleep(睡眠数据)
         */
        const val SLEEP = 0x02 //睡眠数据

        /**
         * Data type of heart rate(心率数据)
         */
        const val HEART_RATE = 0x03 //心率数据

        /**
         * Data type of oxygen(血氧数据)
         */
        const val OXYGEN = 0x04 //血氧数据

        /**
         * Data type of blood pressure(血压数据)
         */
        const val BLOOD_PRESSURE = 0x05 //血压数据

        /**
         * Data type of respiratory rate(呼吸频率数据)
         */
        @Deprecated(
            message = "No device support anymore"
        )
        const val RESPIRATORY_RATE = 0x06 //呼吸频率数据

        /**
         * Data type of sport(运动数据)
         */
        const val SPORT = 0x10 //运动数据

        /**
         * Data type of ecg(心电数据)
         */
        const val ECG = 0x07 //心电数据

        /**
         * Data type of temperature(温度数据)
         */
        const val TEMPERATURE = 0x11 //温度数据

        /**
         * Data type of pressure(压力数据)
         */
        const val PRESSURE = 0x12 //

        /**
         * Data type of game(游戏数据)
         */
        const val GAME = 0x13

        /**
         * Data type of gps(定位数据)
         */
        const val GPS = 0x0A

        /**
         * Data type of Heart rate value measured manually(手动测量心率数据)
         */
        const val HEART_RATE_MEASURE = 0x83

        /**
         * Data type of oxygen value measured manually(手动测量血氧数据)
         */
        const val OXYGEN_MEASURE = 0x84 //手动测量血氧数据

        /**
         * Data type of blood pressure value measured manually
         */
        const val BLOOD_PRESSURE_MEASURE = 0x85 //手动测量血压数据

        /**
         * Data type of respiratory rate value measured manually
         */
        @Deprecated(
            message = "No device support anymore"
        )
        const val RESPIRATORY_RATE_MEASURE = 0x86 //手动测量呼吸频率数据

        /**
         * Data type of temperature value measured manually
         */
        const val TEMPERATURE_MEASURE = 0x91 //手动测量温度数据

        /**
         * Data type of pressure value measured manually
         */
        const val PRESSURE_MEASURE = 0x92 //手动测量压力数据

        /**
         * Data type of day total data
         */
        const val TODAY_TOTAL_DATA = 0xFF //总数据
    }
}