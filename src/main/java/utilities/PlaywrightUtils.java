package utilities;

import org.apache.logging.log4j.Logger;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * The class {@link PlaywrightUtils} includes essential utility methods for interacting with web
 * elements using Playwright.
 */
public final class PlaywrightUtils {

    private static final Logger LOGGER = Log.getLogger(PlaywrightUtils.class);
    private static final int DEFAULT_TIMEOUT = 5;

    private PlaywrightUtils() {
    }

    /**
     * Method to wait until page is loaded completely with default timeout
     * 
     * @param page Playwright Page object
     */
    public static void untilPageLoadComplete(final Page page) {
        untilPageLoadComplete(page, DEFAULT_TIMEOUT);
    }

    /**
     * Method to wait until page is loaded completely.
     * 
     * @param page:             Playwright Page object
     * @param timeoutInSeconds: time to wait until page loads
     */
    public static void untilPageLoadComplete(final Page page, final int timeoutInSeconds) {
        try {
            page.waitForLoadState(LoadState.DOMCONTENTLOADED, 
                    new Page.WaitForLoadStateOptions().setTimeout(timeoutInSeconds * 1000));
            page.waitForLoadState(LoadState.NETWORKIDLE, 
                    new Page.WaitForLoadStateOptions().setTimeout(timeoutInSeconds * 1000));
            LOGGER.info("Page loaded successfully!!!");
        } catch (TimeoutError e) {
            LOGGER.info("Page loading timed out.");
        }
    }

    /**
     * Method to check if an element is visible.
     * 
     * @param locator: Playwright Locator object
     * @return true if the element is visible, else false.
     */
    public static boolean isVisible(final Locator locator) {
        try {
            return locator.isVisible();
        } catch (PlaywrightException e) {
            return false;
        }
    }

    /**
     * Method to check if element exists in DOM.
     * 
     * @param locator: Playwright Locator object
     * @return true if element exists in DOM
     */
    public static boolean isPresent(final Locator locator) {
        return locator.count() > 0;
    }

    /**
     * Method to check if text matches with element content.
     * 
     * @param locator: Playwright Locator object
     * @param text: Expected text
     * @return true if text matches, false otherwise
     */
    public static boolean isTextMatching(final Locator locator, final String text) {
        return text.equals(locator.textContent().trim());
    }

    /**
     * Method to wait until element matches text.
     * 
     * @param locator: Playwright Locator object
     * @param text: Expected text
     * @param timeoutInSecs: Timeout in seconds
     */
    public static void waitUntilTextMatches(final Locator locator, final String text, final int timeoutInSecs) {
        try {
            // First make sure the element exists by waiting for it
            locator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutInSecs * 1000));
            
            // Get the element handle from the locator
            ElementHandle elementHandle = locator.elementHandle();
            if (elementHandle == null) {
                LOGGER.error("Failed to get element handle for locator");
                return;
            }
            
            // Escape single quotes in text
            String escapedText = text.replace("'", "\\'");
            
            // Use the elementHandle for the JavaScript function
            Page page = locator.page();
            page.waitForFunction(
                "element => element.textContent.trim() === '" + escapedText + "'",
                elementHandle,
                new Page.WaitForFunctionOptions().setTimeout(timeoutInSecs * 1000)
            );
            
