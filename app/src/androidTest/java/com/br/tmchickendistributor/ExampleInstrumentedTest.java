package com.br.tmchickendistributor;

import static org.junit.Assert.*;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import org.junit.*;
import org.junit.runner.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
		@Test
		public void useAppContext() {
				// Context of the app under test.
				Context appContext = InstrumentationRegistry.getTargetContext();

				assertEquals("rafaelpinheiro.ufma.com.br.minasfrango", appContext.getPackageName());
		}
}