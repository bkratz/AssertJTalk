package de.birgitkratz;

import org.assertj.core.configuration.Configuration;

public class AssertJConfiguration extends Configuration {

    @Override
    public int maxStackTraceElementsDisplayed() {
        return 10;
    }

    @Override
    public boolean removeAssertJRelatedElementsFromStackTraceEnabled() {
        return false;
    }
}
