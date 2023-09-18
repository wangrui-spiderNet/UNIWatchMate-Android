package com.base.sdk.entity.common

enum class SportType {

    OutdoorRunning,      // 户外跑步

    OutdoorWalking,      // 户外健走

    MountainClimbing,    // 登山

    CrossCountry,        // 越野

    OutdoorHiking,       // 户外徒步

    IndoorRunning,       // 室内跑步

    OutdoorCycling,      // 户外骑行

    SmallWheelBike,      // 小轮车

    Hunting,             // 打猎

    Sailing,             // 帆船运动

    Skateboarding,       // 滑板

    RollerSkating,       // 轮滑

    OutdoorIceSkating,   // 户外滑冰

    HorsebackRiding,     // 马术

    MountainBiking,      // 山地自行车

    IndoorCycling,       // 室内骑行

    FreeTraining,        // 自由训练

    Basketball,          // 篮球

    Soccer,              // 足球

    PingPong,            // 乒乓球

    Badminton,           // 羽毛球

    Tennis,              // 网球

    StrengthTraining,    // 力量训练

    Pilates,             // 普拉提

    IndoorWalking,       // 室内走路

    Treadmill,           // 跑步机

    Gymnastics,          // 体操

    Rowing,              // 划船

    JumpingJack,         // 开合跳

    Elliptical,          // 椭圆机

    Stepping,            // 踏步

    Riding,              // 骑马

    Yoga,                // 瑜伽

    Cricket,             // 板球

    Baseball,            // 棒球

    Bowling,             // 保龄球

    Squash,              // 壁球

    Softball,            // 垒球

    Volleyball,          // 排球

    Ballet,              // 芭蕾

    StreetDance,         // 街舞

    Dance,               // 舞蹈

    Fencing,             // 击剑

    Karate,              // 空手道

    Boxing,              // 拳击

    Judo,                // 柔道

    Wrestling,           // 摔跤

    TaiChi,              // 太极

    Shuttlecock,         // 毽球

    Taekwondo,           // 跆拳道

    CrossTraining,       // 交叉训练

    Situps,              // 仰卧起坐

    AbdominalTraining,   // 腰腹训练

    LatinDance,          // 拉丁舞

    Rugby,               // 橄榄球

    FieldHockey,         // 曲棍球

    RowingMachine,       // 划船机

    Skiing,              // 滑雪

    IceHockey,           // 冰球

    VO2Max,              // 最大摄氧量

    WalkingMachine,      // 漫步机

    TrackAndField,       // 田径

    Relaxation,          // 整理放松

    CrossFit,            // 交叉配合

    FunctionalTraining,  // 功能性训练

    FitnessTraining,     // 体能训练

    Archery,             // 射箭

    Flexibility,         // 柔韧度

    MixedAerobics,       // 混合有氧

    Kickboxing,          // 自由搏击

    AustralianFootball,  // 澳式足球

    MartialArts,         // 武术

    BuildingClimbing,    // 爬楼

    Handball,            // 手球

    Curling,             // 冰壶

    Snowboarding,        // 单板滑雪

    LeisureSports,       // 休闲运动

    AmericanFootball,    // 美式橄榄球

    HandCrankedBike,     // 手摇车

    Fishing,             // 钓鱼

    Frisbee,             // 飞盘

    Golf,                // 高尔夫

    FolkDance,           // 民族舞

    AlpineSkiing,        // 高山滑雪

    SnowSports,          // 雪上运动

    RelaxationMeditation,// 舒缓冥想类运动

    CoreTraining,        // 核心训练

    FitnessGame,         // 健身游戏

    FitnessDance,        // 健身操

    GroupGymnastics,     // 团体操

    BoxingDance,         // 搏击操

    LongFieldHockey,     // 长曲棍球

    FoamRollerRelaxation,// 泡沫轴筋膜放松

    HorizontalBar,       // 单杠

    ParallelBars,        // 双杠

    HulaHoop,            // 呼啦圈

    Darts,               // 飞镖

    Pickleball,          // 匹克球

    HIIT,                // HIIT

    Shooting,            // 射击

    Trampoline,          // 蹦床

    BalanceBike,         // 平衡车

    Rollerblading,       // 溜旱冰

    Parkour,             // 跑酷

    PullUps,             // 引体向上

    PushUps,             // 俯卧撑
    Plank,               // 平板支撑
    RockClimbing,        // 攀岩
    HighJump,            // 跳高
    BungeeJumping,       // 蹦极
    LongJump,            // 跳远
    Marathon,            // 马拉松
    JumpRope             // 跳绳
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
