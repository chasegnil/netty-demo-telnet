package com.github.chasegnil.netty.enumeration;

/**
 * 游戏玩法
 */
public enum GamePlayTypeEnum {

    /**
     * 任选二
     */
    REN_XUAN_ER("任选二", 2),

    /**
     * 任选二胆拖
     */
    REN_XUAN_ER_DAN_TUO("任选二胆拖", 102),

    /**
     * 任选三
     */
    REN_XUAN_SAN("任选三", 3),

    /**
     * 任选三胆拖
     */
    REN_XUAN_SAN_DAN_TUO("任选三胆拖", 103),

    /**
     * 任选四
     */
    REN_XUAN_SI("任选四", 4),

    /**
     * 任选四胆拖
     */
    REN_XUAN_SI_DAN_TUO("任选四胆拖", 104),

    /**
     * 任选五
     */
    REN_XUAN_WU("任选五", 5),

    /**
     * 任选五胆拖
     */
    REN_XUAN_WU_DAN_TUO("任选五胆拖", 105),

    /**
     * 任选六
     */
    REN_XUAN_LIU("任选六", 6),

    /**
     * 任选六胆拖
     */
    REN_XUAN_LIU_DAN_TUO("任选六胆拖", 106),

    /**
     * 任选七
     */
    REN_XUAN_QI("任选七", 7),

    /**
     * 任选七胆拖
     */
    REN_XUAN_QI_DAN_TUO("任选七胆拖", 107),

    /**
     * 任选八
     */
    REN_XUAN_BA("任选八", 8),

    /**
     * 前一直选
     */
    QIAN_YI_ZHI_XUAN("前一直选", 1),

    /**
     * 前二直选
     */
    QIAN_ER_ZHI_XUAN("前二直选", 9),

    /**
     * 前二组选
     */
    QIAN_ER_ZHU_XUAN("前二组选", 10),

    /**
     * 前二组选胆拖
     */
    QIAN_ER_ZHU_XUAN_DAN_TUO("前二组选胆拖", 110),

    /**
     * 前三直选
     */
    QIAN_SAN_ZHI_XUAN("前三直选", 11),

    /**
     * 前三组选
     */
    QIAN_SAN_ZHU_XUAN("前三组选", 12),

    /**
     * 前三组选胆拖
     */
    QIAN_SAN_ZHU_XUAN_DAN_TUO("前三组选胆拖", 112)
    ;


    private String palyTypeName;

    private int palyTypeIndex;

    private GamePlayTypeEnum(String palyTypeName, int palyTypeIndex) {
        this.palyTypeName = palyTypeName;
        this.palyTypeIndex = palyTypeIndex;
    }

    public String getPalyTypeName() {
        return palyTypeName;
    }

    public int getPalyTypeIndex() {
        return palyTypeIndex;
    }

    public static void main(String[] args) {
        System.out.println(GamePlayTypeEnum.QIAN_ER_ZHI_XUAN.getPalyTypeIndex());
    }

}
