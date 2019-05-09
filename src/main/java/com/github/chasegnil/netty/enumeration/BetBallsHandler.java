package com.github.chasegnil.netty.enumeration;

/**
 * 投注号码处理
 */
public class BetBallsHandler {

    /**
     * 处理投注号码
     * @param balls 投注号码
     * @param playType 玩法
     * @return 处理后的号码，号码间用空格隔开，胆码用[]围起来
     */
    public static String dealWithBetBalls(String balls, int playType){
        String result = null;

        if (balls == null || "".equals(balls)) {
            return null;
        }

        balls = balls.trim();

        // 任选二 例：单式：0102，复式：010203
        if (playType == GamePlayTypeEnum.REN_XUAN_ER.getPalyTypeIndex()) {
            result = generic(balls);
        }

        // 任选二胆拖 例：[01]0203(胆至少选择1个，最多1个，托至少选择1个，最多10个)
        if (playType == GamePlayTypeEnum.REN_XUAN_ER_DAN_TUO.getPalyTypeIndex()) {
            result = containsDan(balls);
        }

        // 任选三 例：例：单式：010203，复式：01020304
        if (playType == GamePlayTypeEnum.REN_XUAN_SAN.getPalyTypeIndex()) {
            result = generic(balls);
        }

        // 任选三胆拖 例：[0102]030405(胆至少选择1个，最多2个，托至少选择1个，最多10个)
        if (playType == GamePlayTypeEnum.REN_XUAN_SAN_DAN_TUO.getPalyTypeIndex()) {
            result = containsDan(balls);
        }

        // 任选四 例：单式：01020304，复式：0102030405
        if (playType == GamePlayTypeEnum.REN_XUAN_SI.getPalyTypeIndex()) {
            result = generic(balls);
        }

        // 任选四胆拖 例：[010203]030405(胆至少选择1个，最多3个，托至少选择1个，最多10个)
        if (playType == GamePlayTypeEnum.REN_XUAN_SI_DAN_TUO.getPalyTypeIndex()) {
            result = containsDan(balls);
        }

        // 任选五 例：单式：0102030405，复式：010203040506
        if (playType == GamePlayTypeEnum.REN_XUAN_WU.getPalyTypeIndex()) {
            result = generic(balls);
        }

        // 任选五胆拖 例：[01020304]0506(胆至少选择1个，最多4个，托至少选择1个，最多10个)
        if (playType == GamePlayTypeEnum.REN_XUAN_WU_DAN_TUO.getPalyTypeIndex()) {
            result = containsDan(balls);
        }

        // 任选六 例：单式：010203040506，复式：01020304050607
        if (playType == GamePlayTypeEnum.REN_XUAN_LIU.getPalyTypeIndex()) {
            result = generic(balls);
        }

        // 任选六胆拖 例：[0102030405]0607(胆至少选择1个，最多5个，托至少选择1个，最多10个)
        if (playType == GamePlayTypeEnum.REN_XUAN_LIU_DAN_TUO.getPalyTypeIndex()) {
            result = containsDan(balls);
        }

        // 任选七 例：单式：01020304050607，复式：0102030405060708
        if (playType == GamePlayTypeEnum.REN_XUAN_QI.getPalyTypeIndex()) {
            result = generic(balls);
        }

        // 任选七胆拖 例：[010203040506]0708(胆至少选择1个，最多6个，托至少选择1个，最多10个)
        if (playType == GamePlayTypeEnum.REN_XUAN_QI_DAN_TUO.getPalyTypeIndex()) {
            result = containsDan(balls);
        }

        // 任选八 例：单式：0102030405060708，复式：010203040506070809
        if (playType == GamePlayTypeEnum.REN_XUAN_BA.getPalyTypeIndex()) {
            result = generic(balls);
        }

        // 前一直选 例：单式：01----，复式：010203040506070809----
        if (playType == GamePlayTypeEnum.QIAN_YI_ZHI_XUAN.getPalyTypeIndex()) {
            balls = balls.substring(0, balls.indexOf("-"));
            result = generic(balls);
        }

        // 前二直选 例：单式：01-02---，复式：0102-0305---
        if (playType == GamePlayTypeEnum.QIAN_ER_ZHI_XUAN.getPalyTypeIndex()) {
            String firstBall = balls.substring(0, balls.indexOf("-"));
            String secondBall = balls.substring((balls.indexOf("-") + 1), balls.lastIndexOf("---"));
            StringBuffer sb = new StringBuffer();
            sb.append(generic(firstBall));
            sb.append(",");
            sb.append(generic(secondBall));
            result = sb.toString();
        }

        // 前二组选 例：单式：0102---，复式：010203----
        if (playType == GamePlayTypeEnum.QIAN_ER_ZHU_XUAN.getPalyTypeIndex()) {
            balls = balls.substring(0, balls.indexOf("-"));
            result = generic(balls);
        }

        // 前二组选胆拖 例：[01]0203--- (胆至少选择1个，最多1个，托至少选择1个，最多10个)
        if (playType == GamePlayTypeEnum.QIAN_ER_ZHU_XUAN_DAN_TUO.getPalyTypeIndex()) {
            balls = balls.substring(0, balls.indexOf("-"));
            result = containsDan(balls);
        }

        // 前三直选 例：单式：01-02-06--，复式：0102-0305-08--
        if (playType == GamePlayTypeEnum.QIAN_SAN_ZHI_XUAN.getPalyTypeIndex()) {
            String firstBall = balls.substring(0, balls.indexOf("-"));
            String secondBallAnd3rdBall = balls.substring((balls.indexOf("-") + 1), balls.lastIndexOf("--"));
            String secondBall = secondBallAnd3rdBall.substring(0, secondBallAnd3rdBall.indexOf("-"));
            String thridBall = secondBallAnd3rdBall.substring((secondBallAnd3rdBall.indexOf("-") + 1));
            StringBuffer sb = new StringBuffer();
            sb.append(generic(firstBall));
            sb.append(",");
            sb.append(generic(secondBall));
            sb.append(",");
            sb.append(generic(thridBall));
            result = sb.toString();
        }

        // 前三组选 例：单式：010203--，复式：01020304--
        if (playType == GamePlayTypeEnum.QIAN_SAN_ZHU_XUAN.getPalyTypeIndex()) {
            balls = balls.substring(0, balls.indexOf("-"));
            result = generic(balls);
        }

        // 前三组选胆拖 例：[01]020305-- (胆至少选择1个，最多2个，托至少选择1个，最多10个)
        if (playType == GamePlayTypeEnum.QIAN_SAN_ZHU_XUAN_DAN_TUO.getPalyTypeIndex()) {
            balls = balls.substring(0, balls.indexOf("-"));
            result = containsDan(balls);
        }

        return result;
    }


