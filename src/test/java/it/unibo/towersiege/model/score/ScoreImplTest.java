package it.unibo.towersiege.model.score;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.score.api.Score;
import it.unibo.towersiege.model.score.impl.ScoreImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreImplTest {
    
    private static final int PTS_10 = 10;
    private static final int PTS_NEG_5 = -5;
    private static final int PTS_50 = 50;
    private Score score;

    @BeforeEach
    void setUp() {
        score = new ScoreImpl();
    }

    @Test
    void testInitialState() {
        assertEquals(0, score.getTotal());
    }

    @Test
    void testAddPoints() {
        score.addPoints(PTS_10);
        assertEquals(PTS_10, score.getTotal());

        score.addPoints(PTS_NEG_5);
        assertEquals(PTS_10, score.getTotal());
    }

    @Test
    void testReset() {
        score.addPoints(PTS_50);
        score.reset();
        assertEquals(0, score.getTotal());
    }
}