            LOGGER.info("Text matched: '{}'", text);
        } catch (TimeoutError e) {
            // Get actual text for logging
            String actualText = "";
            try {
                actualText = locator.textContent().trim();
            } catch (Exception ex) {
                LOGGER.error("Could not get actual text: {}", ex.getMessage());
            }
            LOGGER.error("Text did not match within timeout period. Expected: '{}', Actual: '{}'", 
                    text, actualText);
        } catch (PlaywrightException e) {
            LOGGER.error("Error during text matching: {}", e.getMessage());
        }
    }

    /**
     * Method to wait until element has text containing substring.
     * 
     * @param locator: Playwright Locator object
     * @param substring: Expected substring
     * @param timeoutInSecs: Timeout in seconds
     */
    public static void waitUntilTextContains(final Locator locator, final String substring, final int timeoutInSecs) {
        try {
            // First make sure the element exists by waiting for it
            locator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutInSecs * 1000));
            
            // Get the element handle from the locator
            ElementHandle elementHandle = locator.elementHandle();
            if (elementHandle == null) {
                LOGGER.error("Failed to get element handle for locator");
                return;
            }
            
            // Escape single quotes in substring
            String escapedText = substring.replace("'", "\\'");
            
            // Use the elementHandle for the JavaScript function
            Page page = locator.page();
            page.waitForFunction(
                "element => element.textContent.includes('" + escapedText + "')",
                elementHandle,
                new Page.WaitForFunctionOptions().setTimeout(timeoutInSecs * 1000)
            );
            
            LOGGER.info("Text contains: '{}'", substring);
        } catch (TimeoutError e) {
            // Get actual text for logging
            String actualText = "";
            try {
                actualText = locator.textContent().trim();
            } catch (Exception ex) {
                LOGGER.error("Could not get actual text: {}", ex.getMessage());
            }
            LOGGER.error("Text does not contain expected substring within timeout period. Expected to contain: '{}', Actual: '{}'", 
                    substring, actualText);
        } catch (PlaywrightException e) {
            LOGGER.error("Error during text contains check: {}", e.getMessage());
        }
    }

    /**
     * Simpler alternative to waitUntilTextMatches that doesn't use JavaScript
     * Can be used as a fallback if the main method has issues
     * 
     * @param locator Playwright Locator object
     * @param expectedText Text to match
     * @param timeoutInSecs Timeout in seconds
     * @return true if text matches, false otherwise
     */
    public static boolean waitForTextSimple(final Locator locator, final String expectedText, final int timeoutInSecs) {
        try {
            long endTime = System.currentTimeMillis() + (timeoutInSecs * 1000);
            
            // First wait for the element to exist
            locator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutInSecs * 1000));
            
            // Then poll until text matches or timeout
            while (System.currentTimeMillis() < endTime) {
                String actualText = locator.textContent().trim();
                if (expectedText.equals(actualText)) {
                    LOGGER.info("Text matched using simple method: '{}'", expectedText);
                    return true;
                }
                // Sleep briefly between checks
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
            
            // Log timeout
            String actualText = locator.textContent().trim();
            LOGGER.error("Text did not match within timeout period (simple method). Expected: '{}', Actual: '{}'", 
                    expectedText, actualText);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error in waitForTextSimple: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Method to click with JavaScript.
     * Useful for elements that are difficult to click normally.
     * 
     * @param locator: Playwright Locator object
     */
    public static void clickWithJavaScript(final Locator locator) {
        try {
            // First make sure the element exists
            locator.waitFor();
            
            // Then try to click it with JS
            locator.evaluate("element => element.click()");
            LOGGER.info("Clicked element using JavaScript");
        } catch (Exception e) {
            LOGGER.error("Failed to click element with JavaScript: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Method to handle dialog with optional text input.
     * 
     * @param page: Playwright Page object
     * @param accept: Whether to accept dialog
     * @param promptText: Text for prompt dialog (can be null)
     */
    public static void handleDialog(final Page page, final boolean accept, final String promptText) {
        page.onDialog(dialog -> {
            if (promptText != null) {
                dialog.accept(promptText);
            } else if (accept) {
                dialog.accept();
            } else {
                dialog.dismiss();
            }
            LOGGER.info("Dialog {} - Type: {}, Message: {}", 
                    accept ? "accepted" : "dismissed", dialog.type(), dialog.message());
        });
    }

    /**
     * Method to force element visibility with JavaScript.
     * Useful for hidden elements that need to be interacted with.
     * 
     * @param locator: Playwright Locator object
     */
    public static void forceElementVisible(final Locator locator) {
        try {
            locator.evaluate(
                "element => { element.style.height='auto'; " +
                "element.style.visibility='visible'; " +
                "element.style.display='block'; " +
                "element.classList.remove('hidden'); }"
            );
            LOGGER.info("Forced element visibility with JavaScript");
        } catch (Exception e) {
            LOGGER.error("Failed to force element visibility: {}", e.getMessage());
        }
    }
    
    /**
     * Method to hover and then click with a small delay between operations.
     * Useful for menus that require hover to activate.
     * 
     * @param locator Playwright Locator object
     */
    public static void clickAfterHoverWithJS(final Locator locator) {
        try {
            // First make sure the element exists
            locator.waitFor();
            
            // Then hover over it
            locator.hover();
            
            // Small delay to let any hover effects take place
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Then click with JavaScript
            clickWithJavaScript(locator);
            
            LOGGER.info("Successfully hovered and clicked element");
        } catch (Exception e) {
            LOGGER.error("Failed in clickAfterHoverWithJS: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Method to retry an operation multiple times with increasing delays.
     * Useful for flaky operations that might succeed on retry.
     * 
     * @param operation The operation to retry
     * @param maxAttempts Maximum number of attempts
     * @param initialDelayMs Initial delay in milliseconds
     * @return true if operation succeeded, false if all attempts failed
     */
    public static boolean retry(Runnable operation, int maxAttempts, int initialDelayMs) {
        int attempt = 0;
        int delay = initialDelayMs;
        
        while (attempt < maxAttempts) {
            try {
                operation.run();
                LOGGER.info("Operation succeeded on attempt {}", attempt + 1);
                return true;
            } catch (Exception e) {
                attempt++;
                LOGGER.warn("Attempt {} failed: {}. Retrying in {}ms", 
                        attempt, e.getMessage(), delay);
                
                if (attempt >= maxAttempts) {
                    LOGGER.error("All {} attempts failed", maxAttempts);
                    return false;
                }
                
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return false;
                }
                
                // Increase delay for next attempt (exponential backoff)
                delay *= 2;
            }
        }
        
        return false;
    }

    /**
     * Method to wait for element to be visible with timeout
     * 
     * @param locator Playwright Locator object
     * @param timeoutInSecs Timeout in seconds
     */
    public static void waitForElement(final Locator locator, final int timeoutInSecs) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutInSecs * 1000));
            LOGGER.info("Element is visible");
        } catch (TimeoutError e) {
            LOGGER.error("Timeout waiting for element");
            throw e;
        }
    }

    /**
     * Method to wait for element with default timeout
     * 
     * @param locator Playwright Locator object
     */
    public static void waitForElement(final Locator locator) {
        waitForElement(locator, DEFAULT_TIMEOUT);
    }

    /**
     * Safely clicks an element with default timeout
     * 
     * @param locator Playwright Locator object to click
     */
    public static void safeClick(final Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT * 1000));
            locator.hover();
            locator.click();
            LOGGER.info("Element clicked successfully");
        } catch (Exception e) {
            LOGGER.warn("Standard click failed, trying JavaScript click as fallback: {}", e.getMessage());
            clickWithJavaScript(locator);
        }
    }

    /**
     * Safely enters text with default timeout
     * 
     * @param locator Playwright Locator object
     * @param text Text to enter
     */
    public static void enterText(final Locator locator, final String text) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT * 1000));
            locator.hover();
            locator.fill(text);
            LOGGER.info("Text entered successfully: '{}'", text);
        } catch (Exception e) {
            LOGGER.error("Failed to enter text: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Gets text with default timeout
     * 
     * @param locator Playwright Locator object
     * @return Element text content
     */
    public static String getText(final Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT * 1000));
            String text = locator.textContent().trim();
            LOGGER.info("Got text: '{}'", text);
            return text;
        } catch (Exception e) {
            LOGGER.error("Failed to get text: {}", e.getMessage());
            throw e;
        }
    }
 
    /**
     * Wait for element to disappear
     *
     * @param locator Element to wait for disappearance
     * @param timeoutInSecs Maximum time to wait in seconds
     * @return true if element disappeared, false if timeout occurred
     */
    public static boolean waitForElementToDisappear(final Locator locator, final int timeoutInSecs) {
        try {
            if (!isVisible(locator)) {
                return true; // Already not visible
            }

            // Use polling approach instead of waitForSelector which has issues with locator strings
            long endTime = System.currentTimeMillis() + (timeoutInSecs * 1000);
            
            while (System.currentTimeMillis() < endTime) {
                if (!isVisible(locator)) {
                    LOGGER.info("Element disappeared successfully");
                    return true;
                }
                
                // Small pause before checking again
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
            
            LOGGER.warn("Element did not disappear within timeout");
            return false;
        } catch (Exception e) {
            LOGGER.warn("Error while waiting for element to disappear: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Wait for element to disappear with default timeout
     *
     * @param locator Element to wait for disappearance
     * @return true if element disappeared, false if timeout occurred
     */
    public static boolean waitForElementToDisappear(final Locator locator) {
        return waitForElementToDisappear(locator, DEFAULT_TIMEOUT);
    }
}