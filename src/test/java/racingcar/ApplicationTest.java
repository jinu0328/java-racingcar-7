package racingcar;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    @Test
    void 기능_테스트() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "1");
                    assertThat(output()).contains("pobi : -", "woni : ", "최종 우승자 : pobi");
                },
                MOVING_FORWARD, STOP
        );
    }

    @Test
    void 기능_테스트_복잡한_경우() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("j, ji, jin, jinu", "5");
                    assertThat(output())
                            .contains("j : --", "ji : -", "jin : -", "jinu : ")
                            .contains("최종 우승자 : j");
                },
                MOVING_FORWARD, STOP, MOVING_FORWARD, STOP, // 1st round
                MOVING_FORWARD, MOVING_FORWARD, STOP, STOP, // 2nd round
                STOP, MOVING_FORWARD, MOVING_FORWARD, STOP, // 3rd round
                MOVING_FORWARD, STOP, MOVING_FORWARD, STOP, // 4th round
                MOVING_FORWARD, STOP, STOP, MOVING_FORWARD  // 5th round
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 자동차_이름이_1개_이하일_때() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi", "1"))
                        .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 자동차_이름이_중복될_때() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi, pobi", "1"))
                        .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 자동차_이름이_공백일_때() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(" , pobi", "1"))
                        .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 자동차_이름_길이가_기준을_넘었을_때() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobipo, pobi", "1"))
                        .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 라운드_횟수가_숫자로_입력되지_않았을_때() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi, woni", "a"))
                        .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 라운드_횟수가_int_범위를_넘었을_때() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi, woni", "2147483648"))
                        .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 라운드_횟수가_1보다_작을_때() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi, woni", "0"))
                        .isInstanceOf(IllegalArgumentException.class));
    }

    @Test
    void 자동차_개수가_1개_이하일_때() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi", "1"))
                        .isInstanceOf(IllegalArgumentException.class));
    }


    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
