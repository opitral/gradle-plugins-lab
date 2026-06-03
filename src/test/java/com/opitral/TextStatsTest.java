package com.opitral;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextStatsTest {

    @Test
    void countsWords() {
        assertEquals(3, TextStats.wordCount("один два три"));
        assertEquals(0, TextStats.wordCount("   "));
    }

    @Test
    void countsCharsWithoutWhitespace() {
        assertEquals(6, TextStats.charCount("ab cd ef"));
    }

    @Test
    void countsSentences() {
        assertEquals(2, TextStats.sentenceCount("Привіт. Як справи?"));
    }
}
