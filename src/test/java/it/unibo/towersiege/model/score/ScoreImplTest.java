package it.unibo.towersiege.model.score;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towersiege.model.score.api.Score;
import it.unibo.towersiege.model.score.impl.ScoreImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreImplTest {
    
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
        score.addPoints(10);
        assertEquals(10, score.getTotal());

        // Negative points should not be added
        score.addPoints(-5);
        assertEquals(10, score.getTotal());
    }

    @Test
    void testReset() {
        score.addPoints(50);
        score.reset();
        assertEquals(0, score.getTotal());
    }
}