    /**
     * 普通的投注，除直选外的
     * @param balls
     * @return
     */
    private static String generic(String balls){
        String result = null;
        StringBuffer sb = new StringBuffer();
        int length = balls.length();
        for (int i=0; i<length; i=i+2) {
            sb.append(balls.substring(i, i+2));
            if (i == length - 2) {

            } else {
                sb.append(" ");
            }
        }

        result = sb.toString();
        return result;
    }

    /**
     * 包含胆的
     * @param balls
     * @return
     */
    private static String containsDan(String balls){
        String result = null;
        StringBuffer sb = new StringBuffer();
        String[] split = balls.split("]");
        String dan = split[0];
        String notDan = split[1];
        // 胆位
        int danLength = dan.length();
        sb.append("[");
        for (int i=1; i<danLength; i=i+2) {
            sb.append(dan.substring(i, i+2));
            if (i == danLength - 2) {

            } else {
                sb.append(" ");
            }
        }
        sb.append("] ");

        // 非胆位
        int length = notDan.length();
        for (int i=0; i<length; i=i+2) {
            sb.append(notDan.substring(i, i+2));
            if (i == length - 2) {

            } else {
                sb.append(" ");
            }
        }

        result = sb.toString();
        return result;
    }


    public static void main(String[] args) {
        String balls = "[0104]020305--";
        String result = BetBallsHandler.dealWithBetBalls(balls, GamePlayTypeEnum.QIAN_SAN_ZHU_XUAN_DAN_TUO.getPalyTypeIndex());
        System.out.println(result);
    }
}
