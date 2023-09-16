package com.base.sdk.entity.common

enum class SportType {
    WMActivityTypeOutdoorRunning,      // 户外跑步
    WMActivityTypeOutdoorWalking,      // 户外健走
    WMActivityTypeMountainClimbing,    // 登山
    WMActivityTypeCrossCountry,        // 越野
    WMActivityTypeOutdoorHiking,       // 户外徒步
    WMActivityTypeIndoorRunning,       // 室内跑步
    WMActivityTypeOutdoorCycling,      // 户外骑行
    WMActivityTypeSmallWheelBike,      // 小轮车
    WMActivityTypeHunting,             // 打猎
    WMActivityTypeSailing,             // 帆船运动
    WMActivityTypeSkateboarding,       // 滑板
    WMActivityTypeRollerSkating,       // 轮滑
    WMActivityTypeOutdoorIceSkating,   // 户外滑冰
    WMActivityTypeHorsebackRiding,     // 马术
    WMActivityTypeMountainBiking,      // 山地自行车
    WMActivityTypeIndoorCycling,       // 室内骑行
    WMActivityTypeFreeTraining,        // 自由训练
    WMActivityTypeBasketball,          // 篮球
    WMActivityTypeSoccer,              // 足球
    WMActivityTypePingPong,            // 乒乓球
    WMActivityTypeBadminton,           // 羽毛球
    WMActivityTypeTennis,              // 网球
    WMActivityTypeStrengthTraining,    // 力量训练
    WMActivityTypePilates,             // 普拉提
    WMActivityTypeIndoorWalking,       // 室内走路
    WMActivityTypeTreadmill,           // 跑步机
    WMActivityTypeGymnastics,          // 体操
    WMActivityTypeRowing,              // 划船
    WMActivityTypeJumpingJack,         // 开合跳
    WMActivityTypeElliptical,          // 椭圆机
    WMActivityTypeStepping,            // 踏步
    WMActivityTypeRiding,              // 骑马
    WMActivityTypeYoga,                // 瑜伽
    WMActivityTypeCricket,             // 板球
    WMActivityTypeBaseball,            // 棒球
    WMActivityTypeBowling,             // 保龄球
    WMActivityTypeSquash,              // 壁球
    WMActivityTypeSoftball,            // 垒球
    WMActivityTypeVolleyball,          // 排球
    WMActivityTypeBallet,              // 芭蕾
    WMActivityTypeStreetDance,         // 街舞
    WMActivityTypeDance,               // 舞蹈
    WMActivityTypeFencing,             // 击剑
    WMActivityTypeKarate,              // 空手道
    WMActivityTypeBoxing,              // 拳击
    WMActivityTypeJudo,                // 柔道
    WMActivityTypeWrestling,           // 摔跤
    WMActivityTypeTaiChi,              // 太极
    WMActivityTypeShuttlecock,         // 毽球
    WMActivityTypeTaekwondo,           // 跆拳道
    WMActivityTypeCrossTraining,       // 交叉训练
    WMActivityTypeSitups,              // 仰卧起坐
    WMActivityTypeAbdominalTraining,   // 腰腹训练
    WMActivityTypeLatinDance,          // 拉丁舞
    WMActivityTypeRugby,               // 橄榄球
    WMActivityTypeFieldHockey,         // 曲棍球
    WMActivityTypeRowingMachine,       // 划船机
    WMActivityTypeSkiing,              // 滑雪
    WMActivityTypeIceHockey,           // 冰球
    WMActivityTypeVO2Max,              // 最大摄氧量
    WMActivityTypeWalkingMachine,      // 漫步机
    WMActivityTypeTrackAndField,       // 田径
    WMActivityTypeRelaxation,          // 整理放松
    WMActivityTypeCrossFit,            // 交叉配合
    WMActivityTypeFunctionalTraining,  // 功能性训练
    WMActivityTypeFitnessTraining,     // 体能训练
    WMActivityTypeArchery,             // 射箭
    WMActivityTypeFlexibility,         // 柔韧度
    WMActivityTypeMixedAerobics,       // 混合有氧
    WMActivityTypeKickboxing,          // 自由搏击
    WMActivityTypeAustralianFootball,  // 澳式足球
    WMActivityTypeMartialArts,         // 武术
    WMActivityTypeBuildingClimbing,    // 爬楼
    WMActivityTypeHandball,            // 手球
    WMActivityTypeCurling,             // 冰壶
    WMActivityTypeSnowboarding,        // 单板滑雪
    WMActivityTypeLeisureSports,       // 休闲运动
    WMActivityTypeAmericanFootball,    // 美式橄榄球
    WMActivityTypeHandCrankedBike,     // 手摇车
    WMActivityTypeFishing,             // 钓鱼
    WMActivityTypeFrisbee,             // 飞盘
    WMActivityTypeGolf,                // 高尔夫
    WMActivityTypeFolkDance,           // 民族舞
    WMActivityTypeAlpineSkiing,        // 高山滑雪
    WMActivityTypeSnowSports,          // 雪上运动
    WMActivityTypeRelaxationMeditation,// 舒缓冥想类运动
    WMActivityTypeCoreTraining,        // 核心训练
    WMActivityTypeFitnessGame,         // 健身游戏
    WMActivityTypeFitnessDance,        // 健身操
    WMActivityTypeGroupGymnastics,     // 团体操
    WMActivityTypeBoxingDance,         // 搏击操
    WMActivityTypeLongFieldHockey,     // 长曲棍球
    WMActivityTypeFoamRollerRelaxation,// 泡沫轴筋膜放松
    WMActivityTypeHorizontalBar,       // 单杠
    WMActivityTypeParallelBars,        // 双杠
    WMActivityTypeHulaHoop,            // 呼啦圈
    WMActivityTypeDarts,               // 飞镖
    WMActivityTypePickleball,          // 匹克球
    WMActivityTypeHIIT,                // HIIT
    WMActivityTypeShooting,            // 射击
    WMActivityTypeTrampoline,          // 蹦床
    WMActivityTypeBalanceBike,         // 平衡车
    WMActivityTypeRollerblading,       // 溜旱冰
    WMActivityTypeParkour,             // 跑酷
    WMActivityTypePullUps,             // 引体向上
    WMActivityTypePushUps,             // 俯卧撑
    WMActivityTypePlank,               // 平板支撑
    WMActivityTypeRockClimbing,        // 攀岩
    WMActivityTypeHighJump,            // 跳高
    WMActivityTypeBungeeJumping,       // 蹦极
    WMActivityTypeLongJump,            // 跳远
    WMActivityTypeMarathon,            // 马拉松
    WMActivityTypeJumpRope             // 跳绳
}

