/**
 * Unit tests for the HelloWorld module's greeting functionality.
 *
 * This test module demonstrates how to write xunit tests that are run
 * as part of the Gradle build lifecycle via the `testXtc` task.
 */
module HelloWorldTest {
    package greeting import HelloWorld;

    /**
     * Tests for the formatGreeting() function.
     */
    class GreeterTest {
        @Test
        void shouldFormatBasicGreeting() {
            String result = greeting.formatGreeting("Hello ", "there, ", "World");
            assert result == "Hello there, World!";
        }

        @Test
        void shouldFormatGreetingWithCustomName() {
            String result = greeting.formatGreeting("Hello ", "there, ", "Marcus");
            assert result == "Hello there, Marcus!";
        }

        @Test
        void shouldFormatGreetingWithEmptyParts() {
            String result = greeting.formatGreeting("", "", "Test");
            assert result == "Test!";
        }

        @Test
        void shouldFormatGreetingWithDifferentPrefix() {
            String result = greeting.formatGreeting("Hi ", "dear ", "Friend");
            assert result == "Hi dear Friend!";
        }

        @Test
        void shouldAlwaysAppendExclamationMark() {
            String result = greeting.formatGreeting("A", "B", "C");
            assert result.endsWith("!");
        }
    }
}
