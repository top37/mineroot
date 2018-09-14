package start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static demo.scene.ArenaScene.battle;
import static demo.utils.BooleanExp.is2ArenaFinished;
import static demo.utils.BooleanExp.isSelectHeroPage;
import static demo.utils.MouseKvUtils.mousePressOne;

public class MainStart {

    private static final Logger logger = LoggerFactory.getLogger(MainStart.class);

    public static void main(String[] args) {
        for(int i = 0;i < 3;i++)
        {
            logger.info("--------一局 start----------");
            mousePressOne(1161,557);
            battle();
            //点击返回
            mousePressOne(134,689);
            //等待至页面跳转完成
            is2ArenaFinished();
            //点击忍术对战选人页面
            mousePressOne(762, 418);
            //忍术对战选人页面加载完成
            isSelectHeroPage();
            logger.info("--------一局 end----------");
        }
    }
}