enum class SportParamType {
    /**
     * 户外带步数运动
     */
    SPT_OUTDOOR_WITH_STEP,
    /**
     * 户外带距离运动
     */
    SPT_OUTDOOR_WITH_DISTANCE,
    /**
     * 室内带步数运动
     */
    SPT_INDOOR_WITH_STEP,
    /**
     * 基础数据运动
     */
    SPT_BASIC_DATA,
    /**
     * 跳绳运动
     */
    SPT_JUMP_ROPE,
    /**
     * 泳池游泳
     */
    SPT_SWIMMING_IN_POOL,
    /**
     * 开放水域游泳
     */
    SPT_SWIMMING_IN_OPEN,
    /**
     * 户外足球
     */
    SPT_OUTDOOR_FOOTBALL

}

enum class Sport(val id: Int){
    /**
     * 户外骑行
     */
    OUTDOOR_RIDING(0),
    /**
     * 户外跑步
     */
    OUTDOOR_RUNNING(1),
    /**
     * 室内跑步
     */
    INDOOR_RUNNING(2),
    /**
     * 户外健走
     */
    OUTDOOR_WALKING(3),
    /**
     * 登山
     */
    MOUNTAINEERING(4),
    /**
     * 篮球
     */
    BASKETBALL(5),
    /**
     * 游泳  (花样游泳和泳池游泳都属于SportParamType.SPT_BASIC_DATA)
     * TODO 2
     */
    SWIMMING(6),
    /**
     * 羽毛球
     */
    BADMINTON(7),
    /**
     * 足球
     */
    FOOTBALL(8),
    /**
     * 椭圆机
     */
    ELLIPTICAL_MACHINE(9),
    /**
     * 瑜伽
     */
    YOGA(10),
    /**
     * 乒乓球
     */
    TABLE_TENNIS(11),
    /**
     * 跳绳
     */
    JUMP_ROPE(12),
    /**
     * 划船机
     */
    ROWING_MACHINE(13),
    /**
     * 自由训练
     */
    FREE_TRAINING(16),
    /**
     * 网球
     */
    TENNIS(17),
    /**
     * 棒球
     */
    BASEBALL(18),
    /**
     * 橄榄球
     */
    RUGBY(19),
    /**
     * 板球
     */
    CRICKET(20),
    /**
     * 力量训练
     */
    STRENGTH_TRAINING(22),
    /**
     * 室内走路
     */
    INDOOR_WALKING(23),
    /**
     * 室内骑行
     */
    INDOOR_RIDING(24),
    /**
     * 舞蹈
     */
    DANCING(26),
    /**
     * 呼啦圈
     */
    HULA_HOOP(27),
    /**
     * 高尔夫
     */
    GOLF(28),
    /**
     * 跳远
     */
    LONG_JUMP(29),
    /**
     * 仰卧起坐
     */
    SIT_UP(30),
    /**
     * 排球
     */
    VOLLEYBALL(31),
    /**
     * 跑酷
     */
    PARKOUR(32),
    /**
     * 徒步、户外徒步
     */
    HIKING(33),
    /**
     * 曲棍球
     */
    HOCKEY(34),
    /**
     * 划船？？？
     * TODO 12
     */
    ROWING(35),
    /**
     * HIIT（高强度间歇训练）
     */
    HIIT(36),
    /**
     * 垒球
     */
    SOFTBALL(37),
    /**
     * 越野跑
     */
    CROSS_COUNTRY_RUNNING(38),
    /**
     * 滑雪
     */
    SKIING(39),
    /**
     * 漫步机
     */
    WALKING_MACHINE(40),
    /**
     * 整理放松？？？？
     * TODO 12
     */
    STRETCHING(41),
    /**
     * 交叉训练
     */
    CROSS_TRAINING(42),
    /**
     * 普拉提
     */
    PILATES(43),
    /**
     * 交叉配合????
     * TODO 1 2
     */
    CROSS_COORDINATION(44),
    /**
     * 功能性训练
     */
    FUNCTIONAL_TRAINING(45),
    /**
     * 体能训练
     */
    PHYSICAL_TRAINING(46),
    /**
     * 混合有氧
     */
    MIXED_AEROBICS(47),
    /**
     * 拉丁舞
     */
    LATIN_DANCE(48),
    /**
     * 街舞
     */
    HIP_HOP(49),
    /**
     * 自由搏击
     */
    FREE_COMBAT(50),
    /**
     * 芭蕾
     */
    BALLET(51),
    /**
     * 澳式足球
     */
    AUSTRALIAN_FOOTBALL(52),
    /**
     * 保龄球
     */
    BOWLING(53),
    /**
     * 壁球
     */
    SQUASH(54),
    /**
     * 冰壶
     */
    CURLING(55),
    /**
     * 单板滑雪
     */
    SNOWBOARDING(56),
    /**
     * 钓鱼
     */
    FISHING(57),
    /**
     * 飞盘
     */
    FRISBEE(58),
    /**
     * 高山滑雪
     */
    ALPINE_SKIING(59),
    /**
     * 核心训练
     */
    CORE_TRAINING(60),
    /**
     * 滑冰
     */
    SKATING(61),
    /**
     * 健身游戏
     */
    FITNESS_GAME(62),
    /**
     * 健身操
     */
    AEROBICS(63),
    /**
     * 团体操
     */
    GROUP_AEROBICS(64),
    /**
     * 搏击操
     */
    COMBAT_AEROBICS(65),
    /**
     * 击剑
     */
    FENCING(66),
    /**
     * 爬楼
     */
    STAIR_CLIMBING(67),
    /**
     * 美式橄榄球
     */
    AMERICAN_FOOTBALL(68),
    /**
     * 泡沫轴筋膜放松
     */
    FOAM_ROLLER(69),
    /**
     * 匹克球
     */
    PICKLEBALL(70),
    /**
     * 拳击
     */
    BOXING(71),
    /**
     * 跆拳道
     */
    TAEKWONDO(72),
    /**
     * 空手道
     */
    KARATE(73),
    /**
     * 柔韧度????
     * TODO 12
     */
    FLEXIBILITY(74),
    /**
     * 手球
     */
    HANDBALL(75),
    /**
     * 手摇车
     */
    HANDCYCLING(76),
    /**
     * 舒缓冥想类运动
     */
    MEDITATION(77),
    /**
     * 摔跤
     */
    WRESTLING(78),
    /**
     * 踏步
     */
    STEP(79),
    /**
     * 太极
     */
    TAI_CHI(80),
    /**
     * 体操
     */
    GYMNASTICS(81),
    /**
     * 田径
     */
    ATHLETICS(82),
    /**
     * 武术
     */
    MARTIAL_ARTS(83),
    /**
     * 休闲运动
     */
    LEISURE_SPORTS(84),
    /**
     * 雪上运动
     */
    SNOW_SPORTS(85),
    /**
     * 长曲棍球
     */
    FIELD_HOCKEY(86),
    /**
     * 单杠
     */
    PARALLEL_BARS(87),
    /**
     * 双杠
     */
    HORIZONTAL_BAR(88),
    /**
     * 轮滑
     */
    ROLLER_SKATING(89),
    /**
     * 飞镖
     */
    DARTS(90),
    /**
     * 射箭
     */
    ARCHERY(91),
    /**
     * 骑马、马术
     */
    HORSE_RIDING(92),
    /**
     * 毽球
     */
    JIANZI(93),
    /**
     * 冰球
     */
    ICE_HOCKEY(94),
    /**
     * 腰腹训练
     */
    CORE_STRENGTHENING(95),
    /**
     * 最大摄氧量测试
     */
    VO2_MAX_TEST(96),
    /**
     * 柔道
     */
    JUDO(97),
    /**
     * 蹦床
     */
    TRAMPOLINE(98),
    /**
     * 滑板
     */
    SKATEBOARDING(99),
    /**
     * 平衡车
     */
    BALANCE_CAR(100),
    /**
     * 溜旱冰 （已改为室内滑冰）
     * TODO 2
     */
    INLINE_SKATING(101),
    /**
     * 跑步机
     */
    TREADMILL(102),
    /**
     * 跳水
     */
    DIVING(103),
    /**
     * 冲浪
     */
    SURFING(104),
    /**
     * 浮潜
     */
    SNORKELING(105),
    /**
     * 引体向上
     */
    PULL_UP(106),
    /**
     * 俯卧撑
     */
    PUSH_UP(107),
    /**
     * 平板支撑
     */
    PLANK(108),
    /**
     * 攀岩
     */
    ROCK_CLIMBING(109),
    /**
     * 跳高
     */
    HIGH_JUMP(110),
    /**
     * 蹦极
     */
    BUNGEE_JUMPING(111),
    /**
     * 民族舞
     */
    FOLK_DANCE(112),
    /**
     * 打猎
     */
    HUNTING(113),
    /**
     * 射击
     */
    SHOOTING(114),
    /**
     * 马拉松
     */
    MARATHON(115);

    companion object {
        fun fromId(id: Int): Sport? {
            return values().find { it.id == id }
        }
    }
}
